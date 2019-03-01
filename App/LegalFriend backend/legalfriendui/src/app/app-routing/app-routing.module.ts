import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent } from '../app.component';
import { CasesComponent } from '../cases/cases.component';
import { UsermanagementComponent } from '../usermanagement/usermanagement.component';
import { CityComponent } from '../city/city.component';
import { CompletedcasesComponent } from '../completedcases/completedcases.component';
import { DistrictComponent } from '../district/district.component';
import { StateComponent } from '../state/state.component';
import { CourtComponent } from '../court/court.component';
import { RecoursecodeComponent } from '../recoursecode/recoursecode.component';
import { StagecodeComponent } from '../stagecode/stagecode.component';
import { ComplianceComponent } from '../compliance/compliance.component';
import { BranchComponent } from '../branch/branch.component';
import { OrganizationComponent } from '../organization/organization.component';
import { BillingComponent } from '../billing/billing.component';
import { AgainstorganizationComponent } from '../againstorganization/againstorganization.component';
import { FororganizationComponent } from '../fororganization/fororganization.component';
import { BillinglistComponent } from '../billinglist/billinglist.component';
import { LoginComponent } from '../login/login.component';
import { CorporateComponent } from '../corporate/corporate.component';
import { HomeComponent } from '../home/home.component';
import { SignupComponent } from '../signup/signup.component';
import { AdduserComponent } from '../adduser/adduser.component';
import { CommonModule } from '@angular/common';
import { UserService } from '../services/users/user.service';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser/src/browser';

//import { CompletedCaseComponent } from '../completedcase/completedcase.component';
const routes: Routes = [
  { path: '', pathMatch: 'full', component: CorporateComponent },
 //{ path: 'usermanagement', component: UsermanagementComponent },
  { path: 'cases', component: CasesComponent },
  { path: 'city', component: CityComponent },
  { path: 'completedcases', component: CompletedcasesComponent },
  { path: 'district', component: DistrictComponent },
  { path: 'state', component: StateComponent },
  { path: 'court', component: CourtComponent },
  { path: 'recoursecode', component: RecoursecodeComponent },
  { path: 'stagecode', component: StagecodeComponent },
  { path: 'compliance', component: ComplianceComponent },
  { path: 'branch', component: BranchComponent },
  { path: 'organizaton', component: OrganizationComponent },
  { path: 'billing', component: BillingComponent },
  { path: 'againstorganization', component: AgainstorganizationComponent },
  { path: 'fororganization', component: FororganizationComponent },
  { path: 'billinglist', component: BillinglistComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },  
  // { path: 'login/signup', component: SignupComponent },
  // { path: 'signup/login', component: LoginComponent },
  
  { path: 'home', component: HomeComponent, children :[
    { path: '', pathMatch: 'full', component: UsermanagementComponent },
    { path: 'usermanagement', component: UsermanagementComponent, children : [{ path: 'adduser', component: AdduserComponent },] },    
    { path: 'cases', component: CasesComponent },
    { path: 'adduser', component: AdduserComponent },
    { path: 'city', component: CityComponent },
    { path: 'completedcases', component: CompletedcasesComponent },
    { path: 'district', component: DistrictComponent },
    { path: 'state', component: StateComponent },
    { path: 'court', component: CourtComponent },
    { path: 'recoursecode', component: RecoursecodeComponent },
    { path: 'stagecode', component: StagecodeComponent },
    { path: 'compliance', component: ComplianceComponent },
    { path: 'branch', component: BranchComponent },
    { path: 'organizaton', component: OrganizationComponent },
    { path: 'billing', component: BillingComponent },
    { path: 'againstorganization', component: AgainstorganizationComponent },
    { path: 'fororganization', component: FororganizationComponent },
    { path: 'billinglist', component: BillinglistComponent },
  ] },  
];


@NgModule({
  declarations: [    
    CasesComponent,
    UsermanagementComponent,
    CityComponent,
    CompletedcasesComponent,
    DistrictComponent,
    StateComponent,
    CourtComponent,
    RecoursecodeComponent,
    StagecodeComponent,
    ComplianceComponent,
    BranchComponent,
    OrganizationComponent,
    BillingComponent,
    AgainstorganizationComponent,
    FororganizationComponent,
    BillinglistComponent    
  ],
  imports: [CommonModule,UsermanagementComponent,ReactiveFormsModule,BrowserModule,
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule,CommonModule],
  providers: [UserService]
})

export class AppRoutingModule { }
