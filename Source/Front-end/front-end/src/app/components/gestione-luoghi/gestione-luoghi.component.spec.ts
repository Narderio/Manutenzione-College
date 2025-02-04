import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneLuoghiComponent } from './gestione-luoghi.component';

describe('GestioneLuoghiComponent', () => {
  let component: GestioneLuoghiComponent;
  let fixture: ComponentFixture<GestioneLuoghiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestioneLuoghiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestioneLuoghiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
