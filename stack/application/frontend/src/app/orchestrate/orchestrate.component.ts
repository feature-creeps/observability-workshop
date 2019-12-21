import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
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
    this.retrieveImages(null);
  }

  public images;
  public displayImage;
  public transformedImage;
  private displayId: string;
  selectedLink: string;

  async retrieveImages(id: string) {
    let data = await this.http.get<Array<Image>>(environment.backend.imageholder + '/api/images').toPromise();
    if (data.length > 0) {
      document.getElementById("preview").hidden = false;
      this.images = data;

      let showId = id != null ? id : this.images[0].id;
      this.showPreview(showId);
    } else {
      document.getElementById("previewInfo").hidden = false;
      document.getElementById("previewInfoText").innerText = "No images found. Upload some ";
      document.getElementById("preview").hidden = true;
    }
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
        this.selectedLink = environment.backend.imageholder + '/api/images/' + id;
        (<HTMLSelectElement>document.querySelectorAll("#image")[0]).value = id;
      }
    }
  }

  showTransformed(data: HttpResponse<any>) {
    document.getElementById("transformedImage").hidden = false;
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.transformedImage = reader.result;
    }, false);
    if (data) {
      reader.readAsDataURL(data.body);
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

    let transformationRequest = this.buildJson(formInput);
    let transformationRequestString = JSON.stringify(transformationRequest)
    let headers = new HttpHeaders();
    headers = headers.set('Content-Type', 'application/json; charset=utf-8');
    let res;
    try {
      res = await this.http.post(environment.backend.imageorchestrator + '/api/images/transform', transformationRequestString,
        {observe: "response", headers: headers, responseType: 'blob'}).toPromise();
    } catch (e) {
      console.log(e)
      this.hideTransformed()
      OrchestrateComponent.info("Transformation failed", InfoType.danger);
      return;
    }
    this.showTransformed(<HttpResponse<any>>res)
    this.retrieveImages(this.displayId)
    if (transformationRequest.persist) {
      OrchestrateComponent.info("Transformation successful. Persisted with ID: " + res.headers.get("Image-ID"), InfoType.success)
    } else {
      OrchestrateComponent.info("Transformation successful", InfoType.success)
    }
  }

  private static info(text: string, type: InfoType) {
    let info = <HTMLInputElement>document.getElementById("info");
    info.hidden = false
    info.value = text
    info.className = "fade-in btn-block btn-" + InfoType[type] + " dima-btn"
  }

  buildJson(formInput: any) {
    let tfr: TransformationRequest = new TransformationRequest(this.displayId)

    // rotate
    let degrees = formInput.querySelectorAll("#degrees")[0].value;
    if (degrees) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.rotate;
      tr.properties.degrees = degrees
      tfr.transformations.push(tr);
    }

    // resize
    let resize = formInput.querySelectorAll("#resize")[0];
    let factor = formInput.querySelectorAll("#factor")[0].value
    if (factor) {
      let tr: Transformation = new Transformation();
      tr.type = TransformationType.resize;
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


