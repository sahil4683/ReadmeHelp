import { Component, OnInit, ViewChild } from '@angular/core';
import { AdmissionService } from 'src/app/_service/webhims/reception/reception_reception/admission.service';
import { PathoLabReportService } from '../../../_service/webpatho/patho-lab-report.service';
import Swal from 'sweetalert2';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-patho-lab-report',
  templateUrl: './patho-lab-report.component.html',
  styleUrls: ['./patho-lab-report.component.css'],
})
export class PathoLabReportComponent implements OnInit {
  constructor(
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private modalService: NgbModal,
    private service: PathoLabReportService,
    private titleService: Title
  ) { }

  form: any = {};
  formTemp: any = {};
  table_data: any = [];
  table_histroy_data: any = [];
  testList: any = [];
  showContent = false;
  showMainTableContent = false;
  /* Button Spinner */
  isGetByDateTable = false;
  isCollectedGetByDateTable = false;

  /* Count */
  waitingCount = 0;
  collectedCount = 0;
  receivedCount = 0;
  printedCount = 0;
  totalCount = 0;

  /* Table Buttons */
  isWaitingData = false;
  isCollectedData = false;
  isReceivedData = false;
  isUnverify = false;
  isPrintedData = false;

  isChangeResult = false;

  FormatTestNameList: any = [];
  FormatTestNameListArray: any = [];
  selectForObservationList: any = [];

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Patho Lab Report");
    this.tokenStorageService.setSessionPrivileges('pm_1');
    this.formTemp.getByDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
    this.onTable(0);
  }

  isedit_action = false
  isdelete_action = false 
  onTable(flag): void {
    const hasAccess = this.tokenStorageService.getSessionPrivileges()
if (hasAccess.edit_action) { this.isedit_action = true }
if (hasAccess.delete_action) { this.isdelete_action = true }

    if (flag == 0) {
      const currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0];
      this.service.getByDate(currentDate).subscribe(
        data => {
          this.table_histroy_data = data.body;
          this.showMainTableContent = true;
        },
        err => {
          console.error(err);
        }
      );
    }
    if (flag == 1) {
      this.service.get().subscribe(
        data => {
          this.showMainTableContent = false;
          setTimeout(() =>
            this.showMainTableContent = true,
            this.table_histroy_data = data.body
            , 100);
        },
        err => {
          console.error(err);
        }
      );
    }
    if (flag == 2) {
      this.service.getByDate(this.formTemp.getByDate).subscribe(
        data => {
          this.showMainTableContent = false;
          setTimeout(() =>
            this.showMainTableContent = true,
            this.table_histroy_data = data.body
            , 100);
        },
        err => {
          console.error(err);
        }
      );
    }
  }

  changeButtonOnTable(req): void {
    this.isChangeResult = false;
    if (req === "Waiting") {
      this.isWaitingData = true;
      this.isCollectedData = false; this.isReceivedData = false; this.isPrintedData = false; this.isUnverify = false;
    }
    if (req === "Collected") {
      this.isCollectedData = true;
      this.isWaitingData = false; this.isReceivedData = false; this.isPrintedData = false; this.isUnverify = false;
    }
    if (req === "Received") {
      this.isReceivedData = true;
      this.isWaitingData = false; this.isCollectedData = false; this.isPrintedData = false; this.isUnverify = false;
    }
    if (req === "Printed") {
      this.isPrintedData = true;
      this.isWaitingData = false; this.isCollectedData = false; this.isReceivedData = false; this.isUnverify = false;
    }
    if (req === "Unverify") {
      this.isUnverify = true;
      this.isReceivedData = false; this.isWaitingData = false; this.isCollectedData = false; this.isPrintedData = false;
    }
  }

  /*--------------------------- Waiting Date Starts ---------------------------*/

  getWaitingByDateTable(date): void {
    this.isGetByDateTable = true;
    this.changeButtonOnTable("Waiting");

    /**  Get Data Call **/
    this.service.getWaitingPatientByDate(date).subscribe((data) => {
      this.showContent = false;
      setTimeout(() =>
        (this.showContent = true), (this.table_data = data.body), (this.isGetByDateTable = false),
        100
      );
      this.isWaitingData = true;
      this.waitingCount = Number(data.body.length);
    }, (err) => {
      console.error(err);
      this.isGetByDateTable = false;
    }
    );

    this.getCountByDate(date);
  }

  /**  Get Count Call **/
  getCountByDate(date): void {
    this.service.getCountByDate(date).subscribe((data) => {
      this.collectedCount = Number(data.body.collected);
      this.receivedCount = Number(data.body.received);
      this.printedCount = Number(data.body.printed);
      this.totalCount = this.waitingCount + this.collectedCount + this.receivedCount + this.printedCount
    }, (err) => {
      console.error(err);
    }
    );
  }

  waitingClick(item): void {
    Swal.fire({
      title: 'Are you sure you want to collect sample?',
      text: 'Time will be Recorded !!',
      icon: 'warning',
      width: 350,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes✔',
      cancelButtonText: 'No❌',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.waiting(item).subscribe((data) => {
          if (data.status == 200) {
            // Test List To Model PopUP
            this.testList = data.body;
            this.openSelectTestModel();
          }
        }, (err) => {
          console.error(err);
        }
        );
      }
    });
  }

  @ViewChild('testModel') testModel: any;
  openSelectTestModel(): void {
    this.textAreaList = [];
    this.testNames = '';
    this.form.selectedTests = '';

    this.modalService.open(this.testModel, {
      centered: true,
      backdrop: 'static',
      size: 'lg',
    });
  }

  textAreaList: any = [];
  testNames = '';
  SelectAllTest(): void {
    if(this.testList.length > 0){ 
    this.textAreaList = [];
    this.testNames = '';
    this.form.selectedTests = '';

    this.textAreaList = this.testList;
    for (const item of this.testList) {
      this.testNames = this.testNames + item.testName + '\r\n';
    }
    this.form.selectedTests = this.testNames;
    this.testList = [];
  }
  }
  addToTextArea(item): void {
    if (!this.textAreaList.includes(item)) {
      this.textAreaList.push(item);
      this.testNames = this.testNames + item.testName + '\r\n';
      this.form.selectedTests = this.testNames;
    }
  }
  saveTestData(): void {
    let roots = this.textAreaList.map(function (item) {
      delete item.name;
      delete item.consultant;
      delete item.mobile;
      delete item.sex;
      delete item.age;
      return item;
    });
    this.service.saveTestData(roots).subscribe(
      (data) => {
        if (data.status == 200) {
          this.testList = data.body;
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300,
          }).then((result) => {
            if (result.isConfirmed) {
              this.modalService.dismissAll();
              var currentDate = new Date(new Date().toString().split('GMT')[0] + ' UTC').toISOString().split('T')[0]
              this.getOtherByDateTableAndType(currentDate, 'Collected');
            }
          });
        }
      }, (err) => {
        console.error(err);
      }
    );
  }

  /*--------------------------- Waiting Date Ends ---------------------------*/

  getOtherByDateTableAndType(date, type): void {
    this.service.getOtherByDateTableAndType(date, type).subscribe(
      (data) => {
        this.changeButtonOnTable(type)
        this.showContent = false;
        setTimeout(
          () => (this.showContent = true),
          (this.table_data = data.body),
          (this.isCollectedGetByDateTable = false),
          100
        );
      }, (err) => {
        console.error(err);
        this.isCollectedGetByDateTable = false;
      }
    );
    this.getCountByDate(date);
  }


  collectedClick(item): void {
    Swal.fire({
      title: 'Are you sure you want to receive sample?',
      text: 'Time will be Recorded !!',
      icon: 'warning',
      width: 350,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes✔',
      cancelButtonText: 'No❌',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.collected(item.id).subscribe((data) => {
          if (data.status == 200) {
            Swal.fire({
              title: 'Success!',
              text: data.message,
              icon: 'success',
              confirmButtonText: 'OK',
              width: 300
            })
            this.getOtherByDateTableAndType(item.date, 'Received');
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
        }, (err) => {
          console.error(err);
        }
        );
      }
    });
  }

  /* Someting lezy work done here...  */
  UnverifyReceivedClick(item): void {
    this.receivedClick(item);
    this.isChangeResult = true;
  }
  ChangeResultClick(): void {
    this.isChangeResult = false;
  }
  // testObservationClick(item):void{
  //   this.receivedClick(this.form)
  // }
  receivedClick(item): void {

    if (item.id == null) {
      alert("Please Select Patient First")
    } else {
      //Bind That Test Data With this table item on table featch (item.other) ORRRR Call API
      this.service.receivedRequest(item.id).subscribe((data) => {
        if (data.status == 200) {
          this.FormatTestNameList = [];
          this.selectForObservationList = [];
          this.FormatTestNameListArray = data.body;

          const key = 'formattest';
          const arrayUniqueByKey = [
            ...new Map(data.body.map((item) => [item[key], item])).values(),
          ];

          this.FormatTestNameList = arrayUniqueByKey;

          if(item.name){
            this.form.patientName = item.name;
          }else{
            this.form.patientName = item.patientName;
          }
          
          this.form.uhid = item.uhid;
          this.form.ipdno = item.ipdno;
          this.form.batch = item.id;
          this.form.sno = item.sno;
        }
      }, err => {
        console.error(err)
      });
      this.openObservationModel();
      // this.FormatTestNameList = item.testName.split(";")
    }

  }

  updateTime(id): void {
    if(id){
      Swal.fire({
        title: 'Are you sure you want to Update Time ?',
        text: 'Update Collection Time And Received Time!',
        icon: 'warning',
        width: 350,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes✔',
        cancelButtonText: 'No❌',
      }).then((result) => {
        if (result.isConfirmed) {
          this.service.updateTime(id).subscribe((data) => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Success!',
                text: data.message,
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300
              })
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
          }, (err) => {
            console.error(err);
          }
          );
        }
      });
    }
  }

  verifyTestAll(id): void {
    if(id){
      Swal.fire({
        title: 'Are you sure you want to Verify All ?',
        text: 'All test get Verifed !',
        icon: 'warning',
        width: 350,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes✔',
        cancelButtonText: 'No❌',
      }).then((result) => {
        if (result.isConfirmed) {
          this.service.verifyTestAll(id).subscribe((data) => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Success!',
                text: data.message,
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300
              })
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
          }, (err) => {
            console.error(err);
          }
          );
        }
      });
    }
  }



  @ViewChild('observationModel') observationModel: any;
  openObservationModel(): void {
    this.modalService.open(this.observationModel, {
      // centered: true,
      size: 'xl',
      backdrop: 'static',
      keyboard: false
    });
  }

  selectForObservation(item): void {
    this.selectForObservationList = this.FormatTestNameListArray.filter(val => val.formattest == item.formattest);
    console.table(this.selectForObservationList);
    
  }

  checkResult(event, item): void {
    if (item.obvValue == null) {
      event.target.checked = false;
      alert("Please Enter Result!!!")
    }
  }

  isBetweenRange(x, min, max): boolean {
    if (x != null && !isNaN(x) && !isNaN(min)) {
      if (min.trim() != null && max.trim() != null)
        return Number(x) >= Number(min) && Number(x) <= Number(max);
    }
    return true;
  }

  saveObservationTest(): void {
    console.log(this.FormatTestNameListArray);
    this.service.saveObservationTest(this.FormatTestNameListArray).subscribe(
      data => {
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300
          });
        }
      });
  }

  onReload(): void {
    this.componentReloadService.reload();
  }

  printReport(sno, type) {

    this.service.printReport(sno, type, 'pdf').subscribe(
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

  verifyTest(): void {
    Swal.fire({
      title: 'Are you sure you want to Verify Test?',
      // text: 'Time will be Recorded !!',
      icon: 'info',
      width: 350,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Yes✔',
      cancelButtonText: 'No❌',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.verifyTest(this.form.batch, this.selectForObservationList[0].formattest).subscribe((data) => {
          if (data.status == 200) {
            
            /******
             * Write Logic For Chnage Color To Red Of Format Name
             */
             this.FormatTestNameList.filter(ff => ff.formattest == this.selectForObservationList[0].formattest).forEach(v=> v.statusName = 'FINAL');

            Swal.fire({
              title: 'Success!',
              text: data.message,
              icon: 'success',
              confirmButtonText: 'OK',
              width: 300
            })
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
        }, (err) => {
          console.error(err);
        }
        );
      }
    });
  }

  onEdit(item): void {
    this.form = item;
  }

}
