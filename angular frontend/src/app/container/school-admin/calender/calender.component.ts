


import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { ColorPickerService, Cmyk } from 'ngx-color-picker';
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';

import { ICalender } from 'src/app/Shared/Interfaces/school/ICalender';
@Component({

  selector: 'app-school-calender',
  templateUrl: './calender.component.html',
  styleUrls: ['./calender.component.css']
})
export class CalenderComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  calender: ICalender = {} as ICalender;
  calenderList: ICalender[];

  calenderForm: FormGroup;
  // Table

  public color2: string = '#e920e9';
  constructor(private fb: FormBuilder,private cpService: ColorPickerService,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {

  }

  ngOnInit(): void {
    this.getCalenderList();
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

    this.calenderForm = this.fb.group({
      calendar_item_name: ['', [Validators.required]],
      color_code: ['', [Validators.required]],
    });
    this.calenderForm.reset();

    this.calender.color_code='#e920e9';
  }

  get f() { return this.calenderForm.controls; }

  getCalenderList() {
    this.ngxLoader.start();
    this.api.get('calendar_items').subscribe(
      res => {
        this.ngxLoader.stop();
        this.calenderList = res.result;
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
    this.calenderForm.get('calendar_item_name').enable();
    this.calenderForm.get('color_code').enable();
    this.calenderForm.reset();
    this.modalService.open(content, { centered: true });
  }
  submit($event: Event) {
    $event.preventDefault();
    this.submitted = true;

    if (this.calenderForm.invalid) {
      return;
    } else {


      this.calender.calendar_item_name = this.calenderForm.get('calendar_item_name').value;
      //this.calender.color_code = this.calenderForm.get('color_code').value;

      this.ngxLoader.start();


      if (this.calender) {
        this.api.post('calendar_item/create', this.calender)
          .subscribe(
            (response: any) => {
              this.calenderForm.reset();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              this.getCalenderList();
              this.api.showNotification('Message', response.message, null);
             // this.api.showNotification('Success', 'Calender created successfully.', 'success');
            },
            (error: any) => {
              console.log('error ' + JSON.stringify(error));
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            });
      } else {
        this.api.put('calendar_item/update', this.calender)
          .subscribe(
            (response: any) => {
              //this.api.showNotification('Message', response.message, null);

              this.calenderForm.reset();
              this.modalService.dismissAll();
              this.ngxLoader.stop();
              this.getCalenderList();
              this.api.showNotification('Success', 'Calender created successfully.', 'success');
            },
            (error: any) => {
              console.log('error ' + JSON.stringify(error));
              this.modalService.dismissAll();
              this.ngxLoader.stop();
            });
      }
    }


  }
  viewDetails(classDetails, content) {
    this.calender = null;
    this.calender = classDetails;

    this.calenderForm.get('calendar_item_name').disable();
    this.calenderForm.get('color_code').disable();

    this.modalService.open(content, { centered: true });
  }
  editDetails(classDetails, content) {
    this.calender = null;
    this.calender = classDetails;


    this.calenderForm.get('calendar_item_name').enable();
    this.calenderForm.get('color_code').enable();
    this.modalService.open(content, { centered: true });
  }

  delete_class(Klass: ICalender) {
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
        this.api.delete('calendar_item/' + Klass.calendar_item_id + '/delete').subscribe(
          res => {
            this.calenderForm.reset(this.calenderForm.value);

            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'Calender record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getCalenderList();
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
            this.getCalenderList();
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
  // changeColor(e) {
    
  //   this.calender.color_code=e.target.value;

  //   console.log(this.calender.color_code)
  // }
  public changeColor(color: string): Cmyk {
    const hsva = this.cpService.stringToHsva(color);

    const rgba = this.cpService.hsvaToRgba(hsva);
    this.calender.color_code=color;

    // console.log(color);
    //  console.log(rgba);

    return this.cpService.rgbaToCmyk(rgba);
  }
  public onEventLog(event: string, data: any): void {
    console.log(event, data);
  }
}
