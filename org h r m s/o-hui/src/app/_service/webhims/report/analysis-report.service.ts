import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const API_URL = environment.apiUrl+"/report-analysis_reports/";
const httpOptions = {
  headers: new HttpHeaders({
      'Content-Type': 'application/json'
  }),
};

@Injectable({
  providedIn: 'root'
})
export class AnalysisReportService {

  constructor(private http: HttpClient) { }

  printReport(form: any): Observable<any> { 
    return this.http.post(API_URL + 'printReport', form, { responseType: 'blob' });
  }

  getReport(form: any): Observable<any> { 
    return this.http.post(API_URL + 'getReport', form, { responseType: 'json' });
  }

}
