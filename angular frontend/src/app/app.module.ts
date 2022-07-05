import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA, APP_INITIALIZER } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'; 
 
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CommonModule } from '@angular/common';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { SortablejsModule } from 'ngx-sortablejs';
import { BreadcrumbModule } from 'angular-crumbs';
import { ToastrModule } from 'ngx-toastr';

import { SharedModule } from './Shared/shared.module';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PreloaderComponent } from './preloader/preloader.component';

// Services
import { ApiService } from './Services/api.services';
import { AuthService } from './Services/auth.service';
import { AuthGuard } from './Services/guard/auth-guard.guard'; 
import { AppConfigService } from './Services/app-config.service';
import { HttpClientModule } from '@angular/common/http';


import { PageNotFoundComponent } from './container/page-not-found/page-not-found.component';

// Configurations
export function appInit(appConfigService: AppConfigService) {
  return () => appConfigService.load();
}

@NgModule({
  declarations: [
    AppComponent,
    PreloaderComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule, 
    AppRoutingModule,
    BrowserAnimationsModule,
    BreadcrumbModule,
    NgbModule,CommonModule,
    ToastrModule.forRoot(),
    SortablejsModule.forRoot({ animation: 150 }),
    
    HttpClientModule,
    SharedModule
  ],
  providers: [    
    ApiService,
    AuthService, 
    AuthGuard, 
    { 
      provide: LocationStrategy, 
      useClass: HashLocationStrategy 
    },
    AppConfigService,
    {
      provide: APP_INITIALIZER,
      useFactory: appInit,
      multi: true,
      deps: [AppConfigService],
    },
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule { }
