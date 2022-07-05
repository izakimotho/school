import { NgModule } from '@angular/core';
//import { CommonModule } from '@angular/common';

//import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AcademicsRoutingModule } from './academics-routing.module';
import { SharedModule } from '../../Shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';

 
import { Ng2SmartTableModule } from 'ng2-smart-table';
//components
import {ExamsComponent} from './exams/exams/exams.component';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {NotificationServices} from '../../Services/notification.services';
import {AddExamComponent} from './exams/add-exam/add-exam.component';
import {AddComponent} from './exams/add-exam-type/add.component';
import {FlatpickrModule} from 'angularx-flatpickr';
import {NgxMaterialTimepickerModule} from 'ngx-material-timepicker';
import {EditExamComponent} from './exams/edit-exam/edit-exam.component';
import {BreadcrumbModule} from 'angular-crumbs';
import {ViewComponent} from './exams/view-exams/view.component';

import { ExamTypesComponent } from './exam-types/exam_types.component'; 

import { ViewClassExamComponent } from './exam-grading/view-class-exams/view-class-exam.component';
import { ExamGradingComponent } from './exam-grading/exams-for-grading/exam-grading.component';
import { ViewClassPostedExamComponent } from './exam-grading/view-class-exams-posted/view-class-exams-posted.component';
@NgModule({
  declarations: [ 
    ExamsComponent,  AddComponent, AddExamComponent, EditExamComponent,  ViewComponent,
    ExamTypesComponent,
    ExamGradingComponent,ViewClassExamComponent,ViewClassPostedExamComponent
  ],
    imports: [
        //CommonModule,
        NgxMaterialTimepickerModule.setLocale('ar-AE'),
        FormsModule,
        ReactiveFormsModule,
        AcademicsRoutingModule,
        SharedModule,
        CommonModule,
        NgbModule,
        Ng2SmartTableModule,
        ReactiveFormsModule,
        NgBootstrapFormValidationModule.forRoot(),
        NgBootstrapFormValidationModule,
        SweetAlert2Module.forRoot(),
        FlatpickrModule.forRoot(),
        BreadcrumbModule
    ],
  providers: [
    NotificationServices
    //CoursesService
  ],
})
export class AcademicsModule {}
