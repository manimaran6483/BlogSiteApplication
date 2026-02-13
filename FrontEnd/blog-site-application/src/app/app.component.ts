import { Component, OnInit } from '@angular/core';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'BlogsSite Application';
  loginDisplay = false;

  constructor(
  ) { }

  ngOnInit(): void {
    
  }


  login() {
    
  }

  logout() {
    
    window.sessionStorage.clear();
    window.localStorage.clear();
  }

}
