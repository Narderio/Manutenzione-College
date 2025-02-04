import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaLuoghiComuniComponent } from './tabella-luoghi-comuni.component';

describe('TabellaLuoghiComuniComponent', () => {
  let component: TabellaLuoghiComuniComponent;
  let fixture: ComponentFixture<TabellaLuoghiComuniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabellaLuoghiComuniComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabellaLuoghiComuniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
