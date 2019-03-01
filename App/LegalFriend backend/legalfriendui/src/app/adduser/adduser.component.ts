import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms/src/model';
import { Router } from '@angular/router/src/router';
import { FormBuilder } from '@angular/forms/src/form_builder';
import { Validators } from '@angular/forms/src/validators';
import { UserService } from '../services/users/user.service';
import { User } from './user';
import { Status } from './status';

@Component({
  selector: 'app-adduser',
  templateUrl: './adduser.component.html',
  styleUrls: ['./adduser.component.css'],
  providers: [UserService]  
})
export class AdduserComponent implements OnInit {

  public myForm: FormGroup;
  public submitted: boolean;
  public events: any[] = []; 
  constructor(private router: Router, private userService: UserService, fb: FormBuilder) {    
    this.myForm = fb.group({
      username: ['', [<any>Validators.required, <any>Validators.minLength(5)]],
      address: fb.group({
        address1: ['', <any>Validators.required],
        address2: ['', <any>Validators.required],
        city: ['', <any>Validators.required],
        state: ['', <any>Validators.required],        
        zipCode: ['8000']        
      }),      
      status: fb.group({
        statusId: ['', <any>Validators.required],
        statusName: ['', <any>Validators.required],        
        userLoginId: [''],
      }),
      email: [''],      
      password: [''] ,
      firstName: [''] ,
      lastName: [''] ,
      mobileNumber: [''] ,
      organization: ['']
  });
   }  

   selectedStatus:Status = new Status(1, 'ACTIVE',"");
   statusList = [
      new Status(1, 'ACTIVE', "10"),
      new Status(2, 'DEACTIVE',"11" ),
      new Status(3, 'SUSPENDED',"12" ),      
   ];
    ngOnInit() {
    }      
    
    save(model: User, isValid: boolean) {
      this.submitted = true;  
      console.log(model.status.statusId); 
      let loginid = model.username;
      model.status = {
        statusId: +model.status.statusId,
        statusName:"auto",
        userLoginId:model.username
      }
      model.login = {
      userLoginId: model.username,
      password: model.password
      }
      model.clientId = localStorage.getItem('client_id');
    this.userService.addUser(model); 
    let users = this.userService.listUsers(); 
    //console.log(users);  
  }  

}
