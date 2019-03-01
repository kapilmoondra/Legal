import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Status } from '../adduser/status';
import { FormGroup } from '@angular/forms/src/model';
import { Router } from '@angular/router/src/router';
import { FormBuilder } from '@angular/forms/src/form_builder';
import { Validators } from '@angular/forms/src/validators';
import { UserService } from '../services/users/user.service';
import { User } from '../adduser/user';
import { BrowserModule } from '@angular/platform-browser/src/browser';
@Component({
  selector: 'app-usermanagement',
  templateUrl: './usermanagement.component.html',
  styleUrls: ['./usermanagement.component.css','../../assets/datatables/jquery.dataTables.min.css'],
  //providers: [UserService]  
})
@NgModule({
    imports: [
        FormsModule        
      ],
      declarations:[]    
   })
export class UsermanagementComponent implements OnInit {

    public myForm: FormGroup;
    public submitted: boolean;
    public events: any[] = [];
   public users: User[];
  loadAPI :   Promise<any>;
  constructor(private router: Router, private userService: UserService,fb: FormBuilder) { 
    this.loadAPI = new Promise((resolve) => {
      this.loadScript();
      resolve(true);

      this.userService.listUsers()
      .then(res => {
        this.users = res;
        console.log(res); //why the rest is undefined?
        console.log('ini component');
      },
      err => err);
      console.log("users in userment : "+this.users);
  });
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

  public loadScript() {        
    var isFound = false;
    var scripts = document.getElementsByTagName("script")
    for (var i = 0; i < scripts.length; ++i) {
        if (scripts[i].getAttribute('src') != null && scripts[i].getAttribute('src').includes("loader")) {
            isFound = true;
        }
    }

    if (!isFound) {
        var dynamicScripts = [];

        for (var i = 0; i < dynamicScripts .length; i++) {
            let node = document.createElement('script');
            node.src = dynamicScripts [i];
            node.type = 'text/javascript';
            node.async = false;
            node.charset = 'utf-8';
            document.getElementsByTagName('head')[0].appendChild(node);
        }
            

    }
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
      statusId: 1,
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


public addUserComponent(){
  this.router.navigate(['adduser']);
}


}
