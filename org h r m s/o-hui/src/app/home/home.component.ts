import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DashboardMasterService } from 'src/app/_service/webhims/dashboard/dashboard-master.service';
import { Observable, Subscriber } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  content: string;
  roles: string;
  role = false;
  isHIMS = false;
  isIPD = false;
  isPATHO = false;
  isUSER = false;

  dashboardCount: any = [];
  isLoad = false;
  totalRegisterd: string;



  constructor(private userService: UserService,
    private tokenStorage: TokenStorageService,
    private service: DashboardMasterService,
    private _router: Router) { }

  ngOnInit(): void {

    const privileges = this.tokenStorage.getPrivileges();
    const user = this.tokenStorage.getUser();

    if (privileges != null) {

      this.isHIMS = false;
      this.isIPD = false;
      this.isPATHO = false;
      this.isUSER = false;

      let hm_0 = privileges.find(item => item.menu_var === "hm_0");
      let im_0 = privileges.find(item => item.menu_var === "im_0");
      let pm_0 = privileges.find(item => item.menu_var === "pm_0");

      
      if (hm_0 && hm_0.menu_action) {
        if (user.loginType == 'HIMS') { this.isHIMS = true; }
        if (this.isHIMS) {
          this.getDashboardCount();
          this.getDashbgetRegistredCountoardCount();
        }
      }

      if (im_0 && im_0.menu_action) {
        if (user.loginType == 'IPD') { this.isIPD = true; }
      }

      if (pm_0 && pm_0.menu_action) {
        if (user.loginType == 'PATHO') { this.isPATHO = true; }
      }

    }
    if (user.loginType == 'USER') { this.isUSER = true; }
  }

  getDashboardCount(): void {
    this.service.getCount().subscribe(
      data => {
        this.dashboardCount = data.body;
        this.isLoad = true;
      },
      err => {
        console.error(err);
      }
    );
  }

  getDashbgetRegistredCountoardCount(): void {
    this.service.getRegistredCount().subscribe(
      data => {
        this.totalRegisterd = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }



}
