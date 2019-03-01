import { Injectable } from '@angular/core';
import { ApiGateway } from '../../shared/services/api-gateway';
import { Observable } from '../../../../node_modules/rxjs';

@Injectable()
export class CustomRequestService {

  constructor(private apiGateWay: ApiGateway) { }

  GetAllRecourses(): Observable<any> {
    return this.apiGateWay.get('master/recourse/requests');
  }

  ApprovedRecourse(id: any): Observable<any> {
    return this.apiGateWay.put('master/recourse/requests?id=' + id, null);
  }

  DeclineRecourse(id: any): Observable<any> {
    return this.apiGateWay.put('master/recourse/requests/decline?id=' + id, null);
  }

  GetAllStages(): Observable<any> {
    return this.apiGateWay.get('master/stage/requests');
  }

  ApprovedStage(id: any): Observable<any> {
    return this.apiGateWay.put('master/stage/requests?id=' + id, null);
  }

  DeclineStage(id: any): Observable<any> {
    return this.apiGateWay.put('master/stage/requests/decline?id=' + id, null);
  }

  GetAllCourts(): Observable<any> {
    return this.apiGateWay.get('master/court/requests');
  }

  ApprovedCourt(id: any): Observable<any> {
    return this.apiGateWay.put('master/court/requests?id=' + id, null);
  }

  DeclineCourt(id: any): Observable<any> {
    return this.apiGateWay.put('master/court/requests/decline?id=' + id, null);
  }
}
