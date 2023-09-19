import { Component, OnInit, ViewChild } from '@angular/core';
import { OpdRegisterService } from 'src/app/_service/webhims/report/register_report/opd-register.service';
import Swal from 'sweetalert2';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { DoctorReferenceService } from 'src/app/_service/webhims/static/doctor-reference.service';
import { SubdepartmentService } from 'src/app/_service/webhims/master/other_masters1/subdepartment.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { UserService } from 'src/app/_services/user.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-ipd-register',
  templateUrl: './ipd-register.component.html',
  styleUrls: ['./ipd-register.component.css']
})
export class IpdRegisterComponent implements OnInit {

  constructor(
    private opdRegisterService: OpdRegisterService,
    private doctorReferenceService: DoctorReferenceService,
    private subdepartmentService: SubdepartmentService,
    private consultantMasterService: ConsultantMasterService,
    private userService: UserService,
    private componentReloadService:ComponentReloadService,
    private organizationMasterService: OrganizationMasterService
  ) { }

  form: any = {};
  isSubmit = false;
  OrganizationList: any = [];
  DoctorReferenceList: any = [];
  SubDeptList: any = [];
  ConsultantList: any = [];

  UserNameList = [];

  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getOrganizationList();
    this.getDoctorReferenceList();
    this.getSubDeptList();
    this.getConsultantList();
    this.getUserNameList();
  }

  onNew(): void {
    this.componentReloadService.reload();
  }

	getUserNameList(){
    this.userService.getAllUser().subscribe(
      data => {
        this.UserNameList = data.body;
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

  getSubDeptList(): void {
    this.subdepartmentService.get().subscribe(
      data => {
        this.SubDeptList = data.body;
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

  onSubmit(): void {
    alert("Not Found !")
  }

  onExport(type): void {
    this.form.type = 'IPDRegister';
    this.form.format = type;
    this.opdRegisterService.printReport(this.form).subscribe(
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
    this.form.type = 'IPDRegister';
    this.form.format = 'pdf';
    this.opdRegisterService.printReport(this.form).subscribe(data => {
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
