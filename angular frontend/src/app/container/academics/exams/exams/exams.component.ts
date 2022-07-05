import {Component, OnInit, ViewChild} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {SweetAlertOptions} from 'sweetalert2';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {SPINNER} from 'ngx-ui-loader';
import {ApiService} from '../../../../Services/api.services';
import {NotificationServices} from '../../../../Services/notification.services';
import {IExam} from '../../../../Shared/Interfaces/exam/IExam';
import {IExamType} from '../../../../Shared/Interfaces/exam/IExamType';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.css']
})
export class ExamsComponent implements OnInit {
  spinnerType = SPINNER.circle;
  exams: IExam[];
  examTypes: IExamType[];
  examType: IExamType = {} as IExamType;
  exam: IExam = {} as IExam;
  pageNumber = 10;
  count = 10;
  source: LocalDataSource;
  settings = {
    hideSubHeader: true,
    pager: {
      perPage: 10,
    },
    actions: {
      add: false,
      edit: false,
      delete: false,
    },
    columns: {
      names: {
        title: 'Names',
        width: '30%',
        filter: true,
      },
      dob: {
        title: 'dob',
        width: '15%',
        filter: true
      },
      guardians_name: {
        title: 'guardians_name',
        width: '5%',
        type: 'html',
        valuePrepareFunction: (img: number) => {
          return `<img src="${img[0]}" alt="img" />`;
        },
        filter: true
      },
      school_type_name: {
        title: 'Type',
        width: '15%',
        filter: true
      },
      county_name: {
        title: 'County',
        width: '15%',
        filter: true
      },
      sub_county_name: {
        title: 'Sub-County',
        width: '15%',
        filter: true
      },
      education_system_name: {
        title: 'Education System',
        width: '15%',
        filter: true
      }
    }
  };
  dtOptions: any = {};
  examID: string;

  constructor(private api: ApiService, private notification: NotificationServices) {
  }

  onSearch(query: string = '') {
    this.source.setFilter([
      // fields we want to include in the search
      {
        field: 'name',
        search: query
      },
      {
        field: 'code',
        search: query
      }
    ], false);
  }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      'search': {
        'search': ''
      },
      'ordering': false,
      'info': false,
      dom: 'Blfrtip',
      buttons: [
        {
          extend: 'copy',
          text: '<u>C</u>opy',
          key: {
            key: 'c',
            altKey: true
          },
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: 'csv',
          text: '<u>C</u>sv',
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: 'excel',
          text: '<u>E</u>xcel',
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: 'pdf',
          text: '<u>P</u>df',
          className: 'btn btn-sm btn-pill btn-outline-light'
        },
        {
          extend: 'print',
          text: 'Print all',
          exportOptions: {
            modifier: {
              selected: null
            }
          },
          className: 'btn btn-sm btn-pill btn-outline-light'
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
    this.getExams();
  }

  private getExams() {
    this.api.get('schools/exam_schedule').subscribe(
      res => {
        this.exams = res.result;
      },
      errResp => {
        console.error('Error' + errResp);

      }
    );
  }

  public setExamID(examID) {
    this.examID = examID;
  }

  public deleteExam(orgId) {
    this.api.delete('exam_schedule/' + this.examID + '/delete').subscribe(
      res => {
        this.api.showNotification('Message', res.message,null);
        this.getExams();
      },
      errResp => {
        this.notification.toastDanger(errResp, 'Error')
        console.error('Error' + errResp);
      }
    );
  }

  public deleteExamType(orgId) {
    this.api.delete('exam_type/' + this.examID).subscribe(
        res => {
          this.api.showNotification('Message', res.message,null);
          this.getExams();
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
    this.deleteExam(this.examID);
  };
}
