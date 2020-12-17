import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarNoUserComponent } from './navbar-no-user.component';

describe('NavbarNoUserComponent', () => {
  let component: NavbarNoUserComponent;
  let fixture: ComponentFixture<NavbarNoUserComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarNoUserComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarNoUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
