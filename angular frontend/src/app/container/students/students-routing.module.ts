import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StudentsListComponent} from './students-list/students-list.component';
import {AddComponent} from './add-student/add.component';
import {EditComponent} from './edit-student/edit.component';
import {ViewComponent} from './view-student/view.component';
import { PageNotFoundComponent } from '../page-not-found/page-not-found.component';

const routes: Routes = [
  {
    path: 'list',
    component: StudentsListComponent,
    data: { breadcrumb: "List Students" }
  },
  {
    path: 'add',
    component: AddComponent,
    data: { breadcrumb: "Add Student" }
  },
  {
    path: 'edit/:student_id',
    component: EditComponent,
    data: { breadcrumb: "Edit Student" }
  },
  {
    path: 'view/:student_id',
    component: ViewComponent,
    data: { breadcrumb: "View Student" }
  },
  {
    path: '**',
    component: PageNotFoundComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class StudentsRoutingModule { }
