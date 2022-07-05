import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFeeStrctureComponent } from './view-fee-strcture.component';

describe('ViewFeeStrctureComponent', () => {
  let component: ViewFeeStrctureComponent;
  let fixture: ComponentFixture<ViewFeeStrctureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewFeeStrctureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewFeeStrctureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
