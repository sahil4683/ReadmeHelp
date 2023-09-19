import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

const API_URL = environment.apiUrl + '/patho_utility_update_sample/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PathoUtilityUpdateSampleService {

  constructor(private http: HttpClient) { }

  update(form: any): Observable<any> {
    return this.http.put(API_URL + 'update', form, httpOptions);
  }

  get(): Observable<any> {
    return this.http.get(API_URL + 'findAll', { responseType: 'json' });
  }

}
