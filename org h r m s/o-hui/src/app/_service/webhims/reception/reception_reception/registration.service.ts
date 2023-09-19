import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../../environments/environment';

const API_URL = environment.apiUrl+"/reception_reception_registration/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  
  constructor(private http: HttpClient) { }

  // getByVillageID(id): Observable<any> {
  //   return this.http.post(API_URL + 'getByVillageID/'+id, httpOptions);
  // }

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

  getNextUHID(): Observable<any> {
    return this.http.get(API_URL + 'getNextUHID', { responseType: 'json' });
  }

  getNextMLC(): Observable<any> {
    return this.http.get(API_URL + 'getNextMLC', { responseType: 'json' });
  }

  getNextTOKEN(): Observable<any> {
    return this.http.get(API_URL + 'getNextTOKEN', { responseType: 'json' });
  }

  getPatientDetailsByUHID(uhid): Observable<any> {
    return this.http.get(API_URL + 'getPatientDetailsByUHID/'+uhid , httpOptions);
  }

  getByDate(date): Observable<any> {
    return this.http.get(API_URL + 'getByDate/'+date, { responseType: 'json' });
  }

  printReport(format,id,type): Observable<any> {
		return this.http.get(API_URL + 'printReport/'+format+'/'+id+'/'+type, { responseType: 'blob' });
	  }

    // getUhidName(): Observable<any> {
    //   return this.http.get(API_URL + 'getUhidName', { responseType: 'json' });
    // }

    filterUhidName(searchtext): Observable<any> {
      return this.http.get(API_URL + 'filterUhidName/'+searchtext, { responseType: 'json' });
    }
  
    searchOnTableData(searchtext): Observable<any> {
      return this.http.get(API_URL + 'searchOnTableData/'+searchtext, { responseType: 'json' });
    }
    getOnTableData(searchtext): Observable<any> {
      return this.http.get(API_URL + 'getOnTableData/'+searchtext, { responseType: 'json' });
    }
    
}
