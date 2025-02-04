import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneManutenzioniComponent } from './gestione-manutenzioni.component';

describe('GestioneManutenzioniComponent', () => {
  let component: GestioneManutenzioniComponent;
  let fixture: ComponentFixture<GestioneManutenzioniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestioneManutenzioniComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestioneManutenzioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
