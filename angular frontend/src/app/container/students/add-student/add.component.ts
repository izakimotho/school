import { Component, OnInit } from "@angular/core";
import { ApiService } from "../../../Services/api.services";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { FormControl, FormGroup, Validators } from "@angular/forms";
import * as EXIF from "exif-js";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { AuthService } from "../../../Services/auth.service";
import { NotificationServices } from "../../../Services/notification.services";
import { Router } from "@angular/router";

import { NgxUiLoaderService, SPINNER } from "ngx-ui-loader";
import { IStudent } from "../../../Shared/Interfaces/student/IStudent";
import { IHostel } from "../../../Shared/Interfaces/school/IHostel";
import { IPosition } from "../../../Shared/Interfaces/school/IPositions";
import { ISchoolStream } from "../../../Shared/Interfaces/school/ISchoolStream";
@Component({
  selector: "app-add-student",
  templateUrl: "./add.component.html",
  styleUrls: ["./add.component.css"],
})
export class AddComponent implements OnInit {
  spinnerType = SPINNER.circle;
  student: IStudent = {} as IStudent;
  school_stream: ISchoolStream = {} as ISchoolStream;
  position: IPosition = {} as IPosition;
  hostel: IHostel = {} as IHostel;
  school_streams: ISchoolStream[];
  positions: IPosition[];
  hostels: IHostel[];

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
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getSchoolStreams();
    this.getHostels();
    this.getPositions();
  }

  private getPositions() {
    this.ngxLoader.start();
    this.api.get("positions/list").subscribe(
      (res) => {
        this.positions = res.result;
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
        console.error("Error" + errResp);
      }
    );
  }

  private getHostels() {
    this.ngxLoader.start();
    this.api.get("hostel/list").subscribe(
      (res) => {
        this.hostels = res.result;
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
        console.error("Error" + errResp);
      }
    );
  }

  private getSchoolStreams() {
    this.ngxLoader.start();
    this.api.get("schools/streams").subscribe(
      (res) => {
        this.school_streams = res.result;
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
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
          this.submit_form(f);
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
    /*if (token) {
      this.tokenVal = 'Bearer ' + token;
    }
    if (!this.item) {
      return;
    }*/
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
    this.student.first_name = f.form.value.first_name;
    this.student.middle_name = f.form.value.middle_name;
    this.student.surname = f.form.value.surname;
    this.student.gender = f.form.value.gender;
    this.student.dob = f.form.value.dob;
    this.student.guardians_name = f.form.value.guardians_name;
    this.student.guardian_phone = f.form.value.guardian_phone;
    this.student.next_of_kin = f.form.value.next_of_kin;
    this.student.kin_phone = f.form.value.kin_phone;
    this.student.kin_relationship = f.form.value.kin_relationship;
    this.student.is_boarder = f.form.value.is_boarder === "true" ? true : false;
    this.student.admission_date = f.form.value.admission_date;
    this.student.school_stream = {
      school_stream_id: f.form.value.school_stream_id,
    } as ISchoolStream;
    this.student.avatar = this.avatar;
    // if (f.form.value.school_stream_id.length ==! 0){
    //   this.student.school_stream =  {school_stream_id: f.form.value.school_stream_id} as ISchoolStream;
    // }

    // else{
    //   this.api.showNotification('Error', 'MIssing Stream.\n Please select a stream to continue',null);
    //   this.ngxLoader.stop();
    //   return;
    // }
    if (
      f.form.value.hostel_id == !undefined &&
      f.form.value.hostel_id == null
    ) {
      this.student.hostel = { hostel_id: f.form.value.hostel_id } as IHostel;
    }

    if (
      f.form.value.position_id == !undefined &&
      f.form.value.position_id == null
    ) {
      this.student.positions = {
        position_id: f.form.value.position_id,
      } as IPosition;
    }
    //this.student.positions =  {position_id: f.form.value.position_id} as IPosition;

    console.log("f::: ", f);
    this.api.post("students/create", this.student).subscribe(
      (response: any) => {
        this.ngxLoader.stop();
        // this.notification.toastSuccess(response.message, 'Success');
        this.api.showNotification("Message", response.message, null);
        this.router.navigate(["students/list"]);
        this.modalService.dismissAll();
        this.ngxLoader.stop();
      },
      (error: any) => {
        this.ngxLoader.stop();
        this.api.showNotification("Error !", error.message, error);
        console.log("error " + JSON.stringify(error));
        // this.spinner.hide();
      }
    );
  }
}
