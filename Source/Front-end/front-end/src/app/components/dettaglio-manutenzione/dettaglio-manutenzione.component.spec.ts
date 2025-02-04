import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioManutenzioneComponent } from './dettaglio-manutenzione.component';

describe('DettaglioManutenzioneComponent', () => {
  let component: DettaglioManutenzioneComponent;
  let fixture: ComponentFixture<DettaglioManutenzioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DettaglioManutenzioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DettaglioManutenzioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
