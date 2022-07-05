import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service'; 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IPosition } from 'src/app/Shared/Interfaces/school/IPositions';
import { IStaff } from 'src/app/Shared/Interfaces/school/IStaff';
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
@Component({
 
  selector: 'app-school-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.css']
})
export class PositionsComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  position_id: string;

  position: IPosition = {} as IPosition;
  positionData: IPosition = {} as IPosition;
  positionList: IPosition[];

  staff: IStaff = {} as IStaff;
  position_holder: IStaff = {} as IStaff;
  staffs: IStaff[];
  positionHolder:ILeadershipType[];//]= {} as IPositionHolder;

  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getPositionList(); 
      this.getPositionHolder();
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
  getPositionList() {
    this.api.get('positions/list').subscribe(
      res => {
        this.positionList = res.result;
         // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

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
  getPositionHolder() {
  this.positionHolder = [];
  this.api.get("leadership_types").subscribe(
      (res) => {
        //console.log(res.result)
        // console.log(res.result.length)
        //   var result = res.result;
        //   for (var val of result) {
        //     console.log(JSON.stringify(val));
        // }
        // console.log(res.result)

        this.positionHolder=res.result;
        // this.positionHolder=[
        //    "Student","Support Staff", "Teaching Staff"
        // ]
        
      },
      (errResp) => {
          this.ngxLoader.stop();
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
      this.positionData.position_name = "";
      this.positionData.description = "";
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addPosition(f) {
      this.ngxLoader.start();
      this.position.position_name = f.form.value.position_name;
      this.position.description = f.form.value.description;
     // this.position.position_holder = f.form.value.position_holder; 

      this.positionData.position_holder = {
        user_type_id: f.form.value.position_holder,
      } as ILeadershipType;
     
      this.ngxLoader.start();
      this.api.post("positions/create", this.positionData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getPositionList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getPositionList();
              this.ngxLoader.stop();
          }
      );
  }

  editposition(f) {
      this.position.position_name = f.form.value.position_name;
      this.position.description = f.form.value.description;
      this.position.position_holder = f.form.value.position_holder; 

      

      this.ngxLoader.start();
      this.api.post("positions/update", this.position).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getPositionList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getPositionList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(positionDetails, content) {
      this.position = null;
      this.position = positionDetails;
      if (positionDetails.house_master) {
          this.staff = positionDetails.house_master;
      }
     
      this.modalService.open(content, { centered: true });
  }

  editDetails(positionDetails, content) {
      this.position = null;
      this.position = positionDetails;
      if (positionDetails.house_master) {
          this.staff = positionDetails.house_master;
      }
    
      this.modalService.open(content, { centered: true });
  }

  delete_class(positionDetail: IPosition) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = positionDetail.position_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("positions/" + positionDetail.position_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "Position record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getPositionList();
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
                          this.getPositionList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}


export interface IPositionHolder {
  name: string; 
}