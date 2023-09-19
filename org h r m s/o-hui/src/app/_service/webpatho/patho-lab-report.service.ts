import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

// const API_URL = environment.apiUrl + '/patho_patient_master/';
const API_URL = environment.apiUrl + '/patho_lab_report/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PathoLabReportService {

  constructor(private http: HttpClient) { }

  getWaitingPatientByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getWaitingPatientByDate/' + date, { responseType: 'json' });
  }

  getCountByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getCountByDate/' + date, { responseType: 'json' });
  }
  

  /**
   * Error Here Plsease Check 
   */
  //  waiting(uhid,ipdno,dept,date,billNo): Observable<any> {
  //   return this.http.get(API_URL + 'waiting/'+uhid+'/'+ipdno+'/'+dept+'/'+date+'/'+billNo, { responseType: 'json' });
  // }
  waiting(item: any): Observable<any> {
    return this.http.post(API_URL + 'waiting', item, httpOptions);
  }

  saveTestData(roots: any): Observable<any> {
    return this.http.post(API_URL + 'saveTestData', roots, httpOptions);
  }

  
  getOtherByDateTableAndType(date,type): Observable<any> {
    return this.http.get(API_URL + 'getOtherByDateTableAndType/' + date+'/'+type, { responseType: 'json' });
  }

  collected(id): Observable<any> {
    return this.http.get(API_URL + 'collected/'+id, { responseType: 'json' });
  }

  receivedRequest(id): Observable<any> {
    return this.http.get(API_URL + 'receivedRequest/'+id, { responseType: 'json' });
  }


  saveObservationTest(form): Observable<any> {
    return this.http.put(API_URL + 'saveObservationTest', form, httpOptions);
  }

  printReport(id,type,format): Observable<any> {
    return this.http.get(API_URL + 'printReport/'+id+'/'+type+'/'+format, { responseType: 'blob' });
  }

  verifyTest(id,formatName): Observable<any> {
    return this.http.get(API_URL + 'verifyTest/'+id+ '/' + formatName, { responseType: 'json' });
  }

  getByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getByDate/'+date, { responseType: 'json' });
  }

  get(): Observable<any> {
    return this.http.get(API_URL + 'findAll', { responseType: 'json' });
  }
  
  updateTime(id): Observable<any> {
    return this.http.get(API_URL + 'updateTime/'+id, { responseType: 'json' });
  }

  verifyTestAll(id): Observable<any> {
    return this.http.get(API_URL + 'verifyTestAll/'+id, { responseType: 'json' });
  }

  
}
