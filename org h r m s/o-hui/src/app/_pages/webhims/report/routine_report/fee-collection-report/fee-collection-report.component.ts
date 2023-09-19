import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { RoutineReportService } from 'src/app/_service/webhims/report/routine-report.service';
import Swal from 'sweetalert2';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-fee-collection-report',
  templateUrl: './fee-collection-report.component.html',
  styleUrls: ['./fee-collection-report.component.css']
})
export class FeeCollectionReportComponent implements OnInit {

  constructor(
    private userService: UserService,
    private routineReportService: RoutineReportService,
    private componentReloadService: ComponentReloadService
  ) { }

  form: any = {};
  isSubmit = false;
  UserNameList = [];

  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.getUserNameList();
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
  onNew(): void {
    this.componentReloadService.reload();
  }

  // onSubmit(): void {
  //   this.form.type = 'DateWise';
  //   this.form.format = 'pdf';
  //   this.routineReportService.printReport(this.form).subscribe(
  //     data => {
  //       if (data.size == 0) {
  //         Swal.fire({
  //           title: 'Error!',
  //           html: '<i>Data Not Found OR Error !</i>',
  //           icon: 'error',
  //           confirmButtonText: 'OK',
  //           width: 350
  //         });
  //       } else {
  //         const fileURL = URL.createObjectURL(data);
  //         window.open(fileURL, '_blank');
  //       }
  //     },
  //     err => {
  //       console.log(err);
  //     }
  //   );
  // }

  // DateWise(): void {
  //   this.form.type = 'DateWise';
  //   this.form.format = 'pdf';
  //   this.routineReportService.printReport(this.form).subscribe(
  //     data => {
  //       if (data.size == 0) {
  //         Swal.fire({
  //           title: 'Error!',
  //           html: '<i>Data Not Found OR Error !</i>',
  //           icon: 'error',
  //           confirmButtonText: 'OK',
  //           width: 350
  //         });
  //       } else {
  //         const fileURL = URL.createObjectURL(data);
  //         window.open(fileURL, '_blank');
  //       }
  //     },
  //     err => {
  //       console.log(err);
  //     }
  //   );
  // }

  // UserWise(): void {
  //   this.form.type = 'UserWise';
  //   this.form.format = 'pdf';
  //   this.routineReportService.printReport(this.form).subscribe(
  //     data => {
  //       if (data.size == 0) {
  //         Swal.fire({
  //           title: 'Error!',
  //           html: '<i>Data Not Found OR Error !</i>',
  //           icon: 'error',
  //           confirmButtonText: 'OK',
  //           width: 350
  //         });
  //       } else {
  //         const fileURL = URL.createObjectURL(data);
  //         window.open(fileURL, '_blank');
  //       }
  //     },
  //     err => {
  //       console.log(err);
  //     }
  //   );
  // }

  onSubmit(): void {
    alert("Not Found !")
  }


  onExport(type,format): void {
    this.form.type = type;
    this.form.format = format;
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
  onShow(type): void {
    this.isPDFLoad = true;
    this.form.type = type;
    this.form.format = 'pdf';
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
