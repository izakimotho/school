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
@Component({

  selector: 'app-classes',
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
  class: IClasses = {} as IClasses;
  classData: IClasses = {} as IClasses;

  educationSystem: IEducationSystem = {} as IEducationSystem;
  educationSystems: IEducationSystem[];
  schoolLevel: ISchoolLevel = {} as ISchoolLevel;
  schoolLevels: ISchoolLevel[];
  classList: IEducationSystem[];


  myForm: FormGroup;
  // Table

  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }


  ngOnInit(): void {
    this.getClassList();
    this.getEducationSystems();
    this.getSchoolLevels();
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
      class_name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      school_level: ['', [Validators.required]],
      education_system: ['', [Validators.required]],
    });
    this.myForm.reset();
  }

  get f() { return this.myForm.controls; }

  getClassList() {
    this.ngxLoader.start();
    this.api.get('classes/list').subscribe(
      res => {
        this.ngxLoader.stop();
        this.classList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        this.ngxLoader.stop();
        console.error('Error' + errResp);

      }
    );
  }

  getEducationSystems() {
    this.api.get('education_system/list').subscribe(
      res => {
        this.educationSystems = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getSchoolLevels() {
    this.api.get('schools/levels').subscribe(
      res => {
        this.schoolLevels = res.result;
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
    this.myForm.get('class_name').enable();
    this.myForm.get('description').enable();
    this.myForm.get('school_level').enable();
    this.myForm.get('education_system').enable();
    this.myForm.reset();
    this.modalService.open(content, { centered: true });
  }

  submit(f) {
    this.ngxLoader.start();
    this.classData.class_name = f.form.value.class_name;
    this.classData.description = f.form.value.description;
    this.classData.education_system =  {education_system_id: f.form.value.education_system_id} as IEducationSystem;
    this.classData.school_level =  {school_level_id: f.form.value.school_level_id} as ISchoolLevel;
    this.ngxLoader.start();
    this.api.post( 'classes/create', this.classData)
        .subscribe(
            (response: any) => {
              this.api.showNotification('Message', response.message,null);
              this.getClassList();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            },
            (error: any) => {
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              console.log('error ' + JSON.stringify(error));
            });

  }

  editClass(f) {
    this.ngxLoader.start();
    let payLoad: any = {};
    payLoad.class_id = this.class.class_id;
    payLoad.class_name = f.form.value.class_name;
    payLoad.description = f.form.value.description;
    payLoad.education_system =  {education_system_id: f.form.value.education_system_id} as IEducationSystem;
    payLoad.school_level =  {school_level_id: f.form.value.school_level_id} as ISchoolLevel;
    this.ngxLoader.start();
    this.api.put( 'classes/update', payLoad)
        .subscribe(
            (response: any) => {
              this.api.showNotification('Message', response.message,null);
              this.getClassList();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            },
            (error: any) => {
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              console.log('error ' + JSON.stringify(error));
            });

  }

  viewDetails(classDetails, content) {
    this.class = null;
    this.class = classDetails;


    // if (classDetails.class_name){
    //   this.isedit = true; 
    // }else{
    //   this.isedit = false;

    // }

    this.myForm.get('class_name').disable();
    this.myForm.get('description').disable();
    this.myForm.get('school_level').disable();
    this.myForm.get('education_system').disable();
    this.educationSystem = classDetails.education_system;
    this.modalService.open(content, { centered: true });
  }
  editDetails(classDetails, content) {
    this.class = null;
    this.class = classDetails;

    this.myForm.get('class_name').enable();
    this.myForm.get('description').enable();
    this.myForm.get('school_level').enable();
    this.myForm.get('education_system').enable();
    this.modalService.open(content, { centered: true });
  }

  delete_class(Klass: IClasses) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {
        const class_id = Klass.class_id;
        const payload = {
          class_id
        };
        this.ngxLoader.start();
        this.api.delete('classes/' + Klass.class_id + '/delete').subscribe(
          res => {
            this.myForm.reset(this.myForm.value);

            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'Class record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getClassList();
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
            this.getClassList();
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
