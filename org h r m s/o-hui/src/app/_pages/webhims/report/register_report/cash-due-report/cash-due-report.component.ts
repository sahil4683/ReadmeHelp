import { Component, OnInit } from '@angular/core';
import { CashDueReportService } from 'src/app/_service/webhims/report/register_report/cash-due-report.service';
import Swal from 'sweetalert2';
@Component({
  selector: 'app-cash-due-report',
  templateUrl: './cash-due-report.component.html',
  styleUrls: ['./cash-due-report.component.css']
})
export class CashDueReportComponent implements OnInit {

  constructor(
    private cashDueReportService: CashDueReportService
  ) { }

  download = false;

  ngOnInit(): void {

this.getCashDueReport();
  }

  getCashDueReport(): void{
    this.cashDueReportService.printReport('pdf').subscribe(
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
          this.download = true;
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
