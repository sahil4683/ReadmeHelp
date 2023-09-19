import { Component, OnInit, ViewChild } from '@angular/core';
import { OpdRegisterService } from 'src/app/_service/webhims/report/register_report/opd-register.service';
import Swal from 'sweetalert2';
import { DepartmentService } from 'src/app/_service/webhims/master/other_masters1/department.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-ipd-receipt-register',
  templateUrl: './ipd-receipt-register.component.html',
  styleUrls: ['./ipd-receipt-register.component.css']
})
export class IpdReceiptRegisterComponent implements OnInit {

  constructor(
    private opdRegisterService: OpdRegisterService,
    private departmentService: DepartmentService,
    private componentReloadService:ComponentReloadService
  ) { }

  form: any = {};
  isSubmit = false;

  DeptList: any = [];

  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getDeptList();
  }

  onNew(): void {
    this.componentReloadService.reload();
  }

  getDeptList(): void {
    this.departmentService.get().subscribe(
      data => {
        this.DeptList = data.body;
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
    this.form.type = 'IPDReceiptRegister';
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
    this.form.type = 'IPDReceiptRegister';
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

  // @ViewChildren('pdfPreview') pdfPreview;
  // isPDFLoad = false;
  // onShow(): void {
  //   this.isPDFLoad = true;
  //   this.form.type = 'IPDReceiptRegister';
  //   this.form.format = 'pdf';
  //   this.opdRegisterService.printReport(this.form).subscribe(data => {
  //     if (data.size == 0) {
  //       Swal.fire({
  //         title: 'Error!',
  //         html: '<i>Data Not Found OR Error !</i>',
  //         icon: 'error',
  //         confirmButtonText: 'OK',
  //         width: 350
  //       });
  //       this.isPDFLoad = false;
  //     } else {
  //       this.pdfPreview.pdfSrc = data;
  //       this.pdfPreview.refresh();
  //     }
  //   },
  //     err => {
  //       console.log(err);
  //     }
  //   );
  // }

}
