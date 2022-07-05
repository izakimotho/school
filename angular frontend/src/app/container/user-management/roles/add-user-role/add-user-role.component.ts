import { Component, OnInit, ViewChild } from "@angular/core";
import { NgbDateStruct, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { DataTableDirective } from "angular-datatables";

import Swal from "sweetalert2";
import { Subject } from "rxjs";
import { IUser } from "src/app/Shared/Interfaces/userprofile/IUser";
import { IRole } from "src/app/Shared/Interfaces/userprofile/IRole";
import { ApiService } from "src/app/Services/api.services";
import { IPermission } from "src/app/Shared/Interfaces/userprofile/IPermission";
import { NavigationExtras, Router } from "@angular/router";
@Component({
  selector: 'app-add-user-role',
  templateUrl: './add-user-role.component.html',
  styleUrls: ['./add-user-role.component.css']
})
export class AddUserRoleComponent implements OnInit {
  model: NgbDateStruct;
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  permission: IPermission = {} as IPermission;
  permissionList: IPermission[];
  role:IRole = {} as IRole;
  
  permissionDataList: IPermission[];
  gridCheckAll: boolean = false;

  checkedPermissionIds: number[] = [];
  uncheckedPermissionIds: number[] = [];

  // Table

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private router: Router,
    private api: ApiService
  ) {}

  ngOnInit(): void {
    this.getPermissionList();

    this.dtOptions = {
      pagingType: "full_numbers",
      pageLength: 10,
      processing: true,
      search: {
        search: "",
        className: "ms-form-input w-100"
      },       
       select: {
        style: 'os',
        selector: 'td:not(:last-child)' // no row selection on last column
    },
      ordering: false,
      info: false,
      dom: "lfrtip",
     // select: true,
      language: {
        paginate: {
          first: "«",
          previous: "‹",
          next: "›",
          last: "»",
        },
        aria: {
          paginate: {
            first: "First",
            previous: "Previous",
            next: "Next",
            last: "Last",
          },
        },
      },
    };
  
  }

  getPermissionList() {
    this.api.get("permissions").subscribe(
      (res) => {
        this.permissionList = res.result;      
        // ADD THIS
        this.dtTrigger.next();
      },
      (errResp) => {
        console.error("Error" + errResp);
      }
    );


    // this.permissionList.forEach(permission => {
    //   permission.checked = this.isPermissionChecked(permission.permission_id);
    // });
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }


  gridAllRowsCheckBoxChecked(e) {
    if (this.gridCheckAll) {
      this.uncheckedPermissionIds.length = 0;
      this.gridCheckAll = false;

    }
    else {
      this.checkedPermissionIds.length = 0;
      this.gridCheckAll = true;
    }
  }

  rowCheckBoxChecked(e, PermissionId) {

    if (e.currentTarget.checked) {
      this.uncheckedPermissionIds.splice(this.uncheckedPermissionIds.indexOf(PermissionId), 1);
      if (!this.gridCheckAll)
        this.checkedPermissionIds.push(PermissionId);
    }
    else {
      this.checkedPermissionIds.splice(this.checkedPermissionIds.indexOf(PermissionId), 1);
      if (this.gridCheckAll)
        this.uncheckedPermissionIds.push(PermissionId);
    }

  }

  private isPermissionChecked(PermissionId) {
    if (!this.gridCheckAll) {
      return this.checkedPermissionIds.indexOf(PermissionId) >= 0 ? true : false;
    }
    else {
      return this.uncheckedPermissionIds.indexOf(PermissionId) >= 0 ? false : true;
    }
  }

  addRole(f) {
    this.ngxLoader.start();
    this.role.role_name = f.form.value.role_name; 
    this.role.description = f.form.value.description; 
    this.role.permissions=[];
    console.log(JSON.stringify(this.checkedPermissionIds));
      if (this.checkedPermissionIds.length>0){
        for (let i = 0; i < this.checkedPermissionIds.length; i++) {
          // console.log(this.checkedPermissionIds[i]);
            var permi:IPermission;

            permi = {
              permission_id: this.checkedPermissionIds[i],
              } as IPermission;

            this.role.permissions.push(permi);
          }


          console.log(JSON.stringify( this.role.permissions))
      }
   
  
    this.api.post("roles/create", this.role).subscribe(
        (response: any) => {
            this.api.showNotification("Message", response.message, null);
            this.modalService.dismissAll(); 
            this.ngxLoader.stop();
            this.close()
        },
        (error: any) => {
            console.log("error " + JSON.stringify(error));
            this.modalService.dismissAll(); 
            this.ngxLoader.stop();
        }
    );


    // this.ngxLoader.stop();
}
close(){
  var redirectUrl = "";
  redirectUrl = '/user/roles';

  // Set our navigation extras object
  // that passes on our global query params and fragment
  const navigationExtras: NavigationExtras = {
    queryParamsHandling: 'preserve',
    preserveFragment: true
  };

  this.ngxLoader.stopAll();
  // Redirect the user
  this.router.navigate([redirectUrl], navigationExtras);


}
 
}
