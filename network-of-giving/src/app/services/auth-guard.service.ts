import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { TokenStorageService } from '../authentication/token-storage.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {
  
 
  routeURL: string;

  constructor(private tokenService: TokenStorageService, private router: Router) {
    
    this.routeURL = this.router.url;
  }

  
  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // here we check if user is logged in or not
    // the authService returs user object, or
    // it returns undefined/null when user is not logged in
    
    // SINCE OUR 'authService.user' IS OF TYPE 'Observable'
    // WE MUST USE 'subscribe' TO GET VALUE OF 'user'
    if(this.tokenService.isUserLoggedIn())
    {
      this.routeURL = this.router.url;
      // just return true - if user is logged in
      return true;
    }
    else{
      this.router.navigate(['/login'], {
        queryParams: {
          return: state.url
        }});
    }
    
  }
}
