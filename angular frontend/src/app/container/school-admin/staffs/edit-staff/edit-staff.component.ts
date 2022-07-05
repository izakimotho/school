

import { Component, OnInit } from '@angular/core'; 
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as EXIF from 'exif-js';
import {HttpClient, HttpHeaders} from '@angular/common/http'; 
import {ActivatedRoute, Router} from '@angular/router';
import { SPINNER, NgxUiLoaderService } from 'ngx-ui-loader';
import { ApiService } from 'src/app/Services/api.services';
import { AuthService } from 'src/app/Services/auth.service';
import { NotificationServices } from 'src/app/Services/notification.services';
import { IPosition } from 'src/app/Shared/Interfaces/school/IPositions';
import { IStaff } from 'src/app/Shared/Interfaces/school/IStaff';
import { IUserType } from 'src/app/Shared/Interfaces/userprofile/IUserType';

@Component({
  selector: 'app-edit-staff',
  templateUrl: './edit-staff.component.html',
  styleUrls: ['./edit-staff.component.css']
})
export class EditStaffComponent implements OnInit {
  spinnerType = SPINNER.circle;

  staff: IStaff = {} as IStaff;
  staff_id: string;

  position: IPosition = {} as IPosition; 
  positions: IPosition[]; 
  
  userType:IUserType = {} as IUserType; 
  userTypes: IUserType[]; 
  
  //image
  files: File[] = [];
  item: File = null;
  imageSrc: string;
  imgURL: string | ArrayBuffer;
  imagePath: any;
  format: string;
  url: string | ArrayBuffer;
  res: { [key: string]: any };
  myForm = new FormGroup({
    fileSource: new FormControl("", [Validators.required]),
  });

  avatar: string;

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private notification: NotificationServices,
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router
  ) {
    this.staff_id = this.activatedRoute.snapshot.params.staff_id;
   }

  ngOnInit(): void { 
    this.getStaff();
    this.getPositions();
    this.getUserTypes();
  }

  private getStaff() {
    this.ngxLoader.start();
    this.api.get("staff/"+this.staff_id).subscribe(
      (res) => {
        this.staff = res.result;
        console.error(JSON.stringify(this.staff));
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
        console.error("Error" + errResp.message);
      }
    );
  }
  private getPositions() {
    this.api.get("positions/list").subscribe(
      (res) => {
        this.positions = res.result;
      },
      (errResp) => {
        console.error("Error" + errResp);
      }
    );
  }

 
  private getUserTypes() {
    this.api.get("user_types").subscribe(
      (res) => {
        this.userTypes = res.result;
      },
      (errResp) => {
        console.error("Error" + errResp);
      }
    );
  }


  open(content: any) {
    this.modalService.open(content, { centered: true });
  }

  onImageSelect(event) {
    this.ngxLoader.start();
    this.files.push(...event.addedFiles);
    const reader = new FileReader();
    if (event.addedFiles[0]) {
      this.item = event.addedFiles[0];
      this.myForm.value.name = this.item.name;
      reader.readAsDataURL(event.addedFiles[0]);
      if (event.addedFiles[0].type.indexOf("image") > -1) {
        this.format = "image";
      } else if (event.addedFiles[0].type.indexOf("video") > -1) {
        this.format = "video";
      }
      this.url = (event.addedFiles[0] as FileReader).result;
      reader.onload = () => {
        this.imageSrc = reader.result as string;
        this.myForm.patchValue({
          fileSource: reader.result,
        });
      };
 
    }
    this.ngxLoader.stop();
  }

 

  onRemove(event) {
    this.files.splice(this.files.indexOf(event), 1);
  }

  submit(f) {
    this.ngxLoader.start();

    if (this.files.length > 0) {
      const token = this.auth.getToken();
      let headers = new HttpHeaders();
      headers = headers.set("Authorization", "Bearer " + token);
      const httpOptions = {
        headers,
      };
      const formData: FormData = new FormData();
      formData.append("file", this.item, this.item.name);
      const reqUrl = this.api.getAPIConfig();
      this.http.post(reqUrl + "/upload", formData, httpOptions).subscribe(
        (response: any) => {
          this.avatar = response.file_name;
          this.submit_form(f)
        },
        (error: any) => {
          console.log("error " + JSON.stringify(error));
          // this.spinner.hide();
        }
      );
    } else {
      this.submit_form(f);

    }

  }

  submitImage() {
    const token = this.auth.getToken();
    let headers = new HttpHeaders();
    headers = headers.set("Authorization", "Bearer " + token);
    const httpOptions = {
      headers,
    };
    // this.spinner.show();

    const formData: FormData = new FormData();
    formData.append("file", this.item, this.item.name);
    const reqUrl = this.api.getAPIConfig();
    this.http.post(reqUrl + "/upload", formData, httpOptions).subscribe(
      (response: any) => {
        this.avatar = response.file_name;
        // this.loaderData(response);
        // this.spinner.hide();
      },
      (error: any) => {
        console.log("error " + JSON.stringify(error));
        // this.spinner.hide();
      }
    );
  }

  submit_form(f) {
    this.ngxLoader.start();
    //console.log(f.form);
    this.staff.first_name = f.form.value.first_name;
    this.staff.middle_name = f.form.value.middle_name;
    this.staff.surname = f.form.value.surname;
    this.staff.gender = f.form.value.gender; 
    this.staff.spouse_name = f.form.value.spouse_name;
    this.staff.phone_number = f.form.value.phone_number;
    this.staff.employment_date = f.form.value.employment_date;
    this.staff.spouse_phone = f.form.value.spouse_phone;
    this.staff.avatar = this.avatar;
 
    if (
      f.form.value.position_id == !undefined &&
      f.form.value.position_id == null
    ) {
      this.staff.positions = {
        position_id: f.form.value.position_id,
      } as IPosition;
    }
    // else{
    //   this.notification.toastDanger("Please enter a position", "Error");
    //   this.notification.toastDanger("Position "+f.form.value.position_id, "Error");
    //   this.ngxLoader.stop();
    //   return;
    // }
    //this.student.positions =  {position_id: f.form.value.position_id} as IPosition;

    console.log("f::: ", f);
    this.api.put("staff/update", this.staff).subscribe(
      (response: any) => {
        this.ngxLoader.stop();
        // this.notification.toastSuccess(response.message, 'Success');
        this.api.showNotification("Message", response.message, null);
        this.router.navigate(["school/staff"]);
        this.modalService.dismissAll();
        this.ngxLoader.stop();
      },
      (error: any) => {
        this.ngxLoader.stop();
        this.notification.toastDanger(error, "Error");
        console.log("error " + JSON.stringify(error));
        // this.spinner.hide();
      }
    );
  }
}
