import { Component, OnInit } from '@angular/core';
import {
  ICluster,
  ICounty,
  IGender,
  ISchool,
  ISchoolCategory,
  ISchoolLevel, ISchoolType,
  ISponsor,
  ISubCounty,
  IWard
} from '../interface/ISchool';
import { ApiService } from '../../../Services/api.services';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import * as EXIF from 'exif-js';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AuthService } from '../../../Services/auth.service';
import { NotificationServices } from '../../../Services/notification.services';
import { Router } from '@angular/router';

import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { IEducationSystem } from 'src/app/Shared/Interfaces/school/IEducationSystem';
@Component({
  selector: 'app-add-student',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  spinnerType = SPINNER.circle;
  school: ISchool = {} as ISchool;
  county = {} as ICounty;
  subCounty: ISubCounty = {} as ISubCounty;
  ward: IWard = {} as IWard;
  educationSystem: IEducationSystem = {} as IEducationSystem;
  sponsor: ISponsor = {} as ISponsor;
  schoolLevel: ISchoolLevel = {} as ISchoolLevel;
  schoolCategory: ISchoolCategory = {} as ISchoolCategory;
  cluster: ICluster = {} as ICluster;
  gender: IGender = {} as IGender;
  schoolType: ISchoolType = {} as ISchoolType;
  counties: ICounty[];
  subCounties: ISubCounty[];
  wards: IWard[];
  educationSystems: IEducationSystem[];
  sponsors: ISponsor[];
  schoolLevels: ISchoolLevel[];
  schoolCategories: ISchoolCategory[];
  clusters: ICluster[];
  genderTypes: IGender[];
  schoolTypes: ISchoolType[];
  phoneNumbers = [];
  mobilePhoneNumbers = [];
  phoneNo = '';
  isNationalSchool: boolean = false;

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
    fileSource: new FormControl('', [Validators.required])
  });
  logo: any;
  closeResult: string;

  constructor(
    // private spinner: NgxSpinnerService,
    private ngxLoader: NgxUiLoaderService,
    private notification: NotificationServices,
    private http: HttpClient,
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    this.getCounties();
    this.getEducationSystems();
    this.getSponsor();
    this.getSchoolLevels();
    this.getSchoolCategories();
    this.getClusters();
    this.getGenderTypes();
    this.getSchoolTypes();
  }

  private getCounties() {
    this.ngxLoader.start();
    this.api.get('counties').subscribe(
      res => {
        this.counties = res.result;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  getSubCounties(countyId) {
    this.ngxLoader.start();
    this.api.get('sub_counties').subscribe(
      res => {
        this.ngxLoader.stopAll();
        this.subCounties = res.result.filter(value => value.county_code === countyId);
        console.log('this.subCounties', this.subCounties);
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  getWards(subCountyId) {
    this.ngxLoader.start();
    this.api.get('wards').subscribe(
      res => {
        this.ngxLoader.stop();
        this.wards = res.result.filter(value => value.sub_county_code === subCountyId);
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  getEducationSystems() {
    this.api.get('education_system/list').subscribe(
      res => {
        this.educationSystems = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getSponsor() {
    this.api.get('schools/sponsors').subscribe(
      res => {
        this.sponsors = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getSchoolLevels() {
    this.api.get('schools/levels').subscribe(
      res => {
        this.schoolLevels = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getSchoolCategories() {
    this.api.get('schools/categories').subscribe(
      res => {
        this.schoolCategories = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getClusters() {
    this.api.get('schools/clusters').subscribe(
      res => {
        this.clusters = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getGenderTypes() {
    this.api.get('schools/gender').subscribe(
      res => {
        this.genderTypes = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  getSchoolTypes() {
    this.api.get('schools/types').subscribe(
      res => {
        this.schoolTypes = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  open(content: any) {
    this.modalService.open(content, { centered: true });
  }

  addPhoneNumber(phoneNumber: any) {
    this.phoneNumbers.push(phoneNumber);
    this.modalService.dismissAll();
  }

  addMobilePhoneNumber(mobilePhoneNumber: any) {
    this.mobilePhoneNumbers.push(mobilePhoneNumber);
    this.modalService.dismissAll();
  }

  hideCluster(f: any) {
    let cluster_name = this.schoolCategories.find(value => value.school_category_id === f.value.school_category_id).school_category_name;
    if (cluster_name === 'National school') {
      this.isNationalSchool = true;
    } else {
      this.isNationalSchool = false;
    }
  }

  onImageSelect(event) {
    this.ngxLoader.start();
    this.files.push(...event.addedFiles);
    const reader = new FileReader();
    if (event.addedFiles[0]) {
      this.item = event.addedFiles[0];
      this.myForm.value.name = this.item.name;
      reader.readAsDataURL(event.addedFiles[0]);
      if (event.addedFiles[0].type.indexOf('image') > -1) {
        this.format = 'image';
      } else if (event.addedFiles[0].type.indexOf('video') > -1) {
        this.format = 'video';
      }
      this.url = (event.addedFiles[0] as FileReader).result;
      reader.onload = () => {
        this.imageSrc = reader.result as string;
        this.myForm.patchValue({
          fileSource: reader.result
        });

      };

      this.reviewMetadata();
    }
    this.ngxLoader.stop();
  }

  public reviewMetadata() {
    const file: Blob = this.item;
    const fileReader = new FileReader();
    fileReader.addEventListener('load', fileReaderEvent => {
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
      headers = headers.set('Authorization', 'Bearer ' + token);
      const httpOptions = {
        headers
      };
      // this.spinner.show();

      const formData: FormData = new FormData();
      formData.append('file', this.item, this.item.name);
      const reqUrl = this.api.getAPIConfig();
      this.http.post(reqUrl + '/upload', formData, httpOptions)
        .subscribe(
          (response: any) => {
            //console.log(response)
            this.logo = (response.file_name);
            this.submit_(f);
            this.ngxLoader.stop();
          },
          (error: any) => {
            this.ngxLoader.stop();
            console.log('error ' + JSON.stringify(error));
            // this.spinner.hide();
          });

    } else {
      this.submit_(f);
      this.ngxLoader.stop();
    }
  }

  submitImage() {
    const token = this.auth.getToken();
    let headers = new HttpHeaders();
    headers = headers.set('Authorization', 'Bearer ' + token);
    const httpOptions = {
      headers
    };
    // this.spinner.show();

    const formData: FormData = new FormData();
    formData.append('file', this.item, this.item.name);
    const reqUrl = this.api.getAPIConfig();
    this.http.post(reqUrl + '/multipleimageupload', formData, httpOptions)
      .subscribe(
        (response: any) => {
          this.logo = (response.result);
          ;
        },
        (error: any) => {
          console.log('error ' + JSON.stringify(error));
          // this.spinner.hide();
        });

  }



  submit_(f) {
 
    this.school.phone_numbers = this.phoneNumbers;
    this.school.mobile_phone_numbers = this.mobilePhoneNumbers;
    this.school.org_name = f.form.value.org_name;
    this.school.code = f.form.value.code;
    this.school.logo = this.logo;    
    this.school.email_address = f.form.value.email_address;
    this.school.school_history = f.form.value.school_history;
    this.school.postal_address = f.form.value.postal_address;
    this.school.motto = f.form.value.motto;
    this.school.county = { county_id: f.form.value.county_id } as ICounty;
    this.school.sub_county = { sub_county_id: f.form.value.sub_county_id } as ISubCounty;
    this.school.ward = { ward_id: f.form.value.ward_id } as IWard;
    this.school.education_system = { education_system_id: f.form.value.education_system_id } as IEducationSystem;
    this.school.school_sponsor = { school_sponsor_id: f.form.value.school_sponsor_id } as ISponsor;
    this.school.school_level = { school_level_id: f.form.value.school_level_id } as ISchoolLevel;
    this.school.school_category = { school_category_id: f.form.value.school_category_id } as ISchoolCategory;
    this.school.gender = { school_gender_id: f.form.value.school_gender_id } as IGender;
    this.school.school_type = { school_type_id: f.form.value.school_type_id } as ISchoolType;

    this.api.post('schools/create', this.school)
      .subscribe(
        (response: any) => {
          this.ngxLoader.stop();
          this.api.showNotification('Message', response.message, null);
          this.router.navigate(['schools/list']);
          this.modalService.dismissAll();

        },
        (error: any) => { 
          this.notification.toastDanger(error.message, 'Error');
          console.log('error ' + JSON.stringify(error));
          // this.spinner.hide();
        });

  }

}
