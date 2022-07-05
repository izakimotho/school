import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { AgentAdminRoutingModule } from './agent-admin-routing.module';
import { SharedModule } from 'src/app/Shared/shared.module';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { DataTablesModule } from 'angular-datatables';
import { NgSelectModule } from '@ng-select/ng-select';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { ChartsModule } from 'ng2-charts';
import { PerfectScrollbarModule } from 'ngx-perfect-scrollbar';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { NotificationServices } from '../../Services/notification.services';
import { FlatpickrModule } from 'angularx-flatpickr';
import { BreadcrumbModule } from 'angular-crumbs';


// COmponents
import { SchoolListComponent } from './schools-list/school-list.component';
import { ClassesComponent } from './classes/classes.component';
import { SubjectComponent } from './subject/subject.component';
import { AddComponent } from './add-school/add.component';
import { EditComponent } from './edit-school/edit.component';
import { ViewComponent } from './view-school/view.component';
import { EducationSystemComponent } from './education-system/education-system.component';

@NgModule({
  declarations: [
    SchoolListComponent,
    SubjectComponent,
    ClassesComponent,
    AddComponent,
    EditComponent, 
    ViewComponent,EducationSystemComponent
  ],
  imports: [
    CommonModule,
    AgentAdminRoutingModule,
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

  ],
  providers: [
    NotificationServices
    // NgBootstrapFormValidationModule
  ]
})
export class AgentAdminModule { }
declare module '@angular/core' {
  interface ModuleWithProviders<T = any> {
    ngModule: Type<T>;
    providers?: Provider[];
  }
}
