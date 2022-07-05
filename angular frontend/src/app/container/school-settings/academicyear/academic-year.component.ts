import { Component, OnInit } from '@angular/core'; 
import { LocalDataSource } from 'ng2-smart-table';
 
import data from './../../../data/datatable.json'; 
@Component({
  selector: 'academic-year-content',
  templateUrl: './academic-year.component.html',
  styleUrls: ['./academic-year.component.css']
})
export class AcademicYearComponent implements  OnInit {

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
    this.source.setFilter([
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
  }

}
