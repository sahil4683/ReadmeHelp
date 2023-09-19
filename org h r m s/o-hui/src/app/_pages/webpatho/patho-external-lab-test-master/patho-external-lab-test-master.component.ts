import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { TokenStorageService } from 'src/app/_services/token-storage.service';

import { PathoExternalLabTestMasterService } from 'src/app/_service/webpatho/patho-external-lab-test-master.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { PathoGroupMasterService } from 'src/app/_service/webpatho/patho-group-master.service';
import { PathoObservationMasterService } from 'src/app/_service/webpatho/patho-observation-master.service';
import {PathoExternalLabMasterService} from "src/app/_service/webpatho/patho-external-lab-master.service"
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-patho-external-lab-test-master',
  templateUrl: './patho-external-lab-test-master.component.html',
  styleUrls: ['./patho-external-lab-test-master.component.css'],
})
export class PathoExternalLabTestMasterComponent implements OnInit {
  constructor(
    private service: PathoExternalLabTestMasterService,
    private tokenStorageService: TokenStorageService,
    private organizationService: OrganizationMasterService,
    private componentReloadService: ComponentReloadService,
    private pathoGroupMasterService:PathoGroupMasterService,
    private pathoObservationMasterService:PathoObservationMasterService,
    private pathoExternalLabMasterService:PathoExternalLabMasterService
  ) {}

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];
  showContent = false;
  organization_data = [];
  groupNameList: any = []; 
  testNameList:any = [];
  labNameList: any = [];

  ngOnInit(): void {
    this.tokenStorageService.setSessionPrivileges('pm_3_6');
    window.scrollTo(0, 0);
    this.onTable();
    this.getGroupNameList();
    this.getOrganizationData();
    this.getLabNameList();
    this.getTestNameList()
  }

  getLabNameList(): void {
    this.pathoExternalLabMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.labNameList = data.body;
      }
    });
  }

  getTestNameList(): void {
    this.pathoObservationMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.testNameList = data.body;
      }
    });
  }

  getGroupNameList(): void {
    this.pathoGroupMasterService.get().subscribe((data) => {
      if (data.status == 200) {
        this.groupNameList = data.body;
      }
    });
  }


  getOrganizationData(): void {
    this.organizationService.get().subscribe(data => {
      this.organization_data = data.body.map(({organization, id}) => {return {organization, id}});
    }, err => console.error(err));
  }

  isedit_action = false;
  isdelete_action = false;
  onTable(): void {
    const hasAccess = this.tokenStorageService.getSessionPrivileges();
    if (hasAccess.edit_action) {
      this.isedit_action = true;
    }
    if (hasAccess.delete_action) {
      this.isdelete_action = true;
    }

    this.service.get().subscribe(
      (data) => {
        setTimeout(
          () => (this.showContent = true),
          (this.table_data = data.body),
          100
        );
      },
      (err) => {
        console.error(err);
      }
    );
  }
  onEdit(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.form.testName = row.testName;
    this.form.groupName = row.groupName;
    this.form.labName = row.labName;

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
      confirmButtonText: 'Delete it!',
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.delete(id).subscribe(
          (data) => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Deleted!',
                text: 'Data Deleted Success',
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300
              }).then((result) => {
                if (result.isConfirmed) {
                  this.onNew();
                }
              });
            }
          },
          (err) => {
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
      (data) => {
        this.isSubmit = false;
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300,
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
            width: 350,
          });
        }
      },
      (err) => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

  onUpdate(): void {
    this.isSubmit = true;
    this.service.update(this.form).subscribe(
      (data) => {
        this.isSubmit = false;
        if (data.status == 200) {
          Swal.fire({
            title: 'Success!',
            text: data.message,
            icon: 'success',
            confirmButtonText: 'OK',
            width: 300,
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
            width: 350,
          });
        }
      },
      (err) => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }
}
