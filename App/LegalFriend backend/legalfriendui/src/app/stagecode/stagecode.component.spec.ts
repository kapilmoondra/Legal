import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { StagecodeComponent } from './stagecode.component';

describe('StagecodeComponent', () => {
  let component: StagecodeComponent;
  let fixture: ComponentFixture<StagecodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ StagecodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(StagecodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
