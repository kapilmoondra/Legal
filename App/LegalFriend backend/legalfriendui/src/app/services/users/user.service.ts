import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Router } from '@angular/router/src/router';
import { Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { User } from '../../adduser/user';

@Injectable()
export class UserService {  
  userList: User[] ;     
  private baseUrl: string = 'http://localhost:9999/users/';  
  constructor (
    private http: Http, private router : Router
  ) {}
 
 public addUser(model:User){        
    let headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
            let access_token = localStorage.getItem("access_token");
            let client_id = localStorage.getItem("client_id");
            //headers.append("Authorization", "Bearer : "+access_token); 
            let options = new RequestOptions({ headers: headers }); 
            model.clientId = client_id;           
            let result = this.http.post(this.baseUrl+'addusers', model, options)
            .map(this.extractData)
            .catch(this.handleError)
            .subscribe();
            console.log("User : "+result);            
}

public editUser(model:User){        
    let headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
            let access_token = localStorage.getItem("access_token");
            let client_id = localStorage.getItem("client_id");
            //headers.append("Authorization", "Bearer : "+access_token); 
            let options = new RequestOptions({ headers: headers }); 
            model.clientId = client_id;           
            let result = this.http.post(this.baseUrl+'editusers', model, options)
            .map(this.extractData)
            .catch(this.handleError)
            .subscribe();
            console.log("User : "+result);            
}

// load(): Promise<any> {
//     return this.http.get(this.BASEURL + 'api/client/hotel/load')
//         .toPromise()
//         .then(response => {
//             return response.json();
//         })
//         .catch(err => err);
// }
public listUsers():Promise<any> {        
    let headers = new Headers({ 'Content-Type': 'application/json',});
            //let access_token = localStorage.getItem("access_token");
            let client_id = localStorage.getItem("client_id");
            //headers.append("Authorization", "Bearer : "+access_token); 
            let options = new RequestOptions({ headers: headers });             
            return  this.http.get(this.baseUrl+'listusers?clientId=8', options)
            .toPromise()
            .then(response => {
                return response.json();
            })
            .catch(err => err);        
}


    private extractData(res: Response) {
        let body = res.json();
        //this.userList = body;
        console.log('userslist : '+body);
        return body || {};
    }

    private handleError(error: any):string  {
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
		if(errMsg.indexOf("400") >= 0) {
			 console.log("Error:" +errMsg);
		}
		Observable.throw(errMsg);
		 return "NOT_OK"        
    }	    
}
