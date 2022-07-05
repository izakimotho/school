import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';

import { ISchoolLevel, } from '../../../Shared/Interfaces/school/ISchoolLevel';
import { IEducationSystem } from '../../../Shared/Interfaces/school/IEducationSystem';
import { IClasses } from '../../../Shared/Interfaces/school/IClasses';
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IExamType } from 'src/app/Shared/Interfaces/exam/IExamType'; 
@Component({

  selector: 'app-exam_types',
  templateUrl: './exam_types.component.html',
  styleUrls: ['./exam_types.component.css']
})
export class ExamTypesComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  exam_type: IExamType = {} as IExamType;
  exam_typeList: IExamType[];

 
  


  myForm: FormGroup;
  // Table
  
  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {
    
  }
  source: LocalDataSource;
  settings = {
    hideSubHeader: true,
    pager: {
      perPage: 10,
    },
    actions: {
      add: false,
      edit: false,
      delete: false,
    },
    columns: {
      id: {
        title: 'Id',
        filter: true
      },
      img: {
        title: 'Image',
        type: 'html',
        valuePrepareFunction: (img: number) => {
          return `<img src="${img}" alt="img" />`;
        },
        filter: true
      },
      name: {
        title: 'Title',
        filter: true
      },
      location: {
        title: 'Location',
        filter: true
      },
      email: {
        title: "Email I'd",
        filter: true
      },
      product: {
        title: 'Ordered Item',
        filter: true
      },
      price: {
        title: 'Bill',
        type: 'html',
        valuePrepareFunction: (price: number) => {
          return `<span>$${new Intl.NumberFormat().format(price)}</span>`;
        },
        filter: true
      }
    }
  };
 

  ngOnInit(): void {
    this.getExamTypeList();
    
     
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
  
    this.myForm = this.fb.group({
      exam_type_name: ['', [Validators.required]],
      description: ['', [Validators.required]], 
    });
    this.myForm.reset();
  }
 
  get f() { return this.myForm.controls; }

  getExamTypeList() {
    this.api.get('exam_types').subscribe(
      res => {
        this.exam_typeList = res.result;
         // ADD THIS
        this.dtTrigger.next();
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




  openmax(content: any) {
    this.myForm.get('exam_type_name').enable();
    this.myForm.get('description').enable(); 
    this.myForm.reset();
    this.modalService.open(content, { centered: true});
  }
  submit($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.myForm.invalid) {
      return;
    } else {

      
      this.exam_type.exam_type_name = this.myForm.get('exam_type_name').value;
      this.exam_type.description = this.myForm.get('description').value; 

      this.ngxLoader.start();
      this.api.post('exam_type/create', this.exam_type)
        .subscribe(
          (response: any) => {
            this.api.showNotification('Message', response.message, null);            
            this.modalService.dismissAll();
            this.ngxLoader.stop();
          },
          (error: any) => {
            console.log('error ' + JSON.stringify(error));            
            this.modalService.dismissAll();
            this.ngxLoader.stop();
          });
      this.myForm.reset();
      this.api.showNotification('Success', 'Class created successfully.', 'success');
      
    this.getExamTypeList();
    }


  }
  viewDetails(classDetails, content) {
    this.exam_type = null;
    this.exam_type = classDetails; 
     
    this.myForm.get('exam_type_name').disable();
    this.myForm.get('description').disable(); 

    this.modalService.open(content, { centered: true });
  }
  editDetails(exam_typeDetails, content) {
    this.exam_type = null;
    this.exam_type = exam_typeDetails;
     
    this.myForm.get('stream_name').enable();
    this.myForm.get('description').enable(); 

 
    this.modalService.open(content, { centered: true });
  }

  delete_class(examType: IExamType) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {       
        const class_id=examType.exam_type_id;
        const payload = {
          class_id 
        };
        this.ngxLoader.start();
       this.api.delete('schools/streams/'+examType.exam_type_id ).subscribe(
          res => {
            this.myForm.reset(this.myForm.value);
             
            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'Stream record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getExamTypeList();
            this.ngxLoader.stop();
           
          },
          
          errResp => {
  
           
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: 'Oops...',
              text: 'Error : An error has occured. \n Record couldnt be deleted' ,
              footer: ''
            });
            this.modalService.dismissAll();
            this.getExamTypeList();
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
