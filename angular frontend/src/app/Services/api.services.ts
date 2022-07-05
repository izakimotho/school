import { Injectable } from '@angular/core';
import { environment } from './../../environments/environment';
import { Observable } from 'rxjs';
import { throwError, of } from 'rxjs';
// import { _throw as throwError, _throw } from 'rxjs/observable/throw';
import { catchError, tap } from 'rxjs/operators';

import { HttpClient, HttpParams, HttpHeaders, HttpErrorResponse, HttpRequest, HttpEvent } from '@angular/common/http';

import { AuthService } from './auth.service';
import { AppConfigService } from './app-config.service';


declare const $: any;
import Swal from 'sweetalert2/dist/sweetalert2.js';
import { UserPermission } from '../Shared/Interfaces/userprofile/userpermission';
@Injectable()
export class ApiService {


  userProfile: UserPermission;
  constructor(
    // tslint:disable-next-line: variable-name
    private _httpClient: HttpClient,
    private config: AppConfigService,
    private auth: AuthService) {

  }

  getAPIConfig() {
    const apiurl = this.config.getAPIUrl();
    return apiurl;
  }

  setUserPermission(user_Permission: any) { 
    this.userProfile= user_Permission.roles;
  }
  getUserPermission() {   
    return this.userProfile.roles;
  }
  getURL(service: string) {
    // let port;
    // if (service === 'auth') {
    //   port = this.config.getAuthPort();
    // } else if (service === 'ads') {
    //   port = this.config.getAdsPort();
    // } else if (service === 'bulksms') {
    //   port = this.config.getBulksmsPort();
    // } else if (service === 'vendor') {
    //   port = this.config.getVendorPort();
    // }

    const apiurl = this.config.getAPIUrl();
    // const url = `${apiurl}:${port}`; // `${apiurl}`;
    // return url;
    return apiurl;


  }

  signout() {
    this.auth.logout();
  }

  get(url: string) {
    const reqUrl = this.getAPIConfig();
    //return this.request(`http://159.65.181.207:8080/api/${url}`, 'GET');
    return this.request(`${reqUrl}/${url}`, 'GET');
  }

  post(url: string, body: Object) {
    const reqUrl = this.getAPIConfig();
    return this.request(`${reqUrl}/${url}`, 'POST', body);
  }

  postForm(url: string, mapVals: Map<string, string>) {
    const reqUrl = this.getAPIConfig();
    return this.requestForm(`${reqUrl}/${url}`, 'POST', mapVals);
  }

  pushFile(url: string, file: File): Observable<any> {
    const reqUrl = this.getAPIConfig();
    return this.requestPushFile(`${reqUrl}/${url}`, file);
  }
  put(url: string, body: Object) {
    const reqUrl = this.getAPIConfig();
    return this.request(`${reqUrl}/${url}`, 'PUT', body);
  }

  patch(url: string, body: Object) {
    const reqUrl = this.getAPIConfig();
    return this.request(`${reqUrl}/${url}`, 'PATCH', body);
  }

  delete(url: string) {
    const reqUrl = this.getAPIConfig();
    return this.request(`${reqUrl}/${url}`, 'DELETE');
  }

  request(reqUrl: string, method: string, body?: Object): Observable<any> {
    const url = `${reqUrl}`; // `${API_URL}/${reqUrl}`;

    const token = this.auth.getToken();
    // const token = 'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb290QGxvY2FsaG9zdCIsImlhdCI6MTYyNTQ2NDgwMCwiZXhwIjoxNjI1NTUxMjAwfQ.3A4xsuD7vZ6PlqtZF_-os6bTH5RI4l8GNmnBZhZYO6TEP7B-DUcMONH0bKZlTZzU5FfDi7mhCev-zsFzj0VsaA';
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    if (!headers.has('Content-Type')) {
      headers = headers.set('Content-Type', 'application/json');
    }

    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    // Access-Control-Allow-Origin = http://localhost:4200
    headers = headers.set('Access-Control-Allow-Origin', '*');
    headers = headers.set('Access-Control-Allow-Credentials', 'true');
    // headers = headers.set('Access-Control-Allow-Methods', 'HEAD, GET, POST,OPTIONS, PUT, PATCH, DELETE');
    headers = headers.set('Access-Control-Allow-Headers', 'Origin, Content-Type');


    const params = new HttpParams();
    // params = params.set('Authorization', 'Token' + this.auth.getToken());
    // params = params.set('Authorization', 'Token 5f3e92df8f973ccbbae3ef7fc0a3231322ceb87e');


    const reqOpt = {
      body: body ? body : {},
      headers
    };

    if (body) { } else {
      delete reqOpt.body;
    }
    // if (at) { } else {
    //   delete reqOpt.params;
    // }
    return this._httpClient.request<any>(method, url, reqOpt)
      .pipe(
        tap(data => {
          // console.log('server data:', data);
        }),
        catchError(this.handleError(url))
      );
  }



  // postForm1(reqUrl: string,  mapVals: Map<string, string>): Observable<any> {
  requestForm(reqUrl: string, method: string, mapVals: Map<string, string>): Observable<any> {
    const url = `${reqUrl}`; // `${API_URL}/${reqUrl}`;
    // console.log("APi service POST FORM");
    const data: FormData = new FormData();
    // Using object destructuring
    for (const [key, value] of mapVals) {
      // console.log(key, value);
      data.append(key, value);
    }
    const token = this.auth.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }

    // headers = headers.set('Content-Type', 'multipart/form-data');
    const newRequest = new HttpRequest(method, url, data, {
      reportProgress: true,
      responseType: 'text',
      headers
    });

    return this._httpClient.request<any>(newRequest)
      .pipe(
        tap(data => {
          // console.log('server data:', data);
        }),
        catchError(this.handleError(url))
      );
  }
  private handleError(operation: string) {
    return (err: any) => {
      let errResp = {};
      if (err instanceof HttpErrorResponse) {
        errResp = {
          op: operation,
          status: err.status,
          statusText: err.statusText,
          error: err.error.error
        };
      }
      if (err.status === 401) {
        this.auth.logout();
      }
      return throwError(errResp);
    };
  }




  requestPushFile(reqUrl: string, file: File): Observable<any> {
    const url = `${reqUrl}`;
    // console.log("APi service");
    const data: FormData = new FormData();
    if (file.type.indexOf('image') > -1) {
      data.append('files', file);
    } else if (file.type.indexOf('video') > -1) {
      data.append('file', file);
    }
    // data.append('files', file);

    const token = this.auth.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    headers = headers.set('Access-Control-Allow-Credentials', 'true');
    // headers = headers.set('Content-Type', 'multipart/form-data');

    headers = headers.set('Access-Control-Allow-Origin', '*');
    // headers = headers.set('Access-Control-Allow-Credentials', 'true');
    // headers = headers.set('Access-Control-Allow-Methods', 'HEAD, OPTIONS, GET, POST,PUT, PATCH, DELETE');
    // headers = headers.set('Access-Control-Allow-Headers', 'Origin, Content-Type');
    // headers = headers.set('Content-Type', 'multipart/form-data');
    const newRequest = new HttpRequest('POST', url, data, {
      reportProgress: true,
      responseType: 'text',
      headers
    });

    return this._httpClient.request<any>(newRequest)
      .pipe(
        tap(data => {
          // console.log('server data:', data);
        }),
        catchError(this.handleError(url))
      );
  }

  pushFiles(reqUrl: string, file: File, service: string): Observable<any> {


    const req_Url = this.getAPIConfig();


    const url = `${req_Url}/${reqUrl}`;
    console.log('APi service POST FORM', url);
    // const url = `${reqUrl}`;
    // console.log("APi service");
    const data: FormData = new FormData();

    if (file.type.indexOf('image') > -1) {
      data.append('file', file);
    }
    // data.append('file', file);

    // data.append('files', file);

    const token = this.auth.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    headers = headers.set('Access-Control-Allow-Credentials', 'true');
    headers = headers.set('Access-Control-Allow-Origin', '*');
    const newRequest = new HttpRequest('POST', url, data, {
      reportProgress: true,
      responseType: 'text',
      headers
    });

    return this._httpClient.request<any>(newRequest)
      .pipe(
        tap(data => {
          // console.log('server data:', data);
        }),
        catchError(this.handleError(url))
      );
  }
  getImage(imageUrl: string): Observable<Blob> {


    const url = `${imageUrl}/${imageUrl}`;
    const token = this.auth.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    headers = headers.set('Access-Control-Allow-Origin', '*');
    headers = headers.set('Access-Control-Allow-Credentials', 'true');
    headers = headers.set('Access-Control-Allow-Origin', '*');
    const newRequest = new HttpRequest('GET', url, {
      reportProgress: true,
      responseType: 'blob',
      headers
    });
    // console.log(this._httpClient.get(imageUrl, { responseType: 'blob', headers }));
    return this._httpClient.get(imageUrl, { responseType: 'blob', headers });

  }

  getFile(reqUrl: string): Observable<any> {
    const url = `${reqUrl}`;
    const token = this.auth.getToken();
    let headers = new HttpHeaders();

    if (token) {
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    headers = headers.set('Access-Control-Allow-Origin', '*');
    headers = headers.set('Access-Control-Allow-Credentials', 'true');
    headers = headers.set('Access-Control-Allow-Origin', '*');
    const newRequest = new HttpRequest('GET', url, {
      reportProgress: true,
      responseType: 'blob',
      headers
    });

    return this._httpClient.request<any>(newRequest)
      .pipe(
        tap(data => {
          // console.log('server data:', data);
        }),
        catchError(this.handleError(url))
      );
  }

  showNotification(header: string, message: string, messageicon: string) {
    // Swal.fire( header, // headline msg
    // message, // center message
    // icon:icon  // icon
    // );

    Swal.fire(
      header,
      message,
      //icon:messageicon  // icon
    );

  }



}
