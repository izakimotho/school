import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { LocalDataSource } from 'ng2-smart-table';

import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgxUiLoaderService,SPINNER } from 'ngx-ui-loader'; 
import { DataTableDirective } from 'angular-datatables';
 
import { ApiService } from '../../../Services/api.services';
import { AuthService } from '../../../Services/auth.service'; 
import Swal from 'sweetalert2';
import { Subject } from 'rxjs';
import { IPosition } from 'src/app/Shared/Interfaces/school/IPositions';
import { IStaff } from 'src/app/Shared/Interfaces/school/IStaff';
@Component({
 
  selector: 'app-school-positions',
  templateUrl: './positions.component.html',
  styleUrls: ['./positions.component.css']
})
export class PositionsComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};
  submitted = false;
  isedit: boolean = true;
  spinnerType = SPINNER.circle;

  position: IPosition = {} as IPosition;
  positionList: IPosition[];
  
  staff: IStaff = {} as IStaff;
  hostel_master: IStaff = {} as IStaff;
  staffs: IStaff[];

  positionForm: FormGroup;
  // Table
  
  constructor(private fb: FormBuilder,
    private auth: AuthService, private ngxLoader: NgxUiLoaderService,
    private modalService: NgbModal,
    private api: ApiService
  ) {
    
  }
 

  ngOnInit(): void {
    this.getPositionList(); 
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
  
    this.positionForm = this.fb.group({
      position_name: ['', [Validators.required]],
      description: ['', [Validators.required]],
      position_holder: ['', [Validators.required]],
    });
    this.positionForm.reset();
  }

  get f() { return this.positionForm.controls; }

  getPositionList() {
    this.api.get('positions/list').subscribe(
      res => {
        this.positionList = res.result;
         // ADD THIS
        this.dtTrigger.next();
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

   getStaff() {
    this.staffs = [];
    this.api.get("staff/list").subscribe(
        (res) => {
            this.staffs = res.result;
        },
        (errResp) => {
            this.ngxLoader.stop();
            console.error("Error" + errResp);
        }
    );
}



  openmax(content: any) {
    this.positionForm.get('position_name').enable();
    this.positionForm.get('description').enable();
    this.positionForm.get('position_holder').enable(); 
    this.positionForm.reset();
    this.positionForm.reset(this.positionForm.value)


    
// reset form here
this.positionForm.markAsPristine();
this.positionForm.reset();
    this.modalService.open(content, { centered: true});
  }
  submit($event: Event) {
    $event.preventDefault();


    this.submitted = true;


    if (this.positionForm.invalid) {
      return;
    } else {

      this.position.position_name = this.positionForm.get('position_name').value;
      this.position.description = this.positionForm.get('description').value;
      this.position.position_holder = this.positionForm.get('position_holder').value; 
      
      this.ngxLoader.start();
      this.api.post('positions/create', this.position)
        .subscribe(
          (response: any) => {
            this.ngxLoader.stop();
            this.api.showNotification('Message', response.message, null)
          },
          (error: any) => {
            console.log('error ' + JSON.stringify(error));
            this.ngxLoader.stop();
          });
      this.positionForm.reset();
      this.api.showNotification('Success', 'Staff Positions created successfully.', 'success');
      
    this.getPositionList();
    }


  }
  viewDetails(positionsDetails, content) {
    this.position = null;
    this.position = positionsDetails; 
     
    this.positionForm.get('position_name').disable();
    this.positionForm.get('description').disable();
    this.positionForm.get('position_holder').disable(); 
 
    this.modalService.open(content, { centered: true });
  }
  editDetails(positionDetails, content) {
    this.position = null;
    this.position = positionDetails; 
   
     this.positionForm.get('position_name').enable();
     this.positionForm.get('description').enable();
     this.positionForm.get('position_holder').enable();
    this.modalService.open(content, { centered: true });
  }

  delete_position(posit: IPosition) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You will not be able to recover from this action!',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'No, keep it'
    }).then((result: any) => {
      if (result.value) {       
        const position_id=posit.position_id;
        
      this.ngxLoader.start();
       this.api.delete('positions/delete/'+position_id ).subscribe(
          res => {
            this.positionForm.reset(this.positionForm.value);
             
            Swal.fire({
              position: 'top-end',
              icon: 'info',
              title: 'Success...',
              text: 'Position record successfully deleted.!',
              footer: ''
            });
            this.modalService.dismissAll();
            this.getPositionList();
            this.ngxLoader.stop();
           
          },
          
          errResp => {  
           
            Swal.fire({
              position: 'top-end',
              icon: 'error',
              title: 'Oops...',
              text: 'Error : An error has occured. \n Record couldnt be deleted' ,
              footer: ''
            });
            this.modalService.dismissAll();
            this.getPositionList();
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
