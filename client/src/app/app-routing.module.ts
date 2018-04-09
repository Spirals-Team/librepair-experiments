import {DashboardComponent} from './components/dashboard/dashboard.component';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {RegistrationComponent} from './components/registration/registration.component';
import {NgModule} from '@angular/core';
import {AuthGuardService} from './core/auth/auth-guard.service';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuardService ]
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'registration',
    component: RegistrationComponent,
  },

];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
