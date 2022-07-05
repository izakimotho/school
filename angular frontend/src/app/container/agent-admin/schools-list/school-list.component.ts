import {Component, OnInit, ViewChild} from '@angular/core';
import {LocalDataSource} from 'ng2-smart-table';
import {ApiService} from '../../../Services/api.services';
import {ICounty, ISchool} from '../interface/ISchool';
import {SweetAlertOptions} from 'sweetalert2';
import {SwalComponent} from '@sweetalert2/ngx-sweetalert2';
import {NotificationServices} from '../../../Services/notification.services';
import {NgxUiLoaderService, SPINNER} from 'ngx-ui-loader';

@Component({
  selector: 'app-school-list',
  templateUrl: './school-list.component.html',
  styleUrls: ['./school-list.component.css']
})
export class SchoolListComponent implements OnInit {
  spinnerType = SPINNER.circle;
  schools: ISchool[];
  school: ISchool = {} as ISchool;
  county: ICounty = {} as ICounty;
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
      org_name: {
        title: 'Name',
        width: '30%',
        filter: true,
      },
      code: {
        title: 'Code',
        width: '15%',
        filter: true
      },
      logo: {
        title: 'Logo',
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
  orgId: string;

  constructor(private ngxLoader: NgxUiLoaderService, private api: ApiService, private notification: NotificationServices) {
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
    this.getSchools();
  }

  private getSchools() {
    this.ngxLoader.start();
    let list = [];
    this.ngxLoader.start();
    this.api.get('schools/list').subscribe(
      res => {
        this.ngxLoader.stop();
        for (let school of res.result) {
          if (school.county && school.sub_county) {
            this.school = school;
            this.school.county.county_name = school.county.county_name;
            this.school.sub_county.sub_county_name = school.sub_county.sub_county_name;
            this.school.education_system.education_system_name = school.education_system.education_system_name;
            
            list.push(this.school);
          }
        }
        this.schools = list;
        this.ngxLoader.stopAll();
      },
      errResp => {
        this.ngxLoader.stop();
        console.error('Error' + errResp);

      }
    );
  }

  public setOrgId(orgId) {
    this.orgId = orgId;
    //console.log('this.orgId:; ', this.orgId);
  }

  public deleteSchool(orgId) {
    this.ngxLoader.start();
    this.api.delete('schools/delete/' + orgId).subscribe(
      res => {
        this.ngxLoader.stop();
        this.api.showNotification('Message', res.message,null);
        // this.notification.toastSuccess(res.message, 'Success');
        this.getSchools();
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
    this.deleteSchool(this.orgId);
  };
}
