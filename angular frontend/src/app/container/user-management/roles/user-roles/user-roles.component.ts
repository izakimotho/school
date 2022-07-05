import { Component, OnInit, ViewChild } from "@angular/core";
import { NgbDateStruct, NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { DataTableDirective } from "angular-datatables";

import Swal from "sweetalert2";
import { Subject } from "rxjs";
import { IUser } from "src/app/Shared/Interfaces/userprofile/IUser";
import { IRole } from "src/app/Shared/Interfaces/userprofile/IRole";
import { ApiService } from "src/app/Services/api.services";
@Component({
  selector: "app-user-roles",
  templateUrl: "./user-roles.component.html",
  styleUrls: ["./user-roles.component.css"],
})
export class UserRolesComponent implements OnInit {
  model: NgbDateStruct;
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  role: IRole = {} as IRole;
  roleList: IRole[];

  // Table

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {}

  ngOnInit(): void {
    this.getRoleList();

    this.dtOptions = {
      pagingType: "full_numbers",
      pageLength: 10,
      processing: true,
      search: {
        search: "",
      },
      ordering: false,
      info: false,
      dom: "Blfrtip",
      buttons: [
        {
          extend: "copy",
          text: "<u>C</u>opy",
          key: {
            key: "c",
            altKey: true,
          },
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: "csv",
          text: "<u>C</u>sv",
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: "excel",
          text: "<u>E</u>xcel",
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: "pdf",
          text: "<u>P</u>df",
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: "print",
          text: "Print all",
          exportOptions: {
            modifier: {
              selected: null,
            },
          },
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
      ],
      select: true,
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

  submit_class($event: Event) {
    $event.preventDefault();
    this.submitted = true;
  }
  delete_role(roleDetail: IUser) {
    Swal.fire({
      title: "Are you sure?",
      text: "You will not be able to recover from this action!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, delete it!",
      cancelButtonText: "No, keep it",
    }).then((result: any) => {
      if (result.value) {
        const class_id = roleDetail.id;
        const payload = {
          class_id,
        };
        this.ngxLoader.start();
        this.api.delete("subjects/" + roleDetail.id + "/delete").subscribe(
          (res) => {
            // this.myForm.reset(this.myForm.value);

            Swal.fire({
              position: "top-end",
              icon: "info",
              title: "Success...",
              text: "Stream record successfully deleted.!",
              footer: "",
            });
            this.modalService.dismissAll();
            this.getRoleList();
            this.ngxLoader.stop();
          },

          (errResp) => {
            Swal.fire({
              position: "top-end",
              icon: "error",
              title: "Oops...",
              text: "Error : An error has occured. \n Record couldnt be deleted",
              footer: "",
            });
            this.modalService.dismissAll();
            this.getRoleList();
            this.ngxLoader.stop();
          }
        );
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire("Cancelled", "Your record is safe :)", "error");
      }
    });
  }
}
