import { NgModule } from '@angular/core';
//import { CommonModule } from '@angular/common';

//import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { SharedModule } from '../../Shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';
 
import { ChartsModule } from 'ng2-charts';
import { PerfectScrollbarModule, PerfectScrollbarConfigInterface, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';


const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};


//components
import { CoreDashboardComponent } from './core/core-dashboard.component';

@NgModule({
  declarations: [ 
    CoreDashboardComponent
  ],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    DashboardRoutingModule,
    SharedModule,
    CommonModule,
    NgbModule,

    PerfectScrollbarModule,
    ChartsModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule
  ],
  providers: [
    //CoursesService
  ],
})
export class DashboardModule {}
