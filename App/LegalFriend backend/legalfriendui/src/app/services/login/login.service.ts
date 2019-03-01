import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { UserAddress } from './UserAddress';
import { User } from '../../signup/user';
import { Router } from '@angular/router/src/router';
import { Login } from '../../login/login';
//import { Observable } from 'rxjs/Observable';
import { Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class LoginService {
    private success: string;

    private baseUrl: string = 'http://localhost:9999/';
    private loginUrl: string = 'http://localhost:7777/oauth/token';
    constructor(
        private http: Http, private router: Router
    ) { }

    public addUser(model: User):string {
        let headers = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
        let options = new RequestOptions({ headers: headers });
        let result = this.http.post(this.baseUrl + "users/user", model, options)
            .map(this.extractData)
            .catch(this.handleError)
            .subscribe();
            return this.success;                
    }

    public loginUser(model: Login): string {
        let headers = new Headers();

        headers.append("Content-Type", "application/x-www-form-urlencoded");
        let body = new URLSearchParams();
        body.set('username', model.username);
        body.set('password', model.password);
        let options = new RequestOptions({ headers: headers });
        let result = this.http.post(this.baseUrl + "users/login", body, options)
            .map(this.extractData)
            .catch(this.handleError)
            .subscribe(data => {
                if (data.access_token != undefined) {
                    console.log("Login : " + data.user.id);
                    console.log("Login : " + data.access_token);
                    localStorage.setItem('access_token', data.access_token);
                    localStorage.setItem('client_id', data.user.id);
                    this.success = "yes";
                    this.router.navigate(['./home']);                    
                }
                else{
                    this.success = "no";
                }
                
            });        
            return this.success;
    }

    private extractData(res: Response) {
        this.success = "yes";
        let body = res.json();
        return body || {};
    }

    private handleError(error: any): string {
        this.success = "no";
        let errMsg = (error.message) ? error.message :
            error.status ? `${error.status} - ${error.statusText}` : 'Server error';
        if (errMsg.indexOf("400") >= 0) {
            console.log("Error:" + errMsg);
        }
        Observable.throw(errMsg);
        return "NOT_OK"        
    }
}
