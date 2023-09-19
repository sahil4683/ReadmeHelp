import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl + '/reception_reception_admission/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root'
})
export class AdmissionService {

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

  // getNextIPD(): Observable<any> {
  //   return this.http.get(API_URL + 'getNextIPD', { responseType: 'json' });
  // }

  getPatientDetailsByIPD(ipd): Observable<any> {
    return this.http.get(API_URL + 'getPatientDetailsByIPD/' + ipd , httpOptions);
  }
  getFullPatientDetailByIPD(ipd): Observable<any> {
    return this.http.get(API_URL + 'getFullPatientDetailByIPD/' + ipd , httpOptions);
  }

  getByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getByDate/' + date, { responseType: 'json' });
  }

  getPatientDetailList(): Observable<any> {
    return this.http.get(API_URL + 'getPatientDetailList', { responseType: 'json' });
  }
  getAdmitedPatient(searchtext): Observable<any> {
    return this.http.get(API_URL + 'getAdmitedPatient/' + searchtext, { responseType: 'json' });
  }
  getPatientDetailListWithDischarge(): Observable<any> {
    return this.http.get(API_URL + 'getPatientDetailListWithDischarge', { responseType: 'json' });
  }

  printReport(format, id, type): Observable<any> {
		return this.http.get(API_URL + 'printReport/' + format + '/' + id + '/' + type, { responseType: 'blob' });
	  }

    searchOnTableData(searchtext): Observable<any> {
      return this.http.get(API_URL + 'searchOnTableData/'+searchtext, { responseType: 'json' });
    }
    getOnTableData(searchtext): Observable<any> {
      return this.http.get(API_URL + 'getOnTableData/'+searchtext, { responseType: 'json' });
    }

}
