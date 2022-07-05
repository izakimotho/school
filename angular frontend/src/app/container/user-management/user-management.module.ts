import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserManagementRoutingModule } from './user-management-routing.module';
import { SharedModule } from '../../Shared/shared.module';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap'; 
import { NgBootstrapFormValidationModule } from 'ng-bootstrap-form-validation';

import { Ng2SmartTableModule } from 'ng2-smart-table';
import { BreadcrumbModule } from 'angular-crumbs';
import { DataTablesModule } from 'angular-datatables';
//components
import { UserComponent } from './user/user.component';
import { UserRolesComponent } from './roles/user-roles/user-roles.component';
import { UserRolesPermissionsComponent } from './user-roles-permissions/user-roles-permissions.component';
import { EditUserRoleComponent } from './roles/edit-user-role/edit-user-role.component';
import { AddUserRoleComponent } from './roles/add-user-role/add-user-role.component';
import { ViewUserRoleComponent } from './roles/view-user-role/view-user-role.component';
import { UserCredentialsComponent } from './user-credentials/user-credentials.component';
import { UserProfileComponent } from './user-profile/user-profile.component';
@NgModule({
  declarations: [ 
    UserComponent,
    UserRolesComponent,
    UserRolesPermissionsComponent,
    EditUserRoleComponent,
    AddUserRoleComponent,
    ViewUserRoleComponent,
    UserProfileComponent,
    UserCredentialsComponent
  ],
  imports: [
    //CommonModule,
    FormsModule,
    ReactiveFormsModule,
    UserManagementRoutingModule,
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
export class UserManagementModule {}
