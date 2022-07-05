import { Component, OnInit, ViewChild } from "@angular/core";
import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { ApiService } from "src/app/Services/api.services";
import { IPermission } from "src/app/Shared/Interfaces/userprofile/IPermission";
import { NavigationExtras, Router } from "@angular/router";
import { IUserCredentials } from "src/app/Shared/Interfaces/userprofile/IUserCredentials";
import { IUser } from "src/app/Shared/Interfaces/userprofile/IUser";
import EXIF from "exif-js";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  submitted = false;
  spinnerType = SPINNER.circle;

  creds: IUserCredentials = {} as IUserCredentials;

  userProfile: IUser = {} as IUser;
  imageToShow: string | ArrayBuffer;
  imageAvatar: string | ArrayBuffer;




    //image
    files: File[] = [];
    item: File = null;
    imageSrc: string;
    imgURL: string | ArrayBuffer;
    imagePath: any;
    format: string;
    url: string | ArrayBuffer;
    res: { [key: string]: any };
   
  
  // Table

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private router: Router,
    private api: ApiService
  ) { }

  ngOnInit(): void {
    this.getProfile();
  }

  private getProfile() { 
//get user profile data
    this.api.get("profile").subscribe(
      (res) => {
        this.userProfile = res.result;
        if (this.userProfile.avatar === undefined || this.userProfile.avatar === null) {
          // do something             
             
        }else{           
            var url = this.api.getURL("auth");
            let imgUrl = url + '/file/' + this.userProfile.avatar;   
            this.api.getImage(imgUrl).subscribe(data => {
              this.createImageAvatarFromBlob(data);       
            }, error => {
              //console.log(error);
            });
        } 
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
        console.error("Error" + errResp);
      }
    );
  }
//convert image to blob
createImageAvatarFromBlob(image: Blob) {
  let reader = new FileReader();
  reader.addEventListener(
    "load",
    () => {
      this.imageAvatar = reader.result;
    },
    false
  );

  if (image) {
    reader.readAsDataURL(image);
  }
}



onImageSelect(event) {
  this.ngxLoader.start();
  this.files.push(...event.addedFiles);
  const reader = new FileReader();
  if (event.addedFiles[0]) {
    this.item = event.addedFiles[0];
    reader.readAsDataURL(event.addedFiles[0]);
    if (event.addedFiles[0].type.indexOf("image") > -1) {
      this.format = "image";
    } else if (event.addedFiles[0].type.indexOf("video") > -1) {
      this.format = "video";
    }
    this.url = (event.addedFiles[0] as FileReader).result;
    reader.onload = () => {
      this.imageSrc = reader.result as string;
    };

    this.reviewMetadata();
  }
  this.ngxLoader.stop();
}

public reviewMetadata() {
  const file: Blob = this.item;
  const fileReader = new FileReader();
  fileReader.addEventListener("load", (fileReaderEvent) => {
    const data = EXIF.readFromBinaryFile(fileReader.result);
    if (data) {
      this.res = data;
    } else {
      this.res = null;
    }
  });
  fileReader.readAsArrayBuffer(file);
}

onRemove(event) {
  this.files.splice(this.files.indexOf(event), 1);
}
updateUserProfile(f) {
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
