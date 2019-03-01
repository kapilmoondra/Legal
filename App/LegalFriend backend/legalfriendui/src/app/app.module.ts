import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { LeftpanelComponent } from './leftpanel/leftpanel.component';
import { RightpanelComponent } from './rightpanel/rightpanel.component';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { CorporateComponent } from './corporate/corporate.component';
import { LoginsignupComponent } from './loginsignup/loginsignup.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './signup/signup.component';
import { AdduserComponent } from './adduser/adduser.component';
import { UsermanagementComponent } from './usermanagement/usermanagement.component';

// import { BillinglistComponent } from './billinglist/billinglist.component';
// import { AgainstorganizationComponent } from './againstorganization/againstorganization.component';
// import { FororganizationComponent } from './fororganization/fororganization.component';
// import { DistrictComponent } from './district/district.component';
// import { StateComponent } from './state/state.component';
// import { CourtComponent } from './court/court.component';
// import { RecoursecodeComponent } from './recoursecode/recoursecode.component';
// import { StagecodeComponent } from './stagecode/stagecode.component';
// import { ComplianceComponent } from './compliance/compliance.component';
// import { BranchComponent } from './branch/branch.component';
// import { OrganizationComponent } from './organization/organization.component';
// import { BillingComponent } from './billing/billing.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    LeftpanelComponent,
    RightpanelComponent,
    CorporateComponent,
    LoginsignupComponent,
    HomeComponent,
    SignupComponent,
    AdduserComponent    
    // BillinglistComponent,
    // AgainstorganizationComponent,
    // FororganizationComponent,
    // DistrictComponent,
    // StateComponent,
    // CourtComponent,
    // RecoursecodeComponent,
    // StagecodeComponent,
    // ComplianceComponent,
    // BranchComponent,
    // OrganizationComponent,
    // BillingComponent        
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule,    
    ReactiveFormsModule,
    //UsermanagementComponent
    //MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
