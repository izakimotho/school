import { Component, OnInit, ViewChild } from '@angular/core';

import { FormBuilder, FormGroupDirective } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';

import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service';
import { ISchoolLevel, } from '../../../Shared/Interfaces/school/ISchoolLevel';
import { ISubject } from 'src/app/Shared/Interfaces/school/ISubjects';
import { IEducationSystem } from 'src/app/Shared/Interfaces/school/IEducationSystem';

@Component({

    selector: 'app-subjects',
    templateUrl: './subject.component.html',
    styleUrls: ['./subject.component.css']
})
export class SubjectComponent implements OnInit {
    @ViewChild(FormGroupDirective) _fgDirective: FormGroupDirective;

    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    dtTrigger: Subject<any> = new Subject();
    dtOptions: any = {};
    submitted = false;
    isedit: boolean = true;
    spinnerType = SPINNER.circle;
    subject_id: string;

    subject: ISubject = {} as ISubject;
    subjectData: ISubject = {} as ISubject;
    subjectList: ISubject[];


    educationSystem: IEducationSystem = {} as IEducationSystem;
    educationSystems: IEducationSystem[];

    schoolLevel: ISchoolLevel = {} as ISchoolLevel;
    schoolLevels: ISchoolLevel[];

    education_System: IEducationSystem;
    school_Level: ISchoolLevel;
    // Table
    selectedCar: number;

    cars = [
        { id: 1, name: 'Volvo' },
        { id: 2, name: 'Saab' },
        { id: 3, name: 'Opel' },
        { id: 4, name: 'Audi' },
    ];

    constructor(private fb: FormBuilder,
        private auth: AuthService, private ngxLoader: NgxUiLoaderService,
        private modalService: NgbModal,
        private api: ApiService
    ) {

    }


    ngOnInit(): void {
        this.getSubjectList();
        this.getSchoolLevels();
        this.getEducationSystems();
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

    getSubjectList() {
        this.ngxLoader.start();
        this.subjectList = [];
        this.api.get('subjects').subscribe(
            res => {
                this.ngxLoader.stop();
                this.subjectList = res.result;
                // ADD THIS
                // this.dtTrigger.next();
            },
            errResp => {
                this.ngxLoader.stop();
                console.error('Error' + errResp);

            }
        );
    }

    getEducationSystems() {
        this.educationSystems = [];
        this.api.get('education_system/list').subscribe(
            res => {
                this.educationSystems = res.result;
            },
            errResp => {
                console.error('Error' + errResp);

            }
        );
    }

    getSchoolLevels() {
        this.api.get('schools/levels').subscribe(
            res => {
                this.schoolLevels = res.result;
            },
            errResp => {
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
    clearForm() {
        this.subjectData.subject_abbr = '';
        this.subjectData.subject_name = '';
        this.subjectData.description = '';
    }

    openmax(content: any) {
        this.clearForm();
        this.modalService.open(content, { centered: true });
    }

    addSubject(f) {
        this.ngxLoader.start();
        this.subjectData.subject_name = f.form.value.subject_name;
        this.subjectData.subject_abbr = f.form.value.subject_abbr;
        this.subjectData.description = f.form.value.description;
        this.subjectData.education_system = { education_system_id: f.form.value.education_system_id } as IEducationSystem;
        this.subjectData.school_level = { school_level_id: f.form.value.school_level_id } as ISchoolLevel;
        this.ngxLoader.start();
        this.api.post('subjects/create', this.subjectData)
            .subscribe(
                (response: any) => {
                    this.api.showNotification('Message', response.message, null);
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();

                },
                (error: any) => {
                    console.log('error ' + JSON.stringify(error));
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();
                });
    }

    editSubject(f) {
        this.ngxLoader.start();
        this.subject.subject_name = f.form.value.subject_name;
        this.subject.subject_abbr = f.form.value.subject_abbr;
        this.subject.description = f.form.value.description;
        this.subject.education_system = { education_system_id: f.form.value.education_system_id } as IEducationSystem;
        this.subject.school_level = { school_level_id: f.form.value.school_level_id } as ISchoolLevel;
        this.ngxLoader.start();
        this.api.post('subjects/update', this.subject)
            .subscribe(
                (response: any) => {
                    this.api.showNotification('Message', response.message, null);
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();
                },
                (error: any) => {
                    console.log('error ' + JSON.stringify(error));
                    this.modalService.dismissAll();
                    this.getSubjectList();
                    this.ngxLoader.stop();
                });
    }

    viewDetails(subjectDetails, content) {
        this.subject = null;
        this.subject = subjectDetails;
        if (subjectDetails.education_system) {
            this.educationSystem = subjectDetails.education_system;
            this.education_System = subjectDetails.education_system;
            console.log(this.education_System);
        }
        if (subjectDetails.school_level) {
            this.schoolLevel = subjectDetails.sch;
        }
        this.modalService.open(content, { centered: true });
    }

    editDetails(subjectDetails, content) {
        this.subject = null;
        this.subject = subjectDetails;
        if (subjectDetails.education_system) {
            this.education_System = subjectDetails.education_system;
        }
        if (subjectDetails.school_level) {
            this.school_Level = subjectDetails.school_level;
        }
        this.modalService.open(content, { centered: true });
    }

    delete_class(subjectDetail: ISubject) {
        Swal.fire({
            title: 'Are you sure?',
            text: 'You will not be able to recover from this action!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Yes, delete it!',
            cancelButtonText: 'No, keep it'
        }).then((result: any) => {
            if (result.value) {
                const class_id = subjectDetail.subject_id;
                const payload = {
                    class_id
                };
                this.ngxLoader.start();
                this.api.delete('subjects/' + subjectDetail.subject_id + '/delete').subscribe(
                    res => {
                        // this.myForm.reset(this.myForm.value);

                        Swal.fire({
                            position: 'top-end',
                            icon: 'info',
                            title: 'Success...',
                            text: 'Stream record successfully deleted.!',
                            footer: ''
                        });
                        this.modalService.dismissAll();
                        this.getSubjectList();
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
                        this.getSubjectList();
                        this.ngxLoader.stop();

                    }
                );

            } else if (result.dismiss === Swal.DismissReason.cancel) {
                Swal.fire(
                    'Cancelled',
                    'Your record is safe :)',
                    'error'
                );
            }
        });

    }

}
