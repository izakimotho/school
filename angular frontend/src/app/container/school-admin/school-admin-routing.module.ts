import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { PageNotFoundComponent } from './../page-not-found/page-not-found.component';
import { GradingComponent } from './grading/grading.component';
import { ClassesComponent } from './classes/classes.component';
import { HostelComponent } from './hostel/hostel.component';
import { AcademicYearComponent } from './academicyear/academic-year.component'; 
import { PositionsComponent } from './positions/positions.component';
import { CalenderComponent } from './calender/calender.component';
import { CalenderEventsComponent } from './calender_events/calender_events.component';
import { StaffComponent } from './staffs/staff/staff.component';
import { AddStaffComponent } from './staffs/add-staff/add-staff.component';
import { ViewStaffComponent } from './staffs/view-staff/view-staff.component';
import { EditStaffComponent } from './staffs/edit-staff/edit-staff.component';
import { SessionsComponent } from './sessions/sessions.component';
import { TermDetailsComponent } from './term-details/term-details.component';
import { SchoolSubjectComponent } from './school-subject/school-subject.component';


const routes: Routes = [
  {
    path: 'hostel',
    component: HostelComponent,
    data: { breadcrumb: "Hostel"}
  },
  {
    path: 'classes',
    component: ClassesComponent,
    data: { breadcrumb: "Class Stream"}
  },
  {
    path: 'school-subject',
    component: SchoolSubjectComponent,
    data: { breadcrumb: "School Subject"}
  },
  

  {
    path: 'positions',
    component: PositionsComponent,
    data: { breadcrumb: "Leadership Positions"}
  },
  {
    path: 'sessions',
    component: SessionsComponent,
    data: { breadcrumb: "Term/Sessions"}
  },
  {
    path: 'term-details',
    component: TermDetailsComponent,
    data: { breadcrumb: "Term/Sessions"}
  },

  
  {
    path: 'grading',
    component: GradingComponent,
    data: { breadcrumb: "Exam Grading"}
  },
  {
    path: 'academic-year',
    component: AcademicYearComponent,
    data: { breadcrumb: "School Academic Year"}
  }, 
  {
    path: 'calender',
    component: CalenderComponent,
    data: { breadcrumb: "School Calender"}
  }, 
 
  {
    path: 'calender_events',
    component: CalenderEventsComponent,
    data: { breadcrumb: "School Calender Events"}
  }, 
  // staff start
  {
    path: 'staff',
    component: StaffComponent,
    data: { breadcrumb: "Staff"}
  },
  {
    path: 'add-staff',
    component: AddStaffComponent,
    data: { breadcrumb: "Add Staff"}
  },
  {
    path: 'view-staff/:staff_id',
    component: ViewStaffComponent,
    data: { breadcrumb: "View Staff Details"}
  },
  {
    path: 'edit-staff/:staff_id',
    component: EditStaffComponent,
    data: { breadcrumb: "Edit Staff"}
  },
  // staff end

  
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
export class SchoolAdminRoutingModule {

}
