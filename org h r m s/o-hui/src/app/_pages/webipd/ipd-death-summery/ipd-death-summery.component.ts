import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DoctorReferenceService } from '../../../_service/webhims/static/doctor-reference.service';
import { ConsultantMasterService } from '../../../_service/webhims/static/consultant-master.service';
import { IpdDeathSummeryService } from '../../../_service/webipd/ipd_dashboard/ipd-death-summery.service';
import { DiagnosisMasterService } from '../../../_service/webipd/ipd_dashboard/diagnosis-master.service';
import { TemplateMasterService } from '../../../_service/webipd/ipd_dashboard/template-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ActivatedRoute } from '@angular/router';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-ipd-death-summery',
  templateUrl: './ipd-death-summery.component.html',
  styleUrls: ['./ipd-death-summery.component.css']
})
export class IpdDeathSummeryComponent implements OnInit {

  constructor(
    private modalService: NgbModal,
    private admissionService: AdmissionService,
    private consultantMasterService: ConsultantMasterService,
    private doctorReferenceService: DoctorReferenceService,
    private service: IpdDeathSummeryService,
    private diagnosisMasterService: DiagnosisMasterService,
    private templateMasterService: TemplateMasterService,
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private activatedroute: ActivatedRoute,
    private titleService: Title
  ) { }

  form: any = {};
  PatientDetails: any = [];
  table_data: any = [];
  isSubmit = false;
  DoctorReferenceList: any = [];
  ConsultantList: any = [];
  showContent = false;
  isEdit = false;
  Diagnosis1: any = [];
  Diagnosis2: any = [];
  /* ********************************************************************
      Model_Diagnosis End
  ************************************************************************  */



  /* ********************************************************************
      Model_Clinical Start
  ************************************************************************  */
  Clinical: any = [];

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Death Summery");
    this.tokenStorageService.setSessionPrivileges('im_1_3');
    this.getUhidBillNoName();
    this.getDoctorReferenceList();
    this.getConsultantList();
    this.onTable(0);
    this.activatedroute.queryParams.subscribe(data => {
      if(data.ipdno){
        this.getPatientDetails_IPD(data.ipdno);
      }
    });
  }

  /* ********************************************************************
      Model_Diagnosis Start
  ************************************************************************  */
  getDiagnosis(): void {
    this.diagnosisMasterService.get().subscribe(
      data => {
        this.Diagnosis1 = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_Diagnosis(targetModal) {
    this.getDiagnosis();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterDiagnosis1(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('Diagnosis1_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('Diagnosis1_UL');
    li = ul.getElementsByTagName('li');
    for (i = 0; i < li.length; i++) {
      a = li[i].getElementsByTagName('label')[0];
      txtValue = a.textContent || a.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        li[i].style.display = '';
      } else {
        li[i].style.display = 'none';
      }
    }
  }
  filterDiagnosis2(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('Diagnosis2_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('Diagnosis2_UL');
    li = ul.getElementsByTagName('li');
    for (i = 0; i < li.length; i++) {
      a = li[i].getElementsByTagName('label')[0];
      txtValue = a.textContent || a.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        li[i].style.display = '';
      } else {
        li[i].style.display = 'none';
      }
    }
  }
  addToDiagnosis2(e, item): void {
    this.diagnosisMasterService.getByChapter(item.chapter).subscribe(
      data => {
        this.Diagnosis2 = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  addToDiagnosisSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.diagnosisSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.diagnosis; } else { str += '\r\n' + item.diagnosis; }
      this.form.diagnosisSummary = str;
    }
  }
  addToSummary1(targetModal): void {
    this.form.summary1 = this.form.diagnosisSummary;
    this.modalService.dismissAll(targetModal);
  }
  getClinical(): void {
    this.templateMasterService.findByTemplate('Clinical History').subscribe(
      data => {
        this.Clinical = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_Clinical(targetModal) {
    this.getClinical();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterClinical(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('Clinical_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('Clinical_UL');
    li = ul.getElementsByTagName('li');
    for (i = 0; i < li.length; i++) {
      a = li[i].getElementsByTagName('label')[0];
      txtValue = a.textContent || a.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        li[i].style.display = '';
      } else {
        li[i].style.display = 'none';
      }
    }
  }
  addToClinicalSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.clinicalSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.clinicalSummary = str;
    }
  }
  addToSummary2(targetModal): void {
    this.form.summary2 = this.form.clinicalSummary;
    this.modalService.dismissAll(targetModal);
  }
  /* ********************************************************************
      Model_Clinical End
  ************************************************************************  */







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
  }

  isedit_action = false
  isdelete_action = false
  onTable(flag): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
    if(hasAccess.edit_action) { this.isedit_action = true }
    if(hasAccess.delete_action) { this.isdelete_action = true }

    if (flag == 0) {
      let currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
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

  onNew(): void {
    this.componentReloadService.reload();
  }
  onSubmit(): void {
    this.form.summaryTitle1 = 'Diagnosis';
    this.form.summaryTitle2 = 'Clinical Summary';
    this.form.time = new Date().toTimeString().substring(0, 5);

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
            for (let ob of data.fieldErrorMessageList) {
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
            for (let ob of data.fieldErrorMessageList) {
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
    this.form = {};
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
            this.form.uhid = data.body.admission.uhid;
            this.form.name = data.body.registration.name;
            this.form.ipdno = data.body.admission.ipdno;
            this.form.dob = data.body.registration.dob;
            this.form.sex = data.body.admission.sex;
            this.form.age = data.body.admission.age;
            this.form.address = data.body.registration.address + ' ,' + data.body.registration.area + ' ,' + data.body.registration.city + ' ,' + data.body.registration.state + ' ,' + data.body.registration.country;

            this.form.consultant1 = data.body.admission.consultant1;
            this.form.consultant2 = data.body.admission.consultant2;

            this.form.refBy1 = data.body.admission.refBy1;
            this.form.refBy2 = data.body.admission.refBy2;

            this.form.admissionDate = data.body.admission.date;
            this.form.admissionTime = data.body.admission.time;


            this.form.procedureDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
            this.form.dischargeDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
            this.form.dischargeTime = new Date().toTimeString().substring(0, 5);

            this.form.caseType = 'GENERAL';

            // Extra
            // this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]

          }
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  getPdf(id){
    if (id != null && id != 'undefined'){
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
