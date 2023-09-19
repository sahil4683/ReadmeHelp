import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { RegistrationService } from 'src/app/_service/webhims/reception/reception_reception/registration.service';
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { CashDuesService } from 'src/app/_service/webhims/reception/reception_bill/cash-dues.service';
import { PlasticMoneyMasterService } from 'src/app/_service/webhims/master/other_masters1/plastic-money-master.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { debounceTime } from 'rxjs/operators';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-cash-dues',
  templateUrl: './cash-dues.component.html',
  styleUrls: ['./cash-dues.component.css']
})
export class CashDuesComponent implements OnInit {
  constructor(private registrationService: RegistrationService,
              private activatedroute: ActivatedRoute,
              private service: CashDuesService,
              private componentReloadService: ComponentReloadService,
              private plasticMoneyMasterService: PlasticMoneyMasterService,
              private tokenStorageService:TokenStorageService,
              private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  isPay = false;
  table_data: any = [];
  spinner = false;
  isPLASTICMONEY = false;
  isCHEQUE = false;
  PlasticInstrumentNameList: any = [];
  isDue = false;
  table_due_data: any = [];
  showContent = false;
  n: any = [];

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Cash Dues");
    this.tokenStorageService.setSessionPrivileges('hm_1_2_5');
    window.scrollTo(0, 0);
    this.form.receiptDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
    this.form.receiptNo = 0;
    this.activatedroute.queryParams.subscribe(data => {
      if (data.uhid){
        this.getPatientDetails(data.uhid);
      }
    });
    this.onTable(0);
    this.getPlasticInstrumentNameList();
    this.getTableSearch();
  }


  getDue(): void{
    this.service.getDue().subscribe(
      data => {
        this.table_due_data = data.body;
        this.isDue = true;
      },
      err => {
        console.error(err);
      }
    );
  }

  getPdf(uhid){
    if (uhid != null && uhid != 'undefined'){
    this.service.printReport('pdf', uhid).subscribe(
      data => {
        if (data.size == 0) {
          Swal.fire({
            title: 'Error!',
            html: '<i>Data Not Found OR Error !</i>',
            icon: 'error',
            confirmButtonText: 'OK',
            width: 350
          });
        } else {
          const fileURL = URL.createObjectURL(data);
          window.open(fileURL, '_blank');
        }
      },
      err => {
        console.log(err);
      }
    );
    }
  }
  getxlsx(uhid){
    if (uhid != null && uhid != 'undefined'){
    this.service.printReport('xlsx', uhid).subscribe(
      data => {
        if (data.size == 0) {
          Swal.fire({
            title: 'Error!',
            html: '<i>Data Not Found OR Error !</i>',
            icon: 'error',
            confirmButtonText: 'OK',
            width: 350
          });
        } else {
          const fileURL = URL.createObjectURL(data);
          window.open(fileURL, '_blank');
        }
      },
      err => {
        console.log(err);
      }
    );
  }
  }

  getPlasticInstrumentNameList(): void {
    this.plasticMoneyMasterService.get().subscribe(
      data => {
        this.PlasticInstrumentNameList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

 isedit_action = false
  isdelete_action = false
  onTable(flag): void {
    const hasAccess = this.tokenStorageService.getSessionPrivileges()
if (hasAccess.edit_action) { this.isedit_action = true }
if (hasAccess.delete_action) { this.isdelete_action = true }

    if (flag == 0) {
      const currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
      this.service.getByDate(currentDate).subscribe(
        data => {
          this.table_data = data.body;
          this.showContent = true;
        },
        err => {
          console.error(err);
        }
      );
    }
    if (flag == 1) {
      this.service.get().subscribe(
        data => {
          this.showContent = false;
          setTimeout(() =>
          this.showContent = true,
          this.table_data = data.body
          , 100);
        },
        err => {
          console.error(err);
        }
      );
    }
    if (flag == 2) {
      this.service.getByDate(this.form.getByDate).subscribe(
        data => {
          this.showContent = false;
          setTimeout(() =>
        this.showContent = true,
        this.table_data = data.body
        , 100);
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  							
tableDistinctList = [];
@Input()
typeaheadTextInput_table = new EventEmitter<string>();
// @Output() onChange_table = new EventEmitter<{}>();
openSuggestionDropdown_table = false;
tokensAreLoading_table = false;
  getTableSearch(): void {
    this.typeaheadTextInput_table
      .pipe(debounceTime(1000),)
      .subscribe(searchtext => {
        if (searchtext) {
          this.openSuggestionDropdown_table = true;
          if (this.tableDistinctList.length == 0) {
            this.tokensAreLoading_table = true;
          }
          this.refreshTableDistinctList(searchtext)
        }
        else {
          this.openSuggestionDropdown_table = false;
          this.tableDistinctList = []
        }
      });
  }
  refreshTableDistinctList(searchtext: string): void {
    this.service.searchOnTableData(searchtext).subscribe(
      data => {
        this.tableDistinctList = data.body
        this.tokensAreLoading_table = false;
      },
      err => {
        console.error(err)
      }
    );
  }
  onTableSearch(search): void {
    if(search){
      this.service.getOnTableData(this.form.searchTable).subscribe(
        data => {
          this.showContent = false;
          setTimeout(() =>
          this.showContent = true,
          this.table_data = data.body
          , 100);
        },
        err => {
          console.error(err);
        }
      );
    }
  }




  getPatientDetails(uhid): void{
    // const ipd = this.form.ipdno;
    // if (this.form.ipdno != null && this.form.ipdno.length >= 0 && this.form.ipdno != ''){
      this.spinner = true;
      // this.registrationService.getPatientDetailsByUHID
      this.registrationService.getPatientDetailsByUHID(uhid).subscribe(
        data => {
          if (data.body == null) {
            Swal.fire({
              title: 'Error!',
              text: 'Not Found',
              icon: 'error',
              confirmButtonText: 'OK',
              width: 300
            });
            this.form = {};
          }else{
            this.form = data.body;
            this.form.receivedFrom = data.body.name + '(' + data.body.uhid + ')';
            // this.form.ipdno = ipd;
          }
          this.spinner = false;
        },
        err => {
          console.error(err);
        }
      );
    // }
  }
  onNew(): void {
    this.componentReloadService.reload();

  }

  typeChange(): void{

    this.isPLASTICMONEY = false;
    this.isCHEQUE = false;

    if (this.form.type == 'PLASTICMONEY'){
      this.isPLASTICMONEY = true;
    }
    if (this.form.type == 'CHEQUE'){
      this.isCHEQUE = true;
    }

  }

  onSave(): void {
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
  }

  onEdit(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.isEdit = true;
    this.isPay = false;
    this.typeChange();
  }

  onEditDeu(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.form.amount = row.due;
    this.form.receiptDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.against = row.patientName + ' (BILLNO : ' + row.billNo + ')';
    this.form.receivedFrom = row.dept + ' INCOME';
    this.isEdit = false;
    this.isPay = true;
    this.typeChange();
    this.inWords();
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
      this.service.delete(id).subscribe(
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

  onSubmit(): void {
    this.isSubmit = true;
    this.service.save(this.form).subscribe(
      data => {
        this.isSubmit = false;
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
      },
      err => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

  onUpdate(): void {
    this.isSubmit = true;
    this.service.update(this.form).subscribe(
      data => {
        this.isSubmit = false;
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
      },
      err => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }
   inWords(): void {
     let num = this.form.amount;
     const a = ['', 'One ', 'Two ', 'Three ', 'Four ', 'Five ', 'Six ', 'Seven ', 'Eight ', 'Nine ', 'Ten ', 'Eleven ', 'Twelve ', 'Thirteen ', 'Fourteen ', 'Fifteen ', 'Sixteen ', 'Seventeen ', 'Eighteen ', 'Nineteen '];
     const b = ['', '', 'Twenty', 'Thirty', 'Forty', 'Fifty', 'Sixty', 'Seventy', 'Eighty', 'Ninety'];

     if ((num = num.toString()).length > 9) { this.form.words = 'overflow'; }
     this.n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
     if (!this.n) { return; } let str = '';
     str += (this.n[1] != 0) ? (a[Number(this.n[1])] || b[this.n[1][0]] + ' ' + a[this.n[1][1]]) + 'Crore ' : '';
     str += (this.n[2] != 0) ? (a[Number(this.n[2])] || b[this.n[2][0]] + ' ' + a[this.n[2][1]]) + 'Lakh ' : '';
     str += (this.n[3] != 0) ? (a[Number(this.n[3])] || b[this.n[3][0]] + ' ' + a[this.n[3][1]]) + 'Thousand ' : '';
     str += (this.n[4] != 0) ? (a[Number(this.n[4])] || b[this.n[4][0]] + ' ' + a[this.n[4][1]]) + 'Hundred ' : '';
     str += (this.n[5] != 0) ? ((str != '') ? '' : '') + (a[Number(this.n[5])] || b[this.n[5][0]] + ' ' + a[this.n[5][1]]) + '' : '';
      // return str;
     str += 'Rupees Only';
     this.form.words = str;
  }

}
