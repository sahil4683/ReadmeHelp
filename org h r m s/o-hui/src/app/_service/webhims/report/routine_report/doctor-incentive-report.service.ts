import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl+"/report_routine_doctor_incentive/";
const httpOptions = {
  headers: new HttpHeaders({
      'Content-Type': 'application/json'
  }),
};

@Injectable({
  providedIn: 'root'
})
export class DoctorIncentiveReportService {

  constructor(private http: HttpClient) { }

  printReport(form: any): Observable<any> { 
    return this.http.post(API_URL + 'printReport', form, { responseType: 'blob' });
  }

  printReportByDoctor(form: any): Observable<any> { 
    return this.http.post(API_URL + 'printReportByDoctor', form, { responseType: 'blob' });
  }

  getTransactedDoctorList(fromDate,toDate): Observable<any> {
    return this.http.get(API_URL + 'getTransactedDoctorList/'+fromDate+'/'+toDate, { responseType: 'json' });
  }

}
