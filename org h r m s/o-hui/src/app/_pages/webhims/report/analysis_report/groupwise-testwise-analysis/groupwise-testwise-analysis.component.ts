import { Component, OnInit } from '@angular/core';
import { GroupService } from 'src/app/_service/webhims/master/other_masters1/group.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { OpdService } from 'src/app/_service/webhims/master/test_master/opd.service';
import { AnalysisReportService } from 'src/app/_service/webhims/report/analysis-report.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-groupwise-testwise-analysis',
  templateUrl: './groupwise-testwise-analysis.component.html',
  styleUrls: ['./groupwise-testwise-analysis.component.css']
})
export class GroupwiseTestwiseAnalysisComponent implements OnInit {

  constructor(
    private groupService: GroupService,
    private doctorReferenceService: DoctorReferenceService,
    private consultantMasterService: ConsultantMasterService,
    private opdService: OpdService,
    private analysisReportService: AnalysisReportService
  ) { }

  opdform: any = {};
  ipdform: any = {};
  labform: any = {};
  radioform: any = {};
  hcform: any = {};

  GroupList: any = [];
  DoctorReferenceList: any = [];
  ConsultantList: any = [];
  ParticularsList: any = [];

  ngOnInit(): void {
    this.opdform.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.opdform.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];

    this.labform.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.labform.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];

    this.radioform.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.radioform.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];

    this.hcform.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.hcform.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];

    this.ipdform.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.ipdform.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];

    this.getGroupList();
    this.getDoctorReferenceList();
    this.getConsultantList();
  }

  getGroupList(): void {
    this.groupService.getGruopListByDepartmentAndSuperGroup(17, 35).subscribe(
      data => {
        this.GroupList = data.body;
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

  getParticularsList(groupName, organization): void {
    this.opdService.getParticularsListByGroupAndOrganization(groupName, organization).subscribe(
      data => {
        this.ParticularsList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  opdSubmit(){
    this.opdform.type = 'GroupWiseTest_OPD';
    this.opdform.format = 'pdf';
    this.analysisReportService.printReport(this.opdform).subscribe(
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

  labSubmit(){
    this.labform.type = 'GroupWiseTest_LAB';
    this.labform.format = 'pdf';
    this.analysisReportService.printReport(this.labform).subscribe(
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

  radioSubmit(){
    this.radioform.type = 'GroupWiseTest_RADIO';
    this.radioform.format = 'pdf';
    this.analysisReportService.printReport(this.radioform).subscribe(
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

  hcSubmit(){
    this.hcform.type = 'GroupWiseTest_HC';
    this.hcform.format = 'pdf';
    this.analysisReportService.printReport(this.hcform).subscribe(
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

  ipdSubmit(){
  }

}
