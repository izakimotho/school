import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { AuthService } from '../auth.service';

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService, 
    private router: Router
    ) { 

    }
     

 
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      const currentUser = this.authService.currentUserValue;
          // authorised so return true
      if (!currentUser) {
              this.router.navigate(['/auth/login']);
              return false;
          }
      return true;

  }

canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
boolean | Observable<boolean> | Promise<boolean> {
  return this.canActivate(route, state);
}



checkTokenExpiration() {
  // checking whether your token has expired
   
  return false || true


  // const token = this.getToken();
  // return token
  //   ? this.http.post("https://jsonplaceholder.typicode.com/users", { verify: this.token }).pipe(
  //       tap(res => (localStorage.data = JSON.stringify(res))),
  //       map(res => true, error => false)
  //     )
  //   : of(false);
}

canLoad() {
return this.checkTokenExpiration()
}

}
