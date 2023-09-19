import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BedmasterService } from 'src/app/_service/webhims/static/bedmaster.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import Swal from 'sweetalert2';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-bed-shifting',
  templateUrl: './bed-shifting.component.html',
  styleUrls: ['./bed-shifting.component.css']
})


export class BedShiftingComponent implements OnInit {

  constructor(
    private bedmasterService: BedmasterService,
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private activatedroute: ActivatedRoute,
    private titleService: Title) { }

  form: any = {};
  getByDate: string;
  bedForm: any = {};
  isSubmit = false;
  bedDetails: Array<any> = [];
  bedHistoryDetails: Array<any> = [];
  isEdit = false;
  isShifting = false;
  patientDetails: Array<any> = [];
  bedAdmit = false;
  showContent1 = false;
  showContent2 = false;
  showContent3 = false;
  product: string;

  isedit_action = false
  isdelete_action = false

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Bed Shifting");
    this.tokenStorageService.setSessionPrivileges('hm_2_2_1');
    this.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
    this.bedMasterHistory(0);
    this.getAdmittedPatiendDetails();
    this.getBedList();

    this.activatedroute.queryParams.subscribe(data => {
      if(data.query){ 
        this.getPatientDetails_IPDNO(data.query);
      }
    });

  }

  getPatientDetails_IPDNO(ipdno){
    this.bedmasterService.getAdmittedPatiendDetailsByIpdNo(ipdno).subscribe(data => {
      if(data.status === 200){
        this.form = {};
        window.scrollTo(0, 0);
        setTimeout(() =>
        this.form = data.body
        , 100);
        this.isShifting = true;
        this.isEdit = false;
        this.form.shiftingDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
        this.form.shiftingTime = new Date().toTimeString().substring(0, 5);
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
    });
  }

  bedMasterHistory(flag): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
		if(hasAccess.edit_action) { this.isedit_action = true }
		if(hasAccess.delete_action) { this.isdelete_action = true }
    
    this.showContent1 = false;

    if (flag == 0) {
      var currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
      this.bedmasterService.getByDate(currentDate).subscribe(
      data => {
        setTimeout(() =>
        this.showContent1 = true,
        this.bedHistoryDetails = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }
    if (flag == 1) {
      this.bedmasterService.bedMasterHistory().subscribe(
      data => {
        setTimeout(() =>
        this.showContent1 = true,
        this.bedHistoryDetails = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }
    if (flag == 2) {
      this.bedmasterService.getByDate(this.getByDate).subscribe(
      data => {
        setTimeout(() =>
        this.showContent1 = true,
        this.bedHistoryDetails = data.body
        , 100);
      },
      err => {
        console.error(err)
      }
      );
    }
  }

  getAdmittedPatiendDetails(): void {
    this.bedmasterService.getAdmittedPatiendDetails().subscribe(data => {
      setTimeout(() =>
        this.showContent2 = true,
        this.patientDetails = data.body
        , 100);
    });
  }

  getBedList(): void {
    this.bedmasterService.getBedList().subscribe(data => {
      setTimeout(() =>
        this.showContent3 = true,
        this.bedDetails = data.body
        , 100);
    });
  }

  onEdit(row): void {
    window.scrollTo(0, 0);
    this.isShifting = false;
    this.isEdit = true;
    if (this.form.uhid == null) {
      Swal.fire({
        title: 'Error!',
        html: '<i>Patient Not Selected !</i>',
        icon: 'warning',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      this.form.shiftedbedno = row.bedno;
      this.form.shiftingDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
      this.form.shiftingTime = new Date().toTimeString().substring(0, 5);
    }
  }

  onPatientEdit(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.isShifting = true;
    this.isEdit = false;
    this.form.shiftingDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.shiftingTime = new Date().toTimeString().substring(0, 5);
  }

  onSubmit(): void {

    this.bedForm.companyID = this.form.companyId;
    this.bedForm.id = this.form.id;
    this.bedForm.ipdno = this.form.ipdno;
    this.bedForm.admitDate = this.form.date;
    this.bedForm.uhid = this.form.uhid;
    this.bedForm.pname = this.form.name;
    this.bedForm.preBedNo = this.form.bedno;
    this.bedForm.shiftBedNo = this.form.shiftedbedno;
    this.bedForm.shiftDate = this.form.shiftingDate;
    this.bedForm.shiftTime = this.form.shiftingTime;

    this.bedmasterService.shiftAllocatedBed(this.bedForm).subscribe(data => {
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
    });

  }

  onNew(): void {
    this.componentReloadService.reload();
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
        this.bedmasterService.deleteShifting(id).subscribe(
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

}
