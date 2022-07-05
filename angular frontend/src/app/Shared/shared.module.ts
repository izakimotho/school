import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CommonModule } from "@angular/common";


// Modules 
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgbPopoverModule, NgbAccordion } from '@ng-bootstrap/ng-bootstrap' 
import { SortablejsModule } from 'ngx-sortablejs'; 
import { BreadcrumbModule } from 'angular-crumbs'; 
import { PerfectScrollbarModule, PerfectScrollbarConfigInterface, PERFECT_SCROLLBAR_CONFIG } from 'ngx-perfect-scrollbar';
const DEFAULT_PERFECT_SCROLLBAR_CONFIG: PerfectScrollbarConfigInterface = {
  suppressScrollX: true
};
 
// Services
import { MessagesService } from "./messages/messages.service";
import { LoadingService } from "./loading/loading.service";


 // Shared Components
import { BasicLayoutComponent } from './layout/basic-layout/basicLayout.component';
import { TopbarComponent } from './layout/topbar/topbar.component';
import { SidenavComponent } from './layout/sidenav/sidenav.component';  
import { BreadcrumbComponent } from './layout/breadcrumb/breadcrumb.component';


//Pipes
import { SafeUrlPipe } from "./pipes/safe-url.pipe";
 
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { DataTablesModule } from 'angular-datatables';
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';
import { Ng2SmartTableModule } from 'ng2-smart-table';
import { NgxDropzoneModule } from 'ngx-dropzone';
import { NgxUiLoaderModule } from 'ngx-ui-loader';
import { NgSelectModule } from '@ng-select/ng-select';





@NgModule({
  declarations: [
    TopbarComponent, 
    SidenavComponent,   
    BreadcrumbComponent,
    BasicLayoutComponent,
    SafeUrlPipe,
  ],
  imports: [
    NgbModule, 
    NgbPopoverModule,  
    RouterModule,
    CommonModule,  
    PerfectScrollbarModule,
    SortablejsModule,
    BreadcrumbModule,

    NgSelectModule,
    Ng2SmartTableModule,
    DataTablesModule,
    NgBootstrapFormValidationModule.forRoot(),
    NgBootstrapFormValidationModule,
   
    NgxDropzoneModule,
    NgxUiLoaderModule,
    PerfectScrollbarModule,
    SweetAlert2Module.forRoot()
  ],
  exports: [
    NgbModule,
    RouterModule,
    CommonModule, 
    SafeUrlPipe,
    NgbPopoverModule, 
    Ng2SmartTableModule,
    DataTablesModule, 
    NgBootstrapFormValidationModule,
   
    NgxDropzoneModule,
    NgxUiLoaderModule,
    PerfectScrollbarModule, 
    NgSelectModule,
    TopbarComponent, 
    SidenavComponent, 
    BreadcrumbComponent,
    BasicLayoutComponent
  ]
  ,
  providers: [
    MessagesService,
    LoadingService,
    {
      provide: LocationStrategy,
      useClass: HashLocationStrategy,

    },   
    {
      provide: PERFECT_SCROLLBAR_CONFIG,
      useValue: DEFAULT_PERFECT_SCROLLBAR_CONFIG
    }
  ]

})


export class SharedModule {
  static forRoot() {
    return {
      ngModule: SharedModule,
      providers: [

      ],
    };
  }

}
