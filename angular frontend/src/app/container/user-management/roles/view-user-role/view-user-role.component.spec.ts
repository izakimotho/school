import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUserRoleComponent } from './view-user-role.component';

describe('ViewUserRoleComponent', () => {
  let component: ViewUserRoleComponent;
  let fixture: ComponentFixture<ViewUserRoleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewUserRoleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewUserRoleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
