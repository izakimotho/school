import { Component, OnInit } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { IExamType } from '../../../../Shared/Interfaces/exam/IExamType';
import { NotificationServices } from '../../../../Services/notification.services';
import { ApiService } from '../../../../Services/api.services';
import { IExam } from '../../../../Shared/Interfaces/exam/IExam';
import { IClasses } from '../../../../Shared/Interfaces/school/IClasses';
import { ISchoolSubjects } from 'src/app/Shared/Interfaces/school/ISchoolSubjects';
import { ISubject } from 'src/app/Shared/Interfaces/school/ISubjects';
import { Router } from '@angular/router';
@Component({
  selector: 'app-add-exam',
  templateUrl: './add-exam.component.html',
  styleUrls: ['./add-exam.component.css']
})
export class AddExamComponent implements OnInit {
  spinnerType = SPINNER.circle;
  examType: IExamType = {} as IExamType;
  examTypes: IExamType[];
  exam: IExam = {} as IExam;

  schoolSubject: ISchoolSubjects = {} as ISchoolSubjects;
  schoolSubjects: ISchoolSubjects[];

  subject: ISubject = {} as ISubject;
  subjects: ISubject[];

  class: IClasses = {} as IClasses;
  classes: IClasses[];
  timePicker: Date = null;
  constructor(
    private ngxLoader: NgxUiLoaderService,
    private notification: NotificationServices,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.getExamTypes()
    this.getSubjects()
    this.getClasses()
  }



  private getExamTypes() {
    this.ngxLoader.start();
    this.api.get('school/exam_types').subscribe(
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
    this.api.get('school_subjects').subscribe(
      res => {
        this.schoolSubjects = res.result;
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


  open(content: any) {
    this.modalService.open(content, { centered: true });
  }

  submit(f) {
    this.ngxLoader.start();
    console.log(f.form.value.exam_type_id)
    this.exam.exam_schedule_name = f.form.value.exam_schedule_name;
    this.exam.exam_type = { exam_type_id: f.form.value.exam_type_id } as IExamType;
    this.exam.subject = { school_subject_id: f.form.value.school_subject_id } as ISchoolSubjects;
    this.exam.exam_date = f.form.value.exam_date;
    this.exam.exam_time = f.form.value.exam_time;
    this.exam.class_model = { class_id: f.form.value.class_id } as IClasses;
    //  console.log('f::: ', f);
    this.api.post('exam_schedule/create', this.exam)
      .subscribe(
        (response: any) => {
          this.ngxLoader.stop();
          this.api.showNotification('Message', response.message, null);

        this.router.navigate(["academics/exams"]);
        },
        (error: any) => {
          this.ngxLoader.stop();
          this.notification.toastDanger(error, 'Error');
          console.log('error ' + JSON.stringify(error));
        });
  }
}
