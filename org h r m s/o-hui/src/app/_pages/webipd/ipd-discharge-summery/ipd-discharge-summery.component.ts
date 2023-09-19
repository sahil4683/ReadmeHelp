import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DoctorReferenceService } from '../../../_service/webhims/static/doctor-reference.service';
import { ConsultantMasterService } from '../../../_service/webhims/static/consultant-master.service';
import { IpdDischargeSummeryService } from '../../../_service/webipd/ipd_dashboard/ipd-discharge-summery.service';
import { DiagnosisMasterService } from '../../../_service/webipd/ipd_dashboard/diagnosis-master.service';
import { TemplateMasterService } from '../../../_service/webipd/ipd_dashboard/template-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ActivatedRoute } from '@angular/router';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-ipd-discharge-summery',
  templateUrl: './ipd-discharge-summery.component.html',
  styleUrls: ['./ipd-discharge-summery.component.css']
})
export class IpdDischargeSummeryComponent implements OnInit {

  constructor(
    private modalService: NgbModal,
    private admissionService: AdmissionService,
    private consultantMasterService: ConsultantMasterService,
    private doctorReferenceService: DoctorReferenceService,
    private service: IpdDischargeSummeryService,
    private diagnosisMasterService: DiagnosisMasterService,
    private templateMasterService: TemplateMasterService,
    private activatedroute: ActivatedRoute,
    private componentReloadService: ComponentReloadService,
    private tokenStorageService: TokenStorageService,
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
  /* ********************************************************************
      Model_Clinical End
  ************************************************************************  */


  /* ********************************************************************
        Model_PastHistory Start
    ************************************************************************  */
  PastHistory: any = [];
  /* ********************************************************************
      Model_PastHistory End
  ************************************************************************  */


  /* ********************************************************************
        Model_FamilyHistory Start
    ************************************************************************  */
  FamilyHistory: any = [];
  /* ********************************************************************
      Model_FamilyHistory End
  ************************************************************************  */


  /* ********************************************************************
Model_RadiologicalFinding Start
************************************************************************  */
  RadiologicalFinding: any = [];
  /* ********************************************************************
      Model_RadiologicalFinding End
  ************************************************************************  */


  /* ********************************************************************
  Model_ECHO Start
************************************************************************  */
  ECHO: any = [];
  /* ********************************************************************
      Model_ECHO End
  ************************************************************************  */


  /* ********************************************************************
  Model_CathLab Start
************************************************************************  */
  CathLab: any = [];
  /* ********************************************************************
      Model_CathLab End
  ************************************************************************  */


  /* ********************************************************************
  Model_SurgicalProcedure Start
************************************************************************  */
  SurgicalProcedure: any = [];
  /* ********************************************************************
      Model_SurgicalProcedure End
  ************************************************************************  */


  /* ********************************************************************
  Model_TreatmentGiven Start
************************************************************************  */
  TreatmentGiven: any = [];
  /* ********************************************************************
      Model_TreatmentGiven End
  ************************************************************************  */

  /* ********************************************************************
  Model_TreatmentAdvised Start
************************************************************************  */
  TreatmentAdvised: any = [];
  /* ********************************************************************
      Model_TreatmentAdvised End
  ************************************************************************  */

  /* ********************************************************************
  Model_AdviceOnDischarge Start
************************************************************************  */
  AdviceOnDischarge: any = [];
  /* ********************************************************************
      Model_AdviceOnDischarge End
  ************************************************************************  */

  /* ********************************************************************
  Model_FollowUp Start
************************************************************************  */
  FollowUp: any = [];
  /* ********************************************************************
      Model_FollowUp End
  ************************************************************************  */

  /* ********************************************************************
  Model_LabInvestigation Start
************************************************************************  */
  LabInvestigation: any = [];
  /* ********************************************************************
      Model_LabInvestigation End
  ************************************************************************  */

  /* ********************************************************************
  Model_OtherDetails Start
************************************************************************  */
  OtherDetails: any = [];

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Discharge Summery");
    this.tokenStorageService.setSessionPrivileges('im_1_2');
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
  getPastHistory(): void {
    this.templateMasterService.findByTemplate('PAST HISTORY').subscribe(
      data => {
        this.PastHistory = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_PastHistory(targetModal) {
    this.getPastHistory();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterPastHistory(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('PastHistory_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('PastHistory_UL');
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
  addToPastHistorySummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.PastHistorySummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.PastHistorySummary = str;
    }
  }
  addTosummary3(targetModal): void {
    this.form.summary3 = this.form.PastHistorySummary;
    this.modalService.dismissAll(targetModal);
  }
  getFamilyHistory(): void {
    this.templateMasterService.findByTemplate('FAMILY HISTORY').subscribe(
      data => {
        this.FamilyHistory = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_FamilyHistory(targetModal) {
    this.getFamilyHistory();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterFamilyHistory(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('FamilyHistory_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('FamilyHistory_UL');
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
  addToFamilyHistorySummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.FamilyHistorySummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.FamilyHistorySummary = str;
    }
  }
  addTosummary4(targetModal): void {
    this.form.summary4 = this.form.FamilyHistorySummary;
    this.modalService.dismissAll(targetModal);
  }
  getRadiologicalFinding(): void {
    this.templateMasterService.findByTemplate('RADIOLOGICAL FINDING').subscribe(
      data => {
        this.RadiologicalFinding = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_RadiologicalFinding(targetModal) {
    this.getRadiologicalFinding();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterRadiologicalFinding(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('RadiologicalFinding_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('RadiologicalFinding_UL');
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
  addToRadiologicalFindingSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.RadiologicalFindingSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.RadiologicalFindingSummary = str;
    }
  }
  addTosummary5(targetModal): void {
    this.form.summary5 = this.form.RadiologicalFindingSummary;
    this.modalService.dismissAll(targetModal);
  }
  getECHO(): void {
    this.templateMasterService.findByTemplate('ECHO').subscribe(
      data => {
        this.ECHO = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_ECHO(targetModal) {
    this.getECHO();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterECHO(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('ECHO_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('ECHO_UL');
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
  addToECHOSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.ECHOSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.ECHOSummary = str;
    }
  }
  addTosummary6(targetModal): void {
    this.form.summary6 = this.form.ECHOSummary;
    this.modalService.dismissAll(targetModal);
  }
  getCathLab(): void {
    this.templateMasterService.findByTemplate('CATH LAB').subscribe(
      data => {
        this.CathLab = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_CathLab(targetModal) {
    this.getCathLab();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterCathLab(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('CathLab_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('CathLab_UL');
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
  addToCathLabSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.CathLabSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.CathLabSummary = str;
    }
  }
  addTosummary7(targetModal): void {
    this.form.summary7 = this.form.CathLabSummary;
    this.modalService.dismissAll(targetModal);
  }
  getSurgicalProcedure(): void {
    this.templateMasterService.findByTemplate('Surgical Procedure').subscribe(
      data => {
        this.SurgicalProcedure = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_SurgicalProcedure(targetModal) {
    this.getSurgicalProcedure();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterSurgicalProcedure(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('SurgicalProcedure_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('SurgicalProcedure_UL');
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
  addToSurgicalProcedureSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.SurgicalProcedureSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.SurgicalProcedureSummary = str;
    }
  }
  addTosummary8(targetModal): void {
    this.form.summary8 = this.form.SurgicalProcedureSummary;
    this.modalService.dismissAll(targetModal);
  }
  getTreatmentGiven(): void {
    this.templateMasterService.findByTemplate('TREATMENT GIVEN').subscribe(
      data => {
        this.TreatmentGiven = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_TreatmentGiven(targetModal) {
    this.getTreatmentGiven();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterTreatmentGiven(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('TreatmentGiven_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('TreatmentGiven_UL');
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
  addToTreatmentGivenSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.TreatmentGivenSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.TreatmentGivenSummary = str;
    }
  }
  addTosummary9(targetModal): void {
    this.form.summary9 = this.form.TreatmentGivenSummary;
    this.modalService.dismissAll(targetModal);
  }
  getTreatmentAdvised(): void {
    this.templateMasterService.findByTemplate('Treatment Advised').subscribe(
      data => {
        this.TreatmentAdvised = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_TreatmentAdvised(targetModal) {
    this.getTreatmentAdvised();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterTreatmentAdvised(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('TreatmentAdvised_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('TreatmentAdvised_UL');
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
  addToTreatmentAdvisedSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.TreatmentAdvisedSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.TreatmentAdvisedSummary = str;
    }
  }
  addTosummary10(targetModal): void {
    this.form.summary10 = this.form.TreatmentAdvisedSummary;
    this.modalService.dismissAll(targetModal);
  }
  getAdviceOnDischarge(): void {
    this.templateMasterService.findByTemplate('Advice On Discharge').subscribe(
      data => {
        this.AdviceOnDischarge = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_AdviceOnDischarge(targetModal) {
    this.getAdviceOnDischarge();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterAdviceOnDischarge(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('AdviceOnDischarge_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('AdviceOnDischarge_UL');
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
  addToAdviceOnDischargeSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.AdviceOnDischargeSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.AdviceOnDischargeSummary = str;
    }
  }
  addTosummary11(targetModal): void {
    this.form.summary11 = this.form.AdviceOnDischargeSummary;
    this.modalService.dismissAll(targetModal);
  }
  getFollowUp(): void {
    this.templateMasterService.findByTemplate('FOLLOW UP').subscribe(
      data => {
        this.FollowUp = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_FollowUp(targetModal) {
    this.getFollowUp();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterFollowUp(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('FollowUp_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('FollowUp_UL');
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
  addToFollowUpSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.FollowUpSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.FollowUpSummary = str;
    }
  }
  addTosummary12(targetModal): void {
    this.form.summary12 = this.form.FollowUpSummary;
    this.modalService.dismissAll(targetModal);
  }
  getLabInvestigation(): void {
    this.templateMasterService.findByTemplate('Lab Investigation').subscribe(
      data => {
        this.LabInvestigation = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_LabInvestigation(targetModal) {
    this.getLabInvestigation();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterLabInvestigation(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('LabInvestigation_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('LabInvestigation_UL');
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
  addToLabInvestigationSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.LabInvestigationSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.LabInvestigationSummary = str;
    }
  }
  addTosummary13(targetModal): void {
    this.form.summary13 = this.form.LabInvestigationSummary;
    this.modalService.dismissAll(targetModal);
  }
  getOtherDetails(): void {
    this.templateMasterService.findByTemplate('Other Details').subscribe(
      data => {
        this.OtherDetails = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  openModel_OtherDetails(targetModal) {
    this.getOtherDetails();
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl'
    });
  }
  filterOtherDetails(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('OtherDetails_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('OtherDetails_UL');
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
  addToOtherDetailsSummary(e, item): void {
    if (e.target.checked) {
      let str = this.form.OtherDetailsSummary;
      if (typeof str == 'undefined') { str = ''; }
      if (str == '') { str += item.template; } else { str += '\r\n' + item.template; }
      this.form.OtherDetailsSummary = str;
    }
  }
  addTosummary14(targetModal): void {
    this.form.summary14 = this.form.OtherDetailsSummary;
    this.modalService.dismissAll(targetModal);
  }
  /* ********************************************************************
      Model_OtherDetails End
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
    this.form.summaryTitle2 = 'Clinical History';
    this.form.summaryTitle3 = 'Past History';
    this.form.summaryTitle4 = 'Family History';
    this.form.summaryTitle5 = 'Radiological Finding';
    this.form.summaryTitle6 = 'ECHO';
    this.form.summaryTitle7 = 'Cath Lab';
    this.form.summaryTitle8 = 'Surgical Procedure';
    this.form.summaryTitle9 = 'Treatment Given';
    this.form.summaryTitle10 = 'Treatment Advised';
    this.form.summaryTitle11 = 'Advice On Discharge';
    this.form.summaryTitle12 = 'Follow Up';
    this.form.summaryTitle13 = 'Lab Investigation';
    this.form.summaryTitle14 = 'Other Details';

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
            this.form.footer = 'Please Bring this Summary with You while visiting again to Doctor';
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
