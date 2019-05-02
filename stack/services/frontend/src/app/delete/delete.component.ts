import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})

export class DeleteComponent implements OnInit {

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.retrieveImages();
  }

  public ids;
  public images;
  public displayImage;
  public deleteId: string;

  async deleteOne() {
    let res;
    try {
      res = await this.http.delete('http://localhost:8080/api/images/' + this.deleteId, {responseType: 'Blob'}).toPromise();
    } catch (e) {
      console.log(e)
    }
    if (res != null) {
      console.log("image deleted " + this.deleteId);
      document.getElementById("info").innerText = "Deleted " + this.deleteId;
      this.retrieveImages();
    }
  }

  async deleteAll() {
    let res;
    try {
      res = await this.http.post('http://localhost:8080/api/images/delete/all').toPromise();
    } catch (e) {
      console.log(e)
      // todo: deletion works but somehow response fails
      console.log("Deleted all images");
      document.getElementById("info").innerText = "Deleted all images";
    }
    if (res != null) {
      console.log("Deleted all images");
      document.getElementById("info").innerText = "Deleted all images";
    }
  }

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>('http://localhost:8080/api/images').toPromise();
    if (data.length > 0) {
      document.getElementById("preview").hidden = false;
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id);
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
    this.ids = list;
    return list;
  }

  async showImage(id: string) {
    let data = await this.http.get('http://localhost:8080/api/images/' + id, {responseType: 'blob'}).toPromise();
    if (data != null) {
      this.displayImage = this.createImageFromBlob(data);
      this.deleteId = id;
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
