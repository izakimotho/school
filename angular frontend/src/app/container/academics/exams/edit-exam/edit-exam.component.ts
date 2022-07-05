import { Component, OnInit } from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as EXIF from 'exif-js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';

import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import {IExamType} from '../../../../Shared/Interfaces/exam/IExamType';
import {NotificationServices} from '../../../../Services/notification.services';
import {AuthService} from '../../../../Services/auth.service';
import {ApiService} from '../../../../Services/api.services';
import {ISchool} from '../../../../Shared/Interfaces/school/ISchool';
import {IExam} from '../../../../Shared/Interfaces/exam/IExam';
import {ISubject} from '../../../../Shared/Interfaces/school/ISubjects';
import {IClasses} from '../../../../Shared/Interfaces/school/IClasses';
import {IStaff} from '../../../../Shared/Interfaces/school/IStaff';
@Component({
  selector: 'app-edit-exam',
  templateUrl: './edit-exam.component.html',
  styleUrls: ['./edit-exam.component.css']
})
export class EditExamComponent implements OnInit {
  spinnerType = SPINNER.circle;
  schools: ISchool[];
  school: ISchool = {} as ISchool;
  examType: IExamType = {} as IExamType;
  examTypes: IExamType[];
  exam: IExam = {} as IExam;
  subject: ISubject = {} as ISubject;
  subjects: ISubject[];
  class: IClasses = {} as IClasses;
  classes: IClasses[];
  staff: IStaff = {} as IStaff;
  staffs: IStaff[];
  exam_id: any;

  constructor(
    private ngxLoader: NgxUiLoaderService,
    private notification: NotificationServices,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private auth: AuthService,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router
  ) {
    this.exam_id = this.activatedRoute.snapshot.params.exam_id;
    console.log(this.exam_id);
  }

  ngOnInit(): void {
    this.getExam()
    this.getSchools()
    this.getExamTypes()
    this.getSubjects()
    this.getClasses()
    this.getStaffs()
  }

  private getExam() {
    this.ngxLoader.start();
    this.api.get('exam_schedule/' + this.exam_id).subscribe(
        res => {
          this.exam = res.result;
          this.ngxLoader.stopAll();
        },
        errResp => {
          this.ngxLoader.stopAll();
          console.error('Error' + errResp);

        }
    );
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

  private getExamTypes() {
    this.ngxLoader.start();
    this.api.get('exam_types').subscribe(
        res => {
        this.examTypes = res.result;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  private getSubjects() {
    this.ngxLoader.start();
    this.api.get('subjects').subscribe(
        res => {
        this.subjects = res.result;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  private getClasses() {
    this.ngxLoader.start();
    this.api.get('classes/list').subscribe(
        res => {
        this.classes = res.result;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.error('Error' + errResp);

      }
    );
  }

  private getStaffs() {
    this.ngxLoader.start();
    this.api.get('staff/list').subscribe(
        res => {
        this.staffs = res.result;
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
    let payLoad = {} as any;
    payLoad.exam_schedule_id = this.exam.exam_schedule_id;
    payLoad.exam_schedule_name = f.form.value.exam_schedule_name;
    payLoad.exam_type =  {exam_type_id: f.form.value.exam_type_id} as IExamType;
    payLoad.subject =  {subject_id: f.form.value.subject_id} as ISubject;
    payLoad.exam_date = f.form.value.exam_date;
    payLoad.exam_time = f.form.value.exam_time;
    payLoad.class_model =  {class_id: f.form.value.class_id} as IClasses;
    payLoad.organization =  {org_id: f.form.value.org_id} as ISchool;
    payLoad.schedule_by =  {user_id: f.form.value.user_id} as IStaff;
    console.log('f::: ', f);
    this.api.put( 'exam_schedule/update', payLoad)
      .subscribe(
        (response: any) => {
          this.ngxLoader.stop();
          this.api.showNotification('Message', response.message,null);
          this.router.navigate(["academics/exams"]);
        },
        (error: any) => {
          this.ngxLoader.stop();
          this.notification.toastDanger(error, 'Error');
          console.log('error ' + JSON.stringify(error));
          // this.spinner.hide();
        });
  }
}
