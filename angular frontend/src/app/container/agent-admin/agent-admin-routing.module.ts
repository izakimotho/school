import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SchoolListComponent} from './schools-list/school-list.component';
import {ClassesComponent} from './classes/classes.component';
import { SubjectComponent } from './subject/subject.component';
import {AddComponent} from './add-school/add.component';
import {EditComponent} from './edit-school/edit.component';
import {ViewComponent} from './view-school/view.component';

import { EducationSystemComponent } from './education-system/education-system.component';

const routes: Routes = [
  {
    path: 'list',
    component: SchoolListComponent,
    data: { breadcrumb: "List School" }
  },
  {
    path: 'add',
    component: AddComponent,
    data: { breadcrumb: "Add School" }
  },
  {
    path: 'edit/:org_id',
    component: EditComponent,
    data: { breadcrumb: "Edit School" }
  },
  {
    path: 'view/:org_id',
    component: ViewComponent,
    data: { breadcrumb: "view School" }
  },

  {
    path: 'education_system',
    component: EducationSystemComponent,
    data: { breadcrumb: "Education System" }
  },

  
  {
    path: 'classes',
    component: ClassesComponent,
    data: { breadcrumb: "classes" }
  },
  {
    path: 'subjects',
    component: SubjectComponent,
    data: { breadcrumb: "List Subjects" }
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AgentAdminRoutingModule { }
