import { NgModule } from '@angular/core';
//import { CommonModule } from '@angular/common';

//import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthsRoutingModule } from './auth-routing.module';
import { SharedModule } from './../../Shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 


import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { OtpConfirmComponent } from './otp-confirm/otp-confirm.component';
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';



@NgModule({
  declarations: [ 
    LoginComponent,
    SignupComponent, 
    OtpConfirmComponent
  ],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    AuthsRoutingModule,
    SharedModule,
    CommonModule,
    NgbModule,

    ReactiveFormsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule
  ],
  providers: [
    //CoursesService
  ],
})
export class AuthModule {}
