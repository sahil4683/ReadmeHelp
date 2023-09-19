import { Injectable } from '@angular/core';
import * as CryptoJS from 'crypto-js';
import { UserService } from './user.service';

const TOKEN_KEY = 'auth-token';
const USER_KEY = 'auth-user';
const PRIVILEGES_KEY = 'privileges_access';
const THEME_KEY = 'theme_type';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  roleAs: string;
  accountYearId: string;
  id: string;
  keySetter = "alligatesys";

  constructor(
    private userService:UserService
  ) { }

  /* logout */
  exit(): void {
    window.localStorage.clear();
    window.location.href="/portal-login"
  }
  signOut(): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.removeItem(USER_KEY);
    window.localStorage.removeItem(PRIVILEGES_KEY);
  }
  

  /* Store User Information */
  public saveToken(token: string): void {
    window.localStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }
  public saveUser(user): void {
    window.localStorage.removeItem(USER_KEY);
    if(user.privileges.length != 0){ 
      user.privileges = this.setPrivileges(user.privileges) 
    }
    window.localStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  /* Get User Information */
  public getToken(): string {
    return localStorage.getItem(TOKEN_KEY);
  }
  public getUser(): any {
    return JSON.parse(localStorage.getItem(USER_KEY));
  }
  getRole() {
    this.roleAs = this.getUser().roles;
    return this.roleAs;
  }
  getUserId() {
    this.id = this.getUser().id;
    return this.id.toString();
  }
  getaccountYearId() {
    this.accountYearId = this.getUser().accountYearId;
    return this.accountYearId.toString();
  }



  /* [Menu] Privileges */
  setPrivileges(privileges) {
    return privileges = CryptoJS.AES.encrypt(JSON.stringify(privileges), this.keySetter).toString();
  }
  getPrivileges() {
    if(this.getUser().privileges.length != 0){
      return JSON.parse(CryptoJS.AES.decrypt(this.getUser().privileges, this.keySetter).toString(CryptoJS.enc.Utf8))
    }
    return null;
  }
  /* [Edit,Delete] Privileges */
  setSessionPrivileges(row: string) {
    const privileges = this.getPrivileges()
    let item = privileges.find(item => item.menu_var === row);
    window.localStorage.setItem(PRIVILEGES_KEY, CryptoJS.AES.encrypt(JSON.stringify(item), this.keySetter).toString());
  }
  getSessionPrivileges() {
    return JSON.parse(CryptoJS.AES.decrypt(localStorage.getItem(PRIVILEGES_KEY), this.keySetter).toString(CryptoJS.enc.Utf8));
  }



  /* Theme Setup */
  public getTheme(): any {
    return JSON.parse(window.localStorage.getItem(THEME_KEY));
  }
  public setTheme(oldValue, newValue): void {
    let values={  "oldValue": oldValue, "newValue": newValue }
    window.localStorage.setItem(THEME_KEY, JSON.stringify(values));
  }

  getNextNumberSequence(name){
    return this.userService.getNextNumberSequence(name);
  }

}
