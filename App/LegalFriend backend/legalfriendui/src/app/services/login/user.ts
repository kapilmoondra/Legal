import {UserAddress} from './UserAddress';
export class User {
    id: number;
    username: string;
    password: string;
    firstName: string;
    lastName: string;   
	organization: string;
	address: UserAddress;
	mobileNumber: string;	
    isClient: string;
}