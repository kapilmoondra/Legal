export class User {
    username: string;
    password: string;
    firstName: string;
    lastName: string;   
    organization: string;
    mobileNumber: string;	
    isClient: string;
    login?:{        
        userLoginId?: string;
        password?: string;        
    }
    address?: {      
      address1?: string;
      address2?: string;
      city?: string;
      state?: string;
      zipCode?: string;
    }    
  }