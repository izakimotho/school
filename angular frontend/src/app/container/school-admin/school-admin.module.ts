
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { DataTablesModule } from 'angular-datatables';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { ChartsModule } from 'ng2-charts';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { SchoolAdminRoutingModule } from './school-admin-routing.module';
import { SharedModule } from 'src/app/Shared/shared.module';
import { ColorPickerModule } from 'ngx-color-picker';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { NotificationServices } from '../../Services/notification.services';

import { FullCalendarModule } from '@fullcalendar/angular'; 
import interactionPlugin from '@fullcalendar/interaction';
import dayGridPlugin from '@fullcalendar/daygrid';

FullCalendarModule.registerPlugins([ 
  interactionPlugin,
  dayGridPlugin
]);
import { BreadcrumbModule } from 'angular-crumbs';
// Components
import { HostelComponent } from './hostel/hostel.component';
import { AcademicYearComponent } from './academicyear/academic-year.component';
import { ClassesComponent } from './classes/classes.component';
import { GradingComponent } from './grading/grading.component';
import { PositionsComponent } from './positions/positions.component';
import { CalenderComponent } from './calender/calender.component';
import { CalenderEventsComponent } from './calender_events/calender_events.component';
import { FullCallenderComponent } from './full-callender/full-callender.component';
import { StaffComponent } from './staffs/staff/staff.component';
import { AddStaffComponent } from './staffs/add-staff/add-staff.component';
import { ViewStaffComponent } from './staffs/view-staff/view-staff.component';
import { EditStaffComponent } from './staffs/edit-staff/edit-staff.component';
import { SessionsComponent } from './sessions/sessions.component';
import { TermDetailsComponent } from './term-details/term-details.component';
import { SchoolSubjectComponent } from './school-subject/school-subject.component';

 
 
@NgModule({
  declarations: [
    GradingComponent,
    ClassesComponent,SchoolSubjectComponent,
    AcademicYearComponent,
    HostelComponent,
    StaffComponent,AddStaffComponent,
    PositionsComponent,
    CalenderComponent,
    CalenderEventsComponent,
    FullCallenderComponent,
    ViewStaffComponent,
    EditStaffComponent,SessionsComponent,TermDetailsComponent
  ],
  imports: [
    CommonModule,
    SchoolAdminRoutingModule,
    SharedModule,
    NgbModule,
    FormsModule,
    Ng2SmartTableModule,
    ReactiveFormsModule,
    NgxDatatableModule,
    DataTablesModule,
    NgSelectModule,
    NgxDropzoneModule,
    NgxUiLoaderModule,
    ChartsModule,
    PerfectScrollbarModule,
    SweetAlert2Module.forRoot(),
    BreadcrumbModule,
    ColorPickerModule,
    FullCalendarModule
  ],
  providers: [
    NotificationServices
    // NgBootstrapFormValidationModule
  ]
})
export class SchoolAdminModule { }
declare module '@angular/core' {
  interface ModuleWithProviders<T = any> {
    ngModule: Type<T>;
    providers?: Provider[];
  }
}
