import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TokenStorageService } from '../../_services/token-storage.service';

@Component({
  selector: 'app-no-access',
  templateUrl: './no-access.component.html',
  styleUrls: ['./no-access.component.css']
})
export class NoAccessComponent implements OnInit {

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
      if(timeleft===0){ window.location.href="/login"; }
    }, 1000);

  }
  exit(): void {
    window.location.href="/login"
  }

}
