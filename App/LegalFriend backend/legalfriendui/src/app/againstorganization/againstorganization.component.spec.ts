import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AgainstorganizationComponent } from './againstorganization.component';

describe('AgainstorganizationComponent', () => {
  let component: AgainstorganizationComponent;
  let fixture: ComponentFixture<AgainstorganizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AgainstorganizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AgainstorganizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
