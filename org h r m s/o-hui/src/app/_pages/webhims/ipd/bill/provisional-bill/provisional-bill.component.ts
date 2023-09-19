import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { DepartmentService } from 'src/app/_service/webhims/master/other_masters1/department.service';
import { SubdepartmentService } from 'src/app/_service/webhims/master/other_masters1/subdepartment.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import { ClassMasterService } from 'src/app/_service/webhims/master/other_masters1/class-master.service';
import { GroupService } from 'src/app/_service/webhims/master/other_masters1/group.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { IpdService } from 'src/app/_service/webhims/master/test_master/ipd.service';
import { ConcessionService } from 'src/app/_service/webhims/master/other_masters1/concession.service';
import { ReceiptService } from 'src/app/_service/webhims/account/entry/receipt.service';
import { ProvisionalBillService } from 'src/app/_service/webhims/ipd/bill/provisional-bill.service';
import { RequestSheetIpdService } from 'src/app/_service/webipd/ipd_dashboard/request-sheet-ipd.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { BillDetailSheetService } from 'src/app/_service/webhims/ipd/bill/bill-detail-sheet.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-provisional-bill',
  templateUrl: './provisional-bill.component.html',
  styleUrls: ['./provisional-bill.component.css']
})
export class ProvisionalBillComponent implements OnInit {

  constructor(
    private admissionService: AdmissionService,
    private departmentService: DepartmentService,
    private subdepartmentService: SubdepartmentService,
    private consultantMasterService: ConsultantMasterService,
    private doctorReferenceService: DoctorReferenceService,
    private classMasterService: ClassMasterService,
    private groupService: GroupService,
    private organizationMasterService: OrganizationMasterService,
    private ipdService: IpdService,
    private concessionService: ConcessionService,
    private receiptService: ReceiptService,
    private service: ProvisionalBillService,
    private tokenStorageService: TokenStorageService,
    private requestSheetIpdService: RequestSheetIpdService,
    private billDetailSheetService:BillDetailSheetService,
    private componentReloadService: ComponentReloadService,
    private titleService: Title
  ) { }
  petientData: any;

  form: any = {};
  temp: any = {};
  isSubmit = false;
  PatientDetails: any = [];
  DepartmentList: any = [];
  SubDepartmentList: any = [];
  ConsultantList: any = [];
  DoctorReferenceList: any = [];
  ClassList: any = [];
  GroupList: any = [];
  OrganizationList: any = [];
  TestList: any = [];
  ConcessionList: any = [];
  showContent = false;
  table_data: any = [];
  labRequestList: any = [];

  isEdit = false;

  isedit_action = false
  isdelete_action = false

  isLoadFromBillDetails = false;
  isLoadFromLab = false;
  isLoadFromRadio = false;

  ServiceChargesList = [
    { id: 2, serviceName: '', rate: 0 },
    { id: 1, serviceName: 'Service Charge', rate: 10 },
    { id: 3, serviceName: 'Service Charge(Linen Nursing Charge)', rate: 10 }
  ];

  showLabContent = false;

  public DetailsList: any[] = [{
    id: 0,
    sno: 1,
    ipdBollNo: 0,
    testName: '',
    testCode: 0,
    groupName: '',
    procedureDoctor1: '',
    procedureDoctor2: '',
    qty: 0,
    rate: 0,
    discount: '',
    discountPer: 0,
    additional: '',
    less: '',
    newSubGroupCode: 0,
    groupCode: 0,
    drAdd: 0,
    className: "1",
    classCode: 0,
    amount: 0,
    orgcode: 0,
    packageYn: '',
    packageName: '',
    pdate: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
    ptime: new Date().toTimeString().substring(0, 5),
    packageFlag: '',
    serviceFlag: '',
    requestType: ''
    }];


  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Provisional Bill");
    this.tokenStorageService.setSessionPrivileges('hm_2_1_3');
    this.temp.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
    this.getUhidBillNoName();
    this.getDepartmentList();
    this.getSubDepartmentList();
    this.getConsultantList();
    this.getDoctorReferenceList();
    this.getClassList();
    this.getGroupList();
    this.getOrganizationList();
    // this.getTestList();
    this.getConcessionList();
    this.onTable(0);

    this.getNextNumberSequence();

    this.getTableSearch();

    
  }
  
  getNextNumberSequence():void{
    this.tokenStorageService.getNextNumberSequence('Provisional Bill').subscribe(
      data => {
        this.form.billNo = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  onEdit(item): void {
    window.scrollTo(0, 0);
    this.form = {};
    this.form = item[0];
    this.form.name = item[1];
    this.isEdit = true;
    this.labRequestList = [];
    this.showLabContent = false;
    this.form.concession = item[0].discount;
    
    let SortDoctor1 = this.ConsultantList.find((e => e.name == item[0].consultant1));
    let SortDoctor2 = this.ConsultantList.find((e => e.name == item[0].consultant2));
    if (SortDoctor1 == null) { SortDoctor1 = ''; }
    if (SortDoctor2 == null) { SortDoctor2 = ''; }

    this.form.consultant1 = SortDoctor1.id;
    this.form.consultant2 = SortDoctor2.id;

    this.form.concession = item[0].discount;
    
    this.form.subDept = Number(item[0].subDept);
    this.form.department = Number(item[0].department);

    // showLabContent    labRequestList
    this.service.getDetailsById(item[0].id).subscribe(
      data => {
        setTimeout(() =>
        (this.showLabContent = true),
        (this.labRequestList = data.body)
        , 100);

        this.form.className = Number(this.labRequestList[0].classCode);
        this.form.organizationName = Number(this.labRequestList[0].orgcode);
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

  getPdf(id, type){
    if (id != null && id != 'undefined'){
    this.service.printReport('pdf', id, type).subscribe(
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


  fillServiceData(): void {
    if(this.form.total){
      const findRate = this.ServiceChargesList.find(f => f.serviceName === this.form.serviceName);
      if(findRate){
        this.form.servicePer = findRate.rate;
        this.form.serviceAmt = (Number(this.form.serviceOn) / 100) * Number(this.form.servicePer);
      }
      this.calGrandTotal();
    }
  }


  calculateDischargeDays():void {
    if(this.form.admissionDate){
      this.form.totalDays = this.getNumberOfDays(this.form.admissionDate,this.form.dischargeDate)
    }
  }

   getNumberOfDays(start, end) {
    const date1 = new Date(start);
    const date2 = new Date(end);
    const oneDay = 1000 * 60 * 60 * 24;
    const diffInTime = date2.getTime() - date1.getTime();
    const diffInDays = Math.round(diffInTime / oneDay);
    return diffInDays != 0 ? diffInDays : 1;
}

  calGrandTotal(): void {
    let gTotal = 0;
    gTotal = Number(this.form.total);
    if (this.form.serviceAmt) {
      gTotal = gTotal + Number(this.form.serviceAmt);
    }
    if (this.form.concession) {
      gTotal = gTotal - Number(this.form.concession);
    }
    if (this.form.otherDiscountPer) {
      gTotal = gTotal - Number(this.form.otherDiscountPer);
    }
    // if (this.form.additionalCharges) {
    //   gTotal = gTotal + Number(this.form.additionalCharges);
    // }
    this.form.grandTotal = gTotal;

    // if (this.form.lessDeposit) {
      this.form.netTotal = Number(this.form.lessDeposit) - gTotal;
    // } else {
      // this.form.netTotal = 0;
    // }

  }




  onEditTest(data,i): void {

    if(!this.DetailsList[0].testName){
    this.form.organizationName = Number(data.orgcode);
    this.form.className =  Number(data.classCode);
    this.form.groupName =  Number(data.groupCode);
    this.getTestListByGroup(this.form.groupName,this.form.organizationName);
    
    window.scrollTo(0, 0);

      this.DetailsList[0] = {
        id: data.id,
        sno: data.sno,
        ipdBollNo: data.ipdBollNo,
        testName: data.testCode==null?Number(data.testId):Number(data.testCode),
        testCode: data.testCode==null?Number(data.testId):Number(data.testCode),
        groupName: data.groupName,
        procedureDoctor1: data.procedureDoctor1,
        procedureDoctor2: data.procedureDoctor2,
        qty: data.qty,
        rate: data.rate,
        discount: data.discount,
        discountPer: data.otherConcession,
        additional: data.additional,
        less: data.less,
        newSubGroupCode: data.newSubGroupCode,
        groupCode: data.groupCode,
        drAdd: data.doctorAdditional,
        className: data.className,
        classCode: data.classCode,
        amount: data.amount,
        orgcode: data.orgcode,
        packageYn: data.packageYn,
        packageName: data.packageName,
        pdate: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
        ptime: new Date().toTimeString().substring(0, 5),
        packageFlag: data.packageFlag,
        serviceFlag: data.serviceFlag,
        requestType:data.requestType
        };

        this.labRequestList.splice(i, 1);
    }
    
  }

  changeSequence():void{ 
    let counter = 1;
    this.labRequestList.map(item =>{
      item.sno = counter;
      counter++;
    });
    
  }

  onDeleteTest(id,i): void {
    if (id == 0 || !this.isEdit) {
     
      this.labRequestList.splice(i, 1);
      this.fillTotal();
      // const index = this.labRequestList.findIndex((el) => el.id === id);
      // this.labRequestList.splice(index, 1);

    }else if(this.isEdit){
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
          this.service.deleteDetailById(id).subscribe(
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
                    const index = this.labRequestList.findIndex((el) => el.id === id);
                    this.labRequestList.splice(index, 1);
                    this.fillTotal();
                  }
                });
              }
            },
            err => {
              console.error(err);
            }
          );
    }
     else {
 
        }
      });
    }
    this.changeSequence();
  }
  getlabRequestList(): void {
    if (this.form.ipdno == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Patient Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      this.service.getLabRequest(this.form.ipdno,"LabRequest").subscribe(
        data => {
          if (data.status == 200) {
            setTimeout(() =>
              this.showLabContent = true,
              // this.labRequestList.push(data.body)
              this.labRequestList.push.apply(this.labRequestList, data.body)
              , 100);
            this.fillTotal();
            this.calGrandTotal();
            this.isLoadFromLab = true;
            this.changeSequence();
          } else {
            Swal.fire({
              title: 'Error!',
              text: data.message,
              html: '<pre>' + data.message + '</pre>',
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
  }
  getRadioRequestList(): void {
    if (this.form.ipdno == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Patient Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      this.service.getLabRequest(this.form.ipdno,"RadioRequest").subscribe(
        data => {
          if (data.status == 200) {
            setTimeout(() =>
              this.showLabContent = true,
              // this.labRequestList.push(data.body)
              this.labRequestList.push.apply(this.labRequestList, data.body)
              , 100);
            this.fillTotal();
            this.calGrandTotal();
            this.isLoadFromRadio = true;
            this.changeSequence();
          } else {
            Swal.fire({
              title: 'Error!',
              text: data.message,
              html: '<pre>' + data.message + '</pre>',
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
  }

  /*****
   * Load From Bill Detail Sheet *
   * ***/

  getBillDetailRequestList():void{
    if (this.form.ipdno == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Patient Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      this.billDetailSheetService.getLabRequest(this.form.ipdno).subscribe(
        data => {
          if (data.status == 200) {
            setTimeout(() =>
              this.showLabContent = true,
              // this.labRequestList.push(data.body)
              this.labRequestList.push.apply(this.labRequestList, data.body)
              , 100);
            this.fillTotal();
            this.calGrandTotal();
            this.isLoadFromBillDetails = true;
            this.changeSequence();
          } else {
            Swal.fire({
              title: 'Error!',
              text: data.message,
              html: '<pre>' + data.message + '</pre>',
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
  }


  onTable(flag): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
		if(hasAccess.edit_action) { this.isedit_action = true }
		if(hasAccess.delete_action) { this.isdelete_action = true }

    if (flag == 0) {
      const currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
      this.service.getByDate(currentDate).subscribe(
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
      this.service.getByDate(this.temp.getByDate).subscribe(
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





  // calculateDue() {
  //   this.form.due = Number(this.form.netTotal) - Number(this.form.paidAmount)
  // }
  fillConcessionData(data): void {
    if (this.form.grandTotal) {
       // filter and get percents
    if(!data){
      this.form.concessionPer = this.ConcessionList.find((e => e.id == Number(this.form.concessionType))).concession;
    }
    // calculate modulas
    const concessionTotal = (Number(this.form.total)+Number(this.form.serviceAmt)) * Number(this.form.concessionPer) / 100;
    
    this.form.concession = concessionTotal;
    this.calGrandTotal();
    // //Total Caluculate
    // this.form.netTotal = Number(this.form.total) - Number(concessionTotal)
    // this.form.paidAmount = Number(this.form.total) - Number(concessionTotal)
    }
  }
  getConcessionList(): void {
    this.concessionService.get().subscribe(
      data => {
        this.ConcessionList = data.body;
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
    // if(!this.DetailsList[0].procedureDoctor1){
    //   alert("Please Select Procedure Doctor")
    // }else{
    if (this.DetailsList[0].testName != 0) {
      const SortTest = this.TestList.find((e => e.id == Number(this.DetailsList[0].testName)));
      let SortDoctorName1;
      let SortDoctorName2;

      let SortDoctor1 = this.ConsultantList.find((e => e.id == Number(this.DetailsList[0].procedureDoctor1)));
      let SortDoctor2 = this.ConsultantList.find((e => e.id == Number(this.DetailsList[0].procedureDoctor2)));

      if (SortDoctor1 == null) { SortDoctorName1 = this.DetailsList[0].procedureDoctor1; }else { SortDoctorName1 = SortDoctor1.name }
      if (SortDoctor2 == null) { SortDoctorName2 = this.DetailsList[0].procedureDoctor2; }else { SortDoctorName2 = SortDoctor2.name }
      if (this.DetailsList[0].id == 0) {
        // add New
        this.labRequestList.push({
          id: this.DetailsList[0].id,
          sno: this.labRequestList.length + 1,
          ipdBollNo: this.DetailsList[0].ipdBollNo,
          testName: SortTest.testName,
          testCode: SortTest.id,
          testId: SortTest.id,
          groupName: this.GroupList.find((e => e.id == Number(this.form.groupName))).groupName,
          procedureDoctor1: SortDoctorName1,
          procedureDoctor2: SortDoctorName2,
          qty: this.DetailsList[0].qty,
          rate: this.DetailsList[0].rate,
          discount: this.DetailsList[0].discount,
          discountPer: this.DetailsList[0].discountPer,
          additional: this.DetailsList[0].additional,
          less: this.DetailsList[0].less,
          newSubGroupCode: this.DetailsList[0].newSubGroupCode,
          groupCode: this.form.groupName,
          drAdd: this.DetailsList[0].drAdd,
          className: '1',
          classCode: this.form.className,
          amount: this.DetailsList[0].amount,
          orgcode: this.form.organizationName,
          packageYn: this.DetailsList[0].packageYn,
          packageName: this.DetailsList[0].packageName,
          pdate: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
          ptime: new Date().toTimeString().substring(0, 5),
          packageFlag: this.DetailsList[0].packageFlag,
          serviceFlag: this.DetailsList[0].serviceFlag,
          requestType: 'Direct',
        });
      } else {
        // find and update
        // const index = this.labRequestList.findIndex((el) => el.id === this.DetailsList[0].id);
        // this.labRequestList[index] = {
        this.labRequestList.push({
          id: this.DetailsList[0].id,
          sno: this.DetailsList[0].sno,
          ipdBollNo: this.DetailsList[0].ipdBollNo,
          testName: SortTest.testName,
          testCode: SortTest.id,
          testId: SortTest.id,
          groupName: this.DetailsList[0].groupName,
          procedureDoctor1: SortDoctorName1,
          procedureDoctor2: SortDoctorName2,
          qty: this.DetailsList[0].qty,
          rate: this.DetailsList[0].rate,
          discount: this.DetailsList[0].discount,
          discountPer: this.DetailsList[0].discountPer,
          additional: this.DetailsList[0].additional,
          less: this.DetailsList[0].less,
          newSubGroupCode: this.DetailsList[0].newSubGroupCode,
          groupCode: this.DetailsList[0].groupCode,
          drAdd: this.DetailsList[0].drAdd,
          className: this.DetailsList[0].className,
          classCode: this.DetailsList[0].classCode,
          amount: this.DetailsList[0].amount,
          orgcode: this.DetailsList[0].orgcode,
          packageYn: this.DetailsList[0].packageYn,
          packageName: this.DetailsList[0].packageName,
          pdate: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
          ptime: new Date().toTimeString().substring(0, 5),
          packageFlag: this.DetailsList[0].packageFlag,
          serviceFlag: this.DetailsList[0].serviceFlag,
          requestType: this.DetailsList[0].requestType,
        });
      }
      // For Reset DetailList
      this.DetailsList = [{
        id: 0,
        sno: 1,
        ipdBollNo: 0,
        testName: 0,
        testCode: 0,
        groupName: '',
        procedureDoctor1: '',
        procedureDoctor2: '',
        qty: 0,
        rate: 0,
        discount: '',
        discountPer: 0,
        additional: '',
        less: '',
        newSubGroupCode: 0,
        groupCode: 0,
        drAdd: 0,
        className: "1",
        classCode: 0,
        amount: 0,
        orgcode: 0,
        packageYn: '',
        packageName: '',
        pdate: new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0],
        ptime: new Date().toTimeString().substring(0, 5),
        packageFlag: '',
        serviceFlag: '',
        requestType: ''
          }];
        }
        this.fillTotal();
        // this.calGrandTotal();
        this.fillServiceData();
        this.showLabContent = true;     
      // }
  this.changeSequence();
  }

  removeAddress(i: number, id) {
    if (confirm('Are you sure you want to remove it?')) {
      // if (id != 0) {
      //   Swal.fire({
      //     title: 'Are you sure?',
      //     text: "Do you want this!",
      //     icon: 'warning',
      //     width: 300,
      //     showCancelButton: true,
      //     confirmButtonColor: '#3085d6',
      //     cancelButtonColor: '#d33',
      //     confirmButtonText: 'Delete it!'
      //   }).then((result) => {
      //     if (result.isConfirmed) {
      //       // this.service.deleteDetailById(id).subscribe(
      //       //   data => {
      //       //     if (data.status == 200) {
      //       //       Swal.fire({
      //       //         title: 'Deleted!',
      //       //         text: 'Data Deleted Success',
      //       //         icon: 'success',
      //       //         confirmButtonText: 'OK',
      //       //         width: 300,
      //       //         
      //       //       }).then((result) => {
      //       //         if (result.isConfirmed) {
      //       //           this.onReload();
      //       //         }
      //       //       });
      //       //     }
      //       //   },
      //       //   err => {
      //       //     console.log(err)
      //       //   }
      //       // );
      //     }
      //   })
      // }
      this.DetailsList.splice(i, 1);
    }
  }

  filterTestData(i): void {
    if (this.form.className == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Class Name Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      this.DetailsList[i].qty = 1;
      const SortTest = this.TestList.find((e => e.id == Number(this.DetailsList[i].testName)));
      if (this.form.className == 53) { this.DetailsList[i].rate = SortTest.general; }
      if (this.form.className == 54) { this.DetailsList[i].rate = SortTest.nicu; }
      if (this.form.className == 55) { this.DetailsList[i].rate = SortTest.semiSpecial; }
      if (this.form.className == 56) { this.DetailsList[i].rate = SortTest.special; }
      if (this.form.className == 57) { this.DetailsList[i].rate = SortTest.deluxe; }
      if (this.form.className == 58) { this.DetailsList[i].rate = SortTest.executive; }
      if (this.form.className == 59) { this.DetailsList[i].rate = SortTest.icu; }
      if (this.form.className == 61) { this.DetailsList[i].rate = SortTest.emergency; }
      this.DetailsList[i].amount = Number(this.DetailsList[i].qty) * Number(this.DetailsList[i].rate);
    }
  }
  fillTotalAmount(i): void {
    let amount = Number(this.DetailsList[i].qty) * Number(this.DetailsList[i].rate);


    if (this.DetailsList[i].drAdd) {
      amount = amount + Number(this.DetailsList[i].drAdd);
    }
    if (this.DetailsList[i].discountPer) {
      amount = amount - Number(this.DetailsList[i].discountPer);
    }
    this.DetailsList[i].amount = amount;
  }

  fillTotal(): void {
    let amount = 0;
    // this.DetailsList.forEach(value => {
    //   amount = amount + Number(value.amount);
    // });

    this.labRequestList.forEach(value => {
      amount = amount + Number(value.amount);
    });

    this.form.serviceOn = amount;
    this.form.total = amount;
  }

  getTestListByGroup(group,org):void{
    this.ipdService.getParticularsListByGroup(group,org).subscribe(
      data => {
        this.TestList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getTestList(): void {
    this.ipdService.get().subscribe(
      data => {
        this.TestList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getOrganizationList(): void {
    this.organizationMasterService.getOrganizationList().subscribe(
      data => {
        this.OrganizationList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getGroupList(): void {
    this.groupService.getGruopListByDepartment("16").subscribe(
      data => {
        this.GroupList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getClassList(): void {
    this.classMasterService.get().subscribe(
      data => {
        this.ClassList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getDoctorReferenceList(): void {
    this.doctorReferenceService.getDoctorReferenceList().subscribe(
      data => {
        this.DoctorReferenceList = data.body;
      },
      err => {
        console.error(err);
      }
    );
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
  getDepartmentList(): void {
    this.departmentService.get().subscribe(
      data => {
        this.DepartmentList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getSubDepartmentList(): void {
    this.subdepartmentService.get().subscribe(
      data => {
        this.SubDepartmentList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
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
    // this.form = {};
    this.labRequestList = [];
    if (ipdno != null && ipdno.length >= 0 && ipdno != '') {
      this.admissionService.getFullPatientDetailByIPD(ipdno).subscribe(
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
            const currentdate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
            this.form.date = currentdate;
            this.form.time = new Date().toTimeString().substring(0, 5);
            this.days_between(data.body.admission.date, currentdate);
            this.form.name = data.body.registration.name;
            this.form.uhid = data.body.admission.uhid;
            this.form.ipdno = data.body.admission.ipdno;
            this.form.age = data.body.admission.age;
            this.form.sex = data.body.admission.sex;

            // this.getNextNumberSequence();
            this.form.admissionDate = data.body.admission.date;
            this.form.admissionTime = data.body.admission.time;
            this.form.dischargeDate = currentdate;
            this.form.dischargeTime = new Date().toTimeString().substring(0, 5);

            this.form.vtype = 'Credit';
            this.form.department = 16;
            this.form.subDept = 2;
            this.form.consultant1 = data.body.admission.consultant1;
            this.form.consultant2 = data.body.admission.consultant2;
            this.form.referredBy = data.body.admission.refBy1;
            this.form.highRisk = 'No';
            this.form.emergency = 'No';
            this.form.dischargeType = "Date Of Discharge";

            this.form.organizationName = data.body.admission.organization;

            this.form.className = this.ClassList.find(f => f.className === data.body.admission.wardname).id;

            if(this.OrganizationList){
              this.form.creditAgainst = this.OrganizationList.find(x => x.id === Number(data.body.admission.organization)).organization;
            }

            this.receiptService.findByIpdno(ipdno).subscribe(
              data => {
                this.form.lessDeposit = Number(data.body);
                this.form.serviceAmt = 0;
                this.form.concession = 0;
                this.form.serviceOn = 0;
                // this.form.additionalCharges = 0;
                this.form.grandTotal = 0;
                this.form.netTotal = 0;
                this.form.total = 0;
              });
          }
        },
        err => {
          console.error(err);
        }
      );
    }
  }
  days_between(d1, d2): void {
    const date1 = Date.parse(d1);
    const date2 = Date.parse(d2);
    const ONE_DAY = 1000 * 60 * 60 * 24;
    const differenceMs = date2 - date1;
    const cal = differenceMs / ONE_DAY;
    this.form.totalDays = cal;
  }

  onSave(): void {
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
  }

  onSubmit(): void {
    this.isSubmit = true;
    this.form.time = new Date().toTimeString().substring(0, 5);
    this.form.orgcode = this.form.organizationName;
    this.form.serviceFlag = 'True';
    this.form.details = this.labRequestList;
    this.form.creditId = this.form.uhid;
    this.form.discount = this.form.concession;

    let SortDoctor1 = this.ConsultantList.find((e => e.id == Number(this.form.consultant1)));
    let SortDoctor2 = this.ConsultantList.find((e => e.id == Number(this.form.consultant2)));
    if (SortDoctor1 == null) { SortDoctor1 = ''; }
    if (SortDoctor2 == null) { SortDoctor2 = ''; }

    this.form.consultant1 = SortDoctor1.name;
    this.form.consultant2 = SortDoctor2.name;

    this.service.save(this.form).subscribe(
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
              this.onTable(0);
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
          this.isSubmit = false;
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
    this.form.time = new Date().toTimeString().substring(0, 5);
    this.form.orgcode = this.form.organizationName;
    this.form.serviceFlag = 'True';
    this.form.details = this.labRequestList;
    this.form.creditId = this.form.uhid;
    this.form.discount = this.form.concession;

    let SortDoctor1 = this.ConsultantList.find((e => e.id == Number(this.form.consultant1)));
    let SortDoctor2 = this.ConsultantList.find((e => e.id == Number(this.form.consultant2)));
    if (SortDoctor1 == null) { SortDoctor1 = ''; }
    if (SortDoctor2 == null) { SortDoctor2 = ''; }

    this.form.consultant1 = SortDoctor1.name;
    this.form.consultant2 = SortDoctor2.name;

    this.service.update(this.form).subscribe(
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
              this.onTable(0);
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
          this.isSubmit = false;
        }
      },
      err => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

}
