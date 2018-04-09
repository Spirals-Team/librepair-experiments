import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  title = 'Demo';
  greeting = {'id': 'XXX', 'content': 'Hello World'};

  constructor() { }

  ngOnInit() {
  }

}
