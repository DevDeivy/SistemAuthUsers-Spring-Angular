import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

export const rout: Routes = [
  {
    path: '',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import('../auth.module').then(m => m.AuthModule)
  },
  {
    path: '**',
    redirectTo: 'auth/login',
    pathMatch: 'full'
  },

]

@NgModule({
  declarations: [],
  imports: [
    CommonModule,RouterModule.forRoot(rout)
  ],
  exports:[RouterModule]
})
export class RoutesModule { }
