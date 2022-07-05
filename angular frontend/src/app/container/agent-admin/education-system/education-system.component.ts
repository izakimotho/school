import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import { ApiService } from '../../../Services/api.services';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs'; 
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType'; 
import { IEducationSystem } from 'src/app/Shared/Interfaces/school/IEducationSystem';
@Component({
 
  selector: 'app-education-systems',
  templateUrl: './education-system.component.html',
  styleUrls: ['./education-system.component.css']
})
export class EducationSystemComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  educationSystem_id: string;
  educationSystem: IEducationSystem = {} as IEducationSystem;
  educationSystemData: IEducationSystem = {} as IEducationSystem;
  educationSystemList: IEducationSystem[];


  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.geteducationSystemList(); 
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
  geteducationSystemList() {
    this.api.get('education_system/list').subscribe(
      res => {
        this.educationSystemList = res.result;
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
      this.educationSystemData.education_system_name = "";
      this.educationSystemData.description = "";
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addEducationSystem(f) {
      this.ngxLoader.start();
      this.educationSystem.education_system_name = f.form.value.educationSystem_name;
      this.educationSystem.description = f.form.value.description;
    
      this.ngxLoader.start();
      this.api.post("education_system/add", this.educationSystemData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.geteducationSystemList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.geteducationSystemList();
              this.ngxLoader.stop();
          }
      );
  }

  editEducationSystem(f) {
      this.educationSystem.education_system_name = f.form.value.educationSystem_name;
      this.educationSystem.description = f.form.value.description; 

      this.ngxLoader.start();
      this.api.post("education_system/update", this.educationSystem).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.geteducationSystemList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.geteducationSystemList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(educationSystemDetails, content) {
      this.educationSystemData = null;
      this.educationSystemData = educationSystemDetails;     
      this.modalService.open(content, { centered: true });
  }

  editDetails(educationSystemDetails, content) {
      this.educationSystemData = null;
      this.educationSystemData = educationSystemDetails;  
      this.modalService.open(content, { centered: true });
  }

  delete_class(educationSystemDetail: IEducationSystem) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = educationSystemDetail.education_system_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("educationSystems/" + educationSystemDetail.education_system_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "educationSystem record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.geteducationSystemList();
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
                          this.geteducationSystemList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}

