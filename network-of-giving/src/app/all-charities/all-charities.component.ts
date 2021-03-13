import { Component, OnInit } from '@angular/core';
import { CharitiesService } from '../services/charities.service';
import { ImageService } from '../services/image.service';

@Component({
  selector: 'app-all-charities',
  templateUrl: './all-charities.component.html',
  styleUrls: ['./all-charities.component.css']
})
export class AllCharitiesComponent implements OnInit {

  
  charities: any[];
  
  constructor(private charitiesService: CharitiesService) { }

  ngOnInit(): void {
    this.charitiesService.getCharities().subscribe(
      response =>this.handleSuccessfulResponse(response),
     );

  }

  handleSuccessfulResponse(response)
{
  
    this.charities=response;
}





}
