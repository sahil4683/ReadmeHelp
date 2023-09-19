import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { environment } from 'src/environments/environment'

@Component({
  selector: 'app-license-login',
  templateUrl: './license-login.component.html',
  styleUrls: ['./license-login.component.css']
})
export class LicenseLoginComponent implements OnInit {
  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string;

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private _router: Router,
  ) { }

  ngOnInit(): void {
    this.inputChange();

    console.log(window.location.href.substring(window.location.href.lastIndexOf('/') + 1));

    if (localStorage.getItem('console_login')) {
      this.isLoginFailed = false;
      this.isLoggedIn = true;
      this._router.navigate(['login']);
    }


    this.form.username = environment.license_login_user;
    this.inputChange();
  }

  inputChange() {
    const inputs = document.querySelectorAll('.input');
    function addClass() {
      const parent = this.parentNode.parentNode;
      parent.classList.add('focus');
    }
    function removeClass() {
      const parent = this.parentNode.parentNode;
      if (this.value == '') {
        parent.classList.remove('focus');
      }
    }

    inputs.forEach((input) => {
      input.addEventListener('focus', addClass);
      input.addEventListener('blur', removeClass);
    });

  }


  onSubmit(): void {

    this.authService.consoleLogin(this.form).subscribe(
      data => {

        if (data.status == 200) {
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this._router.navigate(['login']);
          window.localStorage.setItem('console_login', 'true');
        } else {
          this.isLoginFailed = true;
          this.errorMessage = 'Contact Administrator';
        }

      },
      err => {
        console.log(err);
        if(err.status == 0){ this.errorMessage = "Server Error! Please Contact Administrator"; }
        else if(err.status == 405){ this.errorMessage = "Invalid User Or Not Found !"; }
        else{ this.errorMessage = err.error; }
        this.isLoginFailed = true;
        
      });


    // this.authService.login(this.form).subscribe(
    //   data => {
    //     this.tokenStorage.saveToken(data.accessToken);
    //     this.tokenStorage.saveUser(data);

    //     this.isLoginFailed = false;
    //     this.isLoggedIn = true;

    //     // this.reloadPage();
    //     // this.roles = this.tokenStorage.getUser().roles;

    //     this.reloadPage();

    //   },
    //   err => {
    //     this.errorMessage = err.error.message;
    //     this.isLoginFailed = true;
    //   }
    // );


  }

  // reloadPage(): void {
  //   window.location.reload();
  // }

}
