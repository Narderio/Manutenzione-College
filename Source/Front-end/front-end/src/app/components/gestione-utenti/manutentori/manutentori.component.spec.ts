import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManutentoriComponent } from './manutentori.component';

describe('ManutentoriComponent', () => {
  let component: ManutentoriComponent;
  let fixture: ComponentFixture<ManutentoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManutentoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ManutentoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
