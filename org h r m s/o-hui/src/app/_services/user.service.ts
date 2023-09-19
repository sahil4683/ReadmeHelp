import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

// const API_URL = 'http://localhost:5000/api/auth/';
import { environment } from '../../environments/environment';
const API_URL = environment.apiUrl + '/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getAllUser(): Observable<any> {
    return this.http.get(API_URL + 'allUser');
  }

  
  getUsersByType(login_type): Observable<any> {
    return this.http.get(API_URL + 'getUsersByLoginType/' + login_type);
  }

  getAccess(id): Observable<any> {
    return this.http.get(API_URL + 'get-privileges/' + id);
  }

  updateAccess(form): Observable<any> {
    return this.http.post(API_URL + 'update-privileges', form, httpOptions);
  }

  deleteUser(id): Observable<any> {
    return this.http.post(API_URL + 'user-delete/' + id, httpOptions);
  }

  updateUser(form): Observable<any> {
    return this.http.post(API_URL + 'user-update', form, httpOptions);
  }

  updatePass(form): Observable<any> {
    return this.http.post(API_URL + 'user-pass-update', form, httpOptions);
  }



  consoleLoginUpdate(credentials): Observable<any> {
    return this.http.post(API_URL + 'console-update', {
      username: credentials.username,
      password: credentials.password,
      loginType: credentials.loginType,
    }, httpOptions);
  }

  getConsoleUser(): Observable<any> {
    return this.http.get(API_URL + 'getConsoleUser');
  }

  getNumberSequence():Observable<any> {
    return this.http.get(API_URL + 'getNumberSequence');
  }

  saveNumberSequence(form):Observable<any> {
      return this.http.post(API_URL + 'number-sequence-update', form, httpOptions);
    }

    getNextNumberSequence(name): Observable<any> {
      return this.http.get(API_URL + 'getNextNumberSequence/' + name);
    }

}
