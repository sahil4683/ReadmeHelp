import { ConsultantMasterService } from 'src/app/_service/webhims/static/consultant-master.service';
import { DepartmentService } from 'src/app/_service/webhims/master/other_masters1/department.service';
import { GroupService } from 'src/app/_service/webhims/master/other_masters1/group.service';
import { Component, OnInit } from '@angular/core';
import { DoctorIncentiveMasterService } from 'src/app/_service/webhims/master/other_masters1/doctor-incentive-master.service';
import Swal from 'sweetalert2';
import { TokenStorageService } from 'src/app/_services/token-storage.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';

@Component({
  selector: 'app-doctor-incentive-master',
  templateUrl: './doctor-incentive-master.component.html',
  styleUrls: ['./doctor-incentive-master.component.css'],
})
export class DoctorIncentiveMasterComponent implements OnInit {
  constructor(
    private doctorService: ConsultantMasterService,
    private departmentService: DepartmentService,
    private groupService: GroupService,
    private service: DoctorIncentiveMasterService,
    private componentReloadService: ComponentReloadService,
    private tokenStorageService: TokenStorageService
  ) {}

  form: any = {};
  isSubmit = false;
  SubGroup1List: any = [];
  isEdit = false;
  table_data: any = [];
  showContent = false;
  doctorNameList: any = [];
  // departmentList: any = [];
  // groupNameList: any = [];

  ngOnInit(): void {
    this.tokenStorageService.setSessionPrivileges('hm_5_6_9');
    window.scrollTo(0, 0);
    this.onTable();
    this.getDoctorNameList();
    // this.getDepartmentList();
    // this.getGroupList();
  }

  activeIds: string[] = [];
  panels = [1, 2, 3, 4, 5, 6, 7, 8];
  openAll() {
    this.activeIds = this.panels.map((p) => 'panel-' + p);
  }

  getDoctorNameList(): void {
    this.doctorService.get().subscribe((data) => {
        this.doctorNameList = data.body;
      },(err) => {
        console.error(err);
      }
    );
  }
  // getDepartmentList(): void {
  //   this.departmentService.get().subscribe((data) => {
  //       this.departmentList = data.body;
  //     },(err) => {
  //       console.error(err);
  //     }
  //   );
  // }
  // getGroupList(): void {
  //   this.groupService.get().subscribe((data) => {
  //       this.groupNameList = data.body;
  //     },(err) => {
  //       console.error(err);
  //     }
  //   );
  // }

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
        setTimeout(() => 
          (this.showContent = true),
          (this.table_data = data.body),
          100);
      },(err) => {
        console.error(err);
      }
    );
  }
  onEdit(row): void {
    this.form = {};
    window.scrollTo(0, 0);
    this.form = row;
    this.form.doctorName = Number(row.doctorName);
    // this.form.department = Number(row.department);
    // this.form.groupName = Number(row.groupName);
    this.isEdit = true;
    this.openAll();
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
        this.service.delete(id).subscribe((data) => {
            if (data.status == 200) {
              Swal.fire({
                title: 'Deleted!',
                text: 'Data Deleted Success',
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300
              }).then((result) => {
                if (result.isConfirmed) {
                  this.onReload();
                }
              });
            }
          },(err) => {
            console.error(err);
          }
        );
      }
    });
  }

  onReload(): void {
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
    this.service.save(this.form).subscribe((data) => {
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
              this.onReload();
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
      },(err) => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }

  onUpdate(): void {
    this.isSubmit = true;
    this.service.update(this.form).subscribe((data) => {
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
              this.onReload();
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
      },(err) => {
        this.isSubmit = false;
        console.error(err);
      }
    );
  }
}
