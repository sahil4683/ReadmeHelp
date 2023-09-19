import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const API_URL = environment.apiUrl + '/template_master/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class TemplateMasterService {
  constructor(private http: HttpClient) { }


  findByTemplate(template): Observable<any> {
    return this.http.get(API_URL + 'findByTemplate/' + template, { responseType: 'json' });
  }

}
