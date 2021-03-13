import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  

  constructor(private httpClient:HttpClient) { }

  getBalance( username: String)
  {
    
    return this.httpClient.get<Number>('http://localhost:8080/users/balance/'+username);
  }

  getName( username: String)
  {
    return this.httpClient.get<String>('http://localhost:8080/users/name/'+username);
  }

  updateBalance(username : String, balance : Number)
  {
    return this.httpClient.put('http://localhost:8080/users/balance/'+username,{},{ params: { newBalance : balance.toString() }});
  }

  getUserInfo(username : String)
  {
    return this.httpClient.get<any>('http://localhost:8080/users/'+username);
  }
}
