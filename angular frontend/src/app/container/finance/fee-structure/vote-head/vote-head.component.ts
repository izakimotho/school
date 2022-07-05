import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';   
import { ApiService } from 'src/app/Services/api.services';
import { IVoteHead } from 'src/app/Shared/Interfaces/Finance/IVoteHead';

@Component({
  selector: 'app-vote-head',
  templateUrl: './vote-head.component.html',
  styleUrls: ['./vote-head.component.css']
})
export class VoteHeadComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  fee_vote_id: string;

  votehead: IVoteHead = {} as IVoteHead;
  voteheadData: IVoteHead = {} as IVoteHead;
  voteheadList: IVoteHead[];


  constructor(
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getvoteheadList();  
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
  getvoteheadList() {
    this.api.get('fee-vote').subscribe(
      res => {
        this.voteheadList = res.result;
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
      this.voteheadData.vote_head_name = "";
      this.voteheadData.description = "";
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addvotehead(f) {
      this.ngxLoader.start();
      this.votehead.vote_head_name = f.form.value.vote_head_name;
      this.votehead.description = f.form.value.description;
     
      this.ngxLoader.start();
      this.api.post("fee-vote", this.voteheadData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getvoteheadList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getvoteheadList();
              this.ngxLoader.stop();
          }
      );
  }

  editvotehead(f) {
      this.votehead.vote_head_name = f.form.value.votehead_name;
      this.votehead.description = f.form.value.description; 

      this.ngxLoader.start();
      this.api.put("fee-vote", this.votehead).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getvoteheadList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getvoteheadList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(voteheadDetails, content) {
      this.votehead = null;
      this.votehead = voteheadDetails;     
      this.modalService.open(content, { centered: true });
  }

  editDetails(voteheadDetails, content) {
      this.votehead = null;
      this.votehead = voteheadDetails;    
      this.modalService.open(content, { centered: true });
  }

  delete_class(voteheadDetail: IVoteHead) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = voteheadDetail.fee_vote_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("fee-vote/" + voteheadDetail.fee_vote_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "votehead record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getvoteheadList();
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
                          this.getvoteheadList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}
  