import { TestBed } from '@angular/core/testing';

import { RegexValidationsService } from './regex-validations.service';

describe('RegexValidationsService', () => {
  let service: RegexValidationsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegexValidationsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
