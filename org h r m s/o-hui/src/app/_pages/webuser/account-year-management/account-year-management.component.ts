import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { AuthService } from '../../../_services/auth.service'
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AdminAccountyearService } from 'src/app/_services/admin_service/accountyear.service';

@Component({
  selector: 'app-account-year-management',
  templateUrl: './account-year-management.component.html',
  styleUrls: ['./account-year-management.component.css']
})
export class AccountYearManagementComponent implements OnInit {

  constructor(
    private authService: AuthService,
    private yearService :AdminAccountyearService,
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
  ngOnInit(): void {

    this.yearService.get().subscribe(
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
    this.yearService.setDefault(this.form.acYear).subscribe(
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

}