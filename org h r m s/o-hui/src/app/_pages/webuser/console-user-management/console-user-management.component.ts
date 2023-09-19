import { Component, OnInit } from '@angular/core';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
// import { AuthService } from 'src/app/_services/auth.service';
import { UserService } from 'src/app/_services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-console-user-management',
  templateUrl: './console-user-management.component.html',
  styleUrls: ['./console-user-management.component.css']
})
export class ConsoleUserManagementComponent implements OnInit {

  constructor(
    // private authService: AuthService,
    private userService :UserService,
    private componentReloadService: ComponentReloadService
  ) { }
  
  form: any = {};
  isSubmit = false

  ngOnInit(): void {
    this.userService.getConsoleUser().subscribe(
      data => {
        this.form.username = data.body.username
      });
  }

  onSubmit(): void {
    this.isSubmit = true
    this.form.loginType = this.form.username
    this.userService.consoleLoginUpdate(this.form).subscribe(
      data => {
        this.isSubmit = false
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
        console.log(err);
        this.isSubmit = false
      }
    )
  }

}
