import { Component, OnInit } from '@angular/core';
import { AnalysisReportService } from 'src/app/_service/webhims/report/analysis-report.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-mis',
  templateUrl: './mis.component.html',
  styleUrls: ['./mis.component.css']
})
export class MisComponent implements OnInit {

  constructor(private analysisReportService: AnalysisReportService) { }

  form: any = {};

  ngOnInit(): void {
    this.form.fromDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.form.toDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
  }

  onSubmit(): void {

    this.analysisReportService.getReport(this.form).subscribe(
      data => {
        const f = this.form.fromDate;
        const t = this.form.toDate;
        this.form = data.body;
        this.form.fromDate = f;
        this.form.toDate = t;
      },
      err => {
        console.error(err);
      }
    );

  }

  onExport(type): void{
  this.form.type = 'MIS';
  this.form.format = type;
  this.analysisReportService.printReport(this.form).subscribe(
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
