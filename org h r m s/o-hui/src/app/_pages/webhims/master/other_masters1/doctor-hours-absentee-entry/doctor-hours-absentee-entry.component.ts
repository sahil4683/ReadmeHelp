import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-doctor-hours-absentee-entry',
  templateUrl: './doctor-hours-absentee-entry.component.html',
  styleUrls: ['./doctor-hours-absentee-entry.component.css']
})
export class DoctorHoursAbsenteeEntryComponent implements OnInit {

  constructor() { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
