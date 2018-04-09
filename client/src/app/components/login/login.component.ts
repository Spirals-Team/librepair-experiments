import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { AuthService } from '../../core/auth/auth.service';
import { NgRedux, select } from '@angular-redux/store';
import { IGlobalState } from '../../core/state/globalState';
import { Observable } from 'rxjs/Observable';
import { getValuesFromForm } from '../../core/helpers/getValuesFromForm';
import { isNil } from 'lodash';
import { ReduxFormsNames } from '../../core/state/ReduxFormsNames';

export interface ILoginForm {
  email: string;
  username: string;
  password: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public showAuthorizationError = false;
  public authorizationForm = ReduxFormsNames.authorizationForm;
  public errorMessage = 'Blędne dane autoryzacji';
  @select(['auth', 'authorizationStatus'])
  readonly status: Observable<number>;
  console = console;
  isNil = isNil;

  constructor(
    private router: Router,
    private authService: AuthService,
    private ngRedux: NgRedux<IGlobalState>,
  ) {
  }

  ngOnInit() {
    this.status.subscribe(value => {
      if (value === 401) {
        this.showAuthorizationError = true;
      }
    });
  }

  public onSubmit(form: NgForm) {
    const values = getValuesFromForm<ILoginForm>(form);
    this.authService.login(values.username, values.password)
      .subscribe(result => {
        if (result === true) {
          this.router.navigate(['/dashboard']);
        }
      });
  }

  public registration() {
    this.router.navigate(['/registration']);
  }
}
