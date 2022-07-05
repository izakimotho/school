import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';



@Component({
  selector: 'otp-confirm',
  templateUrl: './otp-confirm.component.html',
  styleUrls: ['./otp-confirm.component.scss']
})
export class OtpConfirmComponent implements OnInit, OnDestroy {

  public isCollapsed = true;

  otpForm: FormGroup;


  myyear: number = new Date().getFullYear();

  resp: Response;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    public api: ApiService, public auth:AuthService

  ) {

  }

  ngOnInit() {

    this.otpForm = this.formBuilder.group({
      otp: ['',[Validators.required]],
    });
  }
 //form controls
  get f() {
    return this.otpForm.controls;
  }

  ngOnDestroy() {


  }




  //submits form data to remote server
  onSubmit($event: Event) {
    $event.preventDefault();


    // stop here if form is invalid
    if (this.otpForm.invalid) {
      return;
    } else {
      this.validateOtp(this.otpForm.value.otp);
    }
  }


  onResetpassword() {
    this.router.navigate(['api/auth/reset-password']);
  }

  validateOtp(otp) {


      // login using the OTP given
      const payload = {
        otp
      };
      this.api.post('hornBillAuthenticate', payload).subscribe(
        res => {
          this.auth.login();
          this.auth.setToken((res.token));
          this.auth.setUsername((res.email));

          if (this.auth.isLoggedIn) {
            // Usually you would use the redirect URL from the auth service.
            // However to keep the example simple, we will always redirect to `/admin`.
            var redirectUrl ="";
            console.log(res.user.profileUrl)
            if (res.user.profileUrl == null) {
              //this.apiService.showNotification('Null', 'Nulll.' , 'error');
               redirectUrl = '/settings/myprofile';
            } else {
               redirectUrl = '/ads/dashboard';
            }
            // Set our navigation extras object
            // that passes on our global query params and fragment
            const navigationExtras: NavigationExtras = {
              queryParamsHandling: 'preserve',
              preserveFragment: true
            };
            // Redirect the user
            this.router.navigate([redirectUrl], navigationExtras);
          }
        },
        errResp => {
          //this.apiService.showNotification('Wrong Creddentials', 'Credentials entered are wrong \n re-enter to login.', 'error');
          Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'Oops...',
            text: 'Code entered is wrong \n re-enter to login.!',
            footer: ''
          });
          console.log(JSON.stringify(errResp));
        }
      );










  }

}




