import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-ipd-home',
  templateUrl: './ipd-home.component.html',
  styleUrls: ['./ipd-home.component.css']
})
export class IpdHomeComponent implements OnInit {

  constructor(
    private tokenStorageService:TokenStorageService,
    private titleService: Title
  ) { }

  notAccess = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | IPD Home");
    const privileges = this.tokenStorageService.getPrivileges();
    privileges.forEach(element => {
      if (element.menu_var == 'im_0') { 
        this.notAccess = element.menu_action; 
      }
    })
  }

}
