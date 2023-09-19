import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import { OpdBillService } from 'src/app/_service/webhims/reception/reception_bill/opd.service';
import { RegistrationService } from 'src/app/_service/webhims/reception/reception_reception/registration.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { SubdepartmentService } from 'src/app/_service/webhims/master/other_masters1/subdepartment.service';
import { GroupService } from 'src/app/_service/webhims/master/other_masters1/group.service';
import { OpdService } from 'src/app/_service/webhims/master/test_master/opd.service';
import { PlasticMoneyMasterService } from 'src/app/_service/webhims/master/other_masters1/plastic-money-master.service';
import { ConcessionService } from 'src/app/_service/webhims/master/other_masters1/concession.service';
import { ActivatedRoute } from '@angular/router';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-opd',
  templateUrl: './opd.component.html',
  styleUrls: ['./opd.component.css']
})
export class OpdComponent implements OnInit {

  constructor(private doctorReferenceService: DoctorReferenceService,
              private service: OpdBillService,
              private consultantMasterService: ConsultantMasterService,
              private registrationService: RegistrationService,
              private admissionService: AdmissionService,
              private organizationMasterService: OrganizationMasterService,
              private subdepartmentService: SubdepartmentService,
              private componentReloadService: ComponentReloadService,
              private groupService: GroupService,
              private opdService: OpdService,
              private plasticMoneyMasterService: PlasticMoneyMasterService,
              private concessionService: ConcessionService,
              private activatedroute: ActivatedRoute,
              private tokenStorageService:TokenStorageService,
              private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  DoctorReferenceList: any = [];
  ConsultantList: any = [];
  spinner = false;
  OrganizationList: any = [];
  SubDeptList: any = [];
  GruopList: any = [];
  ParticularsList: any = [];
  isPLASTICMONEY = false;
  isCHEQUE = false;
  isCASH = false;
  PlasticInstrumentNameList: any = [];
  ConcessionList: any = [];
  table_data: any = [];
  PatientDetails: any = [];
  showContent = false;

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  public DetailsList: any[] = [{
    sno: 1,
    id: 0,
    groupName: '',
    particulars: '',
    // procedureDoctor1: 0,
    // procedureDoctor2: 0,
    qty: 0,
    rate: 0,
    amount: 0
  }];

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | OPD Bill");
    this.tokenStorageService.setSessionPrivileges('hm_1_2_1');
    window.scrollTo(0, 0);
    this.onTable(0);
    this.getDoctorReferenceList();
    this.getConsultantList();
    this.getOrganizationList();
    this.getSubDeptList();
    this.getGruopList();
    this.getConcessionList();
    this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
    this.form.billType = 'CASH';
    this.form.subDept = 28;
    this.form.patientTypeOldNew = 'New';
    this.form.organization = 70;
    this.billTypeChange();
    this.getUhidBillNoName();

    this.activatedroute.queryParams.subscribe(data => {
      if(data.uhid){
        this.getPatientDetails_UHID(data.uhid);
      }
    });
    this.form.methodOfPayment = "CASH";
    this.getNextNumberSequence();

    this.getTableSearch();
  }
  
  getNextNumberSequence():void{
    this.tokenStorageService.getNextNumberSequence('OPD Bill').subscribe(
      data => {
        this.form.billNo = data.body;
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
    this.registrationService.filterUhidName(searchtext).subscribe(
      data => {
        this.PatientDetails = data.body;
        this.tokensAreLoading = false;
      },
      err => {
        console.error(err);
      }
    );
  }

  isedit_action = false;
isdelete_action = false;

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
/**
 * ------------------------------------------------------------------------------------------------
 */
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
/**
 * ------------------------------------------------------------------------------------------------
 */
  fillConcessionData(data): void {
    // filter and get percents
    if(!data){
      this.form.concessionPer = this.ConcessionList.find((e => e.id == Number(this.form.concessionType))).concession;
    }
    // calculate modulas
    const concessionTotal = Number(this.form.total) * Number(this.form.concessionPer) / 100;
    this.form.concession = concessionTotal;

    // Total Caluculate
    this.form.nettotal = Number(this.form.total) - Number(concessionTotal);
    this.form.paidAmount = Number(this.form.total) - Number(concessionTotal);
  }

  fillConcessionValue(vale): void {
    if(vale){
      this.form.nettotal = Number(this.form.total) - Number(vale);
      this.form.paidAmount = Number(this.form.total) - Number(vale);
    }
  }
  
  fillOtherConcessionValue(vallue): void {
    if(vallue){
      this.form.nettotal = Number(this.form.nettotal) - Number(vallue);
      this.form.paidAmount = Number(this.form.paidAmount) - Number(vallue);
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

  calculateTotal(i): void {
    this.DetailsList[i].amount = Number(this.DetailsList[i].qty) * Number(this.DetailsList[i].rate);
    this.calculateFinalTotal();
  }

  calculateFinalTotal() {
    let amount = 0;
    this.DetailsList.forEach(value => {
      amount = amount + Number(value.amount);
    });
    this.form.total = amount;

    this.calculateConcession();
    this.calculateDue();
  }

  calculateConcession() {
    this.form.nettotal = this.form.total;
    this.form.paidAmount = this.form.total;
  }

  calculateDue() {
    this.form.due = Number(this.form.nettotal) - Number(this.form.paidAmount);
  }

  billTypeChange(): void {
    this.isCASH = false;
    if (this.form.billType == 'CASH') {
      this.isCASH = true;
      this.form.methodOfPayment = 'CASH';
      this.typeChange();
    }

  }

  typeChange(): void {
    this.isPLASTICMONEY = false;
    this.isCHEQUE = false;
    if (this.form.methodOfPayment == 'PLASTICMONEY') {
      this.isPLASTICMONEY = true;
      this.getPlasticInstrumentNameList();
    }
    if (this.form.methodOfPayment == 'CHEQUE') {
      this.isCHEQUE = true;
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

  fillParticularsData(i): void {
    const SortRow = this.ParticularsList[i].find((e => e.id == Number(this.DetailsList[i].particulars)));
    this.DetailsList[i].qty = 1;
    this.DetailsList[i].rate = Number(SortRow.rate);
    this.DetailsList[i].amount = Number(SortRow.rate);
    this.calculateTotal(i);
  }

  getParticularsList(i): void {
    this.opdService.getParticularsListByGroupAndOrganization(this.DetailsList[i].groupName, this.form.organization).subscribe(
      data => {
        this.ParticularsList[i] = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getGruopList(): void {
    this.groupService.getGruopListByDepartmentAndSuperGroup(17, 35).subscribe(
      data => {
        this.GruopList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getSubDeptList(): void {
    this.subdepartmentService.getSubDepartmentByDepartment('17').subscribe(
      data => {
        this.SubDeptList = data.body;
        this.form.subDept = 28;
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

  getPatientDetails_IPD(): void {
    if (this.form.ipdno != null && this.form.ipdno.length >= 0 && this.form.ipdno != '') {
      this.spinner = true;
      this.admissionService.getPatientDetailsByIPD(this.form.ipdno).subscribe(
        data => {
          if (data.body == null) {
            Swal.fire({
              title: 'Error!',
              text: 'Not Found',
              icon: 'error',
              confirmButtonText: 'OK',
              width: 300
            });
            // this.form = {};
          } else {
            this.form.refBy1 = data.body.refBy1!=0?data.body.refBy1:'';
            this.form.consultant1 = data.body.consultant1!=0?data.body.consultant1:'';
            this.form.ptype = data.body.patientType;
            this.form.name = data.body.name
            this.form.uhid = data.body.uhid
            this.form.sex = data.body.sex
            this.form.age = data.body.age
          }
          this.spinner = false;
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  getPatientDetails_UHID(uhid): void {
    if (uhid != null && uhid.length >= 0 && uhid != '') {
      this.spinner = true;
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
            // this.form = {};
          } else {
            this.form.refBy1 = data.body.referredBy!=0?data.body.referredBy:'';
            this.form.consultant1 = data.body.consultant!=0?data.body.consultant:'';
            this.form.ptype = data.body.patientType;
            this.form.name = data.body.name
            this.form.uhid = data.body.uhid
            this.form.sex = data.body.sex
            this.form.age = data.body.age
          }
          this.spinner = false;
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  addAddress() {
    this.DetailsList.push({
      sno: this.DetailsList.length + 1,
      id: 0,
      groupName: '',
      particulars: '',
      // procedureDoctor1: 0,
      // procedureDoctor2: 0,
      qty: 0,
      rate: 0,
      amount: 0
    });
  }

  removeAddress(i: number, id) {
    if (id == 0 || !this.isEdit || id === "0") {
      if (confirm('Are you sure you want to remove it?')) {
        this.DetailsList.splice(i, 1);
        this.calculateFinalTotal();
      }
    }else{
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
                    this.DetailsList.splice(i, 1);
                    this.calculateFinalTotal();
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




  onEdit(item): void {
    const row = item.bill;
    this.form = {};
    window.scrollTo(0, 0);
    this.isEdit = true;
    this.service.getDetailsById(row.id).subscribe(
      data => {
        this.form = data.body;
        this.form.subDept = Number(data.body.subDept);
        this.form.organization = Number(data.body.organization);
        this.form.consultant1 = Number(data.body.consultant1);
        this.form.refBy1 = Number(data.body.refBy1);
        this.form.refBy2 = Number(data.body.refBy2);
        this.form.name = item.registration.name;
        let ok = 0;
        data.body.detailsList.forEach(value => {

          value.rate = Number(value.rate);
          value.qty = Number(value.qty);

          if (value.groupName != null) {
            value.groupName = this.GruopList.find((e => e.groupName == value.groupName)).id;
          }

          this.opdService.getParticularsListByGroupAndOrganization(value.groupName, row.organization).subscribe(
            data => {
              this.ParticularsList[ok] = data.body;
              value.particulars = this.ParticularsList[ok].find((e => e.testName == value.particulars)).id;
              ok++;
            },
            err => {
              console.error(err);
            }
          );

          if (value.procedureDoctor1 != null) {
            value.procedureDoctor1 = this.ConsultantList.find((e => e.name == value.procedureDoctor1)).id;
          }
          if (value.procedureDoctor2 != null) {
            value.procedureDoctor2 = this.ConsultantList.find((e => e.name == value.procedureDoctor2)).id;
          }
        });

        this.DetailsList = data.body.detailsList;

      },
      err => {
        console.error(err);
      }
    );
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

  onNew(): void {
    this.componentReloadService.reload();

  }
  onSave(): void {
    // this.form.detailsList = []
    this.form.detailsList = this.DetailsList;
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
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

}
