import {Component, OnInit} from '@angular/core';
import {ApiService} from '../../../Services/api.services';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import * as EXIF from 'exif-js';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from '../../../Services/auth.service';
import {NotificationServices} from '../../../Services/notification.services';
import {ActivatedRoute, Router} from '@angular/router';

import {NgxUiLoaderService, SPINNER} from 'ngx-ui-loader';
import {IStudent} from '../../../Shared/Interfaces/student/IStudent';
import {ISchoolStream} from '../../../Shared/Interfaces/school/ISchoolStream';
import {IHostel} from '../../../Shared/Interfaces/school/IHostel';
import {IPosition} from '../../../Shared/Interfaces/school/IPositions';
import {ISchoolClass} from '../../../Shared/Interfaces/school/ISchoolClass';
import {ChartDataSets, ChartOptions, ChartType} from 'chart.js';
import {Label} from 'ng2-charts';

@Component({
    selector: 'app-view-student',
    templateUrl: './view.component.html',
    styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit {
    spinnerType = SPINNER.circle;
    student: IStudent = {} as IStudent;
    students: IStudent[];
    student_id: any;
    school_stream: ISchoolStream = {} as ISchoolStream;
    position: IPosition = {} as IPosition;
    positions: IPosition[];
    hostel: IHostel = {} as IHostel;
    hostels: IHostel[];
    school_streams: ISchoolStream[];




    dtOptions: any = {};
    pagingItems: any;
    yearSelected: any;
    active = 'top';
    constructor(
        private ngxLoader: NgxUiLoaderService,
        private activatedRoute: ActivatedRoute,
        private notification: NotificationServices,
        private http: HttpClient,
        private auth: AuthService,
        private modalService: NgbModal,
        private api: ApiService,
    ) {
        this.student_id = this.activatedRoute.snapshot.params.student_id;
        console.log(this.student_id);
    }

    // Bar Chart
    public barChartData: ChartDataSets[] = [{data: [], label: ''}];
    public barChartLabels = [];
    // public barChartLabels: Label[] = ["Africa", "Asia", "Europe", "Latin America", "North America"];
    public barChartType: ChartType = 'bar';
    public barChartLegend = true;
    public barChartOptions: ChartOptions = {
        responsive: true,
        legend: {
            display: false,
            labels: {
                fontColor: "#A8A9AD",
            }
        },
        title: {
            display: true,
            text: 'Student Performance per Year',
            fontColor: "#A8A9AD",
        },
        scales: {
            yAxes: [{
                ticks: {
                    fontColor: "#A8A9AD",
                }

            }],
            xAxes: [{
                ticks: {
                    fontColor: "#A8A9AD",
                }
            }]
        }
    }
    /*public barChartData: ChartDataSets[] = [
        {
            label: "Population (millions)",
            backgroundColor: ["#ff0018", "#f7b11b", "#ff6c60", "#8663e1", "#08bf6f"],
            data: [2478, 5267, 1734, 3384, 1433]
        }
    ];*/
    // Pie Chart
    // public PieChartLabels: Label[] = ["Hunger House", "Food Lounge", "Delizious", "Red Resturant", "Hunger Lounge"];
    public PieChartLabels: Label[] = [];
    public PieChartType: ChartType = 'pie';
    public PieChartOptions: ChartOptions = {
        responsive: true,
        legend: {
            labels: {
                fontColor: "#A8A9AD"
            }
        },
        title: {
            display: true,
            text: 'Student Performance Per Year',
            fontColor: "#A8A9AD"
        }
    }
    public PieChartData: ChartDataSets[] = [{data: [], label: ''}];
    /*public PieChartData: ChartDataSets[] = [
        {
            label: "Population (millions)",
            backgroundColor: ["#ff0018", "#f7b11b", "#ff6c60", "#8663e1", "#08bf6f"],
            data: [2478, 5267, 734, 784, 433]
        }
    ];*/

    ngOnInit(): void {
        let d = new Date();
        let year = d.getFullYear();
        this.getStudentPerformance(year);
        this.getStudents();
        this.getStudent();
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

  private getStudents() {
    this.ngxLoader.start();
    this.api.get('students/list').subscribe(
        res => {
          this.students = res.result;
          if (this.student.school_stream === undefined || this.student.school_stream === null) {
            // do something 
              //console.log("school_stream do something");
              this.school_stream.stream_name='N/A';
          }else{
            //console.log(JSON.stringify(this.student.school_stream)); 
            this.school_stream=this.student.school_stream;
          }
          if (this.student.positions === undefined || this.student.positions === null) {
            // do something 
              //console.log("positions do something");
              this.position.position_name='N/A';
          }else{
            //console.log(JSON.stringify(this.student.school_stream)); 
            this.position=this.student.positions;            
          }
          if (this.student.hostel === undefined || this.student.hostel === null) {
            // do something 
              //console.log("hostel do something");
              this.hostel.hostel_name='N/A';
          }else{
            //console.log(JSON.stringify(this.student.school_stream)); 
            this.hostel=this.student.hostel;            
          }

          this.ngxLoader.stopAll();
        },
        errResp => {
          console.error('Error' + errResp);

        }
    );
  }

    private getStudent() {
        this.ngxLoader.start();
        this.api.get('students/' + this.student_id).subscribe(
            res => {
                this.student = res.result;
                console.log('Student' + this.student)
                // this.is_boarder = res.result._boarder === true ? 'Boarder' : 'Day';
                this.ngxLoader.stopAll();
            },
            errResp => {
                this.ngxLoader.stopAll();
                console.error('Error' + errResp);

            }
        );
    }

    getStudentPerformance(year){
        let results = [{"exam": 'cats', "marks": 95},{"exam": 'opener exam', "marks": 85},
            {"exam": 'mid-term exam', "marks": 65},{"exam": 'First term exam', "marks": 40}
            ,{"exam": 'Second Term term exam', "marks": 70},{"exam": 'Third term exam', "marks": 60}];
        this.pagingItems = results;
        const graphData = [];
        const labelData = [];
        this.pagingItems.forEach((student) => {
            labelData.push(student.exam);
            graphData.push(student.marks);
        });
        this.barChartLabels = [...labelData];
        this.barChartData = [{data: [...graphData], label: 'Student Performance', backgroundColor: ["#ff0018", "#f7b11b", "#ff6c60", "#8663e1", "#08bf6f"]}];

        this.PieChartLabels = [...labelData];
        this.PieChartData = [{data: [...graphData], label: 'Student Performance', backgroundColor: ["#ff0018", "#f7b11b", "#ff6c60", "#8663e1", "#08bf6f"]}];
        /*this.api.get('students/performance' + this.student_id + '/'+ year).subscribe((res) => {
            this.pagingItems = res.result;
            const graphData = [];
            const labelData = [];
            this.pagingItems.forEach((student) => {
                labelData.push(student.exam);
                graphData.push(student.marks);
            });
            this.barChartLabels = [...labelData];
            this.barChartData = [{data: [...graphData], label: 'Student Performance'}];
        }, errResp => {
            this.ngxLoader.stopAll();
            console.error('Error' + errResp);
        });*/
    }
}
