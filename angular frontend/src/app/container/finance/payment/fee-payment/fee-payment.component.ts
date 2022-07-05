import { Component, OnInit, ViewChild } from "@angular/core"; 
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { ApiService } from "src/app/Services/api.services"; 
import { IFeePayment } from "src/app/Shared/Interfaces/Finance/IFeePayment";
import { ICounty } from "src/app/Shared/Interfaces/school/ICounty";
import { ITermDetails } from "src/app/Shared/Interfaces/school/ITermDetails";
import { IStudent } from "src/app/Shared/Interfaces/student/IStudent";

@Component({
  selector: "app-fee-payment",
  templateUrl: "./fee-payment.component.html",
  styleUrls: ["./fee-payment.component.css"],
})
export class FeePaymentComponent implements OnInit {
  spinnerType = SPINNER.circle;
  students: IStudent[];
  student: IStudent = {} as IStudent;
  feePayment: IFeePayment = {} as IFeePayment;
  termDetails: ITermDetails = {} as ITermDetails; 
  termDetailsList: ITermDetails[];

  submitted = false;
  amount = 0;
  pageNumber = 10;
  count = 10;

  dtOptions: any = {};
  studentID: string;

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private api: ApiService, 
    private modalService: NgbModal,
  ) {}

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: "full_numbers",
      pageLength: 10,
      processing: true,
      search: {
        search: "",
      },
      ordering: false,
      info: false,
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
    this.getStudents();
    this.getTermDetails();
  }

  private getStudents() {
    this.ngxLoader.start();
    this.api.get("students/list").subscribe(
      (res) => {
        this.ngxLoader.stop();
        this.students = res.result;
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

  makePayment(stufdDetails, content) {
    this.student = null;
    this.student = stufdDetails;    
    this.modalService.open(content, { centered: true });
}

addPayment(f) {
    this.ngxLoader.start();
     
    this.feePayment.term = {
      term_details_id: f.form.value.term_details_id,
    } as ITermDetails;

    this.feePayment.student = {
      student_id:this.student.student_id
    } as IStudent;
    this.feePayment.fee_amount = f.form.value.fee_amount;
     
alert(this.feePayment.fee_amount);

   
    this.ngxLoader.start();
    this.api.post("student-fees", this.feePayment).subscribe(
        (response: any) => {
            this.api.showNotification("Message", response.message, null);
            this.modalService.dismissAll();
            this.getStudents();
            this.ngxLoader.stop();
        },
        (error: any) => {
            console.log("error " + JSON.stringify(error));
            this.modalService.dismissAll();
            this.getStudents();
            this.ngxLoader.stop();
        }
    );
}

  public setStudentID(studentID) {
    this.studentID = studentID;
  }
}
