import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CoreDashboardComponent } from './core/core-dashboard.component'; 

const routes: Routes = [
  {
    path: '',
    component: CoreDashboardComponent,
  },
  {
    path: 'dashboard',
    component: CoreDashboardComponent,
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
export class DashboardRoutingModule {

}
