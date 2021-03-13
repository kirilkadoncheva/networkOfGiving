import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SignUpInfo } from './signup-info';
import { AuthLoginInfo } from './login-info';
import { JwtResponse } from './jwt-response';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private loginUrl = 'http://localhost:8080/authenticate';
  private signupUrl = 'http://localhost:8080/register';

  constructor(private http: HttpClient) { }

  signUp(info: SignUpInfo): Observable<String>{
    return this.http.post<String>(this.signupUrl,info,httpOptions);
  }

  authenticate(credentials: AuthLoginInfo): Observable<JwtResponse>
  {
    return this.http.post<JwtResponse>(this.loginUrl,credentials,httpOptions);
  }
}
