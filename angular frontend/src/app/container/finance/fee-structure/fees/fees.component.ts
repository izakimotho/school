
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs'; 
import { IStaff } from 'src/app/Shared/Interfaces/school/IStaff';
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
import { ApiService } from 'src/app/Services/api.services';
import { IVoteHead } from 'src/app/Shared/Interfaces/Finance/IVoteHead';
import { IFeeStructure } from 'src/app/Shared/Interfaces/Finance/IFeeStructure';
import { IAcademicYear } from 'src/app/Shared/Interfaces/school/IAcademicYear';
import { IClasses } from 'src/app/Shared/Interfaces/school/IClasses';
import { ITermDetails } from 'src/app/Shared/Interfaces/school/ITermDetails';

@Component({
  selector: 'app-fees',
  templateUrl: './fees.component.html',
  styleUrls: ['./fees.component.css']
})
export class FeesComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  feeStructure_id: string;


  feeStructure: IFeeStructure = {} as IFeeStructure;
  feeStructureData: IFeeStructure = {} as IFeeStructure;
  feeStructureList: IFeeStructure[];

  academicYear: IAcademicYear = {} as IAcademicYear; 
  academicYearList: IAcademicYear[];

  classes: IClasses = {} as IClasses; 
  classesList: IClasses[];

  termDetails: ITermDetails = {} as ITermDetails; 
  termDetailsList: ITermDetails[];
 
 
  votehead: IVoteHead = {} as IVoteHead; 
  voteheadList: IVoteHead[];

  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getfeeStructureList(); 
      this.getVoteheads();
      this.getAcademicYears();
      this.getTermDetails();
      this.getClasses();
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
  getfeeStructureList() {
    this.api.get('fees-structure').subscribe(
      res => {
        this.feeStructureList = res.result;
         // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  } 

  getAcademicYears() {
    this.academicYearList = [];
    this.api.get("academic_year").subscribe(
        (res) => {   
          this.academicYearList=res.result;          
        },
        (errResp) => {
            this.ngxLoader.stop();
            console.error("Error" + errResp);
        }
    );
  }


  getClasses() {
    this.classesList = [];
    this.api.get("classes/list").subscribe(
        (res) => {   
          this.classesList=res.result;          
        },
        (errResp) => {
            this.ngxLoader.stop();
            console.error("Error" + errResp);
        }
    );
  }
  
  getTermDetails() {
    this.termDetailsList = [];
    this.api.get("terms_details/list").subscribe(
        (res) => {   
          this.termDetailsList=res.result;          
        },
        (errResp) => {
            this.ngxLoader.stop();
            console.error("Error" + errResp);
        }
    );
  }
 
getVoteheads() {
    this.voteheadList = [];
    this.api.get("fee-vote").subscribe(
        (res) => {   
          this.voteheadList=res.result;          
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
      this.feeStructureData.amount = 0;
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addFeeStructure(f) {
      this.ngxLoader.start();
      this.feeStructure.amount = f.form.value.amount; 

      this.feeStructureData.voteHead = {
        fee_vote_id: f.form.value.fee_vote_id,
      } as IVoteHead;

      this.feeStructureData.classModel = {
        class_id: f.form.value.class_id,
      } as IClasses;
     
      this.feeStructureData.termDetails = {
        term_details_id: f.form.value.term_details_id,
      } as ITermDetails;

      this.feeStructureData.academicYear= {
        academic_year_id: f.form.value.academic_year_id,
      } as IAcademicYear;



      this.ngxLoader.start();
      this.api.post("fees-structure", this.feeStructureData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getfeeStructureList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getfeeStructureList();
              this.ngxLoader.stop();
          }
      );
  }


  editFeeStructure(f) {


    console.log(f);
      this.feeStructure.amount = f.form.value.amount; 

      this.feeStructureData.voteHead = {
        fee_vote_id: f.form.value.fee_vote_id,
      } as IVoteHead;

      this.feeStructureData.classModel = {
        class_id: f.form.value.class_id,
      } as IClasses;
     
      this.feeStructureData.termDetails = {
        term_details_id: f.form.value.term_details_id,
      } as ITermDetails;

      this.feeStructureData.academicYear = {
        academic_year_id: f.form.value.academic_year_id,
      } as IAcademicYear;

      this.feeStructureData.amount = f.form.value.amount;


      this.ngxLoader.start();
      this.api.post("fee_structure/update", this.feeStructure).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getfeeStructureList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getfeeStructureList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(feeStructureDetails, content) {
      this.feeStructure = null;
      this.feeStructure = feeStructureDetails;
      if (feeStructureDetails. votehead) {
        this. votehead = feeStructureDetails. votehead;
    }
     
      this.modalService.open(content, { centered: true });
  }

  editDetails(feeStructureDetails, content) {
      this.feeStructure = null;
      this.feeStructure = feeStructureDetails;


      if (feeStructureDetails. votehead) {
          this. votehead = feeStructureDetails. votehead;
      }
    

     




      this.modalService.open(content, { centered: true });
  }

  delete_class(feeStructureDetail: IFeeStructure) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = feeStructureDetail.fee_structure_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("fee_structure/" + feeStructureDetail.fee_structure_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "feeStructure record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getfeeStructureList();
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
                          this.getfeeStructureList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}


export interface IFeeStructureHolder {
  name: string; 
}