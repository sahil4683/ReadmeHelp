import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

const API_URL = environment.apiUrl+"/bedmaster/";
const API_URL_Discharge = environment.apiUrl+"/discharge/";
const TEMP_API_URL = environment.apiUrl
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class BedmasterService {

  constructor(private http: HttpClient) { }

  getBedList(): Observable<any> {
    return this.http.get(API_URL + 'getBedList', { responseType: 'json' });
  }

  bedStatus():Observable<any>{
     return this.http.get(API_URL+'getBedStatus' , {responseType:'json'});
  }

  vacantBedStatus():Observable<any>{
    return this.http.get(API_URL + 'vacantbedreport'  , {responseType:'blob'});
  }

    
  getAdmittedPatiendDetails():Observable<any>{
    return this.http.get(TEMP_API_URL+'/patient/getAdmittedPatiendDetails', {responseType:'json'});
  }

  getAdmittedPatiendDetailsByIpdNo(ipdno):Observable<any>{
    return this.http.get(TEMP_API_URL+'/patient/getAdmittedPatiendDetailsByIpdNo/'+ipdno, {responseType:'json'});
  }

  shiftAllocatedBed(form: any):Observable<any>{
    return this.http.post(TEMP_API_URL+'/shifting/save',form ,httpOptions);
  }

  bedMasterHistory():Observable<any>{
    return this.http.get(TEMP_API_URL+'/shifting/history', {responseType:'json'});
  }
  
  getByDate(date): Observable<any> {
		return this.http.get(TEMP_API_URL+'/shifting/getByDate/'+date, { responseType: 'json' });
	}

  deleteShifting(id): Observable<any> {
    return this.http.delete(TEMP_API_URL + '/shifting/delete/'+id , httpOptions);
  }


  saveDeschargePatient(form: any): Observable<any> { 
    return this.http.post(API_URL_Discharge + 'saveOrUpdate', form, httpOptions);
  }

  delete(id): Observable<any> {
    return this.http.delete(API_URL_Discharge + 'delete/'+id , httpOptions);
  }

  get():Observable<any>{
    return this.http.get(API_URL_Discharge+'findAll' , {responseType:'json'});
   }

   getByDateDischarge(date): Observable<any> {
		return this.http.get(API_URL_Discharge+'getByDate/'+date, { responseType: 'json' });
	}

 // /api/bedmaster/getBedList

  // getBedDetails():Observable<any>{

  //   return this.http.get(TEMP_API_URL+'/bedmaster/getBedList',{responseType:'json'});

  // }

}
