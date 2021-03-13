import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllCharitiesComponent } from './all-charities/all-charities.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

import { CharityDetailsComponent } from './charity-details/charity-details.component';
import { CreateCharityComponent } from './create-charity/create-charity.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { AuthGuardService } from './services/auth-guard.service';
import { FillAccountComponent } from './fill-account/fill-account.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';


const routes: Routes = [
  
{
    path: '',
    redirectTo: 'charities',
    pathMatch: 'full'
},
  {
    path: 'charities',
    component: AllCharitiesComponent
},
{
  path: 'add_funds',
  component: FillAccountComponent
},
{
  path: 'charities/create',
  component: CreateCharityComponent,
  canActivate:[AuthGuardService] 
},
{
  path: 'charities/:id',
  component: CharityDetailsComponent
},
{
  path: 'profiles/:username',
  component: ProfilePageComponent,
  canActivate:[AuthGuardService] 
},
{
  path: 'search_charities/:input',
  component: SearchResultsComponent
},
{
    path: 'login',
    component: LoginComponent
},

{
    path: 'signup',
    component: RegisterComponent
}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
