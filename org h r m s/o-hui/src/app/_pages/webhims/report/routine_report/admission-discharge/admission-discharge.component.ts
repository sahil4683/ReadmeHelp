import { Component, OnInit, ViewChild } from '@angular/core';
import Swal from 'sweetalert2';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { RoutineReportService } from 'src/app/_service/webhims/report/routine-report.service';
import { ActivatedRoute } from '@angular/router';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-admission-discharge',
  templateUrl: './admission-discharge.component.html',
  styleUrls: ['./admission-discharge.component.css']
})
export class AdmissionDischargeComponent implements OnInit {

  constructor(
    private consultantMasterService: ConsultantMasterService,
    private organizationMasterService: OrganizationMasterService,
    private getService: AdmissionService,
    private routineReportService: RoutineReportService,
    private activatedroute: ActivatedRoute,
    private componentReloadService:ComponentReloadService
  ) { }

  form: any = {};
  isSubmit = false;
  ConsultantList: any = [];
  OrganizationList: any = [];
  PatientDetails: any = [];

  ngOnInit(): void {
    // this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    // this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getConsultantList();
    this.getOrganizationList();
    this.getPatientDetailList();
    this.activatedroute.queryParams.subscribe(data => {
      if(data.ipdno){
        this.getPatientDetails_IPD(data.ipdno);
      }
    });

  }

  onNew(): void {
    this.componentReloadService.reload();
  }

  getPatientDetails_IPD(ipdno): void {
    if (ipdno != null && ipdno.length >= 0 && ipdno != '') {
      this.getService.getPatientDetailsByIPD(ipdno).subscribe(
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
            this.form.patientName = Number(data.body.ipdno);
          }
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  getPatientDetailList(): void {
    this.getService.getPatientDetailListWithDischarge().subscribe(
      data => {
        if (data.status == 200) {
          this.PatientDetails = data.body.filter(opt => opt.name = opt.name + '(' + opt.uhid + ')');
        }
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

  onSubmit(): void {
    alert("Not Found !")
  }

  onExport(type): void {
    this.form.type = 'Admission_Discharge';
    this.form.format = type;
    this.routineReportService.printReport(this.form).subscribe(
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


  @ViewChild('pdfPreview') pdfPreview;
  isPDFLoad = false;
  onShow(): void {
    this.isPDFLoad = true;
    this.form.type = 'Admission_Discharge';
    this.form.format = 'pdf';
    this.routineReportService.printReport(this.form).subscribe(data => {
      if (data.size == 0) {
        Swal.fire({
          title: 'Error!',
          html: '<i>Data Not Found OR Error !</i>',
          icon: 'error',
          confirmButtonText: 'OK',
          width: 350
        });
        this.isPDFLoad = false;
      } else {
        this.pdfPreview.pdfSrc = data;
        this.pdfPreview.refresh();
      }
    },
      err => {
        console.log(err);
      }
    );
  }

}
