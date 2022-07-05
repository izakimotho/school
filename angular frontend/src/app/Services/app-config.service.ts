import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import 'rxjs';
import { IAppConfig } from './../Shared/Interfaces/appConfig';
import { Subject } from 'rxjs';

@Injectable()
export class AppConfigService {
  public version!: string;
  public appName!: string;
  public apiEndpoint!: string;
  public apiUrl!: string; 
   
  apSettings: any; 
  static settings: IAppConfig; 


  config: any;
  public configSubject$: Subject<any> = new Subject<any>();

  constructor(private http: HttpClient) { }
 
  getAPIUrl(){ 
    return this.apiUrl;
  }
 
  getAppName(){ 
    return this.appName;
  }
  getVersion(){ 
    return this.version;
  }
 
  load() :Promise<any>  {

    const promise = this.http.get('assets/config/app.config.json')
      .toPromise()
      .then(data => {
        Object.assign(this, data);
        return data;
      });

    return promise;
}

}
