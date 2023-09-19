import { Component, EventEmitter, Input, OnInit, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { RegistrationService } from 'src/app/_service/webhims/reception/reception_reception/registration.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { MasterCategoryService } from 'src/app/_service/webhims/static/master-category.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { DataTableDirective } from 'angular-datatables';
import { Subject } from 'rxjs';
import { DatatableOptionsService } from 'src/app/_helpers/datatable-options.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { debounceTime } from 'rxjs/operators';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  constructor(private service: RegistrationService,
    private organizationMasterService: OrganizationMasterService,
    private doctorReferenceService: DoctorReferenceService,
    private consultantMasterService: ConsultantMasterService,
    private masterCategoryService: MasterCategoryService,
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private datatableOptionsService:DatatableOptionsService,
    private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  ageString = '';
  OrganizationList: any = [];
  DoctorReferenceList: any = [];
  ConsultantList: any = [];


  IdnoCaptionList: any = [];
  AreaList: any = [];
  CityList: any = [];
  StateList: any = [];
  CountryList: any = [];
  PatientTypeList: any = [];
  ReligionList: any = [];
  OccupationList: any = [];
  showContent = false;

  isedit_action = false
  isdelete_action = false

  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: any = {};

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Registration");
    this.tokenStorageService.setSessionPrivileges('hm_1_1_1');
    this.form.mobileCode = '91';
    this.form.country = 'INDIA';
    this.form.state = 'GUJARAT';
    this.form.city = 'NAVSARI';
    this.form.organization = 70;
    this.form.patientType = 'GENERAL';
    this.onTable(0);
    this.getNextTOKEN();
    this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.time = new Date().toTimeString().substring(0, 5);
    this.form.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getOrganizationList();
    this.getDoctorReferenceList();
    this.getConsultantList();
    this.getCategories();
    this.dtOptions = this.datatableOptionsService.getDtOptions();
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
    this.tokenStorageService.getNextNumberSequence('UHID').subscribe(
      data => {
        this.form.uhid = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }


  getCategories(): void {

    this.masterCategoryService.getCategoryList('IDNO').subscribe(
      data => {
        this.IdnoCaptionList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('area').subscribe(
      data => {
        this.AreaList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('City').subscribe(
      data => {
        this.CityList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('State').subscribe(
      data => {
        this.StateList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('Country').subscribe(
      data => {
        this.CountryList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('PATIENT TYPE').subscribe(
      data => {
        this.PatientTypeList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('Religion').subscribe(
      data => {
        this.ReligionList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.masterCategoryService.getCategoryList('Occupation').subscribe(
      data => {
        this.OccupationList = data.body;
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

  getMLCNO(): void {
    this.service.getNextMLC().subscribe(
      data => {
        this.form.mlcno = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  getNextTOKEN(): void {
    this.service.getNextTOKEN().subscribe(
      data => {
        this.form.tokenno = data.body;
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

  onEdit(row): void {
    this.form = {};
    this.isSubmit = false;
    window.scrollTo(0, 0);
    this.form = row;
    if(row.time.length === 4){ this.form.time ='0'+row.time }
    this.isEdit = true;
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
              // this.onNew();
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


  getPdf(id, type): void {
    if (id != null && id != 'undefined') {
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


  ageCalculator() {
    // collect input from HTML form and convert into date format
    // var userinput = document.getElementById("DOB").value;
    const dob = new Date(this.form.dob);

    // extract the year, month, and date from user date input
    const dobYear = dob.getFullYear();
    const dobMonth = dob.getMonth();
    const dobDate = dob.getDate();

    // get the current date from the system
    const now = new Date();
    // extract the year, month, and date from current date
    const currentYear = now.getFullYear();
    const currentMonth = now.getMonth();
    const currentDate = now.getDate();

    // declare a variable to collect the age in year, month, and days
    let age = {};
    // var ageString = "";
    let yearAge;
    let monthAge;
    let dateAge;
    // get years
    yearAge = currentYear - dobYear;

    // get months
    if (currentMonth >= dobMonth) {
      monthAge = currentMonth - dobMonth;
    }
    else {
      yearAge--;
      monthAge = 12 + currentMonth - dobMonth;
    }

    // get days
    if (currentDate >= dobDate) {
      dateAge = currentDate - dobDate;
    }
    else {
      monthAge--;
      dateAge = 31 + currentDate - dobDate;
      if (monthAge < 0) {
        monthAge = 11;
        yearAge--;
      }
    }
    // group the age in a single variable
    age = {
      years: yearAge,
      months: monthAge,
      days: dateAge
    };

    if ((yearAge > 0) || (monthAge > 0) || (dateAge > 0)) {
      this.ageString = yearAge + 'Year(s) ' + monthAge + 'Month(s) ' + dateAge + 'Day(s)';
    }

    this.form.age = this.ageString;
  }

}
