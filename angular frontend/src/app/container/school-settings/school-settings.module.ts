import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SchoolSettingsRoutingModule } from './school-settings-routing.module';
import { SharedModule } from '../../Shared/shared.module';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';

import { Ng2SmartTableModule } from 'ng2-smart-table';

import { DataTablesModule } from 'angular-datatables';
//components
import { GradingComponent } from './grading/grading.component';
import { ClassesComponent } from './classes/classes.component';
import { AcademicYearComponent } from './academicyear/academic-year.component';

@NgModule({
  declarations: [ 
    GradingComponent,
    ClassesComponent,
    AcademicYearComponent
  ],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SchoolSettingsRoutingModule,
    SharedModule,
    CommonModule,
    NgbModule,
    Ng2SmartTableModule,
    DataTablesModule,
    ReactiveFormsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule
  ],
  providers: [
    //CoursesService
  ],
})
export class SchoolSettingsModule {}
