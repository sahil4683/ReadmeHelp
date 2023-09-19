import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class ComponentReloadService {

  constructor(private router:Router) { }

  reload(): void {
    let currentUrl = this.router.url.split('?')[0];
    this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate([currentUrl]);
    });
  }

}
