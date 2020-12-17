import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReadValuesLogComponent } from './read-values-log.component';

describe('ReadValuesLogComponent', () => {
  let component: ReadValuesLogComponent;
  let fixture: ComponentFixture<ReadValuesLogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReadValuesLogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReadValuesLogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
