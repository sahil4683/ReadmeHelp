import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { PathoExternalLabMasterService } from 'src/app/_service/webpatho/patho-external-lab-master.service';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { debounceTime } from 'rxjs/operators';

@Component({
  selector: 'app-patho-external-lab-test-report',
  templateUrl: './patho-external-lab-test-report.component.html',
  styleUrls: ['./patho-external-lab-test-report.component.css']
})
export class PathoExternalLabTestReportComponent implements OnInit {

  constructor(
    private pathoExternalLabMasterService:PathoExternalLabMasterService,
    private admissionService:AdmissionService
  ) { }


  form: any = {};
  labNameList: any = [];

  @Input()
  typeaheadTextInput = new EventEmitter<string>();
  @Output() onChange = new EventEmitter<{}>();
  openSuggestionDropdown = false;
  tokensAreLoading = false;
  PatientDetails: any = [];

  
  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getLabNameList();
    this.getUhidBillNoName();
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

  getLabNameList(): void {
    this.pathoExternalLabMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.labNameList = data.body;
      }
    });
  }

  onNew():void{
    
  }

}
