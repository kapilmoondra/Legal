import { TestBed, inject } from '@angular/core/testing';

import { CustomRequestService } from './custom-request.service';

describe('CustomRequestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CustomRequestService]
    });
  });

  it('should be created', inject([CustomRequestService], (service: CustomRequestService) => {
    expect(service).toBeTruthy();
  }));
});
