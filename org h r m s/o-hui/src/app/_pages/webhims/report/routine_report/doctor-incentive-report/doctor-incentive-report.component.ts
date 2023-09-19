import { Component, OnInit, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { DatatableOptionsService } from 'src/app/_helpers/datatable-options.service';
import { DoctorIncentiveReportService } from 'src/app/_service/webhims/report/routine_report/doctor-incentive-report.service';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import {debounceTime} from 'rxjs/operators';
import {NgbAlert} from '@ng-bootstrap/ng-bootstrap'

@Component({
  selector: 'app-doctor-incentive-report',
  templateUrl: './doctor-incentive-report.component.html',
  styleUrls: ['./doctor-incentive-report.component.css']
})
export class DoctorIncentiveReportComponent implements OnInit {

  constructor(
    private reloadService: ComponentReloadService,
    private datatableOptionsService:DatatableOptionsService,
    private reportService: DoctorIncentiveReportService
  ) { }

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: any = {};

  form: any = {};
  isSubmit = false;
  doctorName:string;
  isAlert = false;

  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.isDoctorList=false;
    this.dtOptions = this.datatableOptionsService.getDtOptions();
  }

  onReload():void{
    this.reloadService.reload();
  }

  doctorList:any = [];
  isDoctorList = false;
  getTransactedDoctorList():void{
    this.isDoctorList=false
    this.reportService.getTransactedDoctorList(this.form.fromDate,this.form.toDate).subscribe(data =>{
    setTimeout(()=>
    this.isDoctorList=true,
    this.doctorList = data.body
    , 100);
    });
  }

  successMessage = '';
  @ViewChild('selfClosingAlert', {static: false}) selfClosingAlert: NgbAlert;
  private _success = new Subject<string>();
  
  printReportByDoctor(doctor,format): void {
    this.doctorName = doctor.doctorName;
    this.isAlert = true;
    this.form.format = format;
    this.form.consultant = doctor.doctorName
    this.reportService.printReportByDoctor(this.form).subscribe(
      data => {
        if (data.size == 0) {
          Swal.fire({
            title: 'Error!',
            html: '<i>Data Not Found OR Doctor Incentive Not Entered In Master 1 -> Doctor Incentive Master !</i>',
            icon: 'error',
            confirmButtonText: 'OK',
            width: 350
          });
        } else {
          const fileURL = URL.createObjectURL(data);
          window.open(fileURL, '_blank');
          this.isAlert = false;
        }
      },
      err => {
        console.log(err);
      }
    );
  }

  onSubmit(): void {  }

}
