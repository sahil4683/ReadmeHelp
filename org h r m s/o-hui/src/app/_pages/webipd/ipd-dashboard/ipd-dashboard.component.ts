import { Component, OnInit, EventEmitter, Output, Input } from '@angular/core';
import { debounceTime } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { IpdService } from 'src/app/_service/webhims/master/test_master/ipd.service';
import { RequestSheetIpdService } from 'src/app/_service/webipd/ipd_dashboard/request-sheet-ipd.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-ipd-dashboard',
  templateUrl: './ipd-dashboard.component.html',
  styleUrls: ['./ipd-dashboard.component.css'],
})
export class IpdDashboardComponent implements OnInit {
  constructor(
    private modalService: NgbModal,
    private ipdService: IpdService,
    private admissionService: AdmissionService,
    private service: RequestSheetIpdService,
    private titleService: Title
  ) {}

  form: any = {};
  admissionModel: any = {};
  labRequestModel: any = {};
  admissionModel_Table: any = [];
  table_data: any = [];
  isSubmit = false;
  PatientDetails: any = [];
  IPDTestList: any = [];
  IPDTestAvailableList: any = [];
  RequestType: string = "";

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | IPD Dashboard");
    this.getUhidBillNoName();
  }

  DoneTestOfPatient: any = [];
  doneTestOfPatientShow = false;
  getDoneTestOfPatient(ipdno): void {
    this.service.getDoneTestOfPatient(ipdno).subscribe((data) => {
          this.doneTestOfPatientShow = false;
          setTimeout(() =>
            this.doneTestOfPatientShow = true,
            this.DoneTestOfPatient = data.body
            , 100);
        }, (err) => {
          console.error(err);
        }
      );
  }


  deleteDoneTestOfPatient(id,i): void {
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
        this.service.deleteDoneTestOfPatient(id).subscribe((data) => {
          if (data.status == 200) {
              Swal.fire({
                title: 'Deleted!',
                text: 'Data Deleted Success',
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300,
                
              }).then((result) => {
                if (result.isConfirmed) {
                  this.DoneTestOfPatient.splice(i, 1);
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


  getUhidBillNoName(): void {
    this.typeaheadTextInput.pipe(debounceTime(1000)).subscribe((searchtext) => {
      if (searchtext) {
        this.openSuggestionDropdown = true;
        if (this.PatientDetails.length == 0) {
          this.tokensAreLoading = true;
        }
        this.refreshPatientDetails(searchtext);
      } else {
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
      (data) => {
        this.PatientDetails = data.body;
        this.tokensAreLoading = false;
      },
      (err) => {
        console.error(err);
      }
    );
  }

  getPatientDetails_IPD(ipdno): void {
    this.form = {};
    if (ipdno != null && ipdno.length >= 0 && ipdno != '') {
      this.admissionService.getFullPatientDetailByIPD(ipdno).subscribe(
        (data) => {
          if (data.body == null) {
            Swal.fire({
              title: 'Error!',
              text: 'Not Found',
              icon: 'error',
              confirmButtonText: 'OK',
              width: 300,
            });
            this.form = {};
          } else {
            this.form.uhid = data.body.admission.uhid;
            this.form.name = data.body.registration.name;
            this.form.ipdno = data.body.admission.ipdno;
            this.form.dob = data.body.registration.dob;
            this.form.sex = data.body.admission.sex;
            this.form.age = data.body.admission.age;

            this.getDoneTestOfPatient(data.body.admission.ipdno);

            // Extra
            this.form.date = new Date(
              new Date().toString().split('GMT')[0] + ' UTC'
            )
              .toISOString()
              .split('T')[0];
            this.form.organization = data.body.admission.organization;
            this.form.type = "LabRequest";
            this.form.pathoFlag = '0';
          }
        },
        (err) => {
          console.error(err);
        }
      );
    }
  }

  getIPDALLTest(): void {
    this.ipdService.getParticularsListByOrganization(this.form.organization).subscribe(
    // this.ipdService.get().subscribe(
      (data) => {
        this.IPDTestList = data.body;
        this.IPDTestList.forEach(test => {
          test.checked = false;
        });
      },
      (err) => {
        console.error(err);
      }
    );
  }

  onSubmit(): void {
    this.form.details = this.IPDTestAvailableList;
    this.form.type = this.RequestType;
    this.service.save(this.form).subscribe(
      (data) => {
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300,
          }).then((result) => {
            if (result.isConfirmed) {
              this.getDoneTestOfPatient(this.form.ipdno);
              this.modalService.dismissAll();
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
            width: 350,
          });
        }
      },
      (err) => {
        console.error(err);
      }
    );
  }

  ///////////////////////////////////////////
  // Lab Model Start
  /////////////////////////////////////////
  openLabRequestModel(targetModal) {
    if (this.form.ipdno == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Patient Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350,
      });
    } else {
      this.IPDTestAvailableList = [];
      this.RequestType = "LabRequest";
      this.getIPDALLTest();
      this.labRequestModel = this.form;
      this.labRequestModel.date = new Date(
        new Date().toString().split('GMT')[0] + ' UTC'
      )
        .toISOString()
        .split('T')[0];
      this.modalService.open(targetModal, {
        centered: true,
        backdrop: 'static',
        size: 'xl',
      });
    }
  }
  addTestToList(e, item): void {
    item.labFlag = '0';
    item.sno = this.IPDTestAvailableList.length + 1;
    item.testId = item.id;
    item.time = new Date().toTimeString().substring(0, 5);

    if (e.target.checked) {
      if (!this.IPDTestAvailableList.includes(item)) {
        this.IPDTestAvailableList.push(item);
      }
      item.checked = true;
    } else {
      this.IPDTestAvailableList = this.IPDTestAvailableList.filter(function ( n ) {
        return n != item;
      });

      item.checked = false;
    }

    // if (this.IPDTestAvailableList.includes(item)) {
    //   e.target.checked = true;
    //   console.log(e.target.checked);
    // } else {
    //   e.target.checked = false;

    //   console.log(e.target.checked);
    // }
  }

  filterIPDTestList(): void {
    let input, filter, ul, li, a, i, txtValue;
    input = document.getElementById('IPDTestList_Input');
    filter = input.value.toUpperCase();
    ul = document.getElementById('IPDTestList_UL');
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
  ///////////////////////////////////////////
  // Lab Model End
  /////////////////////////////////////////

  ///////////////////////////////////////////
  // Take From Admission Model Start
  /////////////////////////////////////////
  openPatientDetailsModel(targetModal) {
    this.admissionModel_Table = [];
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'xl',
    });
  }

  getPatientDetails_IPD_ForAdmissionModel(ipdno): void {
    if (ipdno != null && ipdno.length >= 0 && ipdno != '') {
      this.admissionService.getFullPatientDetailByIPD(ipdno).subscribe(
        (data) => {
          if (data.body == null) {
            Swal.fire({
              title: 'Error!',
              text: 'Not Found',
              icon: 'error',
              confirmButtonText: 'OK',
              width: 300,
            });
            this.admissionModel_Table = [];
          } else {
            this.admissionModel_Table = Array(data.body);

            

          }
        },
        (err) => {
          console.error(err);
        }
      );
    }
  }
  admissionModel_Table_Edit(item, targetModal) {
    this.form.uhid = item.admission.uhid;
    this.form.name = item.registration.name;
    this.form.ipdno = item.admission.ipdno;
    this.form.dob = item.registration.dob;
    this.form.sex = item.admission.sex;
    this.form.age = item.admission.age;

    // Extra
    this.form.date = new Date(new Date().toString().split('GMT')[0] + ' UTC')
      .toISOString()
      .split('T')[0];
    this.form.organization = item.admission.organization;
    // this.form.type = "LabRequest";
    this.form.pathoFlag = '0';

    this.modalService.dismissAll(targetModal);
  }
  ///////////////////////////////////////////
  // Take From Admission Model End
  /////////////////////////////////////////


/**
 * Load From Radio Start
 */

  openRadioRequestModel(targetModal) {
    if (this.form.ipdno == null) {
      Swal.fire({
        title: 'Error!',
        html: '<pre>Patient Not Selected</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350,
      });
    } else {
      this.IPDTestAvailableList = [];
      this.RequestType = "RadioRequest";
      this.getRadioTest();
      this.labRequestModel = this.form;
      this.labRequestModel.date = new Date(
        new Date().toString().split('GMT')[0] + ' UTC'
      )
        .toISOString()
        .split('T')[0];
      this.modalService.open(targetModal, {
        centered: true,
        backdrop: 'static',
        size: 'xl',
      });
    }
  }
  getRadioTest(): void {
    this.ipdService.getParticularsListByGroup("987",this.form.organization).subscribe(
      (data) => {
        this.IPDTestList = data.body;
        this.IPDTestList.forEach(test => {
          test.checked = false;
        });
      },
      (err) => {
        console.error(err);
      }
    );
  }

/**
 * Load From Radio End
 */

}
