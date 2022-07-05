import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
   
// Components
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import { PaymentTypeComponent } from './payment-type/payment-type.component';
 
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
    path: 'payment-type',
    component: PaymentTypeComponent,
    data: { breadcrumb: "Payment Type "}
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
export class GlobalAccountingRoutingModule {

}
