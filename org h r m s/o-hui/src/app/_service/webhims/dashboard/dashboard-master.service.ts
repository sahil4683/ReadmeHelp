import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const API_URL = environment.apiUrl+"/dashboard_master/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class DashboardMasterService {

  constructor(private http: HttpClient) { }

  getCount(): Observable<any> {
    return this.http.get(API_URL + 'getCount', { responseType: 'json' });
  }

  getRegistredCount(): Observable<any> {
    return this.http.get(API_URL + 'getRegistredCount', { responseType: 'json' });
  }
  

}
