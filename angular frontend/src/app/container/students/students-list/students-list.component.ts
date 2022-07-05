import {Component, OnInit, ViewChild} from '@angular/core';
import {ApiService} from '../../../Services/api.services';
import {SweetAlertOptions} from 'sweetalert2';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {NotificationServices} from '../../../Services/notification.services';
import {NgxUiLoaderService, SPINNER} from 'ngx-ui-loader';
import {IStudent} from '../../../Shared/Interfaces/student/IStudent';
import {ICounty} from '../../../Shared/Interfaces/school/ICounty';

@Component({
  selector: 'app-students-list',
  templateUrl: './students-list.component.html',
  styleUrls: ['./students-list.component.css']
})
export class StudentsListComponent implements OnInit {
  spinnerType = SPINNER.circle;
  students: IStudent[];
  student: IStudent = {} as IStudent;
  county: ICounty = {} as ICounty;
  pageNumber = 10;
  count = 10;

  dtOptions: any = {};
  studentID: string;

  constructor(private ngxLoader: NgxUiLoaderService, private api: ApiService, private notification: NotificationServices) {
  }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      'search': {
        'search': ''
      } ,
      'ordering': false,
      'info': false,
      //dom: 'lfrtip',
      // buttons: [
      //   {
      //     extend: 'copy',
      //     text: '<u>C</u>opy',
      //     key: {
      //       key: 'c',
      //       altKey: true
      //     },
      //     className: 'btn btn-sm btn-pill btn-outline-light'
      //   },
      //   {
      //     extend: 'csv',
      //     text: '<u>C</u>sv',
      //     className: 'btn btn-sm btn-pill btn-outline-light'
      //   },
      //   {
      //     extend: 'excel',
      //     text: '<u>E</u>xcel',
      //     className: 'btn btn-sm btn-pill btn-outline-light'
      //   },
      //   {
      //     extend: 'pdf',
      //     text: '<u>P</u>df',
      //     className: 'btn btn-sm btn-pill btn-outline-light'
      //   },
      //   {
      //     extend: 'print',
      //     text: 'Print all',
      //     exportOptions: {
      //       modifier: {
      //         selected: null
      //       }
      //     },
      //     className: 'btn btn-sm btn-pill btn-outline-light'
      //   }


      // ],
      
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
    this.getStudents();
  }

  private getStudents() {
    this.ngxLoader.start();
    this.api.get('students/list').subscribe(
      res => {
        this.ngxLoader.stop();
        this.students = res.result;
      },
      errResp => {
        this.ngxLoader.stop();
        console.error('Error' + errResp);

      }
    );
  }

  public setStudentID(studentID) {
    this.studentID = studentID;
  }

  public deleteStudent(orgId) {
    this.api.delete('students/' + this.studentID +'/delete').subscribe(
      res => {
        this.api.showNotification('Message', res.message,null);
        this.getStudents();
      },
      errResp => {
        this.notification.toastDanger(errResp, 'Error')
        console.error('Error' + errResp);
      }
    );
  }

  @ViewChild('successSwal') public successSwal: SwalComponent;
  Step1: SweetAlertOptions = {
    title: 'Are you sure?',
    text: "You won't be able to revert this!",
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: 'Yes, delete it!'
  };

  deleteQuestion() {
    this.deleteStudent(this.studentID);
  };
}
