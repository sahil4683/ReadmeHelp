import { Component, OnInit, Renderer2 } from '@angular/core';
import { AdminAccountyearService } from 'src/app/./_services/admin_service/accountyear.service';
import { TokenStorageService } from 'src/app/./_services/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ThemeService } from 'src/app/_services/theme.service';

@Component({
  selector: 'app-side-bar',
  templateUrl: './side-bar.component.html',
  styleUrls: ['./side-bar.component.css']
})
export class SideBarComponent implements OnInit {


  isLoggedIn = false;
  username: string;
  isHIMS = false;
  isIPD = false;
  isPATHO = false;
  isUSER = false;
  navbarCollapsed = true;

  notAccess = false;

  /**
   *  HIMS
   **/
  hm_0 = false;

  hm_1 = false;
  hm_1_1 = false;
  hm_1_1_1 = false;
  hm_1_1_2 = false;

  hm_1_2 = false;
  hm_1_2_1 = false;
  hm_1_2_2 = false;
  hm_1_2_3 = false;
  hm_1_2_4 = false;
  hm_1_2_5 = false;

  hm_1_3 = false;
  hm_1_3_1 = false;
  hm_1_3_2 = false;
  hm_1_3_3 = false;
  hm_1_3_4 = false;
  // --------------

  hm_2 = false;
  hm_2_1 = false;
  hm_2_1_1 = false;
  hm_2_1_2 = false;
  hm_2_1_3 = false;
  hm_2_1_4 = false;
  hm_2_1_5 = false;

  hm_2_2 = false;
  hm_2_2_1 = false;
  hm_2_2_2 = false;
  hm_2_2_3 = false;

  hm_2_3 = false;
  hm_2_3_1 = false;
  hm_2_3_2 = false;

  hm_3 = false;
  hm_3_1 = false;
  hm_3_1_1 = false;
  hm_3_1_2 = false;

  hm_4 = false;
  hm_4_1 = false;
  hm_4_1_1 = false;
  hm_4_1_2 = false;
  hm_4_1_3 = false;
  hm_4_1_4 = false;
  hm_4_1_5 = false;
  hm_4_1_6 = false;
  hm_4_1_7 = false;
  hm_4_1_8 = false;

  hm_4_2 = false;
  hm_4_2_1 = false;
  hm_4_2_2 = false;

  hm_4_3 = false;
  hm_4_3_1 = false;
  hm_4_3_2 = false;
  hm_4_3_3 = false;

  hm_5 = false;
  hm_5_1 = false;
  hm_5_1_1 = false;
  hm_5_1_2 = false;

  hm_5_2 = false;
  hm_5_2_1 = false;
  hm_5_2_2 = false;

  hm_5_3 = false;
  hm_5_3_1 = false;
  hm_5_3_2 = false;
  hm_5_3_3 = false;

  hm_5_4 = false;
  hm_5_4_1 = false;

  hm_5_5 = false;
  hm_5_5_1 = false;
  hm_5_5_2 = false;

  hm_5_6 = false;
  hm_5_6_1 = false;
  hm_5_6_2 = false;
  hm_5_6_3 = false;
  hm_5_6_4 = false;
  hm_5_6_5 = false;
  hm_5_6_6 = false;
  hm_5_6_7 = false;
  hm_5_6_8 = false;
  hm_5_6_9 = false;
  hm_5_6_10 = false;
  hm_5_6_11 = false;
  hm_5_6_12 = false;
  hm_5_6_13 = false;
  hm_5_6_14 = false;
  hm_5_6_15 = false;
  hm_5_6_16 = false;
  hm_5_6_17 = false;
  hm_5_6_18 = false;

  hm_5_7 = false;
  hm_5_7_1 = false;
  hm_5_7_2 = false;
  hm_5_7_3 = false;
  hm_5_7_4 = false;
  hm_5_7_5 = false;
  hm_5_7_6 = false;
  hm_5_7_7 = false;
  hm_5_7_8 = false;
  hm_5_7_9 = false;
  hm_5_7_10 = false;
  hm_5_7_11 = false;
  hm_5_7_12 = false;

  /**
   *  IPD
   **/
  im_0 = false;
  im_1 = false;
  im_1_1 = false;
  im_1_2 = false;
  im_1_3 = false;

  /**
   *  PATHO
   **/
   pm_0 = false;
   pm_1 = false;
   pm_2 = false;
   pm_2_1 = false;
   pm_2_2 = false;
   pm_3 = false;
   pm_3_1 = false;
   pm_3_2 = false;
   pm_3_3 = false;
   pm_3_4 = false;
   pm_3_5 = false;
   pm_3_6 = false;
   pm_3_7 = false;
   pm_3_8 = false;
   pm_3_9 = false;
   pm_3_10 = false;
   pm_3_11 = false;
   pm_3_12 = false;
   pm_4 = false;
   pm_4_1 = false;
   pm_4_2 = false;



  constructor(private tokenStorageService: TokenStorageService,
    private accountYearService: AdminAccountyearService,
    private themeService: ThemeService, private renderer: Renderer2,
    private _router: Router,
  ) { }

  ngOnInit(): void {

    /* Theme Setup  Start ->*/
    if(this.tokenStorageService.getTheme()!=null ){ this.themeService.setTheme(this.tokenStorageService.getTheme().newValue); }
    this.themeService.themeChanges().subscribe(theme => {
      if (theme.oldValue) { this.renderer.removeClass(document.body, theme.oldValue); }
      this.renderer.addClass(document.body, theme.newValue);
    })
    /* <- Theme Setup  End*/





    this.isLoggedIn = !!this.tokenStorageService.getToken();
    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.username = user.username;

      var checkNav = this.getLocation(window.location.href);
      // console.log(checkNav.pathname)
      // if(checkNav.pathname.startsWith("/login")){ this.isHIMS = false; this.isIPD = false; this.isPATHO = false; this.isUSER = false; }
      if(checkNav.pathname.startsWith("/hims")){ this.isHIMS = true; }
      if(checkNav.pathname.startsWith("/ipd")){ this.isIPD = true; }
      if(checkNav.pathname.startsWith("/patho")){ this.isPATHO = true; }
      if(checkNav.pathname.startsWith("/user")){ this.isUSER = true; }

      const privileges = this.tokenStorageService.getPrivileges();
      if (privileges == null || privileges.length === 0) {
        this.notAccess = true;
      } else {

        privileges.forEach(element => {

          /**
           *  HIMS
           **/
           if (element.menu_var == 'hm_0') { this.hm_0 = element.menu_action; }
           if (element.menu_var == 'hm_1') { this.hm_1 = element.menu_action; }
           if (element.menu_var == 'hm_1_1') { this.hm_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_1_1_1') { this.hm_1_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_1_1_2') { this.hm_1_1_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_1_2') { this.hm_1_2 = element.menu_action; }
           if (element.menu_var == 'hm_1_2_1') { this.hm_1_2_1 = element.menu_action; }
           if (element.menu_var == 'hm_1_2_2') { this.hm_1_2_2 = element.menu_action; }
           if (element.menu_var == 'hm_1_2_3') { this.hm_1_2_3 = element.menu_action; }
           if (element.menu_var == 'hm_1_2_4') { this.hm_1_2_4 = element.menu_action; }
           if (element.menu_var == 'hm_1_2_5') { this.hm_1_2_5 = element.menu_action; }
 
           if (element.menu_var == 'hm_1_3') { this.hm_1_3 = element.menu_action; }
           if (element.menu_var == 'hm_1_3_1') { this.hm_1_3_1 = element.menu_action; }
           if (element.menu_var == 'hm_1_3_2') { this.hm_1_3_2 = element.menu_action; }
           if (element.menu_var == 'hm_1_3_3') { this.hm_1_3_3 = element.menu_action; }
           if (element.menu_var == 'hm_1_3_4') { this.hm_1_3_4 = element.menu_action; }
 
           if (element.menu_var == 'hm_2') { this.hm_2 = element.menu_action; }
           if (element.menu_var == 'hm_2_1') { this.hm_2_1 = element.menu_action; }
           if (element.menu_var == 'hm_2_1_1') { this.hm_2_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_2_1_2') { this.hm_2_1_2 = element.menu_action; }
           if (element.menu_var == 'hm_2_1_3') { this.hm_2_1_3 = element.menu_action; }
           if (element.menu_var == 'hm_2_1_4') { this.hm_2_1_4 = element.menu_action; }
           if (element.menu_var == 'hm_2_1_5') { this.hm_2_1_5 = element.menu_action; }
 
           if (element.menu_var == 'hm_2_2') { this.hm_2_2 = element.menu_action; }
           if (element.menu_var == 'hm_2_2_1') { this.hm_2_2_1 = element.menu_action; }
           if (element.menu_var == 'hm_2_2_2') { this.hm_2_2_2 = element.menu_action; }
           if (element.menu_var == 'hm_2_2_3') { this.hm_2_2_3 = element.menu_action; }
 
           if (element.menu_var == 'hm_2_3') { this.hm_2_3 = element.menu_action; }
           if (element.menu_var == 'hm_2_3_1') { this.hm_2_3_1 = element.menu_action; }
           if (element.menu_var == 'hm_2_3_2') { this.hm_2_3_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_3') { this.hm_3 = element.menu_action; }
           if (element.menu_var == 'hm_3_1') { this.hm_3_1 = element.menu_action; }
           if (element.menu_var == 'hm_3_1_1') { this.hm_3_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_3_1_2') { this.hm_3_1_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_4') { this.hm_4 = element.menu_action; }
           if (element.menu_var == 'hm_4_1') { this.hm_4_1 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_1') { this.hm_4_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_2') { this.hm_4_1_2 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_3') { this.hm_4_1_3 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_4') { this.hm_4_1_4 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_5') { this.hm_4_1_5 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_6') { this.hm_4_1_6 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_7') { this.hm_4_1_7 = element.menu_action; }
           if (element.menu_var == 'hm_4_1_8') { this.hm_4_1_8 = element.menu_action; }
 
           if (element.menu_var == 'hm_4_2') { this.hm_4_2 = element.menu_action; }
           if (element.menu_var == 'hm_4_2_1') { this.hm_4_2_1 = element.menu_action; }
           if (element.menu_var == 'hm_4_2_2') { this.hm_4_2_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_4_3') { this.hm_4_3 = element.menu_action; }
           if (element.menu_var == 'hm_4_3_1') { this.hm_4_3_1 = element.menu_action; }
           if (element.menu_var == 'hm_4_3_2') { this.hm_4_3_2 = element.menu_action; }
           if (element.menu_var == 'hm_4_3_3') { this.hm_4_3_3 = element.menu_action; }
 
           if (element.menu_var == 'hm_5') { this.hm_5 = element.menu_action; }
           if (element.menu_var == 'hm_5_1') { this.hm_5_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_1_1') { this.hm_5_1_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_1_2') { this.hm_5_1_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_2') { this.hm_5_2 = element.menu_action; }
           if (element.menu_var == 'hm_5_2_1') { this.hm_5_2_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_2_2') { this.hm_5_2_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_3') { this.hm_5_3 = element.menu_action; }
           if (element.menu_var == 'hm_5_3_1') { this.hm_5_3_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_3_2') { this.hm_5_3_2 = element.menu_action; }
           if (element.menu_var == 'hm_5_3_3') { this.hm_5_3_3 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_4') { this.hm_5_4 = element.menu_action; }
           if (element.menu_var == 'hm_5_4_1') { this.hm_5_4_1 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_5') { this.hm_5_5 = element.menu_action; }
           if (element.menu_var == 'hm_5_5_1') { this.hm_5_5_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_5_2') { this.hm_5_5_2 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_6') { this.hm_5_6 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_1') { this.hm_5_6_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_2') { this.hm_5_6_2 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_3') { this.hm_5_6_3 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_4') { this.hm_5_6_4 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_5') { this.hm_5_6_5 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_6') { this.hm_5_6_6 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_7') { this.hm_5_6_7 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_8') { this.hm_5_6_8 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_9') { this.hm_5_6_9 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_10') { this.hm_5_6_10 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_11') { this.hm_5_6_11 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_12') { this.hm_5_6_12 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_13') { this.hm_5_6_13 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_14') { this.hm_5_6_14 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_15') { this.hm_5_6_15 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_16') { this.hm_5_6_16 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_17') { this.hm_5_6_17 = element.menu_action; }
           if (element.menu_var == 'hm_5_6_18') { this.hm_5_6_18 = element.menu_action; }
 
           if (element.menu_var == 'hm_5_7') { this.hm_5_7 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_1') { this.hm_5_7_1 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_2') { this.hm_5_7_2 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_3') { this.hm_5_7_3 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_4') { this.hm_5_7_4 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_5') { this.hm_5_7_5 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_6') { this.hm_5_7_6 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_7') { this.hm_5_7_7 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_8') { this.hm_5_7_8 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_9') { this.hm_5_7_9 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_10') { this.hm_5_7_10 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_11') { this.hm_5_7_11 = element.menu_action; }
           if (element.menu_var == 'hm_5_7_12') { this.hm_5_7_12 = element.menu_action; }
 
           /**
            *  IPD
            **/
           if (element.menu_var == 'im_0') { this.im_0 = element.menu_action; }
           if (element.menu_var == 'im_1') { this.im_1 = element.menu_action; }
           if (element.menu_var == 'im_1_1') { this.im_1_1 = element.menu_action; }
           if (element.menu_var == 'im_1_2') { this.im_1_2 = element.menu_action; }
           if (element.menu_var == 'im_1_3') { this.im_1_3 = element.menu_action; }



           /**
            *  PATHO
            **/
            if (element.menu_var == 'pm_0') { this.pm_0 = element.menu_action; }
            if (element.menu_var == 'pm_1') { this.pm_1 = element.menu_action; }
            if (element.menu_var == 'pm_2') { this.pm_2 = element.menu_action; }
            if (element.menu_var == 'pm_2_1') { this.pm_2_1 = element.menu_action; }
            if (element.menu_var == 'pm_2_2') { this.pm_2_2 = element.menu_action; }
            if (element.menu_var == 'pm_3') { this.pm_3 = element.menu_action; }
            if (element.menu_var == 'pm_3_1') { this.pm_3_1 = element.menu_action; }
            if (element.menu_var == 'pm_3_2') { this.pm_3_2 = element.menu_action; }
            if (element.menu_var == 'pm_3_3') { this.pm_3_3 = element.menu_action; }
            if (element.menu_var == 'pm_3_4') { this.pm_3_4 = element.menu_action; }
            if (element.menu_var == 'pm_3_5') { this.pm_3_5 = element.menu_action; }
            if (element.menu_var == 'pm_3_6') { this.pm_3_6 = element.menu_action; }
            if (element.menu_var == 'pm_3_7') { this.pm_3_7 = element.menu_action; }
            if (element.menu_var == 'pm_3_8') { this.pm_3_8 = element.menu_action; }
            if (element.menu_var == 'pm_3_9') { this.pm_3_9 = element.menu_action; }
            if (element.menu_var == 'pm_3_10') { this.pm_3_10 = element.menu_action; }
            if (element.menu_var == 'pm_3_11') { this.pm_3_11 = element.menu_action; }
            if (element.menu_var == 'pm_3_12') { this.pm_3_12 = element.menu_action; }

            if (element.menu_var == 'pm_4') { this.pm_4 = element.menu_action; }
            if (element.menu_var == 'pm_4_1') { this.pm_4_1 = element.menu_action; }
            if (element.menu_var == 'pm_4_2') { this.pm_4_2 = element.menu_action; }


        });

      }


    }

  }
 getLocation = function(href) {
    var l = document.createElement("a");
    l.href = href;
    return l;
};

  setSessionPrivileges(row: string): void {
    this.tokenStorageService.setSessionPrivileges(row);
  }

  logout(): void {
    this.tokenStorageService.signOut();
  }

}
