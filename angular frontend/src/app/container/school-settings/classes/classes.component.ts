import { Component, OnInit } from '@angular/core'; 
import { LocalDataSource } from 'ng2-smart-table';
 
import data from './../../../data/datatable.json'; 
@Component({
  selector: 'classes-settings-content',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements  OnInit {
  dtOptions: any = {};
  // Table
  public data = data;
  constructor() {
    this.source = new LocalDataSource(this.data);
  }
  source: LocalDataSource;
  settings = {
    hideSubHeader: true,
    pager:{
      perPage:10,
    },
    actions: {
      add: false,
      edit: false,
      delete: false,
    },
    columns: {
      id: {
        title: 'Id',
        filter: true
      },
      img: {
        title: 'Image',
        type: 'html',
        valuePrepareFunction: (img: number) => {
          return `<img src="${img}" alt="img" />`;
        },
        filter: true
      },
      name: {
        title: 'Title',
        filter: true
      },
      location: {
        title: 'Location',
        filter: true
      },
      email: {
        title: "Email I'd",
        filter: true
      },
      product: {
        title: 'Ordered Item',
        filter: true
      },
      price: {
        title: 'Bill',
        type: 'html',
        valuePrepareFunction: (price: number) => {
          return `<span>$${new Intl.NumberFormat().format(price)}</span>`;
        },
        filter: true
      }
    }
  };
  onSearch(query: string = '') {
    this.data.setFilter([
      // fields we want to include in the search
      {
        field: 'id',
        search: query
      },
      {
        field: 'name',
        search: query
      },
      {
        field: 'location',
        search: query
      },
      {
        field: 'email',
        search: query
      },
      {
        field: 'product',
        search: query
      },
      {
        field: 'price',
        search: query
      },
    ], false);
  }

  ngOnInit(): void {

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

  }

}
