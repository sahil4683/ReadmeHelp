import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { DashboardMasterService } from 'src/app/_service/webhims/dashboard/dashboard-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-hims-home',
  templateUrl: './hims-home.component.html',
  styleUrls: ['./hims-home.component.css']
})
export class HimsHomeComponent implements OnInit {

  constructor(
    private service: DashboardMasterService,
    private tokenStorageService:TokenStorageService,
    private titleService: Title
  ) { }
  
  dashboardCount: any = [];
  isLoad = false;
  totalRegisterd: string;

  notAccess = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | HIMS Home");
    this.getDashboardCount();
    this.getDashbgetRegistredCountoardCount();


    const privileges = this.tokenStorageService.getPrivileges();
    privileges.forEach(element => {
      if (element.menu_var == 'hm_0') { 
        this.notAccess = element.menu_action; 
      }
    })
  
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
