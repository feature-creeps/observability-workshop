import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit {

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.retrieveImages();
  }

  public data;
  public images;
  public displayImage;

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>('http://localhost:8080/api/images').toPromise();
    if (data.length > 0) {
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id);
    }
  }

  setIds(data: Array<Image>): String[] {
    let list: String[] = [];

    for (var i = 0; i < data.length; i++) {
      list.push(data[i].id)
    }

    this.data = list;
    return list;
  }

  async showImage(id: string) {

    let data = await this.http.get('http://localhost:8080/api/images/' + id, {responseType: 'blob'}).toPromise();
    if (data != null) {

      this.displayImage = this.createImageFromBlob(data);
    }
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.displayImage = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

}

interface Image {
  id: String;
  contentType: String;
  name: String;
}
