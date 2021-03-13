import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Donation } from '../charity-details/charity-details.component';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class DonationsService {

  constructor(private httpClient:HttpClient) { }

  makeDonation(donation : Donation)
  {
    
    return this.httpClient.post<boolean>('http://localhost:8080/donations',donation);
  }

  getAllDonationsByUser(username : String)
  {
    return this.httpClient.get<any>('http://localhost:8080/donations/by_username/' + username);
  }
}
