import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BedmasterService } from 'src/app/_service/webhims/static/bedmaster.service';

@Component({
  selector: 'app-bed-status',
  templateUrl: './bed-status.component.html',
  styleUrls: ['./bed-status.component.css']
})
export class BedStatusComponent implements OnInit {

  constructor(private bedMasterService: BedmasterService,
    private titleService: Title) { }

  form: any = {};
  isSubmit = false;

  bedStatus: any = [];
  availableBedStatus: any = [];
  occupiedBedStatus: any = [];
  allBedStatus: any = [];

  totalBed = '0';
  availableBed = '0';
  occupideBed = '0';

  directCount = '0';
  companyCount = '0';
  insuranceCount = '0';

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Bed Status");
    this.getBedList();
  }

  getBedList(): void {
    this.bedMasterService.bedStatus().subscribe(
      data => {

        this.bedStatus = data.body.bedList;
        this.allBedStatus = data.body.bedList;

        this.totalBed = data.body.totalBeds;
        this.occupideBed = data.body.occupiedBeds;
        this.availableBed = data.body.availableBeds;

        if (data.body.statusWiseCount != null){

          if ('Direct' in data.body.statusWiseCount){
            this.directCount = data.body.statusWiseCount.Direct;
          }
          if ('Company' in data.body.statusWiseCount){
            this.companyCount = data.body.statusWiseCount.Company;
          }
          if ('Insurance' in data.body.statusWiseCount){
            this.insuranceCount = data.body.statusWiseCount.Insurance;
          }
        }
      },
      err => {
        console.error(err);
      }
    );
  }

  filterAvailableBed(): void {
    this.bedStatus =  this.allBedStatus.filter(item => !item.occupied)
  }

  filterOccupiedBed(): void {
    this.bedStatus =  this.allBedStatus.filter(item => item.occupied)
  }
  
  filterRemove(): void {
    this.bedStatus = this.allBedStatus;
  }

  filterBed(name): void {
    if(name){
      this.bedStatus =  this.allBedStatus.filter(item => item.bedNumber.toUpperCase().startsWith(name.toUpperCase()))
    }else{
      this.filterRemove();
    }
  }

}
