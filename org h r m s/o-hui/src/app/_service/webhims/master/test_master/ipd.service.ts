import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl + '/master-test_master-ipd_test/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class IpdService {

  constructor(private http: HttpClient) { }

  save(form: any): Observable<any> {
    return this.http.post(API_URL + 'save', form, httpOptions);
  }

  update(form): Observable<any> {
    return this.http.put(API_URL + 'update', form, httpOptions);
  }

  delete(id): Observable<any> {
    return this.http.delete(API_URL + 'delete/' + id , httpOptions);
  }

  get(): Observable<any> {
    return this.http.get(API_URL + 'findAll', { responseType: 'json' });
  }

  getParticularsListByOrganization(organization): Observable<any> {
    return this.http.get(API_URL + 'getParticularsListByOrganization/' + organization, { responseType: 'json' });
  }

  getParticularsListByGroup(group,org): Observable<any> {
    return this.http.get(API_URL + 'getParticularsListByGroup/' + group+'/'+org, { responseType: 'json' });
  }

  getDailyTestList(): Observable<any> {
    return this.http.get(API_URL + 'getDailyTestList', { responseType: 'json' });
  }

}
