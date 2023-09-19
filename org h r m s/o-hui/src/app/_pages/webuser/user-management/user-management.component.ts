import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { AuthService } from '../../../_services/auth.service'
import { UserService } from 'src/app/_services/user.service';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private userService :UserService,
    private modalService: NgbModal,
    private componentReloadService: ComponentReloadService
  ) { }


  form: any = {};
  isSubmit = false;
  content: string;
  table_data = [];
  showContent = false
  loginTypeList = ["HIMS", "IPD", "PATHO"];

  
  // editUser :userInfo=new userInfo();
  editMaster: UserDetails = new UserDetails();
  ngOnInit(): void {

    this.userService.getAllUser().subscribe(
      data => {
        // this.table_data = data.body;
        setTimeout(() => 
          (this.showContent = true),
          (this.table_data = data.body),
          100
        );
      },
      err => {
        console.log(err)
      }
    );

  }
  onSubmit(): void {
    this.isSubmit = true;
    this.authService.register(this.form).subscribe(
      data => {
        this.isSubmit = false;
        if (data.code == 200) {
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
        } 
        else {
          Swal.fire({
            title: 'Error!',
            text: data.message,
            icon: 'error',
            confirmButtonText: 'OK',
            width: 300
          })
        }
      },
      err => {
        this.isSubmit = false;
        console.log(err);
      }
    )
  }


  
  onDelete(id): void {
    Swal.fire({
      title: 'Are you sure?',
      text: "Do you want this!",
      icon: 'warning',
      width: 300,
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Delete it!'
    }).then((result) => {
      this.userService.deleteUser(id).subscribe(
        data => {
          if (result.isConfirmed) {
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
          console.log(err)
        }
      );
    })
  }


  
  onUpdatePass(data: any): void {
    this.isSubmit = true;
    this.userService.updatePass(this.editMaster).subscribe(
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
          Swal.fire({
            title: 'Error!',
            text: data.message,
            icon: 'error',
            confirmButtonText: 'OK',
            width: 300
          })
        }
      },
      err => {
        this.isSubmit = false;
        console.log(err);
      }
    )
  }



  openModal(targetModal, rowData) {
    this.editMaster = rowData;
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static'
    });
  }

  openPasswardModal(targetModal, rowData) {
    this.editMaster = rowData;
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static'
    });
  }



}


export class UserDetails {
  id: string;
  username: string;
  password: string;
  loginType: string;
  role: string;
}