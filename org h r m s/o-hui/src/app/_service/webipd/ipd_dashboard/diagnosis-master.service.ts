import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const API_URL = environment.apiUrl + '/diagnosis_master/';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DiagnosisMasterService {

  constructor(private http: HttpClient) { }

  get(): Observable<any> {
    return this.http.get(API_URL + 'findAll', { responseType: 'json' });
  }

  getByChapter(chapter): Observable<any> {
    return this.http.get(API_URL + 'getByChapter/' + chapter, { responseType: 'json' });
  }

}
