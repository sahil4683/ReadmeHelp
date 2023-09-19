import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-organization-report',
  templateUrl: './organization-report.component.html',
  styleUrls: ['./organization-report.component.css']
})
export class OrganizationReportComponent implements OnInit {

  constructor() { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
