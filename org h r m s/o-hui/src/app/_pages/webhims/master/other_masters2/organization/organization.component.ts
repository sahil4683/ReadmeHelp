import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { MasterCategoryService } from 'src/app/_service/webhims/static/master-category.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-organization',
  templateUrl: './organization.component.html',
  styleUrls: ['./organization.component.css']
})
export class OrganizationComponent implements OnInit {

  constructor(private service: OrganizationMasterService,
    private tokenStorageService: TokenStorageService,
    private componentReloadService: ComponentReloadService,
    private masterCategoryService: MasterCategoryService) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  StateList: any = [];
  CityList: any = [];
  DesignationList: any = [];
  CountryCodeList: any = [];
  showContent = false;

  ngOnInit(): void {
    this.tokenStorageService.setSessionPrivileges('hm_5_7_1');
    this.form.categoryType = 'PATIENT TYPE';
    window.scrollTo(0, 0);
    this.onTable();
    this.getStateList();
    this.getCityList();
    this.getDesignationList();
    this.getCountryCodeList();
  }


  getCountryCodeList(): void {
    this.masterCategoryService.getCategoryList('MobileCode').subscribe(
      data => {
        this.CountryCodeList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getDesignationList(): void {
    this.masterCategoryService.getCategoryList('Designation').subscribe(
      data => {
        this.DesignationList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getStateList(): void {
    this.masterCategoryService.getCategoryList('State').subscribe(
      data => {
        this.StateList = data.body;
      },
      err => {
        console.error(err);
      }
    );
  }
  getCityList(): void {
    this.masterCategoryService.getCategoryList('City').subscribe(
      data => {
        this.CityList = data.body;
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
    if (hasAccess.edit_action) { this.isedit_action = true }
    if (hasAccess.delete_action) { this.isdelete_action = true }

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

}
