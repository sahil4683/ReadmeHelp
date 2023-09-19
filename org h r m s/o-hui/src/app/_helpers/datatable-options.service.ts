import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DatatableOptionsService {

  constructor() { }

  getDtOptions(){
    return {
		  pagingType: 'full_numbers',
		  pageLength: 10,
		  lengthMenu: [5, 10, 25],
		  processing: true,
		  dom: 'Bfrtip',
		  buttons: [{ extend: 'copy'},{extend: 'print',title: '',},{extend: 'csv',title: '',filename: 'Report_CSV'}, { extend: 'excel', title: '', filename: 'Report_EXCEL' }, { extend: 'pdf', title: '', filename: 'Report_PDF' },{ extend: 'colvis' }]
		};
  }
}
