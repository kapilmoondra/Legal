import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FororganizationComponent } from './fororganization.component';

describe('FororganizationComponent', () => {
  let component: FororganizationComponent;
  let fixture: ComponentFixture<FororganizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FororganizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FororganizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
