import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompletedcasesComponent } from './completedcases.component';

describe('CompletedcasesComponent', () => {
  let component: CompletedcasesComponent;
  let fixture: ComponentFixture<CompletedcasesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompletedcasesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompletedcasesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
