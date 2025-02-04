import { TestBed } from '@angular/core/testing';

import { ManutenzioneService } from './manutenzione.service';

describe('ManutenzioneService', () => {
  let service: ManutenzioneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManutenzioneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
