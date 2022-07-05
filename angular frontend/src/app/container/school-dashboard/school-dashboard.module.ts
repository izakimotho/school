import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {DataTablesModule} from 'angular-datatables';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgxDropzoneModule} from 'ngx-dropzone';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import {ChartsModule} from 'ng2-charts';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {SchoolDashboardComponent} from './school-dashboard.component';
import {SchoolDashboardRoutingModule} from './school-dashboard-routing.module';
import {SharedModule} from '../../Shared/shared.module';

@NgModule({
  declarations: [
    SchoolDashboardComponent
  ],
  imports: [
    CommonModule,
    SchoolDashboardRoutingModule,
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
    PerfectScrollbarModule
  ],
  providers: [
    // NgBootstrapFormValidationModule
  ]
})
export class SchoolDashboardModule { }
declare module '@angular/core' {
  interface ModuleWithProviders<T = any> {
      ngModule: Type<T>;
      providers?: Provider[];
  }
}
