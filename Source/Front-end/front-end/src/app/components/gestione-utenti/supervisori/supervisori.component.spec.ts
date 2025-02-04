import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SupervisoriComponent } from './supervisori.component';

describe('SupervisoriComponent', () => {
  let component: SupervisoriComponent;
  let fixture: ComponentFixture<SupervisoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SupervisoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SupervisoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
