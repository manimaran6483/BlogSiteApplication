import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  submitting = false;

  constructor(private userService: UserService, private snackBar: MatSnackBar, private router: Router) {}

  submit(form: NgForm) {
    if (!form.valid) return;
    this.submitting = true;
    const payload = { name: this.name.trim(), email: this.email.trim(), password: this.password };
    this.userService.register(payload).subscribe(
      (res) => {
        this.snackBar.open('Registration successful', 'Close', { duration: 3000 });
        this.submitting = false;
        this.router.navigate(['/login']);
      },
      (err) => {
        const msg = err && err.error && err.error.message ? err.error.message : 'Registration failed';
        this.snackBar.open(msg, 'Close', { duration: 4000 });
        this.submitting = false;
      }
    );
  }
}
