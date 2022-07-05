import { Component, OnInit } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { ApiService } from 'src/app/Services/api.services';
import { IUser } from 'src/app/Shared/Interfaces/userprofile/IUser';

@Component({
  selector: 'core-dashboard-content',
  templateUrl: './core-dashboard.component.html',
  styleUrls: ['./core-dashboard.component.css']
})
export class CoreDashboardComponent implements OnInit {
  curr_user: string = '';

  stats;

  userProfile: IUser = {} as IUser;
  constructor(private ngxLoader: NgxUiLoaderService, private api: ApiService) {

    //console.log(localStorage.getItem('perm'));
    this.getProfile();
    this.getStats();
  }
  private getStats() {
    //get user profile data
    this.api.get("dashboard").subscribe(
      (res) => {
        this.stats = res.result;
      },
      (errResp) => {
        console.error("Error" + errResp);
      }
    );
  }

  private getProfile() {
    //get user profile data
    this.api.get("profile").subscribe(
      (res) => {
        this.userProfile = res.result;
        this.curr_user = this.userProfile.firstName + " " + this.userProfile.lastName;
        this.ngxLoader.stopAll();
      },
      (errResp) => {
        this.ngxLoader.stopAll();
        console.error("Error" + errResp);
      }
    );
  }




  // Statics
  statbox = [
    {
      icon: 'folder',
      title: 'My Products',
      text: 'Manage Products',
      notification: 6
    },
    {
      icon: 'people',
      title: 'My Clients',
      text: 'Manage Users',
      notification: 5
    },
    {
      icon: 'help',
      title: 'Support Tickets',
      text: 'View Tickets',
      notification: 3
    },
    {
      icon: 'graphic_eq',
      title: 'Management',
      text: 'Manage Product',
      notification: 2
    },
  ];
  // User Country
  countrytable = [
    {
      countryflag: 'assets/img/costic/country-1.jpg',
      entrance: 725,
      bouncerate: 17.24,
      exits: 7.65,
    },
    {
      countryflag: 'assets/img/costic/country-2.jpg',
      entrance: 890,
      bouncerate: 12.90,
      exits: 9.12,
    },
    {
      countryflag: 'assets/img/costic/country-3.jpg',
      entrance: 729,
      bouncerate: 20.75,
      exits: 14.29,
    },
    {
      countryflag: 'assets/img/costic/country-4.jpg',
      entrance: 316,
      bouncerate: 32.09,
      exits: 10.99,
    },
  ];
  // Pie chart
  public PieChartLabels: Label[] = ["USA", "Germany", "UK", "Russia", "France"];
  public PieChartType: ChartType = 'pie';
  public PieChartData: ChartDataSets[] = [
    {
      label: "Users (thousands)",
      backgroundColor: ["#ff0018", "#f7b11b", "#ff6c60", "#8663e1", "#08bf6f"],
      data: [725, 890, 729, 316, 275]
    }
  ];
  public PieChartOptions: ChartOptions = {
    responsive: true,
    title: {
      display: false,
      text: 'Users By Country'
    },
    legend: {
      display: false
    },
  }
  public lineChartType: ChartType = 'line';
  // User Traffic
  public UsertrafficChartLabels: Label[] = ["Jan-11", "Jan-12", "Jan-13", "Jan-14", "Jan-15", "Jan-16", "Jan-17", "Jan-18", "Jan-19"];
  public UsertrafficChartData: ChartDataSets[] = [
    {
      label: "Users",
      borderColor: '#ff0018',
      pointBorderColor: '#ff0018',
      pointBackgroundColor: '#ff0018',
      pointHoverBackgroundColor: '#ff0018',
      pointHoverBorderColor: '#ff0018',
      pointBorderWidth: 1,
      pointHoverRadius: 4,
      pointHoverBorderWidth: 1,
      pointRadius: 2,
      fill: true,
      backgroundColor: "rgba(53,127,250,0.4)",
      borderWidth: 1,
      data: [1800, 1600, 2300, 2800, 3600, 2900, 3000, 3800, 3600]
    }
  ];
  public UsertrafficChartOptions: ChartOptions = {
    responsive: true,
    legend: {
      display: false,
      position: "bottom"
    },
    scales: {
      yAxes: [{
        ticks: {
          fontColor: "#A8A9AD",
          beginAtZero: true,
          maxTicksLimit: 200,
          padding: 20
        },
        gridLines: {
          drawTicks: false,
          display: false
        }

      }],
      xAxes: [{
        gridLines: {
          zeroLineColor: "transparent"
        },
        ticks: {
          padding: 20,
          fontColor: "#A8A9AD"
        }
      }]
    }
  }
  // User Purchase
  public UserpchChartLabels: Label[] = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"];
  public UserpchChartOptions: ChartOptions = {
    responsive: true,
    elements: {
      line: {
        tension: 0
      }
    },
    legend: {
      display: false,
      position: "bottom"
    },
    scales: {
      yAxes: [{
        display: false,
      }],
      xAxes: [{
        display: false,
      }]
    }
  };
  // engaged-users
  public engagedChartData: ChartDataSets[] = [
    {
      label: "Data",
      borderColor: '#07be6e',
      pointBorderColor: '#07be6e',
      pointBackgroundColor: '#07be6e',
      pointHoverBackgroundColor: '#07be6e',
      pointHoverBorderColor: '#07be6e',
      pointBorderWidth: 0,
      pointHoverRadius: 0,
      pointHoverBorderWidth: 0,
      pointRadius: 0,
      fill: true,
      backgroundColor: "rgba(7, 190, 110,0.3)",
      borderWidth: 2,
      data: [5, 1, 8, 1, 3, 7, 8, 4, 3, 6, 8, 9, 4, 5, 8, 2, 6, 4, 8, 3]
    }
  ];
  // page-impressions
  public pageimmpChartData: ChartDataSets[] = [
    {
      label: "Data",
      borderColor: '#07be6e',
      pointBorderColor: '#07be6e',
      pointBackgroundColor: '#07be6e',
      pointHoverBackgroundColor: '#07be6e',
      pointHoverBorderColor: '#07be6e',
      pointBorderWidth: 0,
      pointHoverRadius: 0,
      pointHoverBorderWidth: 0,
      pointRadius: 0,
      fill: true,
      backgroundColor: "rgba(7, 190, 110,0.3)",
      borderWidth: 2,
      data: [8, 5, 1, 8, 5, 9, 4, 3, 4, 5, 8, 4, 4, 8, 9, 5, 5, 1, 3, 6]
    }
  ];
  // support tickets
  tickets = [
    {
      userimg: 'assets/img/costic/customer-4.jpg',
      username: 'Lorem ipsum dolor',
      date: 'February 24, 2021',
      query: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla luctus lectus a facilisis bibendum. Duis quis convallis sapien ... ',
      comment: 16,
      attachment: 3,
      open: true,
      close: false,
    },
    {
      userimg: 'assets/img/costic/customer-1.jpg',
      username: 'Lorem ipsum dolor',
      date: 'February 24, 2021',
      query: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla luctus lectus a facilisis bibendum. Duis quis convallis sapien ... ',
      comment: 11,
      attachment: 1,
      open: true,
      close: false,
    },
    {
      userimg: 'assets/img/costic/customer-7.jpg',
      username: 'Lorem ipsum dolor',
      date: 'February 24, 2021',
      query: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla luctus lectus a facilisis bibendum. Duis quis convallis sapien ... ',
      comment: 21,
      attachment: 5,
      open: false,
      close: true,
    },
  ];
  public currentUserId = 1;
  chats = [
    {
      userImg: 'assets/img/costic/customer-1.jpg',
      userId: 1,
      time: '10:33 pm',
      message: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.'
    },
    {
      userImg: 'assets/img/costic/customer-2.jpg',
      userId: 2,
      time: '11:01 pm',
      message: "I'm doing great, thanks for asking"
    },
    {
      userImg: 'assets/img/costic/customer-2.jpg',
      userId: 2,
      time: '11:01 pm',
      message: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.'
    },
    {
      userImg: 'assets/img/costic/customer-1.jpg',
      userId: 1,
      time: '11:03 pm',
      message: 'It is a long established fact that a reader will be distracted by the readable content of a page'
    },
    {
      userImg: 'assets/img/costic/customer-1.jpg',
      userId: 1,
      time: '11:03 pm',
      message: 'There are many variations of passages of Lorem Ipsum available'
    },
  ]

  getChatInitialMsg(item: { userId: number; userImg: string; }, i: number) {
    var content = '';
    i === 0 || (i !== 0 && this.chats[i - 1].userId !== item.userId) ?
      content += '<div class="ms-chat-status ms-status-online ms-chat-img">' +
      '<img src="' + item.userImg + '" class="ms-img-round" alt="people">' +
      '</div>' : content = '';
    return content;
  }

  getChatInitialTime(item: { userId: number; time: string; }, i: number) {
    var content = '';
    i === this.chats.length - 1 || (i + 1 <= this.chats.length - 1 && this.chats[i + 1].userId !== item.userId) ?
      content += '<p class="ms-chat-time">' + item.time + '</p>' : content = '';
    return content;
  }

  ngOnInit(): void {
  }

}
