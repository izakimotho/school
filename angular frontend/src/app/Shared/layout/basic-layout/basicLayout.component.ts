import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subscriber } from 'rxjs'; 
declare var $: any;

@Component({
  selector: 'basic',
  templateUrl: 'basicLayout.template.html',
  styleUrls: ['./basicLayout.component.scss'],
  host: {
    '(window:resize)': 'onResize()'
  }
})
export class BasicLayoutComponent implements OnInit, AfterViewInit {

  date = new Date().getFullYear();

  constructor(
    private http: HttpClient,
  ) {

  }
  ngAfterViewInit() {
    
  }




  ngOnInit(): any {

  }





  public onResize() {

  }




}
