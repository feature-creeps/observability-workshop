import { Component, OnInit } from '@angular/core';
import { environment } from "../../../../../environments/environment";
import { DeleteService } from '../../services/delete.service';
import { Image } from '../../../../shared/models';

@Component({
  selector: 'app-delete',
  templateUrl: './delete.component.html',
  styleUrls: ['./delete.component.css']
})

export class DeleteComponent implements OnInit {


  public ids;
  public images: Array<Image>;
  public displayImage;
  public deleteId: string;
  public selectedLink: string;

  public previewVisible: boolean = true;

  public constructor(private readonly deleteService: DeleteService) {}

  ngOnInit(): void {
    this.retrieveImages();
  }

  async deleteOne() {
    let res;
    if (this.deleteId == undefined) {
      const info = document.getElementById("info");
      info.innerText = "No image selected";
      info.className = "btn btn-block btn-warning dima-btn"
      return
    }
    try {
      res = await this.deleteService.deleteImageById(this.deleteId);
    } catch (e) {
      const info = document.getElementById("info");
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
    const info = document.getElementById("info");
    info.innerText = "Successfully deleted " + this.deleteId;
    info.className = "btn btn-block btn-success dima-btn"
  }

  async deleteAll() {
    let res;
    try {
      res = await this.deleteService.deleteAllImages();
    } catch (e) {
      const info = document.getElementById("info");
      info.innerText = "Failed to delete all images";
      info.className = "btn btn-block btn-danger dima-btn"
    }
    if (res != null) {
      this.onDeleteAllImages();
      this.clearSelection()
    }
  }

  private onDeleteAllImages() {
    this.previewVisible = false;
    const info = document.getElementById("info");
    info.innerText = "Successfully deleted all images";
    info.className = "btn btn-block btn-success dima-btn"
  }

  private clearSelection() {
    this.images = []
  }

  async retrieveImages() {
    const data = await this.deleteService.getImages();
    if (data.length > 0) {
      this.previewVisible = true;
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id);
    } else {
      this.noImages();
    }
  }

  private noImages() {
    document.getElementById("info").innerText = "No images found";
    this.previewVisible = false;
  }

  public setIds(data: Array<Image>): Array<string> {
    const list: string[] = [];
    for (var i = 0; i < data.length; i++) {
      list.push(data[i].id)
    }
    this.ids = list;
    return list;
  }

  public async showImage(id: string) {
    const data = await this.deleteService.getImageById(id);
    if (data != null) {
      this.displayImage = this.createImageFromBlob(data);
    }
    this.selectedLink = environment.backend.imageholder + '/api/images/' + id;
    this.deleteId = id;
  }

  private createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener("load", () => {
      this.displayImage = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

}