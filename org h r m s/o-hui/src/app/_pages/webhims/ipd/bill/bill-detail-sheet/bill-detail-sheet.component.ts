import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { IpdService } from 'src/app/_service/webhims/master/test_master/ipd.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { ReceiptService } from 'src/app/_service/webhims/account/entry/receipt.service';
import { BillDetailSheetService } from 'src/app/_service/webhims/ipd/bill/bill-detail-sheet.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { ActivatedRoute } from '@angular/router';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-bill-detail-sheet',
  templateUrl: './bill-detail-sheet.component.html',
  styleUrls: ['./bill-detail-sheet.component.css']
})
export class BillDetailSheetComponent implements OnInit {

  constructor(
    private admissionService: AdmissionService,
    private organizationMasterService: OrganizationMasterService,
    private ipdService: IpdService,
    private tokenStorageService: TokenStorageService, 
    private billDetailSheetService: BillDetailSheetService,
    private receiptService: ReceiptService,
    private consultantMasterService: ConsultantMasterService,
    private componentReloadService: ComponentReloadService,
    private activatedroute: ActivatedRoute,
    private titleService: Title) { }

  form: any = {};
  temp: any = {};

  isSubmit = false;
  ClassList: any = [];
  PatientDetails: any = [];
  OrganizationList: any = [];
  table_data: any = [];
  
  isEdit = false;

  isedit_action = false
  isdelete_action = false

  showContent = false;

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  DailyTestList: any = [];
  isDailyTestList = false;
  SelectedDailyTestList: any = [];

  TestList: any = [];


  ConsultantList: any = [];

  public DetailsList: any[] = [{
    sno: 1,
    date: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
    time: new Date().toTimeString().substring(0, 5),
    id: 0,
    testName: '',
    procedureDoctor1: '',
    procedureDoctor2: '',
    qty: 0,
    rate: 0,
    amount: 0
  }];

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Bill Details Sheet");
    this.tokenStorageService.setSessionPrivileges('hm_2_1_1');
    this.temp.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getOrganizationList();
    this.getUhidBillNoName();
    this.getTestList();
    this.getConsultantList();
    this.onTable(0);

    this.activatedroute.queryParams.subscribe(data => {
      if(data.ipdno){
        this.getPatientDetails_IPD(data.ipdno);
      }
    });

    this.getTableSearch()
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
    this.billDetailSheetService.searchOnTableData(searchtext).subscribe(
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
      this.billDetailSheetService.getOnTableData(this.form.searchTable).subscribe(
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

  onTable(flag): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
		if(hasAccess.edit_action) { this.isedit_action = true }
		if(hasAccess.delete_action) { this.isdelete_action = true }

    if (flag == 0) {
      const currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
      this.billDetailSheetService.getByDate(currentDate).subscribe(
        data => {
          this.showContent = false;
          this.table_data = data.body;
          this.showContent = true;
        },
        err => {
          console.error(err);
        }
      );
    }
    if (flag == 1) {
      this.billDetailSheetService.get().subscribe(
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
      this.billDetailSheetService.getByDate(this.temp.getByDate).subscribe(
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

  // getTable(): void{
  //   const hasAccess = this.tokenStorageService.getSessionPrivileges()
	// 	if(hasAccess.edit_action) { this.isedit_action = true }
	// 	if(hasAccess.delete_action) { this.isdelete_action = true }

  //   this.billDetailSheetService.get().subscribe(
  //     data => {
  //       setTimeout(() =>
  //       this.showContent = true,
  //       this.table_data = data.body
  //       , 100);
  //     },
  //     err => {
  //       console.error(err);
  //     }
  //   );
  // }

getUhidBillNoName(): void {
    this.typeaheadTextInput
      .pipe(debounceTime(1000), )
      .subscribe(searchtext => {
        if (searchtext) {
          this.openSuggestionDropdown = true;
          if (this.PatientDetails.length == 0) {
            this.tokensAreLoading = true;
          }
          this.refreshPatientDetails(searchtext);
        }
        else {
          this.openSuggestionDropdown = false;
          this.PatientDetails = [];
        }
      });
  }
  tokensChanged() {
    this.openSuggestionDropdown = false;
  }
  refreshPatientDetails(searchtext: string) {
    this.admissionService.getAdmitedPatient(searchtext).subscribe(
      data => {
        this.PatientDetails = data.body;
        this.tokensAreLoading = false;
      },
      err => {
        console.error(err);
      }
    );
  }

  getPatientDetails_IPD(ipdno): void {
    if (ipdno != null && ipdno.length >= 0 && ipdno != '') {
      this.admissionService.getPatientDetailsByIPD(ipdno).subscribe(
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
          } else {
            this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
            this.form.highRisk = 'NO';
            this.form.emergency = 'NO';
            this.form.uhid = data.body.uhid;
            this.form.ipdno = data.body.ipdno;
            this.form.organization = data.body.organization;
            this.form.runningOrganization = data.body.organization;
            this.form.runningClass = data.body.wardname;
            this.form.name = data.body.name;
          }
        },
        err => {
          console.error(err);
        }
      );
      this.receiptService.findByIpdno(ipdno).subscribe(
        data => {
        this.form.advance = Number(data.body);
      });
    }
  }

  getOrganizationList(): void{
    this.organizationMasterService.getOrganizationList().subscribe(
      data => {
        this.OrganizationList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getDailyTestList(): void{
    this.DailyTestList = [];
    this.isDailyTestList = false;
      this.ipdService.getDailyTestList().subscribe(
        data => {
          setTimeout(()=>
          (this.isDailyTestList = true),
          (this.DailyTestList = data.body)
          , 100);
        },
        err => {
          console.error(err);
        }
      );
  }
  addDailyTestList(e, item): void{
    if (e.target.checked) {
     this.SelectedDailyTestList.push(item);
    } else {
      const index = this.SelectedDailyTestList.indexOf(item);
      if (index > -1) {
        this.SelectedDailyTestList.splice(index, 1);
      }
    }
  }
  getTestList(): void{
    this.ipdService.get().subscribe(
      data => {
        this.TestList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  fillParticularsData(i): void{
    const SortRow = this.TestList.find((e => e.id == Number(this.DetailsList[i].testName)));

    if (this.form.runningClass == "GENERAL") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.general);
      this.DetailsList[i].amount = Number(SortRow.general);
     }
     if (this.form.runningClass == "SEMI SPECIAL") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.semiSpecial);
      this.DetailsList[i].amount = Number(SortRow.semiSpecial);
     }
     if (this.form.runningClass == "SPECIAL") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.special);
      this.DetailsList[i].amount = Number(SortRow.special);
     }
     if (this.form.runningClass == "DELUXE") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.deluxe);
      this.DetailsList[i].amount = Number(SortRow.deluxe);
     }
     if (this.form.runningClass == "EXECUTIVE") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.executive);
      this.DetailsList[i].amount = Number(SortRow.executive);
     }
     if (this.form.runningClass == "ICU") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.icu);
      this.DetailsList[i].amount = Number(SortRow.icu);
     }
     if (this.form.runningClass == "EMERGENCY") { 
      this.DetailsList[i].qty = 1;
      this.DetailsList[i].rate = Number(SortRow.emergency);
      this.DetailsList[i].amount = Number(SortRow.emergency);
     }

      this.calculateTotal(i);
  }
  calculateTotal(i): void {
    this.DetailsList[i].amount = Number(this.DetailsList[i].qty) * Number(this.DetailsList[i].rate);
    this.calculateFinalTotal();
  }
  calculateFinalTotal() {
    let amount = 0;
    this.DetailsList.forEach(value => {
      amount = amount + Number(value.amount);
    });
    this.form.netTotal = amount;
    this.form.total = Number(this.form.advance) - Number(this.form.netTotal);
  }
  getConsultantList(): void {
    this.consultantMasterService.getConsultantList().subscribe(
      data => {
        this.ConsultantList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  onSave(): void {
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
  }

  onSubmit(): void {
    this.form.listData = this.DetailsList;
    this.form.dailyTestList = this.SelectedDailyTestList;
    this.billDetailSheetService.save(this.form).subscribe(
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
              this.onReload();
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
        console.error(err);
      }
    );
  }

  onUpdate(): void {
    this.isSubmit = true;
    this.form.listData = this.DetailsList;
    this.form.dailyTestList = this.SelectedDailyTestList;
    this.billDetailSheetService.update(this.form).subscribe(
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
              this.onReload();
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
        console.error(err);
      }
    );
  }

  onReload(): void {
    this.componentReloadService.reload();
  } 

  addRow() {
    this.DetailsList.push({
      sno: this.DetailsList.length + 1,
      date: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
      time: new Date().toTimeString().substring(0, 5),
      id: 0,
      testName: '',
      procedureDoctor1: '',
      procedureDoctor2: '',
      qty: 0,
      rate: 0,
      amount: 0
    });
  }

  removeAddress(i: number, id) {
    if (confirm('Are you sure you want to remove it?')) {
      if (id != 0) {
        Swal.fire({
          title: 'Are you sure?',
          text: "Do you want this!",
          icon: 'warning',
          width: 300,
          showCancelButton: true,
          confirmButtonColor: '#3085d6',
          cancelButtonColor: '#d33',
          confirmButtonText: 'Delete it!'
        }).then((result) => {
          if (result.isConfirmed) {
            this.billDetailSheetService.deleteDetailById(id).subscribe(
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
                      this.onReload();
                    }
                  });
                }
              },
              err => {
                console.log(err)
              }
            );
          }
        })
      }
      this.DetailsList.splice(i, 1);
    }
  }



  onEdit(item): void {
    this.isEdit = true;
    window.scrollTo(0, 0);
    this.form = {};
    this.form = item
    this.isDailyTestList = true;
    this.form.organization = Number(item.organization);


    /** 
     * Commented For Daily Test List Edit Show In table Chacked 
     * **/
    // for (let element of item.dailyTest.split(',')) {
    //   element.checked = true;
    //   this.SelectedDailyTestList.push(element);
    // }

    
    this.billDetailSheetService.getDetailsById(item.id).subscribe(
      data => {
        setTimeout(() =>
        (this.DetailsList = data.body)
        , 100);
      },
      err => {
        console.error(err);
      }
    );
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
        this.billDetailSheetService.delete(id).subscribe(
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
                  this.onReload();
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
