import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { IpdPackageMasterService } from 'src/app/_service/webhims/master/package_master/ipd-package-master.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { IpdService } from 'src/app/_service/webhims/master/test_master/ipd.service';
import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-ipd-package-master',
  templateUrl: './ipd-package-master.component.html',
  styleUrls: ['./ipd-package-master.component.css']
})
export class IpdPackageMasterComponent implements OnInit {

  constructor(private service: IpdPackageMasterService,
              private organizationMasterService: OrganizationMasterService,
              private ipdService: IpdService,
              private tokenStorageService: TokenStorageService,
              private componentReloadService: ComponentReloadService,
              private consultantMasterService: ConsultantMasterService) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  OrganizationList: any = [];
  ParticularsList: any = [];
  ConsultantList: any = [];
  showContent = false;



  public DetailsList: any[] = [{
    sno: 1,
    id: 0,
    testName: '',
    procedureDoctor: '',
    qty: 0,
    rate: 0,
    amount: 0
  }];

  ngOnInit(): void {
    this.tokenStorageService.setSessionPrivileges('hm_5_5_1');
    window.scrollTo(0, 0);
    this.onTable();
    this.getOrganizationList();
    this.getConsultantList();
    this.form.organizationCode = 70;
    this.getParticularsList();
  }

  fillParticularsData(i): void{
    const SortRow = this.ParticularsList.find((e => e.id == Number(this.DetailsList[i].testName)));
    this.DetailsList[i].qty = 1;
    this.DetailsList[i].rate = SortRow.rate;
    this.DetailsList[i].amount = SortRow.rate;
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

  getParticularsList(): void{
    this.ipdService.getParticularsListByOrganization(this.form.organizationCode).subscribe(
      data => {
        this.ParticularsList = data.body;
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

  isedit_action = false
		isdelete_action = false
  onTable(): void {

    const hasAccess = this.tokenStorageService.getSessionPrivileges()
		if(hasAccess.edit_action) { this.isedit_action = true }
		if(hasAccess.delete_action) { this.isdelete_action = true }

    this.service.get().subscribe(
      data => {
        setTimeout(() =>
        this.showContent = true,
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
    this.form.organizationCode = Number(row.organizationCode);
    this.service.getDetailsById(row.id).subscribe(
      data => {
        this.DetailsList = data.body;
        // testName = testCode ?
      },
      err => {
        console.error(err);
      }
    );
    this.isEdit = true;
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
    this.form.details = this.DetailsList;
    if (this.isEdit) {
      this.onUpdate();
    } else {
      this.onSubmit();
    }
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

  addAddress() {
    this.DetailsList.push({
      sno: this.DetailsList.length + 1,
      id: 0,
      testName: '',
      procedureDoctor: '',
      qty: 0,
      rate: 0,
      amount: 0
    });
  }

  removeAddress(i: number, id) {
    if (confirm('Are you sure you want to remove it?')) {
      if (id != 0) {
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
            this.service.deleteDetailById(id).subscribe(
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
      this.DetailsList.splice(i, 1);
    }
  }

  calculateTotal(i): void {
    this.DetailsList[i].amount = this.DetailsList[i].qty * this.DetailsList[i].rate;
  }

}
