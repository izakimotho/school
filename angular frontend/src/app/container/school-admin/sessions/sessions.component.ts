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
import { IStaff } from 'src/app/Shared/Interfaces/school/IStaff';
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
import { ITerm } from 'src/app/Shared/Interfaces/school/ITerm';
@Component({
 
  selector: 'app-school-sessions-terms',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.css']
})
export class SessionsComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  term_id: string;

  term: ITerm = {} as ITerm;
  termData: ITerm = {} as ITerm;
  termList: ITerm[];

  staff: IStaff = {} as IStaff;
  term_holder: IStaff = {} as IStaff;
  staffs: IStaff[];
  termHolder:ILeadershipType[];//]= {} as ITermHolder;

  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.gettermList();  
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
  gettermList() {
    this.api.get('terms/list').subscribe(
      res => {
        this.termList = res.result;
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
  clearForm() {
      this.termData.name = "";
      this.termData.description = "";
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addTerm(f) {
      this.ngxLoader.start();
      this.term.name = f.form.value.term_name;
      this.term.description = f.form.value.description;
     
      this.ngxLoader.start();
      this.api.post("terms/create", this.termData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.gettermList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.gettermList();
              this.ngxLoader.stop();
          }
      );
  }

  editTerm(f) {
      this.term.name = f.form.value.term_name;
      this.term.description = f.form.value.description; 

      this.ngxLoader.start();
      this.api.post("terms/update", this.term).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.gettermList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.gettermList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(termDetails, content) {
      this.termData = null;
      this.termData = termDetails;     
      this.modalService.open(content, { centered: true });
  }

  editDetails(termDetails, content) {
      this.termData = null;
      this.termData = termDetails;    
      this.modalService.open(content, { centered: true });
  }

  delete_class(termDetail: ITerm) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = termDetail.term_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("terms/" + termDetail.term_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "term record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.gettermList();
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
                          this.gettermList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}


export interface ITermHolder {
  name: string; 
}