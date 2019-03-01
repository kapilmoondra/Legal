import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsermanagementComponent } from '../usermanagement/usermanagement.component';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
   
export class HomeComponent implements OnInit {

  loadAPI :   Promise<any>;
  constructor() { 
    this.loadAPI = new Promise((resolve) => {
      this.loadScript();
      resolve(true);
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
        var dynamicScripts = ["../assets/js/jquery.app.js","../assets/js/jquery.nicescroll.js"
        //,"../assets/js/bootstrap.js", "../assets/datatables/jquery.dataTables.min.js","../assets/datatables/dataTables.bootstrap.js", "../assets/customdatatable.js", "../assets/js/bootstrap.min.js","../assets/js/jquery.chat.js","../assets/js/jquery.counterup.min.js","../assets/js/jquery.dashboard2","../assets/js/jquery.dashboard","../assets/js/jquery.js","../assets/js/jquery.scrollTo.min.js","../assets/js/jquery.todo.js","../assets/js/jquery-1.8.3.min.js","../assets/js/jquery-ui-1.10.1.custom.min.js","../assets/js/modernizr.min.js","../assets/js/npm.js","../assets/js/pace.min.js","../assets/js/skycons.min.js","../assets/js/waypoints.min.js","../assets/js/wow.min.js"
    ];
        

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
ngOnInit() {
}


}
