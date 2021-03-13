import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Participation } from '../charity-details/charity-details.component';

@Injectable({
  providedIn: 'root'
})
export class ParticipationsService {

  constructor(private httpClient:HttpClient) { }

  participate(participation: Participation)
  {
    
    return this.httpClient.post<boolean>('http://localhost:8080/participations',participation);
  }
}
