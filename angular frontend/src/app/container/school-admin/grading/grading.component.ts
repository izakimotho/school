
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';

import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';

import { IGrade } from '../../../Shared/Interfaces/school/IGrade'; 
@Component({
   
  selector: 'app-school-grading',
  templateUrl: './grading.component.html',
  styleUrls: ['./grading.component.css']
})
export class GradingComponent implements OnInit {


  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  grade: IGrade = {} as IGrade;
  gradeList: IGrade[];

  grade_id: string;

  gradeForm: FormGroup;
  // Table

  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }

  ngOnInit(): void {
    this.grade_id=''
    this.getGradeList();
  
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      "search": {
        "search": ""
      },
      "ordering": false,
      "info": false,
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
    this.gradeForm = this.fb.group({
      grade_name: ['', [Validators.required]],
      grade_low: ['', [Validators.required]],
      grade_high: ['', [Validators.required]],
      remarks: ['', [Validators.required]], 
    });
    this.gradeForm.reset();
  }
  
  get f() { return this.gradeForm.controls; }

 
  getGradeList() {
    this.ngxLoader.start();
    this.api.get('grades/list').subscribe(
      res => {
        this.ngxLoader.stop();
        this.gradeList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        this.ngxLoader.stop();
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




  openmax(content: any) {
    this.gradeForm.get('grade_name').enable();
    this.gradeForm.get('grade_low').enable();
    this.gradeForm.get('grade_high').enable(); 
    this.gradeForm.get('remarks').enable();  

    this.gradeForm.reset();
    this.modalService.open(content, { centered: true });

     
  }   
  submit($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.gradeForm.invalid) {
      return;
    } else {


       
      this.grade.grade_name = this.gradeForm.get('grade_name').value; 
      this.grade.lower_point= this.gradeForm.get('grade_low').value; 
      this.grade.higher_point = this.gradeForm.get('grade_high').value; 
      this.grade.remarks = this.gradeForm.get('remarks').value; 
 
      this.ngxLoader.start();


      if (this.grade_id) {       
        this.api.put('grades/'+this.grade_id, this.grade)
          .subscribe(
            (response: any) => {
              //this.api.showNotification('Message', response.message, null);

              this.gradeForm.reset();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              this.api.showNotification('Success', 'Grade was  updated successfully.', 'success');
              
            },
            (error: any) => {
              console.log('error ' + JSON.stringify(error));
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            });
      } else {
        this.api.post('grades/create', this.grade)
        .subscribe(
          (response: any) => {
            ///this.api.showNotification('Message', response.message, null);
            this.gradeForm.reset();
            this.modalService.dismissAll();
            this.ngxLoader.stop();
            this.api.showNotification('Success', 'Grade was created successfully.', 'success');
          },
          (error: any) => {
            console.log('error ' + JSON.stringify(error));
            this.modalService.dismissAll();
            this.ngxLoader.stop();
          });
      }
      this.getGradeList();
    }


  }
  viewDetails(classDetails, content) { 
 
    this.gradeForm.get('grade_name').disable();
    this.gradeForm.get('grade_low').disable();
    this.gradeForm.get('grade_high').disable(); 
    this.gradeForm.get('remarks').disable(); 

    if (classDetails.grades_id){
      this.grade_id=classDetails.grades_id;
      this.grade=classDetails;
    }
 
   
    this.modalService.open(content, { centered: true });
  }
  editDetails(classDetails, content) {  
    this.gradeForm.get('grade_name').enable();
    this.gradeForm.get('grade_low').enable();
    this.gradeForm.get('grade_high').enable(); 
    this.gradeForm.get('remarks').enable(); 

    this.modalService.open(content, { centered: true });
  }

  delete_class(Klass: IGrade) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {

        this.ngxLoader.start();
        this.api.delete('grades/' + Klass.grade_id).subscribe(
          res => {
            this.gradeForm.reset(this.gradeForm.value);

            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'grade event record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getGradeList();
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
            this.getGradeList();
            this.ngxLoader.stop();

          }
        );

      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'Cancelled',
          'Your record is safe :)',
          'error'
        )
      }
    })

  }

}
