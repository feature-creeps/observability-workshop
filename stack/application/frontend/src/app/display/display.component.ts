import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";

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

  data;
  images;
  displayImage;
  selectedLink: string;

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
    if (data.length > 0) {
      document.getElementById("preview").hidden = false;
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id, true);
    } else {
      document.getElementById("info").innerText = "No images found";
      document.getElementById("preview").hidden = true;
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

  async showImageById(formInput: any) {
    let id = formInput.querySelectorAll("#imageIdIn")[0].value
    this.showImageWithId(id)
  }

  showImageWithId(id: string) {
    this.showImage(id, false)
  }

  async showImage(id: string, hideId: boolean) {
    let data = await this.http.get(environment.backend.imageholder + '/api/images/' + id, {responseType: 'blob'}).toPromise();
    if (data != null) {
      this.displayImage = this.createImageFromBlob(data);
      this.selectedLink = environment.backend.imageholder + '/api/images/' + id;
      (<HTMLSelectElement>document.querySelectorAll("#imageSelect")[0]).value = id;
      (<HTMLSelectElement>document.querySelectorAll("#imageIdIn")[0]).value = hideId ? "" : id;
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
