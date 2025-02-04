import { TestBed } from '@angular/core/testing';

import { LuogoService } from './luogo.service';

describe('LuogoService', () => {
  let service: LuogoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LuogoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
