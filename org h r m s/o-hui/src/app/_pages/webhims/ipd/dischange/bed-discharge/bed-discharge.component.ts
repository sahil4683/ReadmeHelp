import { Component, OnInit } from '@angular/core';
import { IpdService } from 'src/app/_service/webhims/master/test_master/ipd.service';
import { BedmasterService } from 'src/app/_service/webhims/static/bedmaster.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import Swal from 'sweetalert2';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';
import { FinalIpdBillService } from 'src/app/_service/webhims/ipd/bill/final-ipd-bill.service';

@Component({
  selector: 'app-bed-discharge',
  templateUrl: './bed-discharge.component.html',
  styleUrls: ['./bed-discharge.component.css']
})
export class BedDischargeComponent implements OnInit {

  constructor(private bedmasterService: BedmasterService, 
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private finalIpdBillService: FinalIpdBillService,
    private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  patientList: any = [];
  patientDetails: any = [];
  isEdit = false;
  dischargePatient: any = {};
  showContent = false;
  showContent2 = false;
  dischargeSummary: any = [];

  getByDate:string;

  isedit_action = false
		isdelete_action = false

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Bed Discharge");
    this.tokenStorageService.setSessionPrivileges('hm_2_3_1');
		this.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
    this.getReadyForDischarge();
    this.onTable(0)

  }

  getPatient(){
    this.showContent = false
    this.bedmasterService.getAdmittedPatiendDetails().subscribe(data => {
      setTimeout(() =>
        this.showContent = true,
        this.patientDetails = data.body
        , 100);
    });
  }

  getReadyForDischarge(){
    this.showContent = false
    this.finalIpdBillService.getReadyForDischarge().subscribe(data => {
      setTimeout(() =>
        this.showContent = true,
        this.patientDetails = data.body
        , 100);
    });
  }

  onTable(flag):void{

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
		if(hasAccess.edit_action) { this.isedit_action = true }
		if(hasAccess.delete_action) { this.isdelete_action = true }

    this.showContent2 = false;
    if (flag == 0) {
      var currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
      this.bedmasterService.getByDateDischarge(currentDate).subscribe(
      data => {
        setTimeout(() =>
        this.showContent2 = true,
        this.dischargeSummary = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }
    if (flag == 1) {
      this.bedmasterService.get().subscribe(
      data => {
        setTimeout(() =>
        this.showContent2 = true,
        this.dischargeSummary = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }
    if (flag == 2) {
      this.bedmasterService.getByDateDischarge(this.getByDate).subscribe(
      data => {
        setTimeout(() =>
        this.showContent2 = true,
        this.dischargeSummary = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }


    // this.bedmasterService.get().subscribe(data => {
      // setTimeout(() =>
      //   this.showContent2 = true,
      //   this.dischargeSummary = data.body
      //   , 100);
    // });

  }


  // onSubmit(): void {

  // }

  saveDeshargePatient() {

    this.dischargePatient = {};

    if (this.isEdit == true){
    this.dischargePatient.id = this.form.id;
    }

    this.dischargePatient.date = this.form.date;
    this.dischargePatient.dtime = this.form.time;
    this.dischargePatient.pname = this.form.patientName;
    this.dischargePatient.ipdno = this.form.ipdno;
    this.dischargePatient.ipdno = this.form.ipdno;
    this.dischargePatient.caseno = this.form.uhid;
    this.dischargePatient.bedNo = this.form.bedno;
    this.dischargePatient.dtype = this.form.dtype;


    this.bedmasterService.saveDeschargePatient(this.dischargePatient).subscribe((data => {

      if (data.status == 200) {
        Swal.fire({
          title: 'Success!',
          text: data.message,
          icon: 'success',
          confirmButtonText: 'OK',
          width: 300
        }).then((result) => {
          if (result.isConfirmed) {
            this.onNew();
          }
        });
      } else {
        let error = '';
        error += data.message + '\n';
        if (data.fieldErrorMessageList != null) {
          for (const ob of data.fieldErrorMessageList) {
            error += ob.fieldName + ' : ' + ob.errorMessage + '\n';
          }
        }
        Swal.fire({
          title: 'Error!',
          text: data.message,
          html: '<pre>' + error + '</pre>',
          icon: 'error',
          confirmButtonText: 'OK',
          width: 350
        });
      }
    }));

  }

  onEdit(row): void {
    this.isEdit = true;
    window.scrollTo(0, 0);

    this.form = row;
    this.form.patientName = row.pname;
    this.form.bedno = row.bedNo;
    this.form.time = row.dtime;

    this.form.uhid = row.caseno;
    this.form.time = row.dtime;

  }

  onDelete(id): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you want this!',
      icon: 'warning',
      width: 300,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.bedmasterService.delete(id).subscribe(
          data => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Deleted!',
                text: 'Data Deleted Success',
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300,
                
              }).then((result) => {
                if (result.isConfirmed) {
                  this.onNew();
                }
              });
            }
          },
          err => {
            console.error(err);
          }
        );
      }
    });
  }

  onPatientEdit(row): void {
    this.isEdit = false;
    this.form = {};
    window.scrollTo(0, 0);

    this.form = row;
    this.form.patientName = row.name;
    const currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.date = currentDate;
    this.form.time = new Date().toTimeString().substring(0, 5);
  }

  onNew(): void {
    this.componentReloadService.reload();
  }

}
