import { Component, OnInit } from '@angular/core';
import { AuthService } from '../_services/auth.service';
import { TokenStorageService } from '../_services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import Swal from 'sweetalert2';
import { UserService } from '../_services/user.service';
import { environment } from 'src/environments/environment'
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string;
  isUserManagement = false;
  app_name: string;

  accountYearList = [
    "2019-2020",
    "2020-2021",
    "2021-2022",
    "2022-2023",
    ]

  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private _router: Router,
    private userService: UserService,
    private titleService: Title
  ) { }

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Main");
    this.app_name = environment.app_name;
    this.form.year = this.accountYearList[2]

    if (!window.localStorage.getItem('console_login')){
      this._router.navigate(['portal-login']);
    }
    const lastRoute = window.location.href.substring(window.location.href.lastIndexOf('/') + 1);
    if (lastRoute == 'user') {  this.isUserManagement = true; } else { this.isUserManagement = false; }
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
    this.getUsers();
  }

  UserList:any = [];
  getUsers(): void {
    this.userService.getAllUser().subscribe(data => {
      this.UserList = data.body;
    }, err => {
      console.error(err);
    });
  }

  onSubmit(): void {
    this.authService.login(this.form).subscribe(
      data => {
        if(data.roles=="ROLE_ADMIN"){
          this.tokenStorage.saveToken(data.accessToken);
          this.tokenStorage.saveUser(data);
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          if(data.username === "user_manager"){
            window.location.assign('user/home');
          }
        }else{
          this.isLoginFailed = true;
          this.errorMessage = "You do not have permission to login !"
        }
      }, err => {
        console.log(err);
        if(err.status == 0){ this.errorMessage = "Server Error! Please Contact Administrator"; }
        else if(err.status == 405){ this.errorMessage = "Invalid User Or Not Found !"; }
        else{ this.errorMessage = err.error; }
        this.isLoginFailed = true;
        
      }
    );
  }

  goToBack(): void {
    this.tokenStorage.exit()
    this._router.navigate(['portal-login']);
  }

  addLoginType(type) {
    let url;
    if(type === "HIMS"){ 
       url = this._router.serializeUrl(this._router.createUrlTree(['/hims/home']));
    }
    if(type === "IPD"){ 
       url = this._router.serializeUrl(this._router.createUrlTree(['/ipd/home']));
    }
    if(type === "PATHO"){ 
       url = this._router.serializeUrl(this._router.createUrlTree(['/patho/home']));
    }
    if(type === "USER"){ 
       url = this._router.serializeUrl(this._router.createUrlTree(['/user/home']));
    }
    window.open(url, '_blank');
  }

  reloadPage(): void {
    window.location.reload();
  }

  // variable
  show_button: Boolean = false;
  show_eye: Boolean = false;

//Function
showPassword() {
    this.show_button = !this.show_button;
    this.show_eye = !this.show_eye;
  }

}
