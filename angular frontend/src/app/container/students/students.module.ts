import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {Ng2SmartTableModule} from 'ng2-smart-table';
import {StudentsRoutingModule} from './students-routing.module';
import {SharedModule} from 'src/app/Shared/shared.module';
import {NgxDatatableModule} from '@swimlane/ngx-datatable';
import {DataTablesModule} from 'angular-datatables';
import {NgSelectModule} from '@ng-select/ng-select';
import {NgxDropzoneModule} from 'ngx-dropzone';
import {NgxUiLoaderModule} from 'ngx-ui-loader';
import {ChartsModule} from 'ng2-charts';
import {PerfectScrollbarModule} from 'ngx-perfect-scrollbar';
import {SweetAlert2Module} from '@sweetalert2/ngx-sweetalert2';
import {NotificationServices} from '../../Services/notification.services';

import {StudentsListComponent} from './students-list/students-list.component';
import {AddComponent} from './add-student/add.component';
import {FlatpickrModule} from 'angularx-flatpickr';
import {EditComponent} from './edit-student/edit.component';
import {ViewComponent} from './view-student/view.component';
import {BreadcrumbModule} from 'angular-crumbs';
 
@NgModule({
    declarations: [
        StudentsListComponent,
        AddComponent,
        EditComponent,
        ViewComponent
    ],
    imports: [
        CommonModule,
        StudentsRoutingModule,
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
        FlatpickrModule.forRoot(),
        BreadcrumbModule
    ],
    providers: [
        NotificationServices
        // NgBootstrapFormValidationModule
    ]
})
export class StudentsModule {
}

declare module '@angular/core' {
    interface ModuleWithProviders<T = any> {
        ngModule: Type<T>;
        providers?: Provider[];
    }
}
