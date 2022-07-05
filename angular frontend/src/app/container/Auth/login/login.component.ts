import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';


import { NgxUiLoaderService, SPINNER } from 'ngx-ui-loader';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import Swal from 'sweetalert2';
import { ApiService } from './../../../Services/api.services';
import { AuthService } from './../../../Services/auth.service';



@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  spinnerType = SPINNER.circle;
  closeResult: string;
  constructor(private modalService: NgbModal, private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute, private ngxLoader: NgxUiLoaderService,
    public api: ApiService, public auth: AuthService

  ) { }
  open(content: any) {
    this.modalService.open(content, { centered: true, windowClass: 'modal-min' });
  }
  loginForm: FormGroup;
  //form controls
  get f() {
    return this.loginForm.controls;
  }
  ngOnInit(): void {
    // Login Form
    // this.loginForm = new FormGroup({
    //   Email: new FormControl('', [
    //     Validators.required,
    //     Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
    //   ]),
    //   Password: new FormControl('', [
    //     Validators.required,
    //     Validators.minLength(8),
    //     Validators.maxLength(20)
    //   ])
    // });

    this.loginForm = new FormGroup({
      Email: new FormControl('root@localhost', [
        Validators.required,
        Validators.pattern(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/)
      ]),
      Password: new FormControl('admin123', [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(20)
      ])
    });


  }
  onSubmit() {
    //console.log(this.loginForm);

    //submits form data to remote server
    // onSubmit($event: Event) {
    // $event.preventDefault();
    // this.router.navigate(['/auth/otp-confirm']);
    // stop here if form is invalid

    if (this.loginForm.invalid) {
      return;
    } else {
      this.validateUser(this.loginForm.value.Email, this.loginForm.value.Password);


    }

    //this.validate_bypass();
  }


  onResetpassword() {
    this.router.navigate(['api/auth/reset-password']);
  }


  validateUser(username, password) {

    this.ngxLoader.start();
    const payload = {
      username,
      password
    };
    this.api.post('auth/login', payload).subscribe(
      res => {
        this.auth.login();
        this.auth.setToken(res.result.accessToken);
        this.auth.setUsername((res.result.username));

        this.api.setUserPermission(res.result);

        this.auth.setUserPermission(res.result);

        if (this.auth.isLoggedIn) {
          // Usually you would use the redirect URL from the auth service.
          // However to keep the example simple, we will always redirect to `/admin`.
          var redirectUrl = "";
          redirectUrl = '/analytics/dashboard';

          // Set our navigation extras object
          // that passes on our global query params and fragment
          const navigationExtras: NavigationExtras = {
            queryParamsHandling: 'preserve',
            preserveFragment: true
          };

          this.ngxLoader.stopAll();
          // Redirect the user
          this.router.navigate([redirectUrl], navigationExtras);

        }
      },
      errResp => {
        this.ngxLoader.stopAll();
        console.log(JSON.stringify(errResp));
        //this.apiService.showNotification('Wrong Creddentials', 'Credentials entered are wrong \n re-enter to login.', 'error');
        Swal.fire({
          position: 'top-end',
          icon: 'error',
          title: 'Oops...',
          text: 'Credentials entered are wrong \n re-enter to login.!',
          footer: ''
        });
      }
    );

  }


  validate_bypass() {
    var redirectUrl = "";
    let nav = [
      {
        "username": "test@gmail.com",
        "roles": [
          "can_view_schools_module",
          "can_view_school_dashboard_module",
          "can_view_students_module",
          "can_view_school_admin_module",
          "can_view_school_academics_module",


          "can_view_schools_list",
          "can_view_academic_year",
          "can_view_students_list",
          "can_view_exam_grading",
          "can_view_exam_list",
          "can_view_class",
          "can_view_subjects",




          "can_add_school_cluster",
          "can_add_school_gender",
          "can_delete_positions",
          "can_edit_user_type",
          "can_delete_subject",
          "can_delete_school_cluster",
          "can_add_parents",
          "can_delete_hostels",
          "can_add_student",
          "can_add_school_category",
          "can_view_grade_posting",
          "can_view_school_stream",
          "can_add_user_category",
          "can_view_school_class",
          "can_add_roles",
          "can_edit_school_stream",
          "can_view_user_role",
          "can_edit_role_permission",
          "can_view_terms",
          "can_delete_parents",
          "can_edit_education_system",
          "can_view_exam_type",
          "can_delete_class_model",
          "can_add_exam_schedule",
          "can_view_school_cluster",
          "can_view_school_sponsor",
          "can_edit_subject",
          "can_view_user_type_specific",
          "can_add_school_class",
          "can_view_class_model",
          "can_view_school_category",
          "can_edit_exam_type",
          "can_add_school_sponsor",
          "can_add_role_permission",
          "can_edit_user_type_specific",
          "can_view_parents",
          "can_add_education_system",
          "can_add_hostels",
          "can_edit_terms",
          "can_edit_user_role",
          "can_add_school_type",
          "can_edit_school_category",
          "can_view_school_level",
          "can_create_role",
          "can_delete_school_level",
          "can_view_organization",
          "can_add_grades",
          "can_edit_grade_posting",
          "can_delete_calender_items",
          "can_edit_school_class",
          "can_add_exam_type",
          "can_add_user_role",
          "can_delete_exam_schedule",
          "can_edit_user",
          "can_edit_school_type",
          "can_view_calender_items",
          "can_edit_calender_items",
          "can_edit_student",
          "can_view_hostels",
          "ROLE_Administration",
          "can_view_school_gender",
          "can_edit_exam_schedule",
          "can_delete_user_role",
          "can_add_user_type",
          "can_view_school_type",
          "can_view_subject",
          "can_delete_stream_details",
          "can_view_roles",
          "can_view_exam_schedule",
          "can_edit_roles",
          "can_edit_staff",
          "can_edit_calender_event",
          "can_view_stream_details",
          "can_view_staff",
          "can_edit_role",
          "can_add_calender_event",
          "can_add_grade_posting",
          "can_delete_organization",
          "can_delete_terms",
          "can_add_user",
          "can_view_grades",
          "can_edit_school_gender",
          "can_view_user_category",
          "can_add_stream_details",
          "can_view_calender_event",
          "can_edit_user_category",
          "can_delete_grades",
          "can_delete_role",
          "can_add_user_type_specific",
          "can_edit_parents",
          "can_delete_grade_posting",
          "can_edit_grades",
          "can_edit_organization",
          "can_view_education_system",
          "can_delete_school_type",
          "can_view_role_permission",
          "can_add_school_stream",
          "can_delete_exam_type",
          "can_add_terms",
          "can_add_school_level",
          "can_delete_roles",
          "can_delete_school_category",
          "can_delete_student",
          "can_delete_user_type_specific",
          "can_delete_school_sponsor",
          "can_view_user",
          "can_delete_user_type",
          "can_edit_positions",
          "can_edit_hostels",
          "can_delete_staff",
          "can_view_student",
          "can_delete_user",
          "can_delete_school_class",
          "can_edit_stream_details",
          "can_edit_class_model",
          "can_view_user_type",
          "can_view_positions",
          "can_delete_education_system",
          "can_delete_school_gender",
          "can_add_staff",
          "can_delete_calender_event",
          "can_edit_school_level",
          "can_delete_school_stream",
          "can_add_positions",
          "can_delete_user_category",
          "can_delete_role_permission",
          "can_add_subject",
          "can_add_calender_items",
          "can_add_class_model",
          "can_edit_school_cluster",
          "can_edit_school_sponsor",
          "can_add_organization"
        ],
        "accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY1MjE4Nzg4NiwiZXhwIjoxNjUyMjc0Mjg2LCJzY2hvb2xfaWQiOiJmMmFkNjFjYi05N2EzLTRlYmYtOTMxYi0xZGFmZTE5MDBhYzEifQ.MlfVglvlCWVmdblrLj0M0UX7LtWl_7sp4UbDbjc4hsjgO0vV0PbNVeGL7GanFmmwGNyJh5JYdInJdYSQuOl_bA",
        "tokenType": "Bearer"
      }
    ];

    this.auth.login();
    this.auth.setToken(('eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiaWRjb0BnbWFpbC5jb20iLCJleHAiOjE2NTI4NjIyOTcsImlhdCI6MTYyMTMyNjI5N30.2ZRadG0leOjQ1tbKyL14qtKmyGfpHcj8g4X24owGAb7HQQ82Tmy_3EG5DUy2T7oAkV3zsZnsChBd1FiUyPdqTQ'));

    this.auth.setUsername('root');

    this.auth.setUserPermission(nav);

    if (this.auth.isLoggedIn) {
      // Usually you would use the redirect URL from the auth service.
      // However to keep the example simple, we will always redirect to `/admin`.
      var redirectUrl = "";
      redirectUrl = '/analytics/dashboard';

      // Set our navigation extras object
      // that passes on our global query params and fragment
      const navigationExtras: NavigationExtras = {
        queryParamsHandling: 'preserve',
        preserveFragment: true
      };

      this.ngxLoader.stopAll();
      // Redirect the user
      this.router.navigate([redirectUrl], navigationExtras);

    }
  }
}







