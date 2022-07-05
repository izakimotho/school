import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { CalendarOptions } from '@fullcalendar/angular';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { Subject } from 'rxjs';
import { ApiService } from 'src/app/Services/api.services';
import { AuthService } from 'src/app/Services/auth.service';


import { ICalender } from '../../../Shared/Interfaces/school/ICalender';
import { ICalenderEvents } from '../../../Shared/Interfaces/school/ICalenderEvents';


@Component({
  selector: 'app-full-callender',
  templateUrl: './full-callender.component.html',
  styleUrls: ['./full-callender.component.css']
})
export class FullCallenderComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  calendar: ICalender = {} as ICalender;
  calendarList: ICalender[];


  calendarEvent: ICalenderEvents = {} as ICalenderEvents;
  calendarEventList: ICalenderEvents[];

  Events = [];
  calendarOptions: CalendarOptions;
  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }
  
  
  onDateClick(res) {
    alert('Clicked on date : ' + res.dateStr)
  }

  ngOnInit(){
    setTimeout(() => {
       this.getcalendarEventList();
      return 
      
    }, 2200);

    setTimeout(() => {
      this.calendarOptions = {
        initialView: 'dayGridMonth',
        dateClick: this.onDateClick.bind(this),
        events: this.Events
      };
    }, 2500);
      
   

    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      "search": {
        "search": ""
      },
      "ordering": false,
      "info": false,
      dom: 'Blfrtip',
      buttons: [
        {
          extend: 'copy',
          text: '<u>C</u>opy',
          key: {
            key: 'c',
            altKey: true
          },
          className: 'btn btn-sm btn-neutral mb-2 p-3'
        },
        {
          extend: 'csv',
          text: '<u>C</u>sv',
          className: 'btn btn-sm btn-neutral mb-2 p-3'
        },
        {
          extend: 'excel',
          text: '<u>E</u>xcel',
          className: 'btn btn-sm btn-neutral mb-2 p-3'
        },
        {
          extend: 'pdf',
          text: '<u>P</u>df',
          className: 'btn btn-sm btn-neutral mb-2 p-3'
        },
        {
          extend: 'print',
          text: 'Print all',
          exportOptions: {
            modifier: {
              selected: null
            }
          },
          className: 'btn btn-sm btn-neutral mb-2 p-3'
        }


      ],
      select: true,
      language: {
        paginate: {
          first: '«',
          previous: '‹',
          next: '›',
          last: '»'
        },
        aria: {
          paginate: {
            first: 'First',
            previous: 'Previous',
            next: 'Next',
            last: 'Last'
          }
        }
      }
    };   
    }

    getcalendarEventList() {
      this.ngxLoader.start();
      this.api.get('calendar_events').subscribe(
        res => {
          this.ngxLoader.stop();
          this.calendarEventList = res.result;
          // ADD THIS
          this.dtTrigger.next();
        },
        errResp => {
          this.ngxLoader.stop();
          console.error('Error' + errResp);
  
        }
      );
    }
  
}
