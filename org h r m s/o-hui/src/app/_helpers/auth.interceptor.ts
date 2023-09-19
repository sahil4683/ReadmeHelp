import { HTTP_INTERCEPTORS, HttpEvent, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import {tap} from 'rxjs/operators';
import { TokenStorageService } from '../_services/token-storage.service';
import { Observable } from 'rxjs';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private token: TokenStorageService,
    private router: Router
    ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let authReq = req;
    const token = this.token.getToken();
    if (token != null) {
      const headers = new HttpHeaders({
        Authorization: 'Bearer ' + token,
        yearCd: this.token.getaccountYearId(),
        userCd: this.token.getUserId()
      });
      authReq = req.clone({headers});
    }
    // return next.handle(authReq);
    return next.handle(authReq).pipe( tap(() => {},
      (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status !== 401) {
         return;
        }
        this.router.navigate(['error']);
      }
    }));
  }
}

export const authInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
];
