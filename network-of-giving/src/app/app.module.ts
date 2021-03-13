import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClarityModule, ClrModalModule } from '@clr/angular';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
import { AllCharitiesComponent } from './all-charities/all-charities.component';
import { AuthInterceptor } from './authentication/auth-interceptor';

import { SearchBarComponent } from './search-bar/search-bar.component';
import { CharityDetailsComponent } from './charity-details/charity-details.component';
import { CreateCharityComponent } from './create-charity/create-charity.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { FillAccountComponent } from './fill-account/fill-account.component';
import { FooterComponent } from './footer/footer.component';
import { ProfilePageComponent } from './profile-page/profile-page.component';



@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    AllCharitiesComponent,
    SearchBarComponent,
    CharityDetailsComponent,
    CreateCharityComponent,
    SearchResultsComponent,
    FillAccountComponent,
    FooterComponent,
    ProfilePageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ClarityModule,
    BrowserAnimationsModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [ { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
