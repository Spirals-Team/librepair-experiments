import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  @Output() open: EventEmitter<any> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }

  public toggle(): void {
    this.open.emit(true);
  }

}
