import { FormGroup } from "@angular/forms/src/model";
import { FormBuilder } from "@angular/forms/src/form_builder";
import { Validators } from "@angular/forms/src/validators";

export class DemoFormNgModelComponent {  
  myForm: FormGroup;  
  productName: string;

  constructor(fb: FormBuilder) {  
    this.myForm = fb.group({  
      'productName':  ['', Validators.required]  
    });  
  }

  onSubmit(value: string): void {  
    console.log('you submitted value: ', value);  
  }
}
