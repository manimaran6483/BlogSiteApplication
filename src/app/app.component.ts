import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'BlogsSite Application';
  loginDisplay = false;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.updateLoginDisplay();
    // update display on route changes (in case login sets token and navigates)
    this.router.events.subscribe(e => {
      if (e instanceof NavigationEnd) this.updateLoginDisplay();
    });
  }


  login() {
    this.router.navigate(['/login']);
  }

  logout() {
    window.sessionStorage.clear();
    window.localStorage.clear();
    this.updateLoginDisplay();
    this.router.navigate(['/login']);
  }

  private updateLoginDisplay() {
    const token = localStorage.getItem('auth_token');
    this.loginDisplay = !!token;
  }

}
