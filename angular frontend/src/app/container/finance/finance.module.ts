import { NgModule } from "@angular/core";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "../../Shared/shared.module";
import { CommonModule } from "@angular/common";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { NgBootstrapFormValidationModule } from "ng-bootstrap-form-validation";

import { Ng2SmartTableModule } from "ng2-smart-table";
import { BreadcrumbModule } from "angular-crumbs";
import { DataTablesModule } from "angular-datatables";

// Modules

import { FinanceRoutingModule } from "./finance-routing.module";
//components
import { FeesComponent } from "./fee-structure/fees/fees.component";
import { VoteHeadComponent } from "./fee-structure/vote-head/vote-head.component";
import { ViewFeeStrctureComponent } from "./fee-structure/view-fee-strcture/view-fee-strcture.component";
import { PaymentTypeComponent } from "./payment/payment-type/payment-type.component";
import { FeePaymentComponent } from "./payment/fee-payment/fee-payment.component";
import { StudFeePaymentHistComponent } from "./payment/stud-fee-payment-hist/stud-fee-payment-hist.component";


@NgModule({
  declarations: [
    FeesComponent,
    VoteHeadComponent,
    ViewFeeStrctureComponent,
    PaymentTypeComponent,
    FeePaymentComponent,
    StudFeePaymentHistComponent,
  ],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    FinanceRoutingModule,
    SharedModule,
    CommonModule,
    NgbModule,
    BreadcrumbModule,
    Ng2SmartTableModule,
    DataTablesModule,
    ReactiveFormsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule,
  ],
  providers: [
    //CoursesService
  ],
})
export class FinanceModule {}
