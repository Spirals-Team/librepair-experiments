import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AuthService} from "../../core/auth/auth.service";

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit {
  @Output() close: EventEmitter<any> = new EventEmitter();

  constructor(private auth: AuthService) {
  }

  ngOnInit() {
  }

  private logout() {
    this.auth.logout();
  }

  public closeSidenav(): void {
    this.close.emit(true);
  }

}
