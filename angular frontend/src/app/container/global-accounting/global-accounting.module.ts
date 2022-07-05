import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../Shared/shared.module';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';

import { Ng2SmartTableModule } from 'ng2-smart-table';
import { BreadcrumbModule } from 'angular-crumbs';
import { DataTablesModule } from 'angular-datatables';


// Modules
import { GlobalAccountingRoutingModule } from './global-accounting-routing.module';
 
//components
import { PaymentTypeComponent } from './payment-type/payment-type.component';

@NgModule({
  declarations: [     
   PaymentTypeComponent, 
],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    GlobalAccountingRoutingModule,
    SharedModule,
    CommonModule,
    NgbModule,BreadcrumbModule,
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
export class GlobalAccountingModule {}
