import { TestBed } from '@angular/core/testing';

import { ClientApiRestService } from './client-api-rest.service';

describe('ClientApiRestService', () => {
  let service: ClientApiRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClientApiRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
