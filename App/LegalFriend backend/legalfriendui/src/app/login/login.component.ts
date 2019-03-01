import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router/src/router';
//import { LoginService } from '../services/login/login.service';
import { FormBuilder } from '@angular/forms/src/form_builder';
import { FormGroup } from '@angular/forms/src/model';
import { Validators } from '@angular/forms/src/validators';
import { Login } from './login';
import { LoginService } from '../services/login/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService] 
})
export class LoginComponent implements OnInit {

  public loginForm: FormGroup;
  public submitted: boolean;
  public events: any[] = []; 
  constructor(private router: Router, private loginService: LoginService, fb: FormBuilder) {    
    this.loginForm = fb.group({
      username: ['', [<any>Validators.required, <any>Validators.minLength(5)]],         
      password: [''] ,      
  });
   }  
    ngOnInit() {
    }      
    
    register():void{      
      this.router.navigate(['./signup']);
    }
    login(model: Login){ 
	    model.grant_type = 'password';
        let result = this.loginService.loginUser(model);
		console.log("result:"+result);
     // this.router.navigate(['./home']);
    }

  //   doLogin(model: Login, isValid: boolean) {
  //     //this.submitted = true;   
  //     //let loginid = model.username;  

  //   //this.loginService.addUser(model);
  //   this.router.navigate(['./home']);      
  // }
}
