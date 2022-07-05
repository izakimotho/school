import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Router } from '@angular/router';
import { User } from '../Shared/Interfaces/userprofile/user.model';
import { map } from 'rxjs/operators';
const AUTH_DATA = "auth_data";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user = 'user';
  storageKey = 'token';
  username = 'username';
  permissions = 'perm';
  userPermission: [];  
  userProfile:any;
  cuk = 'bup';
  updateproject = 0;

  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;
  private subject = new BehaviorSubject<User>(null);

  user$: Observable<User> = this.subject.asObservable();

  isLoggedIn$: Observable<boolean>;
  isLoggedOut$: Observable<boolean>;

  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  showloading: boolean;
  userid: number;
  token: string;

  constructor(private router: Router) {
    
    const bup = localStorage.getItem(this.cuk) ? localStorage.getItem(this.cuk) : '{}';
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(bup));
    this.currentUser = this.currentUserSubject.asObservable();
    this.userid = 0;
    this.token = '';

    this.isLoggedIn$ = this.user$.pipe(map(user => !!user));

    this.isLoggedOut$ = this.isLoggedIn$.pipe(map(loggedIn => !loggedIn));

    const user = localStorage.getItem(AUTH_DATA);

    if (user) {
      this.subject.next(JSON.parse(user));
    }
  }




  isAuthenticated() {
    return this.loggedIn.asObservable();
  }


  login() {
    if (localStorage.getItem('token') !== '' && localStorage.getItem('token') !== 'notoken') {
      this.loggedIn.next(true);
    }
  }

  getAuthorizationToken() {
    if (localStorage.getItem('token') !== '') {
      console.log(localStorage.getItem('token'));
      return this.token;
    }
  }
  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }
  setCurrentUser(userProfile: User) {
    localStorage.setItem(this.cuk, JSON.stringify(userProfile));
    this.currentUserSubject.next(userProfile);
  }

  //store username session
  setUsername(username: string) {
    localStorage.setItem(this.username, username);
  }
  getUsername() {
    return localStorage.getItem(this.username);
  }

  //set token
  setToken(token: string) {
    localStorage.setItem(this.storageKey, token);
  }

  getToken() {
    return localStorage.getItem(this.storageKey);
  }
  setUserPermission(user_Permission: any) {
   // console.log(user_Permission) 
 
   this.userProfile=user_Permission; 
   localStorage.setItem(this.permissions, JSON.stringify(user_Permission.roles));
  }
  getUserPermission() {
    console.log(localStorage.getItem(this.permissions));
    return this.permissions;
  }

  isLoggedIn() {
    return this.getToken() !== null;
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }

}
