import { Component, OnInit } from '@angular/core';
import { ThemeService } from 'src/app/_services/theme.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

@Component({
  selector: 'app-theme-toggle',
  templateUrl: './theme-toggle.component.html',
  styleUrls: ['./theme-toggle.component.scss']
})
export class ThemeToggleComponent implements OnInit {

  theme: string = 'bootstrap';

  constructor(private themeService: ThemeService, 
    private tokenStorageService:TokenStorageService) { }

  ngOnInit(): void {
    if(this.tokenStorageService.getTheme()!=null && this.tokenStorageService.getTheme().newValue === 'bootstrap-dark'){
      this.themeService.setTheme('bootstrap-dark')
      this.theme = 'bootstrap-dark';
    }
  }

  toggleTheme() {
    this.theme === 'bootstrap'? this.theme = 'bootstrap-dark' : this.theme = 'bootstrap';
    this.themeService.setTheme(this.theme)
  }

}
