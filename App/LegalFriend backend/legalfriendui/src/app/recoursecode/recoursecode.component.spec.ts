import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecoursecodeComponent } from './recoursecode.component';

describe('RecoursecodeComponent', () => {
  let component: RecoursecodeComponent;
  let fixture: ComponentFixture<RecoursecodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecoursecodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecoursecodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
