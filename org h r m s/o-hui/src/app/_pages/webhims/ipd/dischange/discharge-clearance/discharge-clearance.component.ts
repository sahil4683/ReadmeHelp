import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-discharge-clearance',
  templateUrl: './discharge-clearance.component.html',
  styleUrls: ['./discharge-clearance.component.css']
})
export class DischargeClearanceComponent implements OnInit {

  constructor() { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
