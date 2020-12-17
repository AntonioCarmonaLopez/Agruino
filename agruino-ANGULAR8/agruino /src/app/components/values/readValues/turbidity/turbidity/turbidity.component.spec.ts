import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TurbidityComponent } from './turbidity.component';

describe('TurbidityComponent', () => {
  let component: TurbidityComponent;
  let fixture: ComponentFixture<TurbidityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TurbidityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TurbidityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
