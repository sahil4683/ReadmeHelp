import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-advetisment-corp',
  templateUrl: './advetisment-corp.component.html',
  styleUrls: ['./advetisment-corp.component.css']
})
export class AdvetismentCorpComponent implements OnInit {

  constructor() { }

  public formBody = new FormGroup({
    issuerName: new FormControl('', [Validators.required, Validators.maxLength(5)]),
    dateOfBirth: new FormControl(new Date()),
    address: new FormControl('', [Validators.required, Validators.maxLength(100)])
  });

  ngOnInit(): void {
  }
  onSubmit(): void {
    if(this.formBody.valid) {
      alert("Valid")
    }
  }
  public hasError = (controlName: string, errorName: string) =>{
    return this.formBody.get(controlName).hasError(errorName);
  }
  // getErrorMessage() {
  //   if(this.formBody.get("issuerName")?.hasError('required')){
  //     return 'issuerName is required';
  //   }
  //   // if(this.issuerName.hasError('required')){
  //   //   return 'issuerName is required';
  //   // }
  //   // if(this.issuerName.hasError('maxlength')){
  //   //   return 'max lenght is 5 email';
  //   // }
  //   return "";
  // }

}
