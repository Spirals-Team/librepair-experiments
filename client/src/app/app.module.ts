import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { MaterialModule } from './material.module';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { AppRoutingModule } from './app-routing.module';
import { SidenavComponent } from './components/sidenav/sidenav.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TokenInterceptor } from './core/auth/token.interceptor';
import { AuthGuardService } from './core/auth/auth-guard.service';
import { AuthService } from './core/auth/auth.service';
import { JwtInterceptor } from './core/auth/jwt.interceptor';
import { NgReduxModule } from '@angular-redux/store';
import { AuthorizationActionCreator } from './core/actionCreator/authorizationActionCreator';
import { StoreModule } from './core/store/module';
import { NgReduxFormModule } from '@angular-redux/form';
import { RegistrationService } from './core/services/RegistrationService';

@NgModule({
  declarations: [
    AppComponent,
    ToolbarComponent,
    DashboardComponent,
    LoginComponent,
    RegistrationComponent,
    SidenavComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    MaterialModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgReduxModule,
    NgReduxFormModule,
    StoreModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
    AuthGuardService,
    AuthService,
    RegistrationService,
    AuthorizationActionCreator,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
