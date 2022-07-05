import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';

import { ISchoolLevel, } from './../../../Shared/Interfaces/school/ISchoolLevel';
import { IEducationSystem } from './../../../Shared/Interfaces/school/IEducationSystem';
import { IClasses } from './../../../Shared/Interfaces/school/IClasses';
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service'; 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { ISchoolStream } from 'src/app/Shared/Interfaces/school/ISchoolStream';
@Component({
 
  selector: 'app-school-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  school_stream: ISchoolStream = {} as ISchoolStream;
  school_streamList: ISchoolStream[];

  classList: IClasses[];
  class: IClasses = {} as IClasses; 


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
    this.getStreamList();
    this. getClassList();
     
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
  
    this.myForm = this.fb.group({
      stream_name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      abbr: ['', [Validators.required]], 
    });
    this.myForm.reset();
  }
 
  get f() { return this.myForm.controls; }

  getStreamList() {
    this.api.get('schools/streams').subscribe(
      res => {
        this.school_streamList = res.result;
         // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }
  getClassList() { 
    this.api.get('classes/filter').subscribe(
      res => { 
        this.classList = res.result; 
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
    this.myForm.get('stream_name').enable();
    this.myForm.get('description').enable();
    this.myForm.get('abbr').enable();
    this.myForm.reset();
    this.modalService.open(content, { centered: true});
  }
  submit($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.myForm.invalid) {
      return;
    } else {

      
      this.school_stream.stream_name = this.myForm.get('stream_name').value;
      this.school_stream.description = this.myForm.get('description').value;
      this.school_stream.abbr = this.myForm.get('abbr').value; 

      this.ngxLoader.start();
      this.api.post('schools/streams/create', this.school_stream)
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
      
    this.getStreamList();
    }


  }
  viewDetails(classDetails, content) {
    this.school_stream = null;
    this.school_stream = classDetails;
     console.log(this.school_stream);
     
    this.myForm.get('stream_name').disable();
    this.myForm.get('description').disable();
    this.myForm.get('abbr').disable(); 

    this.modalService.open(content, { centered: true });
  }
  editDetails(classDetails, content) {
    this.school_stream = null;
    this.school_stream = classDetails;
     
    this.myForm.get('stream_name').enable();
    this.myForm.get('description').enable();
    this.myForm.get('abbr').enable(); 

 
    this.modalService.open(content, { centered: true });
  }

  delete_class(Klass: ISchoolStream) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {       
        const class_id=Klass.school_stream_id;
        const payload = {
          class_id 
        };
        this.ngxLoader.start();
       this.api.delete('schools/streams/'+Klass.school_stream_id ).subscribe(
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
            this.getStreamList();
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
            this.getStreamList();
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
