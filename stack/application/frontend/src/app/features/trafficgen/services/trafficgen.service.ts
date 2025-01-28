import { HttpClient } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";


@Injectable()
export class TrafficgenService {

  public constructor(private readonly httpClient: HttpClient) {}

  public async uploadAll(): Promise<any> {
    return this.httpClient.post(environment.backend.trafficgen + '/api/traffic/image/upload', null, { responseType: 'text' }).toPromise();
  }

  public async sendTransformationRequest(transformationsPerSecond: number): Promise<any> {
    return await this.httpClient.post(environment.backend.trafficgen + '/api/traffic/image/transform/start?transformationsPerSecond=' + transformationsPerSecond, null, { responseType: 'text' }).toPromise();
  }

  public async stopTransformationTraffic(): Promise<any> {
    return await this.httpClient.post(environment.backend.trafficgen + '/api/traffic/image/transform/stop', null, { responseType: 'text' }).toPromise();
  }
}
