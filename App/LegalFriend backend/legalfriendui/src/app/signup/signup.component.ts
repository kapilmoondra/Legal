import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router/src/router';
import { LoginService } from '../services/login/login.service';
import { User } from './user';
import { Input } from '@angular/core/src/metadata/directives';
import { FormGroup, ReactiveFormsModule  } from '@angular/forms';
import { FormBuilder } from '@angular/forms/src/form_builder';
import { Validators } from '@angular/forms/src/validators';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  providers: [LoginService]  
})
export class SignupComponent implements OnInit {

  public myForm: FormGroup;
  public submitted: boolean;
  public events: any[] = []; 
  constructor(private router: Router, private loginService: LoginService, fb: FormBuilder) {    
    this.myForm = fb.group({
      username: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
      address: fb.group({
        address1: ['', <any>Validators.required],
        address2: ['', <any>Validators.required],
        city: ['', <any>Validators.required],
        state: ['', <any>Validators.required],        
        zipCode: ['8000']        
      }),
      email: [''],      
      password: [''] ,
      firstName: [''] ,
      lastName: [''] ,
      mobileNumber: [''] ,
      organization: ['']
  });
   }  
    ngOnInit() {
    }      
    
    save(model: User, isValid: boolean) {
      this.submitted = true;   
      let loginid = model.username;
      model.login = {
      userLoginId: model.username,
      password: model.password
  }
    let success = this.loginService.addUser(model);
    //if(success=="yes"){
      this.router.navigate(['./login']);      
    //}
    // else{
    //   this.router.navigate(['./signup']);      
    // }    
  }

  login():void{      
    this.router.navigate(['./login']);
  }

}
