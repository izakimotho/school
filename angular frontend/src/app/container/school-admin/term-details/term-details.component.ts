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
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
import { IAcademicYear } from 'src/app/Shared/Interfaces/school/IAcademicYear';
import { ITermDetails } from 'src/app/Shared/Interfaces/school/ITermDetails';
import { ITerm } from 'src/app/Shared/Interfaces/school/ITerm';
@Component({
 
  selector: 'app-school-term-details',
  templateUrl: './term-details.component.html',
  styleUrls: ['./term-details.component.css']
})
export class TermDetailsComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  termDetails_id: string;

  termDetails: ITermDetails = {} as ITermDetails;
  termDetailsData: ITermDetails = {} as ITermDetails;
  termDetailsList: ITermDetails[];

  academicYear: IAcademicYear = {} as IAcademicYear;
  academicYears: IAcademicYear[];

  
  term: ITerm = {} as ITerm;
  terms: ITerm[];


  TermDetailsHolder:ILeadershipType[];//]= {} as ITermDetailsHolder;

  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getTermDetailsList();
      this.getAcademicYear();  
      this.getTerms();
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
  getTermDetailsList() {
    this.api.get('terms_details/list').subscribe(
      res => {
        this.termDetailsList = res.result;
         // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  } 
  getAcademicYear() {
    this.academicYears = [];
    this.api.get("academic_year").subscribe(
        (res) => {
            this.academicYears = res.result;
        },
        (errResp) => {
            this.ngxLoader.stop();
            console.error("Error" + errResp);
        }
    );
}
getTerms() {
    this.terms = [];
    this.api.get("terms/list").subscribe(
        (res) => {
            this.terms = res.result;
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
      this.termDetails.term_description = "";
      this.termDetails.term_details_id = "";
      this.termDetails.date_from = new Date();
      this.termDetails.date_to = new Date();
      this.termDetails.term_details_id = "";
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addTermDetails(f) {
      this.ngxLoader.start();
      this.termDetails.date_from = f.form.value.date_from;
      this.termDetails.date_to = f.form.value.date_to;
      this.termDetails.term_description = f.form.value.description;
      this.termDetails.name = f.form.value.name;
 
      this.termDetails.academic_year = {
        academic_year_id: f.form.value.academic_year,
      } as IAcademicYear;

      this.termDetails.terms = {
        term_id: f.form.value.term_id,
      } as ITerm;
     
      this.ngxLoader.start();
      this.api.post("terms_details/create", this.termDetails).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getTermDetailsList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getTermDetailsList();
              this.ngxLoader.stop();
          }
      );
  }

  editTermDetails(f) {
    this.termDetails.date_from = f.form.value.date_from;
    this.termDetails.date_to = f.form.value.date_to;
    this.termDetails.term_description = f.form.value.term_description;
    this.termDetails.name = f.form.value.name;

    this.termDetails.academic_year = {
        academic_year_id: f.form.value.academic_year,
      } as IAcademicYear;

      this.termDetails.terms = {
        term_id: f.form.value.term_id,
      } as ITerm;
     

      this.ngxLoader.start();
      this.api.put("terms_details/update", this.termDetails).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getTermDetailsList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getTermDetailsList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(termDetails, content) {
      this.termDetails = null;
      this.termDetails = termDetails;
      if (termDetails.academicYear) {
          this.academicYear = termDetails.house_master;
      }
     
      if (termDetails.terms) {
        this.term = termDetails.terms;
    }
   
      this.modalService.open(content, { centered: true });
  }

  editDetails(termDetails, content) {
      this.termDetails = null;
      this.termDetails = termDetails;
      if (termDetails.academicYear) {
        this.academicYear = termDetails.house_master;
    }
   
    if (termDetails.terms) {
      this.term = termDetails.terms;
  }
    
      this.modalService.open(content, { centered: true });
  }

  delete_term_details(termDetails: ITermDetails) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = termDetails.term_details_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("terms_details/" + termDetails.term_details_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "Term Details record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getTermDetailsList();
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
                          this.getTermDetailsList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}

