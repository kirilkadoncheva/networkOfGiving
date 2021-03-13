import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CharitiesService } from '../services/charities.service';
import { TokenStorageService } from '../authentication/token-storage.service';
import { DonationsService } from '../services/donations.service';
import { ParticipationsService } from '../services/participations.service';
import { UserService } from '../services/user.service';
import { ImageService } from '../services/image.service';

export class Charity
{
    id : Number;
    owner_username : String;
    name : String;
    description : String;
    required_amount : Number;
    required_participants : Number;
    donated_amount : Number;
    participants : Number;
    completed : Boolean;
    pathToThumbnail : String;

    

    constructor(id : Number,
      owner_username : String,
      name : String,
      description : String,
      required_amount : Number,
      required_participants : Number,
      donated_amount : Number,
      participants : Number,
      completed : Boolean,
      pathToThumbnail : String)
    {
        this.id=id;
        this.owner_username=owner_username;
        this.name=name;
        this.description=description;
        this.required_amount=required_amount;
        this.required_participants=required_participants;
        this.donated_amount=donated_amount;
        this.participants=participants;
        this.completed=completed;
        this.pathToThumbnail=pathToThumbnail;

        this.completed = this.setCompleted();

    }

    public setCompleted() : boolean
    {
        if(this.required_amount>this.donated_amount) return false;
        if(this.required_participants>this.participants) return false;
        return true;
    }

   public getRemaining() : Number{
      return (this.required_amount.valueOf()-this.donated_amount.valueOf());
    }

    public setOwnerUsername(username : String) : void
    {
      this.owner_username = username;
    }
}

export class Donation{
  donorUsername: string;
  charityId: Number;
  amount: Number;
  placeDate: Date;

  constructor(username: string,
    charityId: Number,
    amount: Number,
    placeDate: Date)
  {
      this.donorUsername=username;
      this.charityId=charityId;
      this.amount=amount;
      this.placeDate=placeDate;
  }
}

export class Participation{
  volunteerUsername: string;
  charityId: Number;
  sign_up_date: Date;

  constructor(username: string,
    charityId: Number,
    signUpDate: Date)
  {
      this.volunteerUsername=username;
      this.charityId=charityId;
      this.sign_up_date=signUpDate;
  }
}

@Component({
  selector: 'app-charity-details',
  templateUrl: './charity-details.component.html',
  styleUrls: ['./charity-details.component.css']
})

export class CharityDetailsComponent implements OnInit {

  public charityUrl : String ;

  retrievedImage : any;
  retrieveResponse : any;
  base64Data : any;

  deleteButton : boolean = false;
  response : any = null;
  charity: Charity = new Charity(0,'','','',0,0,0,0,false,'');
  private id: String = '0';
  sub : any ;
  completedDonations: boolean = false;
  completedVolunteers: boolean = false;

  isVolunteerModalVisible : boolean = false;
  isDonationModalVisible: boolean = false;

  donation: Donation = new Donation('',0,0,new Date());
  participation: Participation = new Participation('',0,new Date());
  balance : Number = 0;

  okDonation: boolean = false;
  thankYouDonationModal: boolean = false;
  errorModal: boolean = false;

  fundsModal : boolean=false;
  thankYouVolunteerModal: boolean = false;
  isDeleteModalOpen : boolean = false;

  estimatedDonation : Number = 0;
  
  

  constructor(private activatedRoute:ActivatedRoute,
    private charitiesService: CharitiesService,
    public tokenStorageService: TokenStorageService,
    private donationsService : DonationsService,
    private participationsService : ParticipationsService,
    private userService : UserService,
    private router : Router,
    private imageService : ImageService) { }

  ngOnInit(): void {
    this.sub=this.activatedRoute.paramMap.subscribe(params => { 
      this.id = params.get('id'); 
      this.charitiesService.getCharity(this.id).subscribe(
        response => this.loadCharity(response)
       
       );
      

    
    });

     
    console.log(this.id);
    this.charitiesService.estimateDonation(this.id).subscribe(
      response => this.loadEstimation(response)
    );
    this.charityUrl = 'http://localhost:4200/charities/'+this.id;

      this.userService.getBalance(this.tokenStorageService.getUsername()).subscribe(
        response => this.loadBalance(response)
      );

      this.imageService.getImage(this.id).subscribe(
        res => {
          console.log(res);
                    this.retrieveResponse = res;
          
                    this.base64Data = this.retrieveResponse.picByte;
        
                    this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
         
                  },
        error => {
          console.log(error);
        }
      );
  }

  loadEstimation(response: Number) {
    console.log(response);
    this.estimatedDonation = response;
    this.donation.amount = this.estimatedDonation;
  }

  checkCharityOwner()
  {
    let username = this.tokenStorageService.getUsername();
    if(username===this.charity.owner_username) this.deleteButton = true;
    console.log('username '+username);
    console.log(this.charity.owner_username);
  }

  ngOnDestroy()
  {
    this.sub.unsubscribe();
  }

  loadBalance(response)
  {
    if(response)
    {
      
      this.balance = response;
    }
  }
  

  loadCharity(response)
  {
    if(response)
    {
      console.log(response);
      this.response=response;
      this.charity = new Charity(this.response.id, this.response.owner_username, this.response.name, this.response.description,
        this.response.required_amount,this.response.required_participants, this.response.donated_amount,  this.response.participants,
        this.response.completed, this.response.pathToThumbnail);
        console.log(this.charity);
      if(!(this.charity.required_participants>this.charity.participants)) this.completedVolunteers=true;
      if(!(this.charity.required_amount>this.charity.donated_amount)) this.completedDonations=true;
      this.checkCharityOwner();

    }
    // else
    // {

    // }
    
  }

  checkBalance() : boolean
  {
    
     console.log(this.balance);
    if(this.balance<this.donation.amount) return false;
    return true;
  }
  makeDonation()
  {

      this.isDonationModalVisible=true;
      this.donation.charityId=this.charity.id;
      this.donation.donorUsername = this.tokenStorageService.getUsername();
      this.donation.placeDate=new Date();
  
  }

  cancelDonation()
  {
    this.donation.amount=null;
    this.isDonationModalVisible=false;
  }

  finalizeDonation()
  {
    if(this.checkBalance()){
    console.log(this.donation);
    this.donationsService.makeDonation(this.donation).subscribe(response => this.handleSuccessfulDonation(response),
      error => this.handleErrorDonation(error));
    
    this.donation.amount=null;
    this.isDonationModalVisible=false;
    }
    else{
      this.donation.amount=null;
    this.isDonationModalVisible=false;
    this.fundsModal=true;
    }
    //window.location.reload();
  }  

  handleSuccessfulDonation(response)
  {
    this.okDonation=response; 
    this.thankYouDonationModal = true;
  }

  handleErrorDonation(error)
  {
    this.errorModal=true;

  }

  closeThankYouModal()
  {
    this.thankYouDonationModal=false;
    this.thankYouVolunteerModal=false;
    window.location.reload();
  }

  volunteer()
  {

    this.isVolunteerModalVisible=true;
    this.participation.charityId=this.charity.id;
    this.participation.volunteerUsername = this.tokenStorageService.getUsername();
    this.participation.sign_up_date=new Date();
  }

  cancelVolunteering()
  {
    this.isVolunteerModalVisible=false;

  }

  finalizeVolunteering()
  {
    console.log(this.participation);
    this.participationsService.participate(this.participation).subscribe(response => this.handleSuccessfulParticipation(response),
      error => this.handleErrorParticipation(error));
    
    
    this.isVolunteerModalVisible=false;
  }
  handleErrorParticipation(error: any): void {
    this.errorModal=true;
  }

  handleSuccessfulParticipation(response: boolean): void {
    if(response==false) this.handleErrorParticipation(response);
    else{
      this.thankYouVolunteerModal = true;
    }
  }

  deleteCharity()
  {
    this.charitiesService.deleteCharity(this.charity.id.toString()).subscribe(response => this.handleSuccessfulDeletion(response),
    error => this.handleErrorDeletion(error));
  }

  handleSuccessfulDeletion(response)
  {
      // this.isDeleteModalOpen=false;
      this.router.navigate(['charities']);
  }

  handleErrorDeletion(error)
  {
    this.errorModal=true;
  }



  
}
