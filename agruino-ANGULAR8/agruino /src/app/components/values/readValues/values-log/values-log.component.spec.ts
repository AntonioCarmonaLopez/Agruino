import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValuesLogComponent } from './values-log.component';

describe('ValuesLogComponent', () => {
  let component: ValuesLogComponent;
  let fixture: ComponentFixture<ValuesLogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValuesLogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValuesLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
