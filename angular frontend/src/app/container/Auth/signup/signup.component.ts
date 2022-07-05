import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
 

import {Router} from '@angular/router';
import { ApiService } from './../../../Services/api.services';
import {AuthService} from './../../../Services/auth.service';

@Component({
  selector: 'signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent  implements OnInit {


  signupForm: FormGroup;

  constructor( private router: Router,
    private formBuilder: FormBuilder,
    public apiservice: ApiService,
    private auth: AuthService) {
      
     }

  ngOnInit(): void {
    // Signup Form
    this.signupForm = new FormGroup({
      Firstname: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
      ]),
      Lastname: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
      ]),
      Email: new FormControl('', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
      ]),
      Password: new FormControl('', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20)
      ])
    });
  }
  onSubmit() {
    console.log(this.signupForm);
  }

}
