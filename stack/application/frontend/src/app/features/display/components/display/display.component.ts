import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import { DisplayService } from '../../services/display.service';
import { Image } from '../../../../shared/models';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit {

  public displayForm = new FormGroup({
    imageId: new FormControl<string | null>(null),
    selectedImageId: new FormControl<string | null>(null),
  });

  public data;
  public images: Array<Image> = [];
  public displayImage;
  public selectedLink: string;

  public previewVisible: boolean = true;

  public constructor(private readonly displayService: DisplayService) {}

  public ngOnInit(): void {
    this.retrieveImages();
  }

  async retrieveImages() {
    const data = await this.displayService.getImages();
    if (data.length > 0) {
      this.previewVisible = true;
      this.images = data;
      this.setIds(data);
      this.showImage(this.images[0].id, true);
    } else {
      document.getElementById("info").innerText = "No images found";
      this.previewVisible = false;
    }
  }

  setIds(data: Array<Image>): void {
    this.data = data.map((d) => d.id);
  }

  async showImageById() {
    const id: string | null = this.displayForm.value.imageId
    if(!id) {
      return
    };

    this.showImageWithId(id)
  }

  showImageWithId(id: string) {
    this.showImage(id, false)
  }

  async showImage(id: string, hideId: boolean) {
    const data = await this.displayService.getImageById(id);
    if (data != null) {
      this.displayImage = this.createImageFromBlob(data);
      this.selectedLink = environment.backend.imageholder + '/api/images/' + id;
      this.displayForm.controls.selectedImageId.setValue(null);
      this.displayForm.controls.imageId.setValue(hideId ? "" : id);
    }
  }

  createImageFromBlob(image: Blob) {
    const reader = new FileReader();
    reader.addEventListener("load", () => {
      this.displayImage = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  public get sendButtonEnabled(): boolean {
    const imageId: string | null = this.displayForm.value.imageId;
    return !!imageId && imageId.length > 0;
  }
}
