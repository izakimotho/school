import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ActivatedRoute } from '@angular/router';

import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader'; 
import { NotificationServices } from '../../../../Services/notification.services';
import { ApiService } from '../../../../Services/api.services';
import { IExam } from '../../../../Shared/Interfaces/exam/IExam';
import { FormGroup } from '@angular/forms';
import { IGradePosting } from 'src/app/Shared/Interfaces/exam/IGradePosting';
import { IStudent } from 'src/app/Shared/Interfaces/student/IStudent';

@Component({
    selector: 'app-view-class-exams-posted',
    templateUrl: './view-class-exams-posted.component.html',
    styleUrls: ['./view-class-exams-posted.component.css']
})
export class ViewClassPostedExamComponent implements OnInit {
    spinnerType = SPINNER.circle;
    dtOptions: any = {};
    submitted = false;
    exam_id: any;
    exam: IExam = {} as IExam;

    studentsGrades: IGradePosting[];
    studentGrade: IGradePosting = {} as IGradePosting;
    student: IStudent = {} as IStudent;
    students: IStudent[];


    gradePosting: IGradePosting = {} as IGradePosting;
    myForm: FormGroup;
    constructor(
        private ngxLoader: NgxUiLoaderService,
        private activatedRoute: ActivatedRoute,
        private notification: NotificationServices,
        private modalService: NgbModal,
        private api: ApiService,
    ) {
        this.exam_id = this.activatedRoute.snapshot.params.exam_id;
        console.log(this.exam_id);
    }

    ngOnInit(): void {
        this.getExam();
        this.getStudents();
        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            processing: true,
            'search': {
                'search': ''
            },
            'ordering': false,
            'info': false,
            dom: 'Blfrtip',
            buttons: [
                {
                    extend: 'copy',
                    text: '<u>C</u>opy',
                    key: {
                        key: 'c',
                        altKey: true
                    },
                    className: 'btn btn-sm btn-pill btn-outline-light'
                },
                {
                    extend: 'csv',
                    text: '<u>C</u>sv',
                    className: 'btn btn-sm btn-pill btn-outline-light'
                },
                {
                    extend: 'excel',
                    text: '<u>E</u>xcel',
                    className: 'btn btn-sm btn-pill btn-outline-light'
                },
                {
                    extend: 'pdf',
                    text: '<u>P</u>df',
                    className: 'btn btn-sm btn-pill btn-outline-light'
                },
                {
                    extend: 'print',
                    text: 'Print all',
                    exportOptions: {
                        modifier: {
                            selected: null
                        }
                    },
                    className: 'btn btn-sm btn-pill btn-outline-light'
                }


            ],
            select: true,
            language: {
                paginate: {
                    first: '«',
                    previous: '‹',
                    next: '›',
                    last: '»'
                },
                aria: {
                    paginate: {
                        first: 'First',
                        previous: 'Previous',
                        next: 'Next',
                        last: 'Last'
                    }
                }
            }
        };


       
            // //datatable added with settings
            //  setTimeout(()=>{   
            //    $('#datatableStudents').DataTable( { 
            //        data: this.students,   
            //        pagingType: 'full_numbers',
            //        pageLength: 10,
            //        processing: true,
            //        'search': {
            //            'search': ''
            //        },
            //        'ordering': false,
            //        'info': false,
            //        dom: 'Blfrtip'
                    
            //   } 
              
              
            //   );
            //    }, 1);

    }
  
    private getStudents() {
        //students/exams/89fada16-2101-41d7-a369-cebded24cd6c
        this.ngxLoader.start();
        this.studentsGrades=[];
        this.api.get('grade_posting/' + this.exam_id+'/exam_schedule').subscribe(
            res => {
                this.studentsGrades = res.result;
                // for  (var stude in this.students){
                //     var stud : IGradePosting = {} as IGradePosting;

                //     stud.student=  this.students[stude]; 
                //     console.log('Student   ...  ' + JSON.stringify(stud));
                //     // this.students.push(
                        
                //     //     stude.student_id,
                //     //     staff_id: string;
                //     //     first_name: string;
                //     //     middle_name: string;
                //     //     surname: string;  
                //     //     gradePosting:IGradePosting  
                        
                //     //     this.studentZ,this.gradePosting=null)

                //     this.studentsGrades.push(stud);
                // }

                //this.students = this.studentZ; 
                
                
                
                //console.log('this.students   ...  ' + JSON.stringify(this.studentsGrades));
                this.ngxLoader.stop();
            },
            errResp => {
                console.error('Error' + errResp);
                this.ngxLoader.stop();

            }
        );
    }
    
    private getExam() {
        this.api.get('exam_schedule/' + this.exam_id).subscribe(
            res => {
                this.exam = res.result;
            },
            errResp => {
                console.error('Error' + errResp);

            }
        );
    }

   
}
