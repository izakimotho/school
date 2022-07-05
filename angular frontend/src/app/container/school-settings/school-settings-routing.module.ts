import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { GradingComponent } from './grading/grading.component';
import { ClassesComponent } from './classes/classes.component';
import { AcademicYearComponent } from './academicyear/academic-year.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';
 
const routes: Routes = [
  {
    path: 'grading',
    component: GradingComponent,
  },
  {
    path: 'classes',
    component: ClassesComponent,
  },
  {
    path: 'academic-year',
    component: AcademicYearComponent,
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
export class SchoolSettingsRoutingModule {

}
