import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../authentication/token-storage.service'
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isModalVisible=false;
  name: String = '';

  constructor(public tokenStorageService: TokenStorageService,
    private userService : UserService) { }

  ngOnInit(): void {
    
  }

  

  logOut(){
    this.tokenStorageService.logOut();
    window.location.reload();

  }

  cancel(){
    this.isModalVisible=false;
   
  }

  getUsername()
  {
    return this.tokenStorageService.getUsername();
  }

}
