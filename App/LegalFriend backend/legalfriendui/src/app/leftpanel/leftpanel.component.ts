import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router/src/router';

@Component({
  selector: 'app-leftpanel',
  templateUrl: './leftpanel.component.html',
  styleUrls: ['./leftpanel.component.css']
})
export class LeftpanelComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
    
  }

  public home(){
    this.router.navigate(['./home']);
  }

}
