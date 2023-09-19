import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { PathoFormateMasterService } from 'src/app/_service/webpatho/patho-format-master.service';
import { PathoFormatTestMasterService } from 'src/app/_service/webpatho/patho-format-test-master.service';
import { PathoObservationMasterService } from 'src/app/_service/webpatho/patho-observation-master.service';
import { PathoGroupMasterService } from 'src/app/_service/webpatho/patho-group-master.service';
import { PathoFormatGroupMasterService } from 'src/app/_service/webpatho/patho-format-group-master.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-patho-format-group-master',
  templateUrl: './patho-format-group-master.component.html',
  styleUrls: ['./patho-format-group-master.component.css']
})
export class PathoFormatGroupMasterComponent implements OnInit {

  constructor(private service: PathoFormatGroupMasterService,
    private tokenStorageService: TokenStorageService,
    private pathoFormateMasterService:PathoFormateMasterService,
    private pathoFormatTestMasterService:PathoFormatTestMasterService,
    private pathoObservationMasterService:PathoObservationMasterService,
    private pathoGroupMasterService:PathoGroupMasterService,
    private componentReloadService:ComponentReloadService
    ) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  showContent = false;
  formatNameList: any = [];
  formatTestNameList:any = [];
  particularsList: any = [];
  groupList: any = [];
  ngOnInit(): void {
    this.tokenStorageService.setSessionPrivileges('pm_3_1');
    window.scrollTo(0, 0);
    this.onTable();
    this.getformatNameList();
    this.getformatTestNameList();
    this.getparticularsList();
    this.getgroupList();
  }

  getformatNameList(): void {
    this.pathoFormateMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.formatNameList = data.body;
      }
    });
  }

  getformatTestNameList(): void {
    this.pathoFormatTestMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.formatTestNameList = data.body;
      }
    });
  }
  setFormatTestId(item): void {
    console.log(this.formatTestNameList.find(x => x.formatTestName === item));
    this.form.formatTestId = this.formatTestNameList.find(x => x.formatTestName === item).id
  }

  getparticularsList(): void {
    this.pathoObservationMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.particularsList = data.body;
      }
    });
  }
  setTestCode(testName,i){
    // this.form.formatTestId = this.formatTestNameList.find(x => x.formatTestName === item).id
    this.DetailsList[i].testCode = this.particularsList.find(x => x.testName === testName).id
    // particularsList
  }
  getgroupList(): void {
    this.pathoGroupMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.groupList = data.body;
      }
    });
  }
  

  isedit_action = false
  isdelete_action = false
  onTable(): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
    if(hasAccess.edit_action) { this.isedit_action = true }
    if(hasAccess.delete_action) { this.isdelete_action = true }
    
    this.service.get().subscribe(
      data => {
        setTimeout(()=>
        this.showContent=true,
        this.table_data = data.body
        , 100);
      },
      err => {
        console.error(err);
      }
    );
  }
  onEdit(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.isEdit = true;
    this.DetailsList = this.table_data.filter(entry => entry.formatName===row.formatName && entry.formatTest===row.formatTest && entry.sno === row.sno)
  }

  moveDown(i){
    if(this.DetailsList.length >i){
      let temp = this.DetailsList[i].srno;
      this.DetailsList[i].srno = Number(this.DetailsList[i+1].srno);
      this.DetailsList[i+1].srno = Number(temp);
      [this.DetailsList[i+1], this.DetailsList[i]] = [this.DetailsList[i], this.DetailsList[i+1]]
    }
  }
  moveUp(i){
    if(this.DetailsList.length >i){
      let temp = this.DetailsList[i].srno;
      this.DetailsList[i].srno = Number(this.DetailsList[i-1].srno);
      this.DetailsList[i-1].srno = Number(temp);
      [this.DetailsList[i-1], this.DetailsList[i]] = [this.DetailsList[i], this.DetailsList[i-1]]
    }
  }
  onDelete(id): void {
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
      this.service.delete(id).subscribe(
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
                  this.onNew();
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

  onNew(): void {
    this.componentReloadService.reload();
  }
  onSave(): void {
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
  }
  onSubmit(): void {
    this.isSubmit = true;
    this.DetailsList.forEach(element => {
      element.formatName = this.form.formatName;
      element.formatTest = this.form.formatTest;
      element.formatTestId = this.form.formatTestId;
      element.note = this.form.note;
      element.avgTime = this.form.avgTime;
    });
    this.service.save(this.DetailsList).subscribe(
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

  onUpdate(): void {

    this.DetailsList.forEach(element => {
      element.note = this.form.note;
      element.avgTime = this.form.avgTime;
      element.formatName = this.form.formatName;
      element.formatTest = this.form.formatTest;
      element.formatTestId = this.form.formatTestId;
      element.sno = this.form.sno;
    });

    this.isSubmit = true;
    this.service.update(this.DetailsList).subscribe(
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




  public DetailsList: any[] = [{
    id: 0,
    srno: 1,
    medicineName: '',
    testName: '',
    sno:'',
    testCode:'',
    formatName:'',
    groupName:'',
    formatTest:'',
    formatTestId:'',
    note:'',
    avgTime:''
  }];


  addAddress() {
    this.DetailsList.push({
      srno: this.DetailsList.length + 1,
      id: 0,
      medicineName: '',
      testName: '',
      sno:'',
      testCode:'',
      formatName:'',
      groupName:'',
      formatTest:'',
      formatTestId:'',
      note:'',
      avgTime:''
    });
  }

  removeAddress(i: number) {
    if (confirm("Are you sure you want to remove it?")) {
      this.DetailsList.splice(i, 1);
    } 
  }
}
