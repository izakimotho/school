


import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import { ApiService } from '../../../Services/api.services';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IAcademicYear } from 'src/app/Shared/Interfaces/school/IAcademicYear'; 
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
import moment from 'moment';
@Component({

  selector: 'app-school-academic-year',
  templateUrl: './academic-year.component.html',
  styleUrls: ['./academic-year.component.css']
})
export class AcademicYearComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  academicYear_id: string;

  academicYear: IAcademicYear = {} as IAcademicYear;
  academicYearData: IAcademicYear = {} as IAcademicYear;
  academicYearList: IAcademicYear[];

  
  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getacademicYearList();  
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
                  className: "btn btn-sm btn-neutral mb-2 p-3",
              },
              {
                  extend: "csv",
                  text: "<u>C</u>sv",
                  className: "btn btn-sm btn-neutral mb-2 p-3",
              },
              {
                  extend: "excel",
                  text: "<u>E</u>xcel",
                  className: "btn btn-sm btn-neutral mb-2 p-3",
              },
              {
                  extend: "pdf",
                  text: "<u>P</u>df",
                  className: "btn btn-sm btn-neutral mb-2 p-3",
              },
              {
                  extend: "print",
                  text: "Print all",
                  exportOptions: {
                      modifier: {
                          selected: null,
                      },
                  },
                  className: "btn btn-sm btn-neutral mb-2 p-3",
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
  getacademicYearList() {
    this.api.get('academic_year').subscribe(
      res => {
        this.academicYearList = res.result;
        //this.academicYearList = null;
        // for (let year of res.result){
        //     let academic_year: IAcademicYear = {} as IAcademicYear;
        //     let dateString = year.date_from;  
        //     let date_from = moment(dateString, 'MM-DD-YYYY'); 
        //     let date_to = moment(dateString, 'MM-DD-YYYY');  
        //     // let momentVariable = moment(dateString, 'MM-DD-YYYY');  
        //     // let stringvalue = momentVariable.format('YYYY-MM-DD'); 
        //     academic_year.academic_year_name=year.academic_year_name;
        //     //academic_year.date_from=date_from;
        //     academic_year.date_to=date_to.toString();
        //     this.academicYearList.push(academic_year);
        // }
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
      this.academicYearData.academic_year_name = "";
       
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addAcademicYear(f) {
      this.ngxLoader.start();
      this.academicYear.academic_year_name = f.form.value.academic_year_name;
      this.academicYear.date_from = f.form.value.date_from;
      this.academicYear.date_to = f.form.value.date_to;  

      this.ngxLoader.start();
      this.api.post("academic_year", this.academicYear).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getacademicYearList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getacademicYearList();
              this.ngxLoader.stop();
          }
      );
  }

  editAcademicYear(f) {
      this.academicYear.academic_year_name = f.form.value.academic_year_name;
      this.academicYear.date_from = f.form.value.date_from;
      this.academicYear.date_to = f.form.value.date_to;  

      

      this.ngxLoader.start();
      this.api.put("academic_year", this.academicYear).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getacademicYearList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getacademicYearList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(academicYearDetails, content) {
      this.academicYear = null;
      this.academicYear = academicYearDetails;
      this.modalService.open(content, { centered: true });
  }

  editDetails(academicYearDetails, content) {
      this.academicYear = null;
      this.academicYear = academicYearDetails;    
      this.modalService.open(content, { centered: true });
  }

  delete_class(academicYearDetail: IAcademicYear) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = academicYearDetail.academic_year_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("academic_year/" + academicYearDetail.academic_year_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "academicYear record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getacademicYearList();
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
                          this.getacademicYearList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}

 