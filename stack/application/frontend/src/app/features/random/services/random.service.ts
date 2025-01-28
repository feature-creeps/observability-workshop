import { HttpClient } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {environment} from "../../../../environments/environment";


@Injectable()
export class RandomService {

  public constructor(private readonly httpClient: HttpClient) {}

  private randomImageUrl: string = environment.backend.imageholder + '/api/images/random';

  public async getRandomImage(): Promise<Blob> {
    return this.httpClient.get(this.randomImageUrl, {responseType: 'blob'}).toPromise();
  }
}
