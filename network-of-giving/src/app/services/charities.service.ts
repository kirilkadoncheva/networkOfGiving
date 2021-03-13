import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { InterceptorSkipHeader } from '../authentication/auth-interceptor';
import { CharityToSave } from '../create-charity/create-charity.component';

export class Charity{
  constructor(
    private id : number,
    private owner_username: String,
    private name : String,
    private description : String,
    private required_amount : number,
    private required_participants : number,
    private donated_amount : number,
    private participants : number,
    private completed : boolean = false,
    private pathToThumbnail : String
  ) {}
}

const headers = new HttpHeaders().set(InterceptorSkipHeader, '');

@Injectable({
  providedIn: 'root'
})
export class CharitiesService {

  constructor(
    private httpClient:HttpClient
  ) { }

  public getCharities()
  {
    return this.httpClient.get<Charity[]>('http://localhost:8080/charities/all',{headers});
  }

  public getCharity(id: String)
  {
    return this.httpClient.get<Charity[]>('http://localhost:8080/charities/'+id,{headers});
  }

  public deleteCharity(id : String)
  {
    return this.httpClient.delete('http://localhost:8080/charities/'+id);
  }

  public addCharity(charity : CharityToSave)
  {
    return this.httpClient.post<Number>('http://localhost:8080/charities/',charity)
  }

  public estimateDonation(id : String)
  {
    return this.httpClient.get<Number>('http://localhost:8080/charities/estimate_donation/'+id);
  }

  public getCharitiesByOwner(username : String)
  {
    return this.httpClient.get<Charity[]>('http://localhost:8080/charities/owner/'+username);
  }

  public getParticipatedCharitiesByUser(username : String)
  {
    return this.httpClient.get<Charity[]>('http://localhost:8080/charities/participated/'+username);
  }
}
