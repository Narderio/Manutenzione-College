import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaBagniComponent } from './tabella-bagni.component';

describe('TabellaBagniComponent', () => {
  let component: TabellaBagniComponent;
  let fixture: ComponentFixture<TabellaBagniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabellaBagniComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabellaBagniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
