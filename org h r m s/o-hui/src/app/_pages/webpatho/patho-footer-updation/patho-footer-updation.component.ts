import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { PathoFooterUpdationService } from 'src/app/_service/webpatho/patho-footer-updation.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-patho-footer-updation',
  templateUrl: './patho-footer-updation.component.html',
  styleUrls: ['./patho-footer-updation.component.css']
})
export class PathoFooterUpdationComponent implements OnInit {

  constructor(private service: PathoFooterUpdationService,
    private componentReloadService: ComponentReloadService) { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
    window.scrollTo(0, 0);
    
    this.service.get().subscribe(
      data => {
        this.form.footer1 = data.body[0].footer1;
        this.form.footer2 = data.body[0].footer2;
        this.form.haematologyMachineName = data.body[0].haematologyMachineName;
        this.form.serologyMachineName = data.body[0].serologyMachineName;
        this.form.thyroidMachineName = data.body[0].thyroidMachineName;
        this.form.biochemistryMachineName = data.body[0].biochemistryMachineName;
        this.form.electrolytesMachineName = data.body[0].electrolytesMachineName;
        this.form.coagulationMachineName = data.body[0].coagulationMachineName;
      },
      err => {
        console.error(err);
      }
    );

  }

  onNew(): void {
    this.componentReloadService.reload();
  }

  onSave(): void {
    this.isSubmit = true;
    this.service.update(this.form).subscribe(
      data => {
        this.isSubmit = false;
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300
          }).then((result) => {
            if (result.isConfirmed) {
              this.onNew();
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
        }
      },
      err => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

}
