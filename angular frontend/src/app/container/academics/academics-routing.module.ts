import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
import {ExamsComponent} from './exams/exams/exams.component';
import {AddComponent} from './exams/add-exam-type/add.component';
import {AddExamComponent} from './exams/add-exam/add-exam.component';
import {EditExamComponent} from './exams/edit-exam/edit-exam.component';
import {ViewComponent} from './exams/view-exams/view.component';
import { ExamTypesComponent } from './exam-types/exam_types.component'; 
 
import { ViewClassExamComponent } from './exam-grading/view-class-exams/view-class-exam.component';
import { ExamGradingComponent } from './exam-grading/exams-for-grading/exam-grading.component';
import { ViewClassPostedExamComponent } from './exam-grading/view-class-exams-posted/view-class-exams-posted.component';
const routes: Routes = [
  /*{
    path: 'grading',
    component: GradingComponent,
    data: { breadcrumb: "grading" }
  },*/
  {
    path: 'exams',
    component: ExamsComponent,
    data: { breadcrumb: "Exams" }
  },
  {
    path: 'add-exam-type',
    component: AddComponent,
    data: { breadcrumb: "Add Exam Type" }
  },
  {
    path: 'add-exam',
    component: AddExamComponent,
    data: { breadcrumb: "Add Exam" }
  },
  {
    path: 'edit-exam/:exam_id',
    component: EditExamComponent,
    data: { breadcrumb: "Edit Exam" }
  },
  {
    path: 'view-exam/:exam_id',
    component: ViewComponent,
    data: { breadcrumb: "Exam Scheduled" }
  },{
    path: 'exam-type',
    component: ExamTypesComponent,
    data: { breadcrumb: "Exam Type" }
  },
  {
    path: 'grading',
    component: ExamGradingComponent,
    data: { breadcrumb: "View Scheduled Exam" }
  },

  {
    path: 'grade-student/:exam_id',
    component: ViewClassExamComponent,
    data: { breadcrumb: "Grade Exam" }
  },
  {
    path: 'exam-graded-student/:exam_id',
    component: ViewClassPostedExamComponent,
    data: { breadcrumb: "View Students Graded" }
  },
  
  {
    path: '**',
    component: PageNotFoundComponent
  },
  
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [RouterModule],
  providers: [
    // ConfirmExitGuard
  ]
})
export class AcademicsRoutingModule {

}
