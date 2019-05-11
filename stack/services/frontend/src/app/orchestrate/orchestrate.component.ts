import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-orchestrate',
  templateUrl: './orchestrate.component.html',
  styleUrls: ['./orchestrate.component.css']
})
export class OrchestrateComponent implements OnInit {
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.retrieveImages();
  }

  public data;
  public images;
  public displayImage;
  public transformedImage;
  private displayId: string;

  async retrieveImages() {
    let data = await this.http.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
    if (data.length > 0) {
      document.getElementById("preview").hidden = false;
      this.images = data;
      this.setIds(data);
      this.showPreview(this.images[0].id);
    } else {
      document.getElementById("previewInfo").hidden = false;
      document.getElementById("previewInfoText").innerText = "No images found. Upload some ";
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

  async showPreview(id: string) {
    let data = await this.http.get(environment.backend.imagethumbnail + '/api/images/' + id, {responseType: 'blob'}).toPromise();
    if (data != null) {
      this.displayId = id;
      let reader = new FileReader();
      reader.addEventListener("load", () => {
        this.displayImage = reader.result;
      }, false);
      if (data) {
        reader.readAsDataURL(data);
      }
    }
  }

  showTransformed(data: Blob) {
    document.getElementById("transformedImage").hidden = false;
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.transformedImage = reader.result;
    }, false);
    if (data) {
      reader.readAsDataURL(data);
    }
  }

  private hideTransformed() {
    this.transformedImage = "";
    document.getElementById("transformedImage").hidden = true;
  }

  //=======
  async sendRequest(formInput: any) {
    this.hideTransformed()
    this.hideInfo()

    if (this.displayId == undefined) {
      OrchestrateComponent.info("No image selected", InfoType.warning);
      return
    }

    let tr = JSON.stringify(this.buildJson(formInput))
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    let res;
    try {
      res = await this.http.post(environment.backend.imageorchestrator + '/api/images/transform', tr,
        {headers: headers, responseType: 'blob'}).toPromise();
    } catch (e) {
      console.log(e)
      this.hideTransformed()
      OrchestrateComponent.info("Transformation failed", InfoType.danger);
      return;
    }
    this.showTransformed(res)
    OrchestrateComponent.info("Transformation successful", InfoType.success)
  }

  private static info(text: string, type: InfoType) {
    let info = document.getElementById("info");
    info.hidden = false
    info.innerText = text
    info.className = "fade-in btn btn-block btn-" + InfoType[type] + " dima-btn"
  }

  buildJson(formInput: any) {
    let tfr: TransformationRequest = new TransformationRequest(this.displayId)

    // rotate
    let rotate = formInput.querySelectorAll("#rotate")[0];
    if (rotate.checked == true) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.rotate;
      let degrees = formInput.querySelectorAll("#degrees")[0].value;
      tr.properties.degrees = degrees
      tfr.transformations.push(tr);
    }

    // resize
    let resize = formInput.querySelectorAll("#resize")[0];
    if (resize.checked == true) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.resize;
      let factor = formInput.querySelectorAll("#factor")[0].value;
      tr.properties.factor = factor
      tfr.transformations.push(tr);
    }

    // grayscale
    let grayscale = formInput.querySelectorAll("#grayscale")[0].checked;
    if (grayscale == true) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.grayscale;
      tfr.transformations.push(tr);
    }

    // flip
    let vertical = formInput.querySelectorAll("#vertical")[0].checked;
    let horizontal = formInput.querySelectorAll("#horizontal")[0].checked;
    if (vertical || horizontal) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.flip;
      tr.properties.vertical = vertical;
      tr.properties.horizontal = horizontal;
      tfr.transformations.push(tr);
    }

    // persist
    tfr.persist = formInput.querySelectorAll("#persist")[0].checked;
    if (tfr.persist) {
      tfr.name = formInput.querySelectorAll("#name")[0].value
    }

    return tfr;
  }

  private hideInfo() {
    document.getElementById("info").hidden = true
  }
}

interface Image {
  id: String;
  contentType: String;
  name: String;
}

enum TransformationType {
  rotate = "rotate",
  grayscale = "grayscale",
  resize = "resize",
  flip = "flip"
}

class Transformation {
  type: TransformationType;
  properties: any = {};
}

class TransformationRequest {
  imageId: string;
  transformations: Transformation[];
  persist: boolean;
  name: string;

  constructor(imageId: string) {
    this.imageId = imageId;
    this.transformations = [];
    this.persist = true;
  }
}

enum InfoType {
  warning,
  danger,
  success
}


