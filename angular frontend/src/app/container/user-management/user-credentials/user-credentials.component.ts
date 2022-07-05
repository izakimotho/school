import { Component, OnInit, ViewChild } from "@angular/core";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { ApiService } from "src/app/Services/api.services";
import { IPermission } from "src/app/Shared/Interfaces/userprofile/IPermission";
import { NavigationExtras, Router } from "@angular/router";
import { IUserCredentials } from "src/app/Shared/Interfaces/userprofile/IUserCredentials";

@Component({
  selector: 'app-user-credentials',
  templateUrl: './user-credentials.component.html',
  styleUrls: ['./user-credentials.component.css']
})
export class UserCredentialsComponent implements OnInit {
  submitted = false;
  spinnerType = SPINNER.circle;

  creds: IUserCredentials = {} as IUserCredentials;

  // Table

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private router: Router,
    private api: ApiService
  ) { }

  ngOnInit(): void {


  }


  updateCreds(f) {
    this.ngxLoader.start();
    this.creds.old_password = f.form.value.old_password;
    this.creds.new_password = f.form.value.new_password;
    this.creds.confirm_password = f.form.value.confirm_password;
    this.creds.old_password = f.form.value.old_password;


    if (this.creds.new_password === this.creds.confirm_password) {

      this.api.showNotification("Message", "The new Password & confirm password doesnt not match. \n Please try again", null);
      return;
    } else {
      this.api.post("rauth/change_pass", this.creds).subscribe(
        (response: any) => {
          this.api.showNotification("Message", response.message, null);
          this.router.navigate(["analytics/dashboard"]);
          this.ngxLoader.stop();
        },
        (error: any) => {
          console.log("error " + JSON.stringify(error));
          this.ngxLoader.stop();
        }
      );
    }





    // this.ngxLoader.stop();
  }

}
