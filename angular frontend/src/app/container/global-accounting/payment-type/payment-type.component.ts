import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import { ApiService } from '../../../Services/api.services';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IpaymentMethod } from 'src/app/Shared/Interfaces/school/IpaymentMethod'; 
import { ILeadershipType } from 'src/app/Shared/Interfaces/school/ILeadershipType';
import moment from 'moment';
import { IPaymentMethod } from 'src/app/Shared/Interfaces/global_accounting/IPaymentMethod';

@Component({
  selector: 'app-payment-type',
  templateUrl: './payment-type.component.html',
  styleUrls: ['./payment-type.component.css']
})
export class PaymentTypeComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  paymentMethod_id: string;
  paymentMethod: IPaymentMethod = {} as IPaymentMethod;
  paymentMethodData: IPaymentMethod = {} as IPaymentMethod;
  paymentMethodList: IPaymentMethod[];

  
  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
      this.getpaymentMethodList();  
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
  getpaymentMethodList() {
    this.api.get('payment-methods').subscribe(
      res => {
        this.paymentMethodList = res.result;
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
    this.paymentMethodData.name = "";
    this.paymentMethodData.description = "";
       
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addpaymentMethod(f) {
      this.ngxLoader.start();
      this.paymentMethod.name = f.form.value._name;
      this.paymentMethod.description = f.form.value.description; 

      this.ngxLoader.start();
      this.api.post("payment-methods", this.paymentMethod).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getpaymentMethodList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getpaymentMethodList();
              this.ngxLoader.stop();
          }
      );
  }

  editpaymentMethod(f) {
      this.paymentMethod.name = f.form.value.name;
      this.paymentMethod.description = f.form.value.description; 

      

      this.ngxLoader.start();
      this.api.put("payment-method", this.paymentMethod).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getpaymentMethodList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getpaymentMethodList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(paymentMethodDetails, content) {
      this.paymentMethod = null;
      this.paymentMethod = paymentMethodDetails;
      this.modalService.open(content, { centered: true });
  }

  editDetails(paymentMethodDetails, content) {
      this.paymentMethod = null;
      this.paymentMethod = paymentMethodDetails;    
      this.modalService.open(content, { centered: true });
  }

  delete_class(paymentMethodDetail: IpaymentMethod) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = paymentMethodDetail.academic_year_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("payment-methods/" + paymentMethodDetail.academic_year_id )
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "Payment Method record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getpaymentMethodList();
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
                          this.getpaymentMethodList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}

 