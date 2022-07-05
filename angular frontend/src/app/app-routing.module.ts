import { NgModule } from '@angular/core';
import { RouterModule, Routes, UrlSerializer } from '@angular/router';
import { CustomPreloadingStrategy } from './Services/custom-preloading.strategy';

import { BasicLayoutComponent } from './Shared/layout/basic-layout/basicLayout.component';
const routes: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: '',
    children: [
      {
        path: 'auth',
        loadChildren: () => import('./container/Auth/auth.module').then(module => module.AuthModule),
        // data: { breadcrumbs: ['auth'] }
      }
    ]
  },

  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'analytics',
        loadChildren: () => import('./container/dashboard/dashboard.module').then(module => module.DashboardModule),
        // data: { breadcrumbs: ['analytics'] }
       
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'dashboard',
        loadChildren: () => import('./container/school-dashboard/school-dashboard.module').then(module => module.SchoolDashboardModule)         
        , data: { 
          //breadcrumb: "dashboard"
          preload: false
        }
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'schools',
        loadChildren: () => import('./container/agent-admin/agent-admin.module').then(module => module.AgentAdminModule),
        // data: { breadcrumbs: 'schools' }
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'students',
        loadChildren: () => import('./container/students/students.module').then(module => module.StudentsModule),
        // data: { breadcrumb: "students" }
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'academics',
        loadChildren: () => import('./container/academics/academics.module').then(module => module.AcademicsModule),
        // data: { breadcrumb: "academics" }
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'school',
        loadChildren: () => import('./container/school-admin/school-admin.module').then(module =>module.SchoolAdminModule),
        // data: { breadcrumb: "academics" }
      }
    ]
  },

  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'schoolset',
        loadChildren: () => import('./container/school-settings/school-settings.module').then(module =>module.SchoolSettingsModule)         
        ,data: { 
          //breadcrumb: "School Administration"
          preload: false
        }
      }
    ]
  },

  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'finance',
        loadChildren: () => import('./container/finance/finance.module').then(module =>module.FinanceModule)         
        ,data: { 
          //breadcrumb: "School Administration"
          preload: false
        }
      }
    ]
  },
  {
    path: '',
    children: [
      {
        component: BasicLayoutComponent,
        path: 'user',
        loadChildren: () => import('./container/user-management/user-management.module').then(module =>module.UserManagementModule)         
        ,data: { 
          //breadcrumb: "School Administration"
          preload: false
        }
      }
    ]
  },

  ];

@NgModule({
  imports: [RouterModule.forRoot(
    routes 
    // ,{
    //   preloadingStrategy: CustomPreloadingStrategy,
    //   scrollPositionRestoration:'enabled',
    //   paramsInheritanceStrategy: 'always',
    //   relativeLinkResolution: 'corrected',
    //   // malformedUriErrorHandler:
    //   //     (_error: URIError, urlSerializer: UrlSerializer, url:string) =>
    //   //       urlSerializer.parse("/page-not-found")
    // }
    )],
  exports: [RouterModule],
  providers: [
      //CustomPreloadingStrategy
  ]
})
export class AppRoutingModule { }
