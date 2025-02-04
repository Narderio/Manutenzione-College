import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResidentiComponent } from './residenti.component';

describe('ResidentiComponent', () => {
  let component: ResidentiComponent;
  let fixture: ComponentFixture<ResidentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResidentiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResidentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
