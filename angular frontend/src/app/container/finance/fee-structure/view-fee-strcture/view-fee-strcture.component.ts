import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { Subject } from 'rxjs';
import { ApiService } from 'src/app/Services/api.services';
import { IFeeStructure } from 'src/app/Shared/Interfaces/Finance/IFeeStructure';
import { IFeeStructureDTO } from 'src/app/Shared/Interfaces/Finance/IFeeStructureDTO';

@Component({
  selector: 'app-view-fee-strcture',
  templateUrl: './view-fee-strcture.component.html',
  styleUrls: ['./view-fee-strcture.component.css']
})
export class ViewFeeStrctureComponent implements OnInit {

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtTrigger: Subject<any> = new Subject();
  dtOptions: any = {};

  fee_structure_id: string;
  feeStructure: IFeeStructureDTO = {} as IFeeStructureDTO;
  feeStructureData: IFeeStructureDTO = {} as IFeeStructureDTO; 

  total_amount: number = 0;


  constructor(
    private ngxLoader: NgxUiLoaderService,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient,
    private modalService: NgbModal,
    private api: ApiService,
    private router: Router,
  ) {
    this.fee_structure_id = this.activatedRoute.snapshot.params.fee_structure_id;
     //console.log(this.fee_structure_id);
  }
  ngOnInit(): void {
    this.feeStructure=null;
    this.getfeeStructureList();
  }

  getfeeStructureList() {
    this.ngxLoader.start();
    this.api.get('fees-structure/'+this.fee_structure_id).subscribe(
      res => {
        this.feeStructure = res.result;
        console.log(JSON.stringify(this.feeStructure));
      this.total_amount=this.feeStructure.fee_total_amount;
      
      this.ngxLoader.stopAll();
        // ADD THIS
        // alert(this.total_amount)
        //this.dtTrigger.next();
      },
      errResp => {
        console.error('Error' + errResp); 
        this.ngxLoader.stopAll();

      }
    );
  }

  printPage() {
    window.print();
  }

  invoiceproductlist = [
    {
      id: 1,
      productname: 'Sports',
      qty: 1,
      cost: 30
    },
    {
      id: 2,
      productname: 'Food',
      qty: 1,
      cost: 69
    },
    {
      id: 3,
      productname: 'Lab',
      qty: 2,
      cost: 19
    },
    {
      id: 4,
      productname: 'Trips',
      qty: 2,
      cost: 9
    },
  ];
  public calculateprice() {
   //return this.invoiceproductlist.reduce((subtotal, item) => subtotal + item.qty * item.cost, 0)

    return this.feeStructure.term_fee.reduce((subtotal, item) => subtotal + item.term_total_amount, 0)
  };

}
