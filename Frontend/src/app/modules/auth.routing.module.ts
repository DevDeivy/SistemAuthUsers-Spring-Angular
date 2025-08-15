import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from '../auth/login/login.component';
import { RegisterComponent } from '../auth/register/register.component';
import { ForgotPasswordComponent } from '../auth/forgot-password/forgot-password.component';
import { VerifyCodeComponent } from '../auth/verify-code/verify-code.component';

const rout: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'forgot',
    component: ForgotPasswordComponent
  },
  {
    path: 'forgot/:entity',
    component: VerifyCodeComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(rout)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }