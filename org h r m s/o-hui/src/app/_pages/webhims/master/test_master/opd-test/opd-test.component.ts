import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { SuperGroupService } from 'src/app/_service/webhims/master/other_masters1/super-group.service';
import { GroupService } from 'src/app/_service/webhims/master/other_masters1/group.service';
import { SubGroupService } from 'src/app/_service/webhims/master/other_masters1/sub-group.service';
import { SubdepartmentService } from 'src/app/_service/webhims/master/other_masters1/subdepartment.service';
import { OrganizationMasterService } from 'src/app/_service/webhims/static/organization-master.service';
import { OpdService } from 'src/app/_service/webhims/master/test_master/opd.service';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { Title } from '@angular/platform-browser';

@Component({
  selector: 'app-opd-test',
  templateUrl: './opd-test.component.html',
  styleUrls: ['./opd-test.component.css']
})
export class OpdTestComponent implements OnInit {


  constructor(private service: OpdService,
              private superGroupService: SuperGroupService,
              private groupService: GroupService,
              private subGroupService: SubGroupService,
              private tokenStorageService: TokenStorageService,
              private subdepartmentService: SubdepartmentService,
              private componentReloadService: ComponentReloadService,
              private organizationMasterService: OrganizationMasterService,
              private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  isEdit = false;
  table_data: any = [];

  SuperGroupList: any = [];
  GroupList: any = [];
  SubGroupList: any = [];
  SubDepartmentList: any = [];
  OrganizationList: any = [];
  showContent = false;

  ngOnInit(): void {
    this.titleService.setTitle("OPD Test Master");
    this.tokenStorageService.setSessionPrivileges('hm_5_1_2');
    window.scrollTo(0, 0);
    this.onTable();
    this.getCategories();
  }

  getCategories(): void{

    this.superGroupService.get().subscribe(
      data => {
        this.SuperGroupList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.groupService.getGruopListByDepartment("17").subscribe(
      data => {
        this.GroupList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.subGroupService.get().subscribe(
      data => {
        this.SubGroupList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.subdepartmentService.get().subscribe(
      data => {
        this.SubDepartmentList = data.body;
      },
      err => {
        console.error(err);
      }
    );

    this.organizationMasterService.get().subscribe(
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
    this.form.testNo = row.id;
    this.form.superGroup = Number(row.superGroup);
    this.form.groupName = Number(row.groupName);
    this.form.subGroup = Number(row.subGroup);
    this.form.subDepartment = Number(row.subDepartment);
    this.form.organizationName = Number(row.organizationName);
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
