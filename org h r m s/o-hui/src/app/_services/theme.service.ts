import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TokenStorageService } from './token-storage.service';

export interface ThemeObject {
  oldValue: string;
  newValue: string;
}

@Injectable({
  providedIn: 'root',
})
export class ThemeService {

  initialSetting: ThemeObject = {
    oldValue: null,
    newValue: 'bootstrap',
  };

  themeSelection: BehaviorSubject<ThemeObject> = new BehaviorSubject<ThemeObject>(this.initialSetting);

  constructor(
    private tokenStorageService: TokenStorageService
  ) { }

  setTheme(theme: string) {
    this.tokenStorageService.setTheme(this.themeSelection.value.newValue,theme);
    this.themeSelection.value.newValue === null
      ? this.themeSelection.next({ oldValue: this.themeSelection.value.newValue, newValue: theme})
      : this.themeSelection.next({ oldValue: this.tokenStorageService.getTheme().oldValue, newValue: this.tokenStorageService.getTheme().newValue})
  }

  themeChanges(): Observable<ThemeObject> {
    return this.themeSelection.asObservable();
  }
}
