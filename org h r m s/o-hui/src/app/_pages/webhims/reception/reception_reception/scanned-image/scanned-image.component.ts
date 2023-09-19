import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-scanned-image',
  templateUrl: './scanned-image.component.html',
  styleUrls: ['./scanned-image.component.css']
})
export class ScannedImageComponent implements OnInit {

  constructor() { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
  }

  onSubmit(): void {
  }

}
