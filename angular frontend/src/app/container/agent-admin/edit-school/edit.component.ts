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
import {ApiService} from '../../../Services/api.services';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as EXIF from 'exif-js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '../../../Services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {NgxUiLoaderService, SPINNER} from 'ngx-ui-loader';
import { IEducationSystem } from 'src/app/Shared/Interfaces/school/IEducationSystem';

@Component({
  selector: 'app-edit-school',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {
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
  sub_county_error_message: string = '';
  ward_error_message: string = '';

  spinnerType = SPINNER.circle;

  //image
  files: File[] = [];
  item: File = null; 
  
  imageSrc: string | ArrayBuffer;

  imgURL: string | ArrayBuffer;
  imagePath: any;
  format: string;
  url: string | ArrayBuffer;
  res: { [key: string]: any };
  myForm = new FormGroup({
    fileSource: new FormControl('', [Validators.required])
  });
  org_id: string;
  logo: any;
  closeResult: string;
  constructor(
    // private spinner: NgxSpinnerService,
    private ngxLoader: NgxUiLoaderService,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router
  ) {
    this.org_id = this.activatedRoute.snapshot.params.org_id;
    console.log(this.org_id);
  }

  ngOnInit(): void {
    this.getSchool(this.org_id);
    this.getCounties();
    this.getSubCounties(null);
    this.getWards(null);
    this.getEducationSystems();
    this.getSponsor();
    this.getSchoolLevels();
    this.getSchoolCategories();
    this.getClusters();
    this.getGenderTypes();
    this.getSchoolTypes();
  }

  getSchool(org_id) {
    this.api.get('schools/school/' + org_id).subscribe(
        res => {
          this.school  = res.result;
          //console.log('this.school:: ', this.school);

          if (this.school.logo === undefined || this.school.logo === null) {
            // do something             
  
          } else {
            var url = this.api.getURL('auth');
            let imgUrl = url + '/file/' + this.school.logo;
            this.api.getImage(imgUrl).subscribe(data => {
              this.createImageFromBlob(data);
            }, error => {
              //console.log(error);
            });
          }
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

 

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageSrc = reader.result;
      //this.files.push(reader.readAsDataURL(image));
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
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
          if(countyId === null){
            this.subCounties  = res.result;
          } else {
            this.sub_county_error_message = 'County have changed. Please select sub-county that matches the new county'
            this.subCounties  = res.result.filter(value => value.county_code === countyId);
          }
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
          if(subCountyId === null){
            this.wards  = res.result;
          } else {
            this.sub_county_error_message = '';
            this.ward_error_message = 'Sub-County have changed. Please select ward that matches the new Sub-County';
            this.wards  = res.result.filter(value => value.sub_county_code === subCountyId);
          }
        },
        errResp => {
          this.ngxLoader.stopAll();
          console.error('Error' + errResp);

        }
    );
  }

  changeErrorMessage() {
    this.ward_error_message = '';
  }

  getEducationSystems() {
    this.api.get('education_system/list').subscribe(
        res => {
          this.educationSystems  = res.result;
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getSponsor() {
    this.api.get('schools/sponsors').subscribe(
        res => {
          this.sponsors  = res.result;
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getSchoolLevels() {
    this.api.get('schools/levels').subscribe(
        res => {
          this.schoolLevels  = res.result;
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getSchoolCategories() {
    this.api.get('schools/categories').subscribe(
        res => {
          this.schoolCategories  = res.result;
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getClusters() {
    this.api.get('schools/clusters').subscribe(
        res => {
          this.clusters  = res.result;
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getGenderTypes() {
    this.api.get('schools/gender').subscribe(
        res => {
          this.genderTypes  = res.result;
          console.log('genders:::: ', this.genderTypes);
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

  getSchoolTypes() {
    this.api.get('schools/types').subscribe(
        res => {
          this.schoolTypes  = res.result;
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
  }

  addMobilePhoneNumber(mobilePhoneNumber: any) {
    this.mobilePhoneNumbers.push(mobilePhoneNumber);
  }

  onImageSelect(event) {
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

  submit_(f) {
    this.ngxLoader.start();
    let payLoad: any = {};
    payLoad.org_id = this.school.org_id;
    payLoad.phone_numbers = this.phoneNumbers;
    payLoad.mobile_phone_numbers = this.mobilePhoneNumbers
    payLoad.org_name = f.form.value.org_name;
    payLoad.code = f.form.value.code;
    payLoad.logo = this.logo;    
    payLoad.email_address = f.form.value.email_address;
    payLoad.school_history = f.form.value.school_history;
    payLoad.postal_address = f.form.value.postal_address;
    payLoad.motto = f.form.value.motto;;
    payLoad.county =  {county_id: f.form.value.county_id} as ICounty;
    payLoad.sub_county =  {sub_county_id: f.form.value.sub_county_id, sub_county_name: f.form.value.sub_county_name} as ISubCounty;
    payLoad.ward =  {ward_id: f.form.value.ward_id} as IWard;
    payLoad.education_system =  {education_system_id: f.form.value.education_system_id} as IEducationSystem;
    payLoad.school_sponsor =  {school_sponsor_id: f.form.value.school_sponsor_id} as ISponsor;
    payLoad.school_level =  {school_level_id: f.form.value.school_level_id} as ISchoolLevel;
    payLoad.school_category =  {school_category_id: f.form.value.school_category_id} as ISchoolCategory;
    payLoad.gender = { school_gender_id: f.form.value.school_gender_id } as IGender;
    //payLoad.gender =  {school_gender_id: '9a755c10-9dc6-47b1-8a2f-95dd3d6a5a09'} as IGender;
    payLoad.school_type =  {school_type_id: f.form.value.school_type_id} as ISchoolType;
    //console.log('f.form.value.school_category_id:: ',f.form.value.school_category_id);
    //console.log('payLoad:: ',payLoad);
    this.ngxLoader.start();
    this.api.put( 'schools/update', payLoad)
      .subscribe(
        (response: any) => {
          this.api.showNotification('Message', response.message,null)
          this.router.navigate(['schools/list']);
          this.modalService.dismissAll();
          this.ngxLoader.stop();
        },
        (error: any) => {
          this.modalService.dismissAll();
          this.ngxLoader.stop();
          console.log('error ' + JSON.stringify(error));
        });

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
          // this.loaderData(response);
          // this.spinner.hide();
        },
        (error: any) => {
          console.log('error ' + JSON.stringify(error));
          // this.spinner.hide();
        });

  }
}
