import { Component, OnInit } from '@angular/core';
import { CharitiesService } from '../services/charities.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {

  public searchInput: String='';
  public searchResult: Array<any>;
 
  constructor(private router : Router) { }

  ngOnInit(): void {
    
    
  }

  
  
  

  
  click()
  {
    console.log(this.searchInput);
  
  }

}
