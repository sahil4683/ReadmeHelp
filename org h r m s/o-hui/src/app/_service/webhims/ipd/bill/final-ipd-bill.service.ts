import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl + '/finalipd-bill/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class FinalIpdBillService {

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

  getByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getByDate/' + date, { responseType: 'json' });
  }

  getDetailsById(id): Observable<any> {
    return this.http.get(API_URL + 'getDetailsById/' + id , httpOptions);
  }

  deleteDetailById(id): Observable<any> {
    return this.http.delete(API_URL + 'deleteDetailById/' + id , httpOptions);
  }

  getLabRequest(ipdno,type): Observable<any> {
    return this.http.get(API_URL + 'getLabRequest/' + ipdno +'/' + type , httpOptions);
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


  getReadyForDischarge():Observable<any>{
    return this.http.get(API_URL+'getReadyForDischarge', {responseType:'json'});
  }

}
