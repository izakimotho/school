import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';

import { IHostel } from '../../../../Shared/Interfaces/school/IHostel';
import { ApiService } from '../../../../Services/api.services';
import { AuthService } from '../../../../Services/auth.service';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IStaff } from '../../../../Shared/Interfaces/school/IStaff';
import { IPosition } from '../../../../Shared/Interfaces/school/IPositions';
@Component({

  selector: 'app-school-staff',
  templateUrl: './staff.component.html',
  styleUrls: ['./staff.component.css']
})
export class StaffComponent implements OnInit {
  model: NgbDateStruct;
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;


  staff: IStaff = {} as IStaff;
  staffList: IStaff[];

  position: IPosition = {} as IPosition;
  positions: IPosition[];


  staffForm: FormGroup;
  myForm: FormGroup;
  // Table

  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }


  ngOnInit(): void {
    this.getstaffList();

    this.getPositions();
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

    this.staffForm = this.fb.group({
      first_name: ['', [Validators.required]],
      middle_name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      gender: ['', [Validators.required]],
      title: ['', [Validators.required]],
      phone_number: ['', [Validators.required, Validators.minLength(10)]],
      positions: ['', [Validators.required]],
      marital_status: ['', [Validators.required]],
      spouse_name: [''],
      employment_date: ['', [Validators.required]],

    });
    this.staffForm.reset();

    this.myForm = this.fb.group({
      first_name: ['', [Validators.required]],
      middle_name: ['', [Validators.required]],
      surname: ['', [Validators.required]],
      title: ['', [Validators.required]],
      // gender: ['', [Validators.required]],
      // email: ['', [Validators.required, Validators.email]],
      // phone_number: ['', [Validators.required, Validators.minLength(10)]],
      // positions: ['', [Validators.required]],
      // marital_status: ['', [Validators.required]],
      // spouse_name: ['', [Validators.required]],
      // employment_date: ['', [Validators.required]],
      class_name: ['', [Validators.required]],
      description: ['', [Validators.required]], 
    });
    this.myForm.reset();
  }
  get g() { return this.myForm.controls; }
  get f() { return this.staffForm.controls; }
 /* Date */
 date(e) {
  var convertDate = new Date(e.target.value).toISOString().substring(0, 10);
  this.staffForm.get('employment_date').setValue(convertDate, {
    onlyself: true
  })
}

  getstaffList() {
    this.api.get('staff/list').subscribe(
      res => {
        this.staffList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getPositions() {
    this.api.get('positions/list').subscribe(
      res => {
        this.positions = res.result;
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
    this.staffForm.get('first_name').enable();
    this.staffForm.get('middle_name').enable();
    this.staffForm.get('surname').enable();
    this.staffForm.get('email').enable();
    this.staffForm.get('gender').enable();
    this.staffForm.get('title').enable();
    this.staffForm.get('phone_number').enable();
    this.staffForm.get('spouse_name').enable();
    this.staffForm.get('marital_status').enable();
    this.staffForm.get('employment_date').enable();
    this.staffForm.reset();
    this.modalService.open(content, {size: 'lg', centered: true });
  }
  submit($event: Event) {
    $event.preventDefault();
    this.submitted = true;
    if (this.staffForm.invalid) {

      alert('Please Error sort ')
      return;
    } else {
alert('Please save me ')
      this.staff.first_name = this.staffForm.get('first_name').value;
      this.staff.middle_name = this.staffForm.get('middle_name').value;
      this.staff.surname = this.staffForm.get('surname').value;
      this.staff.email = this.staffForm.get('email').value;
      this.staff.gender = this.staffForm.get('gender').value;
      this.staff.title = this.staffForm.get('title').value;
      this.staff.phone_number = this.staffForm.get('phone_number').value;
      this.staff.spouse_name = this.staffForm.get('spouse_name').value;
      this.staff.marital_status = this.staffForm.get('marital_status').value;
      this.staff.employment_date = this.staffForm.get('employment_date').value;
      this.staff.positions = this.staffForm.get('position').value;




      this.ngxLoader.start();
      this.api.post('staff/create', this.staff)
        .subscribe(
          (response: any) => {
            this.api.showNotification('Message', response.message, null)
            this.ngxLoader.stop();
          },
          (error: any) => {
            console.log('error ' + JSON.stringify(error));
            this.ngxLoader.stop();
          });
      this.staffForm.reset();
      this.formReset();
      this.api.showNotification('Success', 'Staff created successfully.', 'success');

      this.getstaffList();
    }


  }

  formReset() {
    // Reset all form data
    this.staffForm.reset();
    this.staff=new IStaff();
    this.staffForm.setValue({
      first_name: '',
      middle_name: '',
      surname: '',
      email: '',
      gender: '',
      title: '',
      phone_number: '',
      positions: '',
      marital_status: '',
      spouse_name: '',
      employment_date: '',
    });

    return false; // Prevent page refresh
  }
  viewDetails(staffDetails, content) {
    this.staff = null;
    this.staff = staffDetails;
    console.log(this.staff);

    this.staffForm.get('first_name').disable();
    this.staffForm.get('middle_name').disable();
    this.staffForm.get('surname').disable();
    this.staffForm.get('email').disable();
    this.staffForm.get('gender').disable();
    this.staffForm.get('title').disable();
    this.staffForm.get('phone_number').disable();
    this.staffForm.get('spouse_name').disable();
    this.staffForm.get('marital_status').disable();
    this.staffForm.get('employment_date').disable();
    // this.staffForm.get('position').disable();




    this.modalService.open(content, {size: 'md', centered: true });
  }
  editDetails(staffDetails, content) {
    this.staff = null;
    this.staff = staffDetails;
    console.log(this.staff);

    this.staffForm.get('first_name').enable();
    this.staffForm.get('middle_name').enable();
    this.staffForm.get('surname').enable();
    this.staffForm.get('email').enable();
    this.staffForm.get('gender').enable();
    this.staffForm.get('title').enable();
    this.staffForm.get('phone_number').enable();
    this.staffForm.get('spouse_name').enable();
    this.staffForm.get('marital_status').enable();
    this.staffForm.get('employment_date').enable();
     
    this.modalService.open(content, { size: 'xl',centered: true });
  }

  delete_class(Klass: IHostel) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {
        const class_id = Klass.hostel_id;
        const payload = {
          class_id
        };
        this.ngxLoader.start();
        this.api.post('staff/delete', payload).subscribe(
          res => {
            this.staffForm.reset(this.staffForm.value);

            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'Class record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getstaffList();
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
            this.getstaffList();
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
  genders = [
    { 'name': 'Male' },
    { 'name': 'Female' }
  ];
  marital_status_list = [
    { 'name': 'Single' },
    { 'name': 'Married' },
    { 'name': 'Separated' },
    { 'name': 'Divorced' },
    { 'name': 'Widowed' }
  ];
  title_list = [
    { 'name': 'Mr' },
    { 'name': 'Mrs' },
    { 'name': 'Miss' },
    { 'name': 'Ms' },
    { 'name': 'Mx' },
    { 'name': 'Sir' },
    { 'name': 'Dr' },
    { 'name': 'Lady' }
  ];

  openDialog(content: any) { 
    this.myForm.reset();
    this.modalService.open(content, { centered: true});
  }
  submit_class($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.myForm.invalid) {
      return;
    } else {

      this.ngxLoader.start();
      console.log(this.myForm.get('class_name').value)
      console.log(this.myForm.get('class_name').value)
      this.api.showNotification('Message', this.myForm.get('class_name').value, null)
      this.ngxLoader.stop();

     
     
    }


  }
}


