import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl+"/master-test_master-opd_test/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class OpdService {
  
  constructor(private http: HttpClient) { }

  save(form: any): Observable<any> { 
    return this.http.post(API_URL + 'save', form, httpOptions);
  }

  update(form): Observable<any> {
    return this.http.put(API_URL + 'update', form, httpOptions);
  }

  delete(id): Observable<any> {
    return this.http.delete(API_URL + 'delete/'+id , httpOptions);
  }

  get(): Observable<any> {
    return this.http.get(API_URL + 'findAll', { responseType: 'json' });
  }

  getParticularsListByGroup(group): Observable<any> {
    return this.http.get(API_URL + 'getParticularsListByGroup/'+group, { responseType: 'json' });
  }

  getParticularsListByOrganization(organization): Observable<any> {
    return this.http.get(API_URL + 'getParticularsListByOrganization/'+organization, { responseType: 'json' });
  }

  getParticularsListByGroupAndOrganization(group,Organization): Observable<any> {
    return this.http.get(API_URL + 'getParticularsListByGroupAndOrganization/'+group+'/'+Organization, { responseType: 'json' });
  }

  updateRate(form): Observable<any> {
    return this.http.put(API_URL + 'updateRate', form, httpOptions);
  }

}
