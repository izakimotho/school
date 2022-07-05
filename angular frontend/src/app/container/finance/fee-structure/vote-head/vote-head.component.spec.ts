import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteHeadComponent } from './vote-head.component';

describe('VoteHeadComponent', () => {
  let component: VoteHeadComponent;
  let fixture: ComponentFixture<VoteHeadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteHeadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteHeadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
