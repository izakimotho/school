import { Component, Input, OnInit, ViewChild } from "@angular/core";
import { LocalDataSource } from "ng2-smart-table";

import {
    FormBuilder,
    FormControl,
    FormGroup,
    FormGroupDirective,
    Validators,
} from "@angular/forms";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { DataTableDirective } from "angular-datatables";
import Swal from "sweetalert2";
import { Subject } from "rxjs";

import { ApiService } from "../../../Services/api.services";
import { AuthService } from "../../../Services/auth.service";

import { IStaff } from "../../../Shared/Interfaces/school/IStaff";
import { IHostel } from "../../../Shared/Interfaces/school/IHostel";
import { IStudent } from "src/app/Shared/Interfaces/student/IStudent";
import { NotificationServices } from "src/app/Services/notification.services";

@Component({
    selector: "app-hostel",
    templateUrl: "./hostel.component.html",
    styleUrls: ["./hostel.component.css"],
})
export class HostelComponent implements OnInit {
    @ViewChild(FormGroupDirective) _fgDirective: FormGroupDirective;

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    dtTrigger: Subject<any> = new Subject();
    dtOptions: any = {};
    submitted = false;
    isedit: boolean = true;
    spinnerType = SPINNER.circle;
    hostel_id: string;

    hostel: IHostel = {} as IHostel;
    hostelData: IHostel = {} as IHostel;
    hostelList: IHostel[];

    staff: IStaff = {} as IStaff;
    hostel_master: IStaff = {} as IStaff;
    staffs: IStaff[];

    student: IStudent = {} as IStudent;
    hostel_captain: IStudent;
    students: IStudent[];

    constructor(
        private fb: FormBuilder,
        private ngxLoader: NgxUiLoaderService,
        private notification: NotificationServices,
        private modalService: NgbModal,
        private api: ApiService
    ) { }

    ngOnInit(): void {
        this.getHostelList();
        this.getStudents();
        this.getStaff();
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

    getHostelList() {
        this.ngxLoader.start();
        this.hostelList = [];
        this.api.get("hostel/list").subscribe(
            (res) => {
                this.ngxLoader.stop();
                this.hostelList = res.result;
                // ADD THIS
            },
            (errResp) => {
                this.ngxLoader.stop();
                console.error("Error" + errResp);
            }
        );
    }

    getStaff() {
        this.staffs = [];
        this.api.get("staff/list").subscribe(
            (res) => {
                this.staffs = res.result;
            },
            (errResp) => {
                this.ngxLoader.stop();
                console.error("Error" + errResp);
            }
        );
    }

    getStudents() {
        this.api.get("students/list").subscribe(
            (res) => {
                this.students = res.result;
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
    clearForm() {
        this.hostelData.description = "";
        this.hostelData.hostel_capacity = 0;
        this.hostelData.description = "";
    }

    openmax(content: any) {
        this.clearForm();
        this.modalService.open(content, { centered: true });
    }

    addHostel(f) {
        this.ngxLoader.start();
        this.hostel.hostel_name = f.form.value.hostel_name;
        this.hostel.description = f.form.value.description;
        this.hostel.hostel_master = f.form.value.hostel_master;
        this.hostel.hostel_captain = f.form.value.hostel_captain;
        this.hostel.hostel_capacity = f.form.value.hostel_capacity;

        if ( f.form.value.student_id == !undefined &&  f.form.value.student_id == !null ) {
            this.hostelData.hostel_captain = {  student_id: f.form.value.student_id, } as IStudent;
          }else{
            this.hostelData.hostel_captain =null;
          }

          if ( f.form.value.staff_id == !undefined &&  f.form.value.staff_id == !null ) {
            this.hostelData.hostel_master = {  staff_id: f.form.value.staff_id, } as IStaff;
          }else{
            this.hostelData.hostel_master =null;
          }

      console.log(this.hostelData);

        this.ngxLoader.start();
        this.api.post("hostel/create", this.hostelData).subscribe(
            (response: any) => {
                this.api.showNotification("Message", response.message, null);
                this.modalService.dismissAll();
                this.getHostelList();
                this.ngxLoader.stop();
            },
            (error: any) => {
                console.log("error " + JSON.stringify(error));
                this.modalService.dismissAll();
                this.notification.toastDanger(JSON.stringify(error.message), "Error");
                this.getHostelList();
                this.ngxLoader.stop();
            }
        );
    }

    editHostel(f) {
        this.hostel.hostel_name = f.form.value.hostel_name;
        this.hostel.description = f.form.value.description;
        this.hostel.hostel_master = f.form.value.hostel_master;
        this.hostel.hostel_captain = f.form.value.hostel_captain;
        this.hostel.hostel_capacity = f.form.value.hostel_capacity;
        this.hostelData.hostel_master = {
            staff_id: f.form.value.staff_id,
        } as IStaff;
        this.hostelData.hostel_captain = {
            student_id: f.form.value.student_id,
        } as IStudent;

        this.ngxLoader.start();
        this.api.post("hostels/update", this.hostel).subscribe(
            (response: any) => {
                this.api.showNotification("Message", response.message, null);
                this.modalService.dismissAll();
                this.getHostelList();
                this.ngxLoader.stop();
            },
            (error: any) => {
                console.log("error " + JSON.stringify(error));
                this.modalService.dismissAll();
                this.getHostelList();
                this.ngxLoader.stop();
            }
        );
    }

    viewDetails(hostelDetails, content) {
        this.hostel = null;
        this.hostel = hostelDetails;
        if (hostelDetails.house_master) {
            this.staff = hostelDetails.house_master;
        }
        if (hostelDetails.house_captain) {
            this.student = hostelDetails.student;
        }
        this.modalService.open(content, { centered: true });
    }

    editDetails(hostelDetails, content) {
        this.hostel = null;
        this.hostel = hostelDetails;
        if (hostelDetails.house_master) {
            this.staff = hostelDetails.house_master;
        }
        if (hostelDetails.house_captain) {
            this.student = hostelDetails.student;
        }
        this.modalService.open(content, { centered: true });
    }

    delete_class(hostelDetail: IHostel) {
        Swal.fire({
            title: "Are you sure?",
            text: "You will not be able to recover from this action!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, keep it",
        }).then((result: any) => {
            if (result.value) {
                const class_id = hostelDetail.hostel_id;
                const payload = {
                    class_id,
                };
                this.ngxLoader.start();
                this.api
                    .delete("hostels/" + hostelDetail.hostel_id + "/delete")
                    .subscribe(
                        (res) => {
                            // this.myForm.reset(this.myForm.value);

                            Swal.fire({
                                position: "top-end",
                                icon: "info",
                                title: "Success...",
                                text: "Hostel record successfully deleted.!",
                                footer: "",
                            });
                            this.modalService.dismissAll();
                            this.getHostelList();
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
                            this.getHostelList();
                            this.ngxLoader.stop();
                        }
                    );
            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire("Cancelled", "Your record is safe :)", "error");
            }
        });
    }
}
