import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";
import { Image } from '../../../shared/models'


@Injectable()
export class DeleteService {

  public constructor(private readonly httpClient: HttpClient) {}

  public async deleteImageById(deleteId: string): Promise<any> {
    return this.httpClient.delete(environment.backend.imageholder + '/api/images/' + deleteId, {responseType: 'text'}).toPromise();
  }

  public async deleteAllImages(): Promise<any> {
    return this.httpClient.post(environment.backend.imageholder + '/api/images/delete/all', null, {responseType: 'text'}).toPromise();
  }

  public async getImages(): Promise<any> {
    return this.httpClient.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
  }

  public async getImageById(id: string): Promise<any> {
    return this.httpClient.get(environment.backend.imagethumbnail + '/api/images/' + id, {responseType: 'blob'}).toPromise();
  }
}
