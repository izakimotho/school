import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudFeePaymentHistComponent } from './stud-fee-payment-hist.component';

describe('StudFeePaymentHistComponent', () => {
  let component: StudFeePaymentHistComponent;
  let fixture: ComponentFixture<StudFeePaymentHistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudFeePaymentHistComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StudFeePaymentHistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
