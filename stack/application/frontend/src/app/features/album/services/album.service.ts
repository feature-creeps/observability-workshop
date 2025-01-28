import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import { Image } from '../../../shared/models'


@Injectable()
export class AlbumService {

  public constructor(private readonly httpClient: HttpClient) {}

  public async getImages(): Promise<any> {
    return this.httpClient.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
  }
}
