import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LoginComponent } from '../Auth/login/login.component';
import { SignupComponent } from '../Auth/signup/signup.component'; 
import { OtpConfirmComponent } from './otp-confirm/otp-confirm.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
  },  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'signup',
    component: SignupComponent,
  },
  {
    path: 'otp',
    component: OtpConfirmComponent,
  }
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
export class AuthsRoutingModule {

}
