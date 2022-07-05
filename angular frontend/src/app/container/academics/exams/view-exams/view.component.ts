import {Component, OnInit} from '@angular/core';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';

import {NgxUiLoaderService, SPINNER} from 'ngx-ui-loader';
import {IStudent} from '../../../../Shared/Interfaces/student/IStudent';
import {NotificationServices} from '../../../../Services/notification.services';
import {AuthService} from '../../../../Services/auth.service';
import {ApiService} from '../../../../Services/api.services';
import {IExam} from '../../../../Shared/Interfaces/exam/IExam';

@Component({
    selector: 'app-view-student',
    templateUrl: './view.component.html',
    styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
    spinnerType = SPINNER.circle;
    dtOptions: any = {};
    exam_id: any;
    exam: IExam = {} as IExam;

    constructor(
        private ngxLoader: NgxUiLoaderService,
        private activatedRoute: ActivatedRoute,
        private notification: NotificationServices,
        private http: HttpClient,
        private auth: AuthService,
        private modalService: NgbModal,
        private api: ApiService,
    ) {
        this.exam_id = this.activatedRoute.snapshot.params.exam_id;
        console.log(this.exam_id);
    }

    ngOnInit(): void {
        this.getExam();
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
    }

    private getExam() {
        this.ngxLoader.start();
        this.api.get('exam_schedule/' + this.exam_id).subscribe(
            res => {
                this.exam = res.result;
                this.ngxLoader.stopAll();
            },
            errResp => {
                this.ngxLoader.stopAll();
                console.error('Error' + errResp);

            }
        );
    }
}
