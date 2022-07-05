import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import Swal from 'sweetalert2/dist/sweetalert2.js';
import {ToastrService} from 'ngx-toastr';
@Injectable()
export class NotificationServices {

  constructor(
    private toast: ToastrService,
    private _httpClient: HttpClient) {}

  showNotification(header: string, message: string, messageicon: string) {
    // Swal.fire( header, // headline msg
    // message, // center message
    // icon:icon  // icon
    // );
    Swal.fire(
      header,
      message,
      // icon:messageicon  // icon
    );
  }

  toastSuccess(message, tittle,) {
    this.toast.remove;
    this.toast.success(message, tittle, {
      positionClass: 'toast-top-right',
      progressBar: true
    });
  }
  toastDanger(message, tittle,) {
    this.toast.remove;
    this.toast.error(message, tittle, {
      positionClass: 'toast-top-right',
      progressBar: true
    });
  }
  toastWarning(message, tittle,) {
    this.toast.remove;
    this.toast.warning(message, tittle, {
      positionClass: 'toast-top-right',
      progressBar: true
    });
  }
  toastInfo(message, tittle,) {
    this.toast.remove;
    this.toast.info(message, tittle, {
      positionClass: 'toast-top-right',
      progressBar: true
    });
  }

}
