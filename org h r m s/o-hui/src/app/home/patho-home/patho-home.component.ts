import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-patho-home',
  templateUrl: './patho-home.component.html',
  styleUrls: ['./patho-home.component.css']
})
export class PathoHomeComponent implements OnInit {

  constructor(
    private tokenStorageService:TokenStorageService,
    private titleService: Title
  ) { }

  notAccess = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Patho Home");
    const privileges = this.tokenStorageService.getPrivileges();
    privileges.forEach(element => {
      if (element.menu_var == 'pm_0') { 
        this.notAccess = element.menu_action; 
      }
    })
  }

}
