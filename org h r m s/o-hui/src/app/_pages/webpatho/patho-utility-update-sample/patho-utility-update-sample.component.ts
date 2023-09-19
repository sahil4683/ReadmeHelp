import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { PathoUtilityUpdateSampleService } from 'src/app/_service/webpatho/patho-utility-update-sample.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-patho-utility-update-sample',
  templateUrl: './patho-utility-update-sample.component.html',
  styleUrls: ['./patho-utility-update-sample.component.css']
})
export class PathoUtilityUpdateSampleComponent implements OnInit {

  constructor(private service: PathoUtilityUpdateSampleService,
    private componentReloadService: ComponentReloadService) { }

  form: any = {};
  isSubmit = false;

  ngOnInit(): void {
    window.scrollTo(0, 0);
    
    this.service.get().subscribe(
      data => {
        this.form.uhid = data.body[0].uhid;
        this.form.barcode = data.body[0].barcode;
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
