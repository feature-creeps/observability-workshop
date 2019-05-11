import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-preview',
  templateUrl: './preview.component.html',
  styleUrls: ['./preview.component.css']
})
export class PreviewComponent {

  constructor(private http: HttpClient) {
  }

  public displayImage;
  public link;

  public async showImage(id: string) {
    this.link = environment.backend.imageholder + '/api/images/' + id
    let data = await this.http.get(environment.backend.imagethumbnail + '/api/images/' + id, {responseType: 'blob'}).toPromise();
    if (data != null) {
      this.createImageFromBlob(data);
    }
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener(
      "load",
      () => {
        this.displayImage = reader.result;
      },
      false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }
}

