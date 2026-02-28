import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  submitting = false;

  constructor(private userService: UserService, private snackBar: MatSnackBar, private router: Router) {}

  submit(form: NgForm) {
    if (!form.valid) return;
    this.submitting = true;
    const payload = { email: this.email.trim(), password: this.password };
    this.userService.login(payload).subscribe(
      (res) => {
        this.snackBar.open('Login successful', 'Close', { duration: 2500 });
        // store a simple token flag (replace with real token handling)
        try {
          localStorage.setItem(
            'auth_token',
            res && res.token ? res.token : '1',
          );
          sessionStorage.setItem('emailId', res && res.emailId ? res.emailId : '');
          sessionStorage.setItem('name', res && res.name ? res.name : '');
        } catch {}
        this.submitting = false;
        // on success navigate to profile or home
        this.router.navigate(['']);
      },
      (err) => {
        const msg = err && err.error && err.error.message ? err.error.message : 'Login failed';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
        this.submitting = false;
      }
    );
  }
}
