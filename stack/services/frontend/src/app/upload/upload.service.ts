import {map} from 'rxjs/operators';
import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http'
import {Observable} from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({providedIn: "root"})
export class UploadService {

  constructor(private http: HttpClient) {
  }

  public uploadImage(image: File, name: string): Observable<string | any> {
    const formData = new FormData();
    console.log(name);

    formData.append('image', image);
    formData.append('name', name);

    return this.http.post(environment.backend.imageholder + '/api/images', formData, {responseType: 'text'}).pipe(map(((json: any) => json.imageUrl)));
  }
}
