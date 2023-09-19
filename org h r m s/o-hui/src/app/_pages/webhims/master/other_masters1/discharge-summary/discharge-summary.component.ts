import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { DischargeSummaryService } from 'src/app/_service/webhims/master/other_masters1/discharge-summary.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-discharge-summary',
  templateUrl: './discharge-summary.component.html',
  styleUrls: ['./discharge-summary.component.css']
})
export class DischargeSummaryComponent implements OnInit {

  constructor(private service: DischargeSummaryService,
    private componentReloadService: ComponentReloadService) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  TypeList: any = [];

  ngOnInit(): void {
    window.scrollTo(0, 0);
    this.onLoad();
    this.getTypeList();
  }

  onLoad(): void {
    this.service.get().subscribe(
      data => {

        this.form.footer = data.body[0].footer;

        typeof data.body[0] != 'undefined' ? this.form.typeCode1 = Number(data.body[0].typeId) : console.log('1');
        typeof data.body[1] != 'undefined' ? this.form.typeCode2 = Number(data.body[1].typeId) : console.log('2');
        typeof data.body[2] != 'undefined' ? this.form.typeCode3 = Number(data.body[2].typeId) : console.log('3');
        typeof data.body[3] != 'undefined' ? this.form.typeCode4 = Number(data.body[3].typeId) : console.log('4');
        typeof data.body[4] != 'undefined' ? this.form.typeCode5 = Number(data.body[4].typeId) : console.log('5');
        typeof data.body[5] != 'undefined' ? this.form.typeCode6 = Number(data.body[5].typeId) : console.log('6');
        typeof data.body[6] != 'undefined' ? this.form.typeCode7 = Number(data.body[6].typeId) : console.log('7');
        typeof data.body[7] != 'undefined' ? this.form.typeCode8 = Number(data.body[7].typeId) : console.log('8');
        typeof data.body[8] != 'undefined' ? this.form.typeCode9 = Number(data.body[8].typeId) : console.log('9');
        typeof data.body[9] != 'undefined' ? this.form.typeCode10 = Number(data.body[9].typeId) : console.log('10');

        typeof data.body[10] != 'undefined' ? this.form.typeCode11 = Number(data.body[10].typeId) : console.log('11');
        typeof data.body[11] != 'undefined' ? this.form.typeCode12 = Number(data.body[11].typeId) : console.log('12');
        typeof data.body[12] != 'undefined' ? this.form.typeCode13 = Number(data.body[12].typeId) : console.log('13');
        typeof data.body[13] != 'undefined' ? this.form.typeCode14 = Number(data.body[13].typeId) : console.log('14');
        typeof data.body[14] != 'undefined' ? this.form.typeCode15 = Number(data.body[14].typeId) : console.log('15');
        typeof data.body[15] != 'undefined' ? this.form.typeCode16 = Number(data.body[15].typeId) : console.log('16');
        typeof data.body[16] != 'undefined' ? this.form.typeCode17 = Number(data.body[16].typeId) : console.log('17');
        typeof data.body[17] != 'undefined' ? this.form.typeCode18 = Number(data.body[17].typeId) : console.log('18');
        typeof data.body[18] != 'undefined' ? this.form.typeCode19 = Number(data.body[18].typeId) : console.log('19');
        typeof data.body[19] != 'undefined' ? this.form.typeCode20 = Number(data.body[19].typeId) : console.log('20');

        typeof data.body[20] != 'undefined' ? this.form.typeCode21 = Number(data.body[20].typeId) : console.log('21');
        typeof data.body[21] != 'undefined' ? this.form.typeCode22 = Number(data.body[21].typeId) : console.log('22');
        typeof data.body[22] != 'undefined' ? this.form.typeCode23 = Number(data.body[22].typeId) : console.log('23');
        typeof data.body[23] != 'undefined' ? this.form.typeCode24 = Number(data.body[23].typeId) : console.log('24');
        typeof data.body[24] != 'undefined' ? this.form.typeCode25 = Number(data.body[24].typeId) : console.log('25');


        // Pls Change Logic Later
        // var typeNo=0;
        // for(var dd of data.body){
        //   var typeCC  = "";
        //   typeNo++;
        //   typeCC +="typeCode"+typeNo;
        //   this.form.typeCC=dd.typeId;
        // }

      },
      err => {
        console.error(err);
      }
    );
  }

  getTypeList(): void {
    this.service.getTypeList().subscribe(
      data => {
        this.TypeList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }

  saveTitle(): void {

  }

  saveType(): void {
    this.service.add(this.form.type).subscribe(
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
              this.componentReloadService.reload();
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

  deleteType(): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you want this!',
      icon: 'warning',
      width: 300,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete it!'
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.delete(this.form.typeCode).subscribe(
          data => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Deleted!',
                text: 'Data Deleted Success',
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300,
                
              }).then((result) => {
                if (result.isConfirmed) {
                  this.componentReloadService.reload();
                }
              });
            }
          },
          err => {
            console.error(err);
          }
        );
      }
    });
  }

  onSave(): void {

    const numbers = new Array();

    typeof this.form.typeCode1 != 'undefined' ? numbers.push(this.form.typeCode1) : console.log('1');
    typeof this.form.typeCode2 != 'undefined' ? numbers.push(this.form.typeCode2) : console.log('2');
    typeof this.form.typeCode3 != 'undefined' ? numbers.push(this.form.typeCode3) : console.log('3');
    typeof this.form.typeCode4 != 'undefined' ? numbers.push(this.form.typeCode4) : console.log('4');
    typeof this.form.typeCode5 != 'undefined' ? numbers.push(this.form.typeCode5) : console.log('5');
    typeof this.form.typeCode6 != 'undefined' ? numbers.push(this.form.typeCode6) : console.log('6');
    typeof this.form.typeCode7 != 'undefined' ? numbers.push(this.form.typeCode7) : console.log('7');
    typeof this.form.typeCode8 != 'undefined' ? numbers.push(this.form.typeCode8) : console.log('8');
    typeof this.form.typeCode9 != 'undefined' ? numbers.push(this.form.typeCode9) : console.log('9');
    typeof this.form.typeCode10 != 'undefined' ? numbers.push(this.form.typeCode10) : console.log('10');

    typeof this.form.typeCode11 != 'undefined' ? numbers.push(this.form.typeCode11) : console.log('11');
    typeof this.form.typeCode12 != 'undefined' ? numbers.push(this.form.typeCode12) : console.log('12');
    typeof this.form.typeCode13 != 'undefined' ? numbers.push(this.form.typeCode13) : console.log('13');
    typeof this.form.typeCode14 != 'undefined' ? numbers.push(this.form.typeCode14) : console.log('14');
    typeof this.form.typeCode15 != 'undefined' ? numbers.push(this.form.typeCode15) : console.log('15');
    typeof this.form.typeCode16 != 'undefined' ? numbers.push(this.form.typeCode16) : console.log('16');
    typeof this.form.typeCode17 != 'undefined' ? numbers.push(this.form.typeCode17) : console.log('17');
    typeof this.form.typeCode18 != 'undefined' ? numbers.push(this.form.typeCode18) : console.log('18');
    typeof this.form.typeCode19 != 'undefined' ? numbers.push(this.form.typeCode19) : console.log('19');
    typeof this.form.typeCode20 != 'undefined' ? numbers.push(this.form.typeCode20) : console.log('20');

    typeof this.form.typeCode21 != 'undefined' ? numbers.push(this.form.typeCode21) : console.log('21');
    typeof this.form.typeCode22 != 'undefined' ? numbers.push(this.form.typeCode22) : console.log('22');
    typeof this.form.typeCode23 != 'undefined' ? numbers.push(this.form.typeCode23) : console.log('23');
    typeof this.form.typeCode24 != 'undefined' ? numbers.push(this.form.typeCode24) : console.log('24');
    typeof this.form.typeCode25 != 'undefined' ? numbers.push(this.form.typeCode25) : console.log('25');

    this.form.typeId  = numbers.toString().split(',').join('_');

    this.onSubmit();
  }

  onSubmit(): void {
    this.isSubmit = true;
    this.service.save(this.form).subscribe(
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
              this.componentReloadService.reload();
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

