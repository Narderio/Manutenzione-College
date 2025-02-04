import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaStanzeComponent } from './tabella-stanze.component';

describe('TabellaStanzeComponent', () => {
  let component: TabellaStanzeComponent;
  let fixture: ComponentFixture<TabellaStanzeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabellaStanzeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabellaStanzeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
