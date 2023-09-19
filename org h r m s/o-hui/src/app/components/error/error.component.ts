import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../_services/token-storage.service';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {

  constructor(
    private router: Router,
    private tokenStorage: TokenStorageService
  ) { }

 inputValue:string;

  ngOnInit(): void {

    let timeleft = 10;
    let downloadTimer = setInterval(function () {
      if (timeleft <= 0) { clearInterval(downloadTimer); }
      (document.getElementById("progressBar")as HTMLInputElement).value = String(10 - timeleft);
      timeleft -= 1;
      if(timeleft===0){
        window.localStorage.clear();
        window.location.href="/portal-login";
      }
    }, 1000);

  }
  exit(): void {
    this.tokenStorage.exit();
    
  }

}
