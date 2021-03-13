import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CharitiesService } from '../services/charities.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  sub : any;
  searchInput : string = '';
  allCharities : any[];
  searchResults : any[];
  
  constructor(private activatedRoute:ActivatedRoute,
    private charitiesService : CharitiesService) { }

  ngOnInit(): void {
    this.sub=this.activatedRoute.paramMap.subscribe(params => { 
      this.searchInput = params.get('input'); 
      console.log(this.searchInput);
      this.charitiesService.getCharities().subscribe(
        response =>this.handleSuccessfulResponse(response),
       );
      
    });
  }
  handleSuccessfulResponse(response){

    var input = this.searchInput.toLowerCase();
    this.allCharities=response;
    this.searchResults = this.allCharities.filter(function(charity){
          return charity.name.toLowerCase().includes(input);
    })
    
  }

  ngOnDestroy()
  {
    this.sub.unsubscribe();
  }

  emptyResultSet() : boolean
  {
    if(this.searchResults) return true;
    return false;
  }

}
