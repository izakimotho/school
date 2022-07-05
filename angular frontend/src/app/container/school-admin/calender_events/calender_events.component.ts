


import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';

import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';

import { ICalender } from '../../../Shared/Interfaces/school/ICalender';
import { ICalenderEvents } from '../../../Shared/Interfaces/school/ICalenderEvents';
@Component({

  selector: 'app-school-calendar_events',
  templateUrl: './calender_events.component.html',
  styleUrls: ['./calender_events.component.css']
})
export class CalenderEventsComponent implements OnInit {

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


  calendarForm: FormGroup;
  // Table

  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }

  ngOnInit(): void {
    this.getcalendarList();
    this.getcalendarEventList();

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
    this.calendarForm = this.fb.group({
      calendar_event: ['', [Validators.required]],
      event_date: ['', [Validators.required]],
      calendar_item: ['', [Validators.required]],
    });
    this.calendarForm.reset();
  }
  
  get f() { return this.calendarForm.controls; }

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

  getcalendarList() {
    this.ngxLoader.start();
    this.api.get('calendar_items').subscribe(
      res => {
        this.ngxLoader.stop();
        this.calendarList = res.result;
        // ADD THIS
        this.dtTrigger.next();
      },
      errResp => {
        this.ngxLoader.stop();
        console.error('Error' + errResp);

      }
    );
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }




  openmax(content: any) {
    this.calendarForm.get('calendar_item').enable();
    this.calendarForm.get('calendar_event').enable();
    this.calendarForm.get('event_date').enable(); 
    this.calendarForm.reset();
    this.modalService.open(content, { centered: true });

     
  }   
  submit($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.calendarForm.invalid) {
      return;
    } else {


      this.calendarEvent.calendar_event = this.calendarForm.get('calendar_event').value;
      this.calendarEvent.event_date = this.calendarForm.get('event_date').value; 
      this.calendarEvent.calendar_item = this.calendarForm.get('calendar_item').value; 


      this.ngxLoader.start();


      if (this.calendarEvent) {
        this.api.post('calendar_events/create', this.calendar)
          .subscribe(
            (response: any) => {
              ///this.api.showNotification('Message', response.message, null);
              this.calendarForm.reset();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              this.api.showNotification('Success', 'calendar events updated successfully.', 'success');
            },
            (error: any) => {
              console.log('error ' + JSON.stringify(error));
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            });
      } else {
        this.api.put('calendar_events/update', this.calendar)
          .subscribe(
            (response: any) => {
              //this.api.showNotification('Message', response.message, null);

              this.calendarForm.reset();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              this.api.showNotification('Success', 'Calendar events created successfully.', 'success');
              
            },
            (error: any) => {
              console.log('error ' + JSON.stringify(error));
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            });
      }
      this.getcalendarList();
    }


  }
  viewDetails(classDetails, content) {
    this.calendarEvent = null;
    this.calendarEvent = classDetails;
 
    this.calendarForm.get('calendar_item').disable();
    this.calendarForm.get('calendar_event').disable();
    this.calendarForm.get('event_date').disable(); 

    this.calendar=classDetails.calendar_item;
    this.modalService.open(content, { centered: true });
  }
  editDetails(classDetails, content) {
    this.calendarEvent = null;
    this.calendarEvent = classDetails;

    this.calendar=classDetails.calendar_item;
    this.calendarForm.get('calendar_item').enable();
    this.calendarForm.get('calendar_event').enable();
    this.calendarForm.get('event_date').enable(); 
    this.modalService.open(content, { centered: true });
  }

  delete_class(Klass: ICalenderEvents) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {

        this.ngxLoader.start();
        this.api.delete('calendar_events/' + Klass.calendar_event_id).subscribe(
          res => {
            this.calendarForm.reset(this.calendarForm.value);

            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'calendar event record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getcalendarList();
            this.ngxLoader.stop();

          },

          errResp => {


            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: 'Oops...',
              text: 'Error : An error has occured. \n Record couldnt be deleted',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getcalendarList();
            this.ngxLoader.stop();

          }
        );

      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire(
          'Cancelled',
          'Your record is safe :)',
          'error'
        )
      }
    })

  }

}
