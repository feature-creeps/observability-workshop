import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import { Image } from '../../../shared/models'


@Injectable()
export class DisplayService {

  public constructor(private readonly httpClient: HttpClient) {}

  public async getImages(): Promise<any> {
    return this.httpClient.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
  }

  public async getImageById(id: string): Promise<any> {
    return this.httpClient.get(environment.backend.imageholder + '/api/images/' + id, {responseType: 'blob'}).toPromise();
  }
}
