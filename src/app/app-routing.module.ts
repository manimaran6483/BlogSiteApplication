import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { BlogsComponent } from './blogs/blogs.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './guards/auth.guard';


const routes: Routes = [
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: BlogsComponent,
    canActivate: [AuthGuard]
  },
  {
    // Needed for Error routing
    path: 'error',
    component: BlogsComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [RouterModule],
})
export class AppRoutingModule { }
