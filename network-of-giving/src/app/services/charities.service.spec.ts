import { TestBed } from '@angular/core/testing';

import { CharitiesService } from './charities.service';

describe('CharitiesService', () => {
  let service: CharitiesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CharitiesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
