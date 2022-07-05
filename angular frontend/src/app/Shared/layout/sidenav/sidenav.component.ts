import { Component, OnInit } from "@angular/core";
import { ApiService } from "src/app/Services/api.services";
import { AuthService } from "src/app/Services/auth.service";
import { Menu, Submenu } from "../../Interfaces/menu";
import { IOrganization } from "../../Interfaces/settings/IOrganization";
import { IUser } from "../../Interfaces/userprofile/IUser";
import navigation from "./../../data/navigation.json";

@Component({
  selector: "app-sidenav",
  templateUrl: "./sidenav.component.html",
  styleUrls: ["./sidenav.component.css"],
})
export class SidenavComponent implements OnInit {
  public navlist = navigation;
  nav_list: Menu[];

  organization: IOrganization = {} as IOrganization;
  imageToShow: string | ArrayBuffer;


  constructor(public auth: AuthService,
    private api: ApiService) {
    this.nav_list = [];
  }
  navToggle = () => {
    document.getElementById("body").classList.toggle("ms-aside-left-open");
    document.getElementById("ms-side-nav").classList.toggle("ms-aside-open");
    document.getElementById("overlayleft").classList.toggle("d-block");
  };

  ngOnInit(): void {
    this.getProfile();
    //this.navlist=[];
    let permissions: any;
    //permissions=this.auth.getUserPermission;

    //console.log(localStorage.getItem('perm'));
    permissions = JSON.parse(localStorage.getItem("perm"));
    // can_view_school_stream
    //console.log('permissions  == ',permissions);

    // let navi = permissions.find(o => o === 'can_view_school_stream');
    // console.log(navi);

    for (var nav of this.navlist) {
      // Navigation menu
      let navi = permissions.find((o) => o === nav.priviledge);
      if (navi == null) {
        //console.log("Null or undefined value!");
      } else {
        let menu = new Menu();
        menu.id = nav.id;
        menu.linktext = nav.linktext;
        menu.iconclass = nav.iconclass;
        menu.icon = nav.icon;
        menu.child = nav.child;
        menu.submenu = [];

        if (nav.submenu.length > 0) {

          for (var subnav of nav.submenu) {
            //sub navi 
            let subnavi = permissions.find((o) => o === subnav.priviledge);

            if (subnavi == null) {
              //console.log("Null or undefined value!");
            } else {
              let sub_menu = new Submenu();
              sub_menu.id = subnav.id;
              sub_menu.linktext = subnav.linktext;
              sub_menu.link = subnav.link;
              menu.submenu.push(sub_menu);
              //console.log("sub menu  ", sub_menu);
            }
          }


        }

        this.nav_list.push(menu);

      }


    }

    //console.log("Final Menu ", this.nav_list);

   
  }

  private getProfile() {
    this.api.get("profile").subscribe(
      (res) => {
        this.organization = res.result.organization;

        if (this.organization.logo === undefined || this.organization.logo === null) {
          // do something             

        } else {
          var url = this.api.getURL('auth');
          let imgUrl = url + '/file/' + this.organization.logo;
          this.api.getImage(imgUrl).subscribe(data => {
            this.createImageFromBlob(data);
          }, error => {
            //console.log(error);
          });
        }

      },
      (errResp) => {
        console.error("Error" + errResp);
      }
    );
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

}
