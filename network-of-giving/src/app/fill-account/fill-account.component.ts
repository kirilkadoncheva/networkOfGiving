import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { TokenStorageService } from '../authentication/token-storage.service';
import { SelectControlValueAccessor } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-fill-account',
  templateUrl: './fill-account.component.html',
  styleUrls: ['./fill-account.component.css']
})
export class FillAccountComponent implements OnInit {

  moneyToAdd : number;
  moneyInAccount : number;
  username : String;

  successModal : boolean = false;
  errorModal: boolean = false;

  constructor(private userService : UserService,
    private tokenStorageService : TokenStorageService,
    private router : Router) { }

  ngOnInit(): void {
    this.userService.getBalance(this.tokenStorageService.getUsername()).subscribe(
      response => this.loadBalance(response)
    );
  }

  loadBalance(response)
  {
    this.username=this.tokenStorageService.getUsername();
    this.moneyInAccount = response;
  }

  onSubmit()
  {
    let newBalance = this.moneyInAccount + this.moneyToAdd;
    console.log(newBalance);
    this.userService.updateBalance(this.username, newBalance).subscribe(
      response => this.success(response),
      error => this.error(error)
    );
  }

  success(response)
  {
    this.successModal = true;
  }

  error(error)
  {
    console.log(error);
    this.errorModal = true;
  }

  closeSuccess()
  {
    this.successModal = false;
    this.router.navigate(['charities']);
  }

  closeError()
  {
    this.errorModal = false;
    this.router.navigate(['charities']);
  }
}
