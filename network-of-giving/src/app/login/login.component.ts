import { Component, OnInit } from '@angular/core';
import {AuthLoginInfo} from '../authentication/login-info'
import { AuthenticationService } from '../authentication/authentication.service';
import { TokenStorageService } from '../authentication/token-storage.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  
  private loginInfo: AuthLoginInfo;

  constructor(private router: Router,private authService: AuthenticationService, private tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
    if (this.tokenStorageService.getToken()) {
      this.isLoggedIn = true;
     
    }
  }

  onSubmit() {
    console.log(this.form);
 
    this.loginInfo = new AuthLoginInfo(
      this.form.username,
      this.form.password);
 
    this.authService.authenticate(this.loginInfo).subscribe(
      data => {
        this.tokenStorageService.saveToken(data.token);
        this.tokenStorageService.saveUsername(this.form.username);
        console.log(data.token);
 
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.reloadPage();
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }
 
  reloadPage() {
    this.router.navigate(['charities']);
  }

}
