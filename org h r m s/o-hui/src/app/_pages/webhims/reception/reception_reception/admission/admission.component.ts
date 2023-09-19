import { Component, OnInit, EventEmitter, Output, Input, ViewChild } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { RegistrationService } from 'src/app/_service/webhims/reception/reception_reception/registration.service';
import { BedmasterService } from 'src/app/_service/webhims/static/bedmaster.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { InsuranceMasterService } from 'src/app/_service/webhims/master/other_masters1/insurance-master.service';
import { TpaMasterService } from 'src/app/_service/webhims/master/other_masters1/tpa-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { DatatableOptionsService } from 'src/app/_helpers/datatable-options.service';
import { ActivatedRoute } from '@angular/router';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-admission',
  templateUrl: './admission.component.html',
  styleUrls: ['./admission.component.css']
})
export class AdmissionComponent implements OnInit {

  constructor(private service: AdmissionService,
              private registrationService: RegistrationService,
              private bedmasterService: BedmasterService,
              private insuranceMasterService: InsuranceMasterService,
              private organizationMasterService: OrganizationMasterService,
              private tpaMasterService: TpaMasterService,
              private doctorReferenceService: DoctorReferenceService,
              private consultantMasterService: ConsultantMasterService,
              private tokenStorageService: TokenStorageService,
              private datatableOptionsService:DatatableOptionsService,
              private activatedroute: ActivatedRoute,
              private componentReloadService: ComponentReloadService,
              private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  ageString = '';
  isInsurence = false;
  spinner = false;
  BedList: any = [];
  OrganizationList: any = [];
  DoctorReferenceList: any = [];
  ConsultantList: any = [];
  // ipdEdit = 0;
  PatientDetails: any = [];
  InsuranceComList: any = [];
  TpaList: any = [];
  showContent = false;

  isedit_action = false
  isdelete_action = false

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: any = {};

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Admission");
    this.tokenStorageService.setSessionPrivileges('hm_1_1_2');
    window.scrollTo(0, 0);
    // this.form.uhid = 0
    this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.time = new Date().toTimeString().substring(0, 5);
    this.form.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.onTable(0);
    this.getBedList();
    this.getOrganizationList();
    this.getDoctorReferenceList();
    this.getConsultantList();
    this.insurenceShowHide();
    this.getUhidBillNoName();
    // this.getInsuranceComList()
    this.dtOptions = this.datatableOptionsService.getDtOptions();

    this.activatedroute.queryParams.subscribe(data => {
      if(data.uhid){
        // window.location.reload();
        this.getPatientDetails(data.uhid);
        
      }
    });

    this.getNextNumberSequence();
    this.getTableSearch();
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
  
  getNextNumberSequence():void{
    this.tokenStorageService.getNextNumberSequence('IPD').subscribe(
      data => {
        this.form.ipdno = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }


  getInsuranceComList(): void{
    this.insuranceMasterService.get().subscribe(
      data => {
        this.InsuranceComList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getTpaList(): void{
    this.tpaMasterService.get().subscribe(
      data => {
        this.TpaList = data.body;
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

  getConsultantList(): void{
    this.consultantMasterService.getConsultantList().subscribe(
      data => {
        this.ConsultantList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getDoctorReferenceList(): void{
    this.doctorReferenceService.getDoctorReferenceList().subscribe(
      data => {
        this.DoctorReferenceList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getBedList(): void{
    this.bedmasterService.getBedList().subscribe(
      data => {
        this.BedList = data.body;
      },
      err => {
        console.error(err);
      }
    );
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


  getClass(): void{
        this.form.class =  this.BedList.find((e: { bedno: string; }) => e.bedno == this.form.bedno).wardname;
        this.form.wardname = this.BedList.find((e: { bedno: string; }) => e.bedno == this.form.bedno).wardname;
  }

  insurenceShowHide(): void{
    if (this.form.typeofPatient == 'Insurence'){
      this.isInsurence = true;
      this.getInsuranceComList();
      this.getTpaList();
    }else if (this.form.typeofPatient == 'Direct' || this.form.typeofPatient == 'ABPMJAY'){
      this.isInsurence = false;
      this.form.insuranceCom = null;
      this.form.inc_Ex = null;
      this.form.tpaname = null;
      this.form.claimno = null;
      this.form.policyno = null;
    }else{
      this.form.typeofPatient = 'Direct';
    }
  }

  getPatientDetails(uhid): void{
    if (uhid != null && uhid.length >= 0 && uhid != ''){
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
          }else{
            this.form.name = data.body.name;
            this.form.uhid = data.body.uhid;
            this.form.time = data.body.time;
            this.form.userName = data.body.userName;
            this.form.organization = data.body.organization;
            this.form.sex = data.body.sex;
            this.form.age = data.body.age;
            this.form.refBy1 =  Number(data.body.referredBy);
            this.form.consultant1 = Number(data.body.consultant);
            this.form.admitteddept = 'IPD';
            this.form.patientType = 'Hospital Patient';
            this.form.typeofPatient = 'Direct';
            this.insurenceShowHide();
          }
          this.spinner = false;
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
    
    this.showContent = false;
    this.table_data =[];

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

  onEdit(row): void {
    this.form = {};
    this.isSubmit = false;
    window.scrollTo(0, 0);
    this.isEdit = true;
    this.form = row.admission;
    this.form.name = row.registration.name;
    if(row.admission.time.length === 4){ this.form.time ='0'+row.admission.time }
    this.form.class = this.form.wardname;
    // this.ipdEdit = this.form.ipdno;
    this.insurenceShowHide();
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

  onReload(): void {
    this.componentReloadService.reload();
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
              this.form.id = Number(data.body);
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
        }
      },
      err => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

  getPdf(id, type): void {
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


}
