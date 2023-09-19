import { Component, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { BedmasterService } from 'src/app/_service/webhims/static/bedmaster.service';

@Component({
  selector: 'app-vacant-bed',
  templateUrl: './vacant-bed.component.html',
  styleUrls: ['./vacant-bed.component.css']
})
export class VacantBedComponent implements OnInit {

  constructor(private bedmasterService: BedmasterService,
    private titleService: Title) { }

  form: any = {};
  isSubmit = false;
  bedStatus: any = [];
  bedDetails: Array<any> = [];
  showContent = false;

  ngOnInit(): void {
    this.titleService.setTitle("OHIMS | Vacent Bed Status");
    this.bedmasterService.getBedList().subscribe(data => {
      setTimeout(() =>
        this.showContent = true,
        this.bedDetails = data.body
        , 100);
    });
  }

  onSubmit(): void {
    this.bedmasterService.vacantBedStatus().subscribe(res => {
      const fileURL = URL.createObjectURL(res);
      window.open(fileURL, '_blank');
    });
  }
}
