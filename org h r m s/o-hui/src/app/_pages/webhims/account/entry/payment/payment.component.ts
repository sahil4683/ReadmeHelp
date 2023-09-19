import { Component, EventEmitter, Input, OnInit } from '@angular/core';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import Swal from 'sweetalert2';
import { ActivatedRoute } from '@angular/router';
import { PaymentService } from 'src/app/_service/webhims/account/entry/payment.service';
import { PlasticMoneyMasterService } from 'src/app/_service/webhims/master/other_masters1/plastic-money-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { debounceTime } from 'rxjs/operators';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

  constructor(private getService: AdmissionService,
    private tokenStorageService: TokenStorageService,
    private activatedroute: ActivatedRoute,
    private service: PaymentService,
    private componentReloadService: ComponentReloadService,
    private plasticMoneyMasterService: PlasticMoneyMasterService,
    private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  spinner = false;
  isPLASTICMONEY = false;
  isCHEQUE = false;
  PlasticInstrumentNameList: any = [];
  PatientDetails: any = [];
  showContent = false;

  isedit_action = false
  isdelete_action = false

  n: any = [];

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Payment & Refund");
    this.tokenStorageService.setSessionPrivileges('hm_3_1_2');
    window.scrollTo(0, 0);
    this.getPatientDetailList();
    this.onTable(0);
    this.getPlasticInstrumentNameList();

    this.getTableSearch();

    this.activatedroute.queryParams.subscribe(data => {
      this.form.ipdno = data.search;

      // if(data['search'] != 0){
      //   this.filterPatientDetails()
      // }

    });

    this.form.dept = '16';
    this.form.receiptDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
  
    this.getNextNumberSequence();
  }
  
  getNextNumberSequence():void{
    this.tokenStorageService.getNextNumberSequence('Payment').subscribe(
      data => {
        this.form.paymentNo = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getPatientDetailList(): void {
    this.getService.getPatientDetailList().subscribe(
      data => {
        this.PatientDetails = data.body.filter(opt => opt.name = opt.name + '(' + opt.uhid + ')');
        this.filterPatientDetails();
      },
      err => {
        console.error(err);
      }
    );
  }

  filterPatientByName(): void {
    const SortRow = this.PatientDetails.find((e => e.name == this.form.paidTo));
    this.form.ipdno = SortRow.ipd;
    this.form.uhid = SortRow.uhid;
  }

  filterPatientDetails(): void {
    this.form.uhid = '';
    this.form.paidTo = '';
    if (this.form.ipdno != null && this.form.ipdno.length >= 0 && this.form.ipdno != '') {
      const SortRow = this.PatientDetails.find((e => e.ipd == this.form.ipdno));
      if (SortRow != null) {
        this.form.ipdno = SortRow.ipd;
        this.form.uhid = SortRow.uhid;
        this.form.paidTo = SortRow.name;
      }
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

  getPdf(id) {
    if (id != null && id != 'undefined') {
      this.service.printReport('pdf', id).subscribe(
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
  getxlsx(id) {
    if (id != null && id != 'undefined') {
      this.service.printReport('xlsx', id).subscribe(
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

  onNew(): void {
    this.componentReloadService.reload();

  }

  typeChange(): void {
    this.isPLASTICMONEY = false;
    this.isCHEQUE = false;

    if (this.form.type == 'PLASTICMONEY') {
      this.isPLASTICMONEY = true;
    }
    if (this.form.type == 'CHEQUE') {
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
    this.typeChange();
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
