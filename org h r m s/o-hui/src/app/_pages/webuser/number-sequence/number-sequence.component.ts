import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import Swal from 'sweetalert2';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-number-sequence',
  templateUrl: './number-sequence.component.html',
  styleUrls: ['./number-sequence.component.css']
})
export class NumberSequenceComponent implements OnInit {

  constructor(
    private userService :UserService,
    private componentReloadService:ComponentReloadService
  ) { }

  uhidForm: any = {};
  ipdForm: any = {};
  ipdBillForm:any = {};
  provisionalBillForm:any = {};
  opdBillForm: any = {};
  opdBillRefundForm:any = {};
  labBillForm: any = {};
  labBillRefundForm: any = {};
  radioBillForm: any = {};
  radioBillRefundForm: any = {};
  healthCheckupBillForm: any = {};
  healthCheckupBillRefundForm: any = {};
  paymentForm: any = {};
  receiptForm: any = {};


  ngOnInit(): void {
    this.userService.getNumberSequence().subscribe(
      data => {
        for (let item of data.body) {

          if(item.name === 'UHID'){ this.uhidForm = item; }
          if(item.name === 'IPD'){ this.ipdForm = item; }
          
          if(item.name === 'IPD Bill'){ this.ipdBillForm = item; }
          if(item.name === 'Provisional Bill'){ this.provisionalBillForm = item; }

          if(item.name === 'OPD Bill'){ this.opdBillForm = item; }
          if(item.name === 'OPD Bill Refund'){ this.opdBillRefundForm = item; }

          if(item.name === 'Lab Bill'){ this.labBillForm = item; }
          if(item.name === 'Lab Bill Refund'){ this.labBillRefundForm = item; }

          if(item.name === 'Radio Bill'){ this.radioBillForm = item; }
          if(item.name === 'Radio Bill Refund'){ this.radioBillRefundForm = item; }

          if(item.name === 'HealthCheckup Bill'){ this.healthCheckupBillForm = item; }
          if(item.name === 'HealthCheckup Bill Refund'){ this.healthCheckupBillRefundForm = item; }

          if(item.name === 'Payment'){ this.paymentForm = item; }
          if(item.name === 'Receipt'){ this.receiptForm = item; }

        }
      });
  }

  onSubmit(form): void {
    this.userService.saveNumberSequence(form).subscribe(
      data => {
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300
          }).then((result) => {
            if (result.isConfirmed) {
              this.componentReloadService.reload();
            }
          });
        } 
        else {
          Swal.fire({
            title: 'Error!',
            text: data.message,
            icon: 'error',
            confirmButtonText: 'OK',
            width: 300
          })
        }
      },
      err => {
        console.log(err);
      }
    );
  }

}
