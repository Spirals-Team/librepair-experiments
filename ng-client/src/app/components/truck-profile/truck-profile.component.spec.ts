import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TruckProfileComponent } from './truck-profile.component';

describe('TruckProfileComponent', () => {
  let component: TruckProfileComponent;
  let fixture: ComponentFixture<TruckProfileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TruckProfileComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TruckProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
