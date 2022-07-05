import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
   
// Components
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { VoteHeadComponent } from './fee-structure/vote-head/vote-head.component';
import { FeesComponent } from './fee-structure/fees/fees.component';
import { ViewFeeStrctureComponent } from './fee-structure/view-fee-strcture/view-fee-strcture.component';


import { PaymentTypeComponent } from './payment/payment-type/payment-type.component';
import { FeePaymentComponent } from './payment/fee-payment/fee-payment.component';
import { StudFeePaymentHistComponent } from './payment/stud-fee-payment-hist/stud-fee-payment-hist.component';

const routes: Routes = [
  // {
  //   path: 'users',
  //   component: UserComponent,
  //   data: { breadcrumb: "Users"}
  // }, {
  //   path: 'view-user/:user_id',
  //   component: UserComponent,
  //   data: { breadcrumb: "Users"}
  // },
   
  {
    path: 'fee-structures',
    component: FeesComponent,
    data: { breadcrumb: "Fee Structures"}
  }, 
   {
    path: 'vote-heads',
    component: VoteHeadComponent,
    data: { breadcrumb: "Vote Head"}
  },
  {
    path: 'view-fee-strcture/:fee_structure_id',
    component: ViewFeeStrctureComponent,
     data: { breadcrumb: "Fee Structure"}
  },

  {
    path: 'payment-type',
    component: PaymentTypeComponent,
    data: { breadcrumb: "Payment Type "}
  },
  {
    path: 'fee-payment',
    component: FeePaymentComponent,
    data: { breadcrumb: "Fee Payment "}
  },
  {
    path: 'fee-payment-hist/:student_id',
    component: StudFeePaymentHistComponent,
     data: { breadcrumb: "Student Fee Payment History"}
  },

 
 

  {
    path: '**',
    component: PageNotFoundComponent
  },
  
  
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  providers: [
    // ConfirmExitGuard
  ]
})
export class FinanceRoutingModule {

}
