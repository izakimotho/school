import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';

import { ISchoolLevel, } from './../../../Shared/Interfaces/school/ISchoolLevel';
import { IEducationSystem } from './../../../Shared/Interfaces/school/IEducationSystem';
import { IClasses } from './../../../Shared/Interfaces/school/IClasses';
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service'; 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { ISchoolStream } from 'src/app/Shared/Interfaces/school/ISchoolStream';
@Component({
 
  selector: 'app-school-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;
  school_stream_id: string;


  school_stream: ISchoolStream = {} as ISchoolStream;
  schoolStreamData: ISchoolStream = {} as ISchoolStream;
  school_streamList: ISchoolStream[];

  classList: IClasses[];
  class: IClasses = {} as IClasses; 
 
 
  positionHolder:string[];//]= {} as IPositionHolder;

  constructor(
      private fb: FormBuilder,
      private ngxLoader: NgxUiLoaderService,
      private modalService: NgbModal,
      private api: ApiService
  ) { }

  ngOnInit(): void {
    this.getStreamList();
    this. getClassList();
      this.dtOptions = {
          pagingType: "full_numbers",
          pageLength: 10,
          processing: true,
          search: {
              search: "",
          },
          ordering: false,
          info: false,
          dom: "Blfrtip",
          buttons: [
              {
                  extend: "copy",
                  text: "<u>C</u>opy",
                  key: {
                      key: "c",
                      altKey: true,
                  },
                  className: 'btn btn-sm btn-pill btn-outline-light'
              },
              {
                  extend: "csv",
                  text: "<u>C</u>sv",
                  className: 'btn btn-sm btn-pill btn-outline-light'
              },
              {
                  extend: "excel",
                  text: "<u>E</u>xcel",
                  className: 'btn btn-sm btn-pill btn-outline-light'
              },
              {
                  extend: "pdf",
                  text: "<u>P</u>df",
                  className: 'btn btn-sm btn-pill btn-outline-light'
              },
              {
                  extend: "print",
                  text: "Print all",
                  exportOptions: {
                      modifier: {
                          selected: null,
                      },
                  },
                  className: 'btn btn-sm btn-pill btn-outline-light'
              },
          ],
          select: true,
          language: {
              paginate: {
                  first: "«",
                  previous: "‹",
                  next: "›",
                  last: "»",
              },
              aria: {
                  paginate: {
                      first: "First",
                      previous: "Previous",
                      next: "Next",
                      last: "Last",
                  },
              },
          },
      };
  }
 

  getPositionHolder() {
  this.positionHolder = [];
  this.api.get("leadership_types").subscribe(
      (res) => {
        //console.log(res.result)
        // console.log(res.result.length)
        //   var result = res.result;
        //   for (var val of result) {
        //     console.log(JSON.stringify(val));
        // }
        // console.log(res.result)

        this.positionHolder=res.result;
        this.positionHolder=[
           "Student","Support Staff", "Teaching Staff"
        ]
        
      },
      (errResp) => {
          this.ngxLoader.stop();
          console.error("Error" + errResp);
      }
  );
}



getStreamList() {
  this.api.get('schools/streams').subscribe(
    res => {
      this.school_streamList = res.result;
       // ADD THIS
      this.dtTrigger.next();
    },
    errResp => {
      console.error('Error' + errResp);

    }
  );
}
getClassList() { 
  this.api.get('classes/list').subscribe(
    res => { 
      this.classList = res.result; 
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
      this.schoolStreamData.abbr = "";
      this.schoolStreamData.class_capacity =0;
      this.schoolStreamData.description= "";
      this.schoolStreamData.stream_name= ""; 
  }

  openmax(content: any) {
      this.clearForm();
      this.modalService.open(content, { centered: true });
  }

  addPosition(f) {
      this.ngxLoader.start();

      if ( f.form.value.class_id == undefined &&  f.form.value.class_id == null ) {
        
        this.ngxLoader.stop();
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'Oops...',
            text: 'Parameter missing \n Select class to which stream belongs.!',
            footer: ''
          });
        return;
      }else{
        this.schoolStreamData.class_model = {
            class_id: f.form.value.class_id,
        } as IClasses;

        
      }


      this.schoolStreamData.abbr = f.form.value.abbr;
      this.schoolStreamData.class_capacity =f.form.value.class_capacity;
      this.schoolStreamData.description= f.form.value.description;
      this.schoolStreamData.stream_name= f.form.value.stream_name;
    
    
     
     
      // this.positionData.position_holder = {
      //     staff_id: f.form.value.staff_id,
      // } as IStaff;
     
      this.ngxLoader.start(); 
      this.api.post("schools/streams/create", this.schoolStreamData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.schoolStreamData = null;
              this.getStreamList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getStreamList();
              this.ngxLoader.stop();
          }
      );
  }

  editposition(f) {
    this.schoolStreamData.abbr = f.form.value.abbr;
    this.schoolStreamData.class_capacity =f.form.value.class_capacity;
    this.schoolStreamData.description= f.form.value.description;
    this.schoolStreamData.stream_name= f.form.value.stream_name;
  
    this.schoolStreamData.class_model = {
        class_id: f.form.value.class_id,
    } as IClasses;
   
      

      this.ngxLoader.start();
      this.api.post("positions/update", this.schoolStreamData).subscribe(
          (response: any) => {
              this.api.showNotification("Message", response.message, null);
              this.modalService.dismissAll();
              this.getStreamList();
              this.ngxLoader.stop();
          },
          (error: any) => {
              console.log("error " + JSON.stringify(error));
              this.modalService.dismissAll();
              this.getStreamList();
              this.ngxLoader.stop();
          }
      );
  }

  viewDetails(streamDetails, content) {
      this.schoolStreamData = null;
      this.schoolStreamData = streamDetails;
      if (streamDetails.class) {
          this.class = this.schoolStreamData .class_model;
      }
     
      this.modalService.open(content, { centered: true });
  }

  editDetails(streamDetails, content) {
    this.schoolStreamData = null;
    this.schoolStreamData = streamDetails;
    if (streamDetails.class) {
        this.class = this.schoolStreamData.class_model;
    }    
      this.modalService.open(content, { centered: true });
  }

  delete_class(streamDetails: ISchoolStream) {
      Swal.fire({
          title: "Are you sure?",
          text: "You will not be able to recover from this action!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, keep it",
      }).then((result: any) => {
          if (result.value) {
              const class_id = streamDetails.school_stream_id;
              const payload = {
                  class_id,
              };
              this.ngxLoader.start();
              this.api
                  .delete("schools/streams/" + streamDetails.school_stream_id + "/delete")
                  .subscribe(
                      (res) => {
                          // this.myForm.reset(this.myForm.value);

                          Swal.fire({
                              position: "top-end",
                              icon: "info",
                              title: "Success...",
                              text: "Stream record successfully deleted.!",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getStreamList();
                          this.ngxLoader.stop();
                      },

                      (errResp) => {
                          Swal.fire({
                              position: "top-end",
                              icon: "error",
                              title: "Oops...",
                              text: "Error : An error has occured. \n Record couldnt be deleted",
                              footer: "",
                          });
                          this.modalService.dismissAll();
                          this.getStreamList();
                          this.ngxLoader.stop();
                      }
                  );
          } else if (result.dismiss === Swal.DismissReason.cancel) {
              Swal.fire("Cancelled", "Your record is safe :)", "error");
          }
      });
  }
}


export interface IPositionHolder {
  name: string; 
}