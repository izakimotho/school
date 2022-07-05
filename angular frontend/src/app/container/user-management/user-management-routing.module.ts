import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";

import { PageNotFoundComponent } from "../page-not-found/page-not-found.component";
import { UserComponent } from "./user/user.component";
import { UserRolesComponent } from "./roles/user-roles/user-roles.component";
import { EditUserRoleComponent } from "./roles/edit-user-role/edit-user-role.component";

import { UserRolesPermissionsComponent } from "./user-roles-permissions/user-roles-permissions.component";
import { AddUserRoleComponent } from "./roles/add-user-role/add-user-role.component";
import { ViewUserRoleComponent } from "./roles/view-user-role/view-user-role.component";
import { UserCredentialsComponent } from './user-credentials/user-credentials.component';

import { UserProfileComponent } from "./user-profile/user-profile.component";
const routes: Routes = [
  {
    path: "users",
    component: UserComponent,
    data: { breadcrumb: "Users" },
  },
  {
    path: "user-profile",
    component: UserProfileComponent,
    data: { breadcrumb: "Users" },
  },
  {
    path: "user-cred",
    component: UserCredentialsComponent,
    data: { breadcrumb: "Users" },
  },

  
  {
    path: "view-user/:user_id",
    component: UserComponent,
    data: { breadcrumb: "Users" },
  },
  {
    path: "roles",
    component: UserRolesComponent,
    data: { breadcrumb: "Users Roles" },
  },
  {
    path: "add-role",
    component: AddUserRoleComponent,
    data: { breadcrumb: "Add Role Details" },
  },
  {
    path: "edit-role/:role_id",
    component: EditUserRoleComponent,
    data: { breadcrumb: "Edit Role Details" },
  },
  {
    path: "view-role/role_id",
    component: ViewUserRoleComponent,
    data: { breadcrumb: "Role Details" },
  },
  {
    path: "**",
    component: PageNotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    // ConfirmExitGuard
  ],
})
export class UserManagementRoutingModule {}
