import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SegnalaManutenzioneComponent } from './segnala-manutenzione.component';

describe('SegnalaManutenzioneComponent', () => {
  let component: SegnalaManutenzioneComponent;
  let fixture: ComponentFixture<SegnalaManutenzioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SegnalaManutenzioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SegnalaManutenzioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
