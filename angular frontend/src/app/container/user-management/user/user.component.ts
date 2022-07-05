import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbDateStruct, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';

import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IUser } from 'src/app/Shared/Interfaces/userprofile/IUser';
import { IRole } from 'src/app/Shared/Interfaces/userprofile/IRole';
@Component({

  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  model: NgbDateStruct;
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;


  user: IUser = {} as IUser;
  userData: IUser = {} as IUser;
  userList: IUser[];

  role: IRole = {} as IRole;
  roleData: IRole = {} as IRole;
  roleList: IRole[];

  // Table

  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }


  ngOnInit(): void {
    this.getUserList();
    this.getRoleList();
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


  }
  /* Date */
  date(e) {
    var convertDate = new Date(e.target.value).toISOString().substring(0, 10);
    // this.staffForm.get('employment_date').setValue(convertDate, {
    //   onlyself: true
    // })
  }

  getUserList() {
    this.api.get('users/list').subscribe(
      res => {
        this.userList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getRoleList() {
    this.api.get("roles").subscribe(
      (res) => {
        this.roleList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      (errResp) => {
        console.error("Error" + errResp);
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
    this.clearForm();
    this.modalService.open(content, { centered: true });
}
clearForm() {
  this.user = null;
}

  assignRole(f) {
    this.user.username = f.form.value.username;  
    this.user.role = {
          role_id: f.form.value.role_id,
      } as IRole;
    
    

    this.ngxLoader.start();
    this.api.post("users/roles/assign", this.user).subscribe(
        (response: any) => {
            this.api.showNotification("Message", response.message, null);
            this.modalService.dismissAll();
            this.getUserList();
            this.ngxLoader.stop();
        },
        (error: any) => {
            console.log("error " + JSON.stringify(error));
            this.modalService.dismissAll();
            this.getUserList();
            this.ngxLoader.stop();
        }
    );
}

  submit_class($event: Event) {
    $event.preventDefault();
    this.submitted = true;
  }
  delete_class(userDetail: IUser) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {
        const class_id = userDetail.id;
        const payload = {
          class_id
        };
        this.ngxLoader.start();
        this.api.delete('subjects/' + userDetail.id + '/delete').subscribe(
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
            this.getUserList();
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
            this.getUserList();
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


