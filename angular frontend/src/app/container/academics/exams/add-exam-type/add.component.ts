import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as EXIF from 'exif-js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';

import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import {IExamType} from '../../../../Shared/Interfaces/exam/IExamType';
import {NotificationServices} from '../../../../Services/notification.services';
import {AuthService} from '../../../../Services/auth.service';
import {ApiService} from '../../../../Services/api.services';
import {ISchool} from '../../../../Shared/Interfaces/school/ISchool';
@Component({
  selector: 'app-add-student',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent implements OnInit {
  spinnerType = SPINNER.circle;
  schools: ISchool[];
  school: ISchool = {} as ISchool;
  examType: IExamType = {} as IExamType;

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private notification: NotificationServices,
    private http: HttpClient,
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
  ) { }

  ngOnInit(): void {
    this.getSchools()
  }

  private getSchools() {
    this.ngxLoader.start();
    this.api.get('schools/list').subscribe(
        res => {
        this.schools = res.result;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  open(content: any) {
    this.modalService.open(content, { centered: true });
  }

  submit(f) {
    this.ngxLoader.start();
    this.examType.exam_type_name = f.form.value.exam_type_name;
    this.examType.description = f.form.value.description;
    this.examType.organization =  {org_id: f.form.value.org_id} as ISchool;
    console.log('f::: ', f);
    this.api.post( 'exam_type/create', this.examType)
      .subscribe(
        (response: any) => {
          this.ngxLoader.stop();
          this.api.showNotification('Message', response.message,null);
        },
        (error: any) => {
          this.ngxLoader.stop();
          this.notification.toastDanger(error, 'Error');
          console.log('error ' + JSON.stringify(error));
          // this.spinner.hide();
        });
  }
}
