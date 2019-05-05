import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-random',
  templateUrl: './random.component.html',
  styleUrls: ['./random.component.css']
})
export class RandomComponent implements OnInit {

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.changeImage()
  }

  public displayImage;
  private randomImageUrl: string = environment.backend.imageholder + '/api/images/random';

  async changeImage() {
    let data;
    try {
      data = await this.http.get(this.randomImageUrl, {responseType: 'blob'}).toPromise();
    } catch (e) {
      RandomComponent.info("Failed retrieving random image")
    }
    if (data != undefined) {
      RandomComponent.info("")
    } else {
      RandomComponent.info("No images found")
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

  private static info(text: string) {
    document.getElementById("image").hidden = text.length > 0;
    document.getElementById("info").innerText = text;
  }
}
