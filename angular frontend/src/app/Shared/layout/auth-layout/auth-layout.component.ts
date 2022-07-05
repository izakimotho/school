import { Component } from '@angular/core';

// declare var jQuery:any;

@Component({
  selector: 'auth-layout',
  templateUrl: 'auth-layout.template.html'
})
export class AuthLayoutComponent {
  test: Date = new Date();
  public ngOnInit():any {
   
  }
 

  // ngOnDestroy() {
  //   jQuery('body').removeClass('gray-bg');
  // }
}
