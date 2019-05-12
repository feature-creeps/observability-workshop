import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {PreviewComponent} from "./preview/preview.component";
import {PreviewService} from "./preview/preview.service";

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent implements OnInit {

  constructor(private http: HttpClient,
              private previewService: PreviewService) {
  }

  ngOnInit(): void {
    this.retrieveImages()
  }

  public ids;
  public images;
  public displayImage;

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
    if (data.length > 0) {
      this.images = data;
      this.showPreviews(data)
    } else {
      // todo handle
    }
  }

  private showPreviews(data: Array<Image>) {
    for (var i = 0; i < data.length; i++) {
      this.previewService.appendComponentToBody(PreviewComponent, data[i].id, data[i].name);
    }
  }
}

interface Image {
  id: string;
  contentType: string;
  name: string;
}
