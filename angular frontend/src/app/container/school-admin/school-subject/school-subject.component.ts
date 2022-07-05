import { Component, OnInit, ViewChild } from '@angular/core';

import { FormBuilder, FormGroupDirective } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';

import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service'; 
import { ISchoolSubjects } from 'src/app/Shared/Interfaces/school/ISchoolSubjects';
import { ISubject } from 'src/app/Shared/Interfaces/school/ISubjects';

@Component({

    selector: 'app-school-subject',
    templateUrl: './school-subject.component.html',
    styleUrls: ['./school-subject.component.css']
})
export class SchoolSubjectComponent implements OnInit {
    @ViewChild(FormGroupDirective) _fgDirective: FormGroupDirective;

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    dtTrigger: Subject<any> = new Subject();
    dtOptions: any = {};
    submitted = false;
    isedit: boolean = true;
    spinnerType = SPINNER.circle;
    subject_id: string;

    
    subjectList: ISubject[];
    subject: ISubject = {} as ISubject;
    mySubject: ISchoolSubjects = {} as ISchoolSubjects;
    mySubjectList: ISchoolSubjects[];



    constructor(private fb: FormBuilder,
        private auth: AuthService, private ngxLoader: NgxUiLoaderService,
        private modalService: NgbModal,
        private api: ApiService
    ) {

    }


    ngOnInit(): void {
        this.getSubjectList(); 
        this.getMySubjectList();
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
                    className: 'btn btn-sm btn-neutral mb-2 p-3'
                },
                {
                    extend: 'csv',
                    text: '<u>C</u>sv',
                    className: 'btn btn-sm btn-neutral mb-2 p-3'
                },
                {
                    extend: 'excel',
                    text: '<u>E</u>xcel',
                    className: 'btn btn-sm btn-neutral mb-2 p-3'
                },
                {
                    extend: 'pdf',
                    text: '<u>P</u>df',
                    className: 'btn btn-sm btn-neutral mb-2 p-3'
                },
                {
                    extend: 'print',
                    text: 'Print all',
                    exportOptions: {
                        modifier: {
                            selected: null
                        }
                    },
                    className: 'btn btn-sm btn-neutral mb-2 p-3'
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
    }

    getSubjectList() {
        this.ngxLoader.start();
        this.subjectList = [];
        this.api.get('subjects').subscribe(
            res => {
                this.ngxLoader.stop();
                this.subjectList = res.result;
                // ADD THIS
                // this.dtTrigger.next();
            },
            errResp => {
                this.ngxLoader.stop();
                console.error('Error' + errResp);

            }
        );
    }
    getMySubjectList() {
        this.subjectList = [];
        this.api.get('school_subjects').subscribe(
            res => {
                this.mySubjectList = res.result;
            },
            errResp => {
                console.error('Error' + errResp);

            }
        );
    }
    
    rerender(): void {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            // Destroy the table first
            dtInstance.destroy();
            // Call the dtTrigger to rerender again
            this.dtTrigger.next();
        });
    }
    clearForm() {
        // this.mySubject.subject_name = '';
    }

    openmax(content: any) {
        this.clearForm();
        this.modalService.open(content, { centered: true });
    }

    addSubject(f) {
        this.ngxLoader.start(); 
        
        this.mySubject.subject = {
            subject_id: f.form.value.subject_id,
          } as ISubject;

        this.ngxLoader.start();
        this.api.post('school_subject', this.mySubject)
            .subscribe(
                (response: any) => {
                    this.api.showNotification('Message', response.message, null);
                    this.modalService.dismissAll();
                    this.getMySubjectList();
                    this.ngxLoader.stop();

                },
                (error: any) => {
                    console.log('error ' + JSON.stringify(error));
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();
                });
    }

    editSubject(f) {
        this.ngxLoader.start();       
        this.mySubject.subject = {
            subject_id: f.form.value.subject_id,
          } as ISubject;

        this.ngxLoader.start();
        this.api.put('school_subject/update', this.mySubject)
            .subscribe(
                (response: any) => {
                    this.api.showNotification('Message', response.message, null);
                    this.modalService.dismissAll();
                    this.getMySubjectList();
                    this.ngxLoader.stop();
                },
                (error: any) => {
                    console.log('error ' + JSON.stringify(error));
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();
                });
    }

    viewDetails(subjectDetails, content) {
        this.mySubject = null;
        this.mySubject = subjectDetails;
         
        this.modalService.open(content, { centered: true });
    }

    editDetails(subjectDetails, content) {
        this.mySubject = null;
        this.mySubject = subjectDetails;
        
        this.modalService.open(content, { centered: true });
    }

    delete_class(subjectDetail: ISchoolSubjects) {
        Swal.fire({
            title: 'Are you sure?',
            text: 'You will not be able to recover from this action!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, keep it'
        }).then((result: any) => {
            if (result.value) {
                const class_id = subjectDetail.school_subject_id;
                const payload = {
                    class_id
                };
                this.ngxLoader.start();
                this.api.delete('school_subject/' + subjectDetail.school_subject_id + '/delete').subscribe(
                    res => {
                        // this.myForm.reset(this.myForm.value);

                        Swal.fire({
                            position: 'top-end',
                            icon: 'info',
                            title: 'Success...',
                            text: 'Stream record successfully deleted.!',
                            footer: ''
                        });
                        this.modalService.dismissAll();
                        this.getMySubjectList();
                        this.ngxLoader.stop();

                    },

                    errResp => {


                        Swal.fire({
                            position: 'top-end',
                            icon: 'error',
                            title: 'Oops...',
                            text: 'Error : An error has occured. \n Record couldnt be deleted',
                            footer: ''
                        });
                        this.modalService.dismissAll();
                        this.getMySubjectList();
                        this.ngxLoader.stop();

                    }
                );

            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire(
                    'Cancelled',
                    'Your record is safe :)',
                    'error'
                );
            }
        });

    }

}
