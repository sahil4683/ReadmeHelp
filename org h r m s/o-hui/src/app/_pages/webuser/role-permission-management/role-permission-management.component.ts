import { Component, OnInit } from '@angular/core';
import { ComponentReloadService } from 'src/app/_helpers/component-reload.service';
import { UserService } from 'src/app/_services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-role-permission-management',
  templateUrl: './role-permission-management.component.html',
  styleUrls: ['./role-permission-management.component.css']
})
export class RolePermissionManagementComponent implements OnInit {

  constructor(
    private userService: UserService,
    private componentReloadService: ComponentReloadService
  ) { }

  loginTypeList = ["HIMS", "IPD", "PATHO"];

  form: any = {};
  temp: any = {};
  UserList: any = [];
  privileges: any = [];
  loginType: string;
  isSubmit = false




  ngOnInit(): void {

    //-----> Use For All Select In Table <--------
    // <button type="button" class="btn btn-default btn-sm ml-2 checkbox-toggle"><i class="far fa-square"></i>
    // </button>
    // $('button .checkbox-toggle').on("click", function () {
    //   var clicks = $(this).data('clicks')
    //   var cb = $(this), th = cb.parent(), col = th.index() + 1, tbody = cb.parent().parent().parent().parent().find("tbody");
    //   if (clicks) {
    //     $(tbody).find("td:nth-child(" + col + ") input").prop("checked", false);
    //     // $(tbody).find("td:nth-child(" + col + ") input").attr("ng-reflect-model", "false");
    //     $(tbody).find("td:nth-child(" + col + ") .checkbox-toggle .far.fa-check-square").removeClass('fa-check-square').addClass('fa-square');
    //   } else {
    //     $(tbody).find("td:nth-child(" + col + ") input").prop("checked", true);
    //     // $(tbody).find("td:nth-child(" + col + ") input").attr("ng-reflect-model", "true");
    //     $(tbody).find("td:nth-child(" + col + ") .checkbox-toggle .far.fa-check-square").removeClass('fa-square').addClass('fa-check-square')
    //   }
    //   $(this).data('clicks', !clicks)
    // });

    // this.form.privileges = this.privileges
    this.getUsers();

    // this.privileges = this.access_list;
    // this.form.privileges = this.privileges;
  }

  getUsers(): void {
    this.temp.userName = null;
    this.userService.getAllUser().subscribe(data => {
      this.UserList = data.body;
    }, err => {
      console.error(err);
    });
  }

  // getUserList(type): void {
  //   this.temp.userName = null
  //   this.userService.getUsersByType(type).subscribe(data => {
  //     this.UserList = data.body;
  //   }, err => {
  //     console.error(err);
  //   });
  // }


  getAccess(user, deptType): void {
    this.loginType = user.type
    console.log(user);
    this.userService.getAccess(user.id).subscribe(data => {

      if (Array.isArray(data.body) && !data.body.length) {
        this.privileges = this.access_list;
        this.form.privileges = this.privileges;
      } else {
        this.privileges = data.body
        this.form.privileges = this.privileges;
      }
      this.form.user = this.temp.userName.id
      if (deptType == "HIMS") { this.loginType = "HIMS" }
      if (deptType == "IPD") { this.loginType = "IPD" }
      if (deptType == "PATHO") { this.loginType = "PATHO" }

      // if (data.body == null || data.body.length === 0) {
      // alert("Role Not Assigned !")
      // this.form.user = this.temp.userName.id
      // if(deptType=="HIMS"){ this.loginType = "HIMS" }
      // if(deptType=="IPD"){ this.loginType = "IPD" }
      // if(deptType=="PATHO"){ this.loginType = "PATHO" }
      // this.privileges = this.access_list;
      // this.form.privileges = this.privileges;
      // this.form.privileges = this.privileges

      // if(this.loginType=="HIMS"){ this.privileges = this.access_list_hims }
      // if(this.loginType=="IPD"){ this.privileges = this.access_list_ipd }
      // if(this.loginType=="PATHO"){ this.privileges = this.access_list_patho }
      // this.form.privileges = this.privileges
      //   this.form.user = this.temp.userName.id
      // // } else {
      // this.privileges = data.body
      // this.form.privileges = this.privileges;
      //   this.form.user = this.temp.userName.id
      // }
      // this.privileges = this.access_list;
      // this.form.privileges = this.privileges;

    }, err => {
      console.error(err);
    });
  }

  resetAll(): void {
  this.privileges = []
  this.form.privileges = []
  }

  looper(i: number) {
    return new Array(i);
  }

  onSave() {
    this.isSubmit = true
    if (this.form.privileges == undefined || this.form.user == undefined) {
      Swal.fire({
        title: 'Error!',
        html: '<pre> User Not Selected !</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      Swal.fire({
        title: 'Are you sure?',
        text: 'Do you want assign this role to user!',
        icon: 'warning',
        width: 300,
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes'
      }).then((result) => {
        if (result.isConfirmed) {
          this.userService.updateAccess(this.form).subscribe(data => {
            if (data.status == 200) {

              Swal.fire({
                title: 'User Updated!',
                text: data.message,
                icon: 'success',
                confirmButtonText: 'OK',
                width: 300
              }).then((result) => {
                if (result.isConfirmed) {
                  this.componentReloadService.reload();
                }
              });

              // Swal.fire({
              //   title: 'Updated!',
              //   text: 'User Updated Success',
              //   icon: 'success',
              //   confirmButtonText: 'OK',
              //   width: 300,
              //   
              // }).then((result) => {
              //   if (result.isConfirmed) {
              //     window.location.reload();
              //   }
              // });

            }
            this.isSubmit = false
          },
            err => {
              console.error(err);
              this.isSubmit = false
            }
          );
        }
      });
    }
  }





  selectAll(type): void {

    if (this.form.privileges == undefined) {
      Swal.fire({
        title: 'Error!',
        html: '<pre> User Not Selected !</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      if (this.form.privileges != undefined) {
        for (let item of this.form.privileges) {
          if (type === "HIMS" && item.menu_var.startsWith('hm_')) {
            item.menu_action = true;
            if (item.edit_action != undefined) { item.edit_action = true; }
            if (item.delete_action != undefined) { item.delete_action = true; }
          }
          if (type === "IPD" && item.menu_var.startsWith('im_')) {
            item.menu_action = true;
            if (item.edit_action != undefined) { item.edit_action = true; }
            if (item.delete_action != undefined) { item.delete_action = true; }
          }
          if (type === "PATHO" && item.menu_var.startsWith('pm_')) {
            item.menu_action = true;
            if (item.edit_action != undefined) { item.edit_action = true; }
            if (item.delete_action != undefined) { item.delete_action = true; }
          }
        }
      } else {
        for (let item of this.privileges) {
          item.menu_action = true;
          if (item.edit_action != undefined) { item.edit_action = true; }
          if (item.delete_action != undefined) { item.delete_action = true; }
        }
      }
    }
  }

  removeAll(type): void {
    if (this.form.privileges == undefined) {
      Swal.fire({
        title: 'Error!',
        html: '<pre> User Not Selected !</pre>',
        icon: 'error',
        confirmButtonText: 'OK',
        width: 350
      });
    } else {
      if (this.form.privileges != undefined) {
        for (let item of this.form.privileges) {
          if (type === "HIMS" && item.menu_var.startsWith('hm_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
          if (type === "IPD" && item.menu_var.startsWith('im_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
          if (type === "PATHO" && item.menu_var.startsWith('pm_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
        }
      } else {
        for (let item of this.privileges) {
          if (type === "HIMS" && item.menu_var.startsWith('hm_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
          if (type === "IPD" && item.menu_var.startsWith('im_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
          if (type === "PATHO" && item.menu_var.startsWith('pm_')) {
            item.menu_action = false;
            if (item.edit_action != undefined) { item.edit_action = false; }
            if (item.delete_action != undefined) { item.delete_action = false; }
          }
        }
      }
    }
  }





  public access_list = [
    // menu level 3
    {
      menu_name: "Home",
      menu_var: "hm_0",
      menu_action: false,
      i: 0
    },

    // menu level 1 
    {
      menu_name: "Reception",
      menu_var: "hm_1",
      menu_action: false,
      i: 1
    },

    // menu level 2
    {
      menu_name: "Reception",
      menu_var: "hm_1_1",
      menu_action: false,
      i: 3
    },
    // menu level 3  
    {
      menu_name: "Registration",
      menu_var: "hm_1_1_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 4
    },
    {
      menu_name: "Admission",
      menu_var: "hm_1_1_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 5
    },

    // menu level 2
    {
      menu_name: "Bill",
      menu_var: "hm_1_2",
      menu_action: false,
      i: 6
    },
    // menu level 3
    {
      menu_name: "OPD",
      menu_var: "hm_1_2_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 7
    },
    {
      menu_name: "RADIO",
      menu_var: "hm_1_2_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 8
    },
    {
      menu_name: "LAB",
      menu_var: "hm_1_2_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 9
    },
    {
      menu_name: "OPDHEALTH CHECKUP",
      menu_var: "hm_1_2_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 10
    },
    {
      menu_name: "CASH DUES",
      menu_var: "hm_1_2_5",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 11
    },

    // menu level 2
    {
      menu_name: "Bill Refund",
      menu_var: "hm_1_3",
      menu_action: false,
      i: 12
    },
    // menu level 3
    {
      menu_name: "OPD Refund",
      menu_var: "hm_1_3_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 13
    },
    {
      menu_name: "RADIO Refund",
      menu_var: "hm_1_3_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 14
    },
    {
      menu_name: "LAB Refund",
      menu_var: "hm_1_3_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 15
    },
    {
      menu_name: "OPDHEALTHCHECKUP Refund",
      menu_var: "hm_1_3_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 16
    },


    // menu level 1 
    {
      menu_name: "IPD",
      menu_var: "hm_2",
      menu_action: false,
      i: 17
    },

    // menu level 2
    {
      menu_name: "Bill",
      menu_var: "hm_2_1",
      menu_action: false,
      i: 18
    },
    // menu level 3  
    {
      menu_name: "Bill Detail Sheet",
      menu_var: "hm_2_1_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 19
    },
    {
      menu_name: "Provisional Bill",
      menu_var: "hm_2_1_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 20
    },
    {
      menu_name: "Final IPD Bill",
      menu_var: "hm_2_1_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 21
    },
    {
      menu_name: "IPD Advanced",
      menu_var: "hm_2_1_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 22
    },
    {
      menu_name: "Payment/Refund",
      menu_var: "hm_2_1_5",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 23
    },


    // menu level 2
    {
      menu_name: "Bed",
      menu_var: "hm_2_2",
      menu_action: false,
      i: 24
    },
    // menu level 3  
    {
      menu_name: "Bed Shifting",
      menu_var: "hm_2_2_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 25
    },
    {
      menu_name: "Bed Status",
      menu_var: "hm_2_2_2",
      menu_action: false,
      i: 26
    },
    {
      menu_name: "Vacant Bed",
      menu_var: "hm_2_2_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 27
    },


    // menu level 2
    {
      menu_name: "Discharge",
      menu_var: "hm_2_3",
      menu_action: false,
      i: 28
    },
    // menu level 3  
    {
      menu_name: "Bed Discharge",
      menu_var: "hm_2_3_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 29
    },
    {
      menu_name: "Discharge Clearance",
      menu_var: "hm_2_3_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 30
    },



    // menu level 1 
    {
      menu_name: "Account",
      menu_var: "hm_3",
      menu_action: false,
      i: 31
    },

    // menu level 2
    {
      menu_name: "Entry",
      menu_var: "hm_3_1",
      menu_action: false,
      i: 32
    },
    // menu level 3  
    {
      menu_name: "Receipt",
      menu_var: "hm_3_1_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 33
    },
    {
      menu_name: "Payment",
      menu_var: "hm_3_1_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 34
    },


    // menu level 1 
    {
      menu_name: "Reports",
      menu_var: "hm_4",
      menu_action: false,
      i: 35
    },

    // menu level 2
    {
      menu_name: "Register Reports",
      menu_var: "hm_4_1",
      menu_action: false,
      i: 36
    },
    // menu level 3  
    {
      menu_name: "IPD Register",
      menu_var: "hm_4_1_1",
      menu_action: false,
      i: 37
    },
    {
      menu_name: "IPD Receipt Register",
      menu_var: "hm_4_1_2",
      menu_action: false,
      i: 38
    },
    {
      menu_name: "OPD Register",
      menu_var: "hm_4_1_3",
      menu_action: false,
      i: 39
    },
    {
      menu_name: "LAB Register",
      menu_var: "hm_4_1_4",
      menu_action: false,
      i: 40
    },
    {
      menu_name: "RADIO Register",
      menu_var: "hm_4_1_5",
      menu_action: false,
      i: 41
    },
    {
      menu_name: "Health Checkup Register",
      menu_var: "hm_4_1_6",
      menu_action: false,
      i: 42
    },
    {
      menu_name: "Cash Due Report",
      menu_var: "hm_4_1_7",
      menu_action: false,
      i: 43
    },
    {
      menu_name: "Organization Report",
      menu_var: "hm_4_1_8",
      menu_action: false,
      i: 44
    },

    // menu level 2
    {
      menu_name: "Analysis Reports",
      menu_var: "hm_4_2",
      menu_action: false,
      i: 45
    },
    // menu level 3  
    {
      menu_name: "MIS",
      menu_var: "hm_4_2_1",
      menu_action: false,
      i: 46
    },
    {
      menu_name: "Groupwise Testwise Analysis",
      menu_var: "hm_4_2_2",
      menu_action: false,
      i: 47
    },


    // menu level 2
    {
      menu_name: "Routine Reports",
      menu_var: "hm_4_3",
      menu_action: false,
      i: 48
    },
    // menu level 3  
    {
      menu_name: "Admission/Discharge",
      menu_var: "hm_4_3_1",
      menu_action: false,
      i: 49
    },
    {
      menu_name: "Fee Collection Report",
      menu_var: "hm_4_3_2",
      menu_action: false,
      i: 50
    },
    {
      menu_name: "Doctor Incentive Report",
      menu_var: "hm_4_3_3",
      menu_action: false,
      i: 51
    },



    // menu level 1 
    {
      menu_name: "Master",
      menu_var: "hm_5",
      menu_action: false,
      i: 52
    },

    // menu level 2
    {
      menu_name: "Test Master",
      menu_var: "hm_5_1",
      menu_action: false,
      i: 53
    },
    // menu level 3  
    {
      menu_name: "IPD",
      menu_var: "hm_5_1_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 54
    },
    {
      menu_name: "OPD",
      menu_var: "hm_5_1_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 55
    },

    // menu level 2
    {
      menu_name: "Doctor",
      menu_var: "hm_5_2",
      menu_action: false,
      i: 56
    },
    // menu level 3  
    {
      menu_name: "Consultant Doctor",
      menu_var: "hm_5_2_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 57
    },
    {
      menu_name: "Referring Doctor",
      menu_var: "hm_5_2_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 58
    },

    // menu level 2
    {
      menu_name: "Bill Master",
      menu_var: "hm_5_3",
      menu_action: false,
      i: 59
    },
    // menu level 3  
    {
      menu_name: "OPD Bill Master",
      menu_var: "hm_5_3_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 60
    },
    {
      menu_name: "LAB Bill Master",
      menu_var: "hm_5_3_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 61
    },
    {
      menu_name: "Radio Bill Master",
      menu_var: "hm_5_3_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 62
    },

    // menu level 2
    {
      menu_name: "Appointment Master",
      menu_var: "hm_5_4",
      menu_action: false,
      i: 63
    },
    // menu level 3  
    {
      menu_name: "Appointment Scheduler",
      menu_var: "hm_5_4_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 64
    },

    // menu level 2
    {
      menu_name: "Package Master",
      menu_var: "hm_5_5",
      menu_action: false,
      i: 65
    },
    // menu level 3  
    {
      menu_name: "IPD Package Master",
      menu_var: "hm_5_5_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 66
    },
    {
      menu_name: "OPD Package Master",
      menu_var: "hm_5_5_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 67
    },





    // menu level 2
    {
      menu_name: "Others Master 1",
      menu_var: "hm_5_6",
      menu_action: false,
      i: 68
    },
    // menu level 3  
    {
      menu_name: "Department",
      menu_var: "hm_5_6_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 69
    },
    {
      menu_name: "Sub Department",
      menu_var: "hm_5_6_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 70
    },
    {
      menu_name: "Concession",
      menu_var: "hm_5_6_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 71
    },
    {
      menu_name: "Super Group",
      menu_var: "hm_5_6_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 72
    },
    {
      menu_name: "Group",
      menu_var: "hm_5_6_5",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 73
    },
    {
      menu_name: "Sub Group",
      menu_var: "hm_5_6_6",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 74
    },
    {
      menu_name: "Sub Group 1",
      menu_var: "hm_5_6_7",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 75
    },
    {
      menu_name: "Sub Group 2",
      menu_var: "hm_5_6_8",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 76
    },
    {
      menu_name: "Doctor Incentive Master",
      menu_var: "hm_5_6_9",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 77
    },
    {
      menu_name: "Ref Doctor Incentive",
      menu_var: "hm_5_6_10",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 78
    },
    {
      menu_name: "Insurance Master",
      menu_var: "hm_5_6_11",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 79
    },
    {
      menu_name: "Cashless Authorized",
      menu_var: "hm_5_6_12",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 80
    },
    {
      menu_name: "Discharge Summary",
      menu_var: "hm_5_6_13",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 81
    },
    {
      menu_name: "Discharge Summary template Master",
      menu_var: "hm_5_6_14",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 82
    },
    {
      menu_name: "Doctor Hours/Absentee Entry",
      menu_var: "hm_5_6_15",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 83
    },
    {
      menu_name: "Death Summary",
      menu_var: "hm_5_6_16",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 84
    },
    {
      menu_name: "TDS Master",
      menu_var: "hm_5_6_17",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 85
    },
    {
      menu_name: "Casepaper Medicine Master",
      menu_var: "hm_5_6_18",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 86
    },


    // menu level 2
    {
      menu_name: "Others Master 2",
      menu_var: "hm_5_7",
      menu_action: false,
      i: 87
    },
    // menu level 3  
    {
      menu_name: "Organization",
      menu_var: "hm_5_7_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 88
    },
    {
      menu_name: "TPA Master",
      menu_var: "hm_5_7_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 89
    },
    {
      menu_name: "Class Master",
      menu_var: "hm_5_7_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 90
    },
    {
      menu_name: "Ward Master",
      menu_var: "hm_5_7_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 91
    },
    {
      menu_name: "Party Master",
      menu_var: "hm_5_7_5",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 92
    },
    {
      menu_name: "Plastic Money Master",
      menu_var: "hm_5_7_6",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 93
    },
    {
      menu_name: "Patient Type Master",
      menu_var: "hm_5_7_7",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 94
    },
    {
      menu_name: "Doctor Payable Entry",
      menu_var: "hm_5_7_8",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 95
    },
    {
      menu_name: "External Lab Master",
      menu_var: "hm_5_7_9",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 96
    },
    {
      menu_name: "External Radio Master",
      menu_var: "hm_5_7_10",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 97
    },
    {
      menu_name: "External Lab Test Master",
      menu_var: "hm_5_7_11",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 98
    },
    {
      menu_name: "External Radio Test Master",
      menu_var: "hm_5_7_12",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 99
    },





    {
      menu_name: "Home",
      menu_var: "im_0",
      menu_action: false,
      i: 0
    },

    // menu level 1 
    {
      menu_name: "IPD Reception",
      menu_var: "im_1",
      menu_action: false,
      i: 1
    },

    // menu level 2
    {
      menu_name: "IPD Dashboard",
      menu_var: "im_1_1",
      menu_action: false,
      i: 2
    },
    {
      menu_name: "Discharge Summary",
      menu_var: "im_1_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 3
    },
    {
      menu_name: "Death Summary",
      menu_var: "im_1_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 4
    },




    {
      menu_name: "Home",
      menu_var: "pm_0",
      menu_action: false,
      i: 0
    },

    // menu level 1 
    {
      menu_name: "LAB Reports",
      menu_var: "pm_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 1
    },


    // menu level 1 
    {
      menu_name: "Reports",
      menu_var: "pm_2",
      menu_action: false,
      i: 2
    },
    // menu level 2
    {
      menu_name: "External Lab Test Report",
      menu_var: "pm_2_1",
      menu_action: false,
      i: 3
    },
    {
      menu_name: "Lab Test Count Report",
      menu_var: "pm_2_2",
      menu_action: false,
      i: 4
    },


    // menu level 1 
    {
      menu_name: "Master",
      menu_var: "pm_3",
      menu_action: false,
      i: 5
    },
    // menu level 2
    {
      menu_name: "Group Master",
      menu_var: "pm_3_1",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 6
    },
    {
      menu_name: "Format Master",
      menu_var: "pm_3_2",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 7
    },
    {
      menu_name: "Observation Master",
      menu_var: "pm_3_3",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 8
    },
    {
      menu_name: "Format Group Master",
      menu_var: "pm_3_4",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 9
    },
    {
      menu_name: "External lab Master",
      menu_var: "pm_3_5",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 10
    },
    {
      menu_name: "External Lab Test Master",
      menu_var: "pm_3_6",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 11
    },
    {
      menu_name: "LIS Test Master",
      menu_var: "pm_3_7",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 12
    },
    {
      menu_name: "Machine Master",
      menu_var: "pm_3_8",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 13
    },
    {
      menu_name: "Sample Device Master",
      menu_var: "pm_3_9",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 14
    },
    {
      menu_name: "Template Master",
      menu_var: "pm_3_10",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 15
    },
    {
      menu_name: "Format Test Master",
      menu_var: "pm_3_11",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 16
    },
    {
      menu_name: "Status Master",
      menu_var: "pm_3_12",
      menu_action: false,
      edit_action: false,
      delete_action: false,
      i: 17
    },


    // menu level 1 
    {
      menu_name: "Utility",
      menu_var: "pm_4",
      menu_action: false,
      i: 18
    },
    // menu level 2
    {
      menu_name: "Footer Updateion",
      menu_var: "pm_4_1",
      menu_action: false,
      i: 19
    },
    {
      menu_name: "Utility Update Sample",
      menu_var: "pm_4_2",
      menu_action: false,
      i: 20
    },


  ];






}
