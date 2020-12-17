import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConductibityComponent } from './conductibity.component';

describe('ConductibityComponent', () => {
  let component: ConductibityComponent;
  let fixture: ComponentFixture<ConductibityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConductibityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConductibityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
