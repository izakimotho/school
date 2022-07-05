import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamGradingComponent } from './exam-grading.component';

describe('ExamGradingComponent', () => {
  let component: ExamGradingComponent;
  let fixture: ComponentFixture<ExamGradingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamGradingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamGradingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
