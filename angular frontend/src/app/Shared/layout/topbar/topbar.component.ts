import { Component, OnInit } from "@angular/core";
import { NgxUiLoaderService } from "ngx-ui-loader";
import { ApiService } from "src/app/Services/api.services";
import { IStaff } from "../../Interfaces/school/IStaff";
import { IOrganization } from "../../Interfaces/settings/IOrganization";
import { IUser } from "../../Interfaces/userprofile/IUser";

@Component({
  selector: "app-topbar",
  templateUrl: "./topbar.component.html",
  styleUrls: ["./topbar.component.css"],
})
export class TopbarComponent implements OnInit {
  userProfile: IUser = {} as IUser;
  organization: IOrganization = {} as IOrganization;
  imageToShow: string | ArrayBuffer;
  imageAvatar: string | ArrayBuffer;


//css variable 
  navToggle = () => {
    document.getElementById("body").classList.toggle("ms-aside-left-open");
    document.getElementById("ms-side-nav").classList.toggle("ms-aside-open");
    document.getElementById("overlayleft").classList.toggle("d-block");
  };
  optionsToggle = () => {
    document.getElementById("ms-nav-options").classList.toggle("ms-slide-down");
  };


  constructor(private ngxLoader: NgxUiLoaderService, private api: ApiService) {

   }

  ngOnInit(): void {
    this.getProfile();
  }

  private getProfile() { 
//get user profile data
    this.api.get("profile").subscribe(
      (res) => {
        this.userProfile = res.result;
        this.organization=res.result.organization;
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

  
}
