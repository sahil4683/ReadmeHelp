import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-patho-lab-test-count-report',
  templateUrl: './patho-lab-test-count-report.component.html',
  styleUrls: ['./patho-lab-test-count-report.component.css']
})
export class PathoLabTestCountReportComponent implements OnInit {

  constructor() { }
form: any = {};
  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
  }
  onNew(): void {
    
  }

}
