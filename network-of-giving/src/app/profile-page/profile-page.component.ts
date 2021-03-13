import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { Charity } from '../charity-details/charity-details.component';
import { CharitiesService } from '../services/charities.service';
import { DonationsService } from '../services/donations.service';

@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {

    userInfo : any;
    username: String;
    sub : any;

    createdCharities : Charity[];
    participatedCharities : Charity[];
    donations : any[];
    countCreatedCharities : number;
    countParticipatedCharities : number;

  constructor(private activatedRoute:ActivatedRoute,
    private userService: UserService,
    private charitiesService: CharitiesService,
    private donationsService : DonationsService) { }

  ngOnInit(): void {
    this.sub=this.activatedRoute.paramMap.subscribe(params => { 
      this.username = params.get('username');} 

     
       
       );

       this.userService.getUserInfo(this.username).subscribe(
        response => this.loadUserInfo(response));

      this.charitiesService.getCharitiesByOwner(this.username).subscribe(
        response => this.loadCharities(response)
      );

      this.charitiesService.getParticipatedCharitiesByUser(this.username).subscribe(
        response => this.loadParticipatedCharities(response)
      );

      this.donationsService.getAllDonationsByUser(this.username).subscribe(
        response => this.loadDonations(response)
      );



  }
  loadUserInfo(response: any) {
    this.userInfo = response;
    
  }

  loadCharities(response)
  {
    
    this.createdCharities = response;
    this.countCreatedCharities = this.createdCharities.length;
  }

  loadParticipatedCharities(response)
  {
    console.log(response);
    this.participatedCharities = response;
    this.countParticipatedCharities = this.participatedCharities.length;
  }

  loadDonations(response)
  {
    console.log(response);
    this.donations = response;
   
  }
} 
 

