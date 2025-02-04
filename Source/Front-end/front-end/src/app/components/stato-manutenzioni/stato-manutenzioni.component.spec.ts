import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatoManutenzioniComponent } from './stato-manutenzioni.component';

describe('StatoManutenzioniComponent', () => {
  let component: StatoManutenzioniComponent;
  let fixture: ComponentFixture<StatoManutenzioniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StatoManutenzioniComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatoManutenzioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
