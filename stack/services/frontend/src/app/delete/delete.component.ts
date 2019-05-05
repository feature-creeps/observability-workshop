import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

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
    if (this.deleteId == undefined) {
      let info = document.getElementById("info");
      info.innerText = "No image selected";
      info.className = "btn btn-block btn-warning dima-btn"
      return
    }
    try {
      res = await this.http.delete(environment.backend.imageholder + '/api/images/' + this.deleteId, {responseType: 'text'}).toPromise();
    } catch (e) {
      let info = document.getElementById("info");
      info.innerText = "Failed to delete " + this.deleteId;
      info.className = "btn btn-block btn-danger dima-btn"
    }
    if (res != null) {
      this.deletedOne();
      this.clearSelection();
      this.retrieveImages();
    }
  }

  private deletedOne() {
    let info = document.getElementById("info");
    info.innerText = "Successfully deleted " + this.deleteId;
    info.className = "btn btn-block btn-success dima-btn"
  }

  async deleteAll() {
    let res;
    try {
      res = await this.http.post(environment.backend.imageholder + '/api/images/delete/all', null, {responseType: 'text'}).toPromise();
    } catch (e) {
      let info = document.getElementById("info");
      info.innerText = "Failed to delete all images";
      info.className = "btn btn-block btn-danger dima-btn"
    }
    if (res != null) {
      DeleteComponent.deletedAllImages();
      this.clearSelection()
    }
  }

  private static deletedAllImages() {
    document.getElementById("preview").hidden = true;
    let info = document.getElementById("info");
    info.innerText = "Successfully deleted all images";
    info.className = "btn btn-block btn-success dima-btn"
  }

  private clearSelection() {
    this.images = []
  }

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
    if (data.length > 0) {
      document.getElementById("preview").hidden = false;
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id);
    } else {
      DeleteComponent.noImages();
    }
  }

  private static noImages() {
    document.getElementById("info").innerText = "No images found";
    document.getElementById("preview").hidden = true;
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
    let data = await this.http.get(environment.backend.imageholder + '/api/images/' + id, {responseType: 'blob'}).toPromise();
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
