import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment";
import { RandomService } from '../services/random.service';

@Component({
  selector: 'app-random',
  templateUrl: './random.component.html',
  styleUrls: ['./random.component.css']
})
export class RandomComponent implements OnInit {

  public constructor(private readonly randomService: RandomService) {
  }

  ngOnInit() {
    this.changeImage()
  }

  public displayImage;

  public async changeImage() {
    let data: Blob;
    try {
      data = await this.randomService.getRandomImage();
    } catch (e) {
      this.info("Failed retrieving random image")
    }
    if (data != undefined) {
      this.info("")
    } else {
      this.info("No images found")
    }
    this.displayImage = this.createImageFromBlob(data);
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

  private info(text: string): void {
    document.getElementById("image").hidden = text.length > 0;
    document.getElementById("info").innerText = text;
  }
}
