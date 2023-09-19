import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// const AUTH_API = 'http://localhost:5000/api/auth/';
import { environment } from '../../environments/environment';
const AUTH_API = environment.apiUrl + '/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username: credentials.username,
      password: credentials.password,
      year: credentials.year
    }, httpOptions);
  }

  register(user): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username: user.username,
      password: user.password,
      loginType: user.loginType,
      role: user.role,
    }, httpOptions);
  }

  consoleLogin(credentials): Observable<any> {
    return this.http.post(AUTH_API + 'console', {
      username: credentials.username,
      password: credentials.password
    }, httpOptions);
  }

}
