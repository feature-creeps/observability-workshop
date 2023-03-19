import { Component, OnInit } from '@angular/core';
import { PreviewComponent } from "../preview/preview.component";
import { PreviewService } from "../../services/preview.service";
import { AlbumService } from '../../services/album.service';
import { Image } from '../../../../shared/models';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent implements OnInit {

  public constructor(
    private readonly albumService: AlbumService,
    private readonly previewService: PreviewService
  ) {}

  ngOnInit(): void {
    this.retrieveImages()
  }

  public ids;
  public images;
  public displayImage;
  public currentPage = 1
  private data
  private MAX_IMAGES_DISPLAYED = 15
  private firstImageDisplayed = 0

  private clearImages() {
    var e = document.querySelectorAll("#album")[0];
    var child = e.lastElementChild;
    while (child) {
      e.removeChild(child);
      child = e.lastElementChild;
    }
  }

  nextPage() {
    this.clearImages()
    this.firstImageDisplayed = this.firstImageDisplayed + this.MAX_IMAGES_DISPLAYED
    this.showPreviews(this.data, this.firstImageDisplayed)
    this.enablePrevButton();
    this.currentPage++
    if (this.atEnd()) {
      this.disableNextButton();
    }
  }

  previousPage() {
    this.clearImages()
    this.firstImageDisplayed = this.firstImageDisplayed - this.MAX_IMAGES_DISPLAYED
    this.showPreviews(this.data, this.firstImageDisplayed)
    this.enableNextButton();
    this.currentPage--
    if (this.atStart()) {
      this.disablePrevButton();
    }
  }

  private disableNextButton() {
    this.nextButton().setAttribute("disabled", "disabled")
  }

  private enablePrevButton() {
    this.prevButton().removeAttribute("disabled")
  }

  private disablePrevButton() {
    this.prevButton().setAttribute("disabled", "disabled")
  }

  private enableNextButton() {
    this.nextButton().removeAttribute("disabled")
  }

  private prevButton() {
    return document.querySelectorAll("#prevButton")[0]
  }

  private nextButton() {
    return document.querySelectorAll("#nextButton")[0]
  }

  private atEnd() {
    return this.firstImageDisplayed + this.MAX_IMAGES_DISPLAYED >= this.data.length;
  }

  private atStart() {
    return this.firstImageDisplayed - this.MAX_IMAGES_DISPLAYED < 0;
  }

  async retrieveImages() {
    this.data = await this.albumService.getImages();
    if (this.data.length > 0) {
      this.images = this.data;
      this.showPreviews(this.data, this.firstImageDisplayed)
    } else {
      // todo handle
    }
  }

  private showPreviews(data: Array<Image>, firstIndex) {
    let maxImageIndex = firstIndex + this.MAX_IMAGES_DISPLAYED
    for (var i = firstIndex; i < data.length && i < maxImageIndex; i++) {
      this.previewService.appendComponentToBody(PreviewComponent, data[i].id, data[i].name);
    }
  }
}

