import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import { Image } from '../../../shared/models'


@Injectable()
export class OrchestrateService {

  public constructor(private readonly httpClient: HttpClient) {}

  public async getImages(): Promise<any> {
    return this.httpClient.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
  }

  public async getImageById(id: string): Promise<any> {
    return this.httpClient.get(environment.backend.imagethumbnail + '/api/images/' + id, { responseType: 'blob' }).toPromise();
  }

  public async sendTransformationRequest(transformationRequestString: string): Promise<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');

    return this.httpClient.post(environment.backend.imageorchestrator + '/api/images/transform', transformationRequestString,
        { observe: "response", headers: headers, responseType: 'blob' }).toPromise();
  }
}
