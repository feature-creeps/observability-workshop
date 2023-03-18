import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { OrchestrateComponent } from './orchestrate.component';

describe('OrchestrateComponent', () => {
  let component: OrchestrateComponent;
  let fixture: ComponentFixture<OrchestrateComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ OrchestrateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrchestrateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
