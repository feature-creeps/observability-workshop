import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { environment } from "../../../../../environments/environment";
import { OrchestrateService } from '../../services/orchestrate.service';
import { InfoType } from '../../../../shared/enums'
import { Image } from '../../../../shared/models'
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-orchestrate',
  templateUrl: './orchestrate.component.html',
  styleUrls: ['./orchestrate.component.css']
})
export class OrchestrateComponent implements OnInit {
  public manipulateForm = new FormGroup({
    rotation: new FormControl<number | null>(null),
    resize: new FormControl<number | null>(null),
    persist: new FormGroup({
      enable: new FormControl<boolean>(false),
      imageName: new FormControl<string | null>(null),
    }),
    enableGrayscale: new FormControl<boolean>(false),
    flipImage: new FormGroup({
      horizontal: new FormControl<boolean>(false),
      vertical: new FormControl<boolean>(false),
    }),
    selectedImageId: new FormControl<string | null>(null)
  });

  public transformedImageVisible: boolean = false;
  public previewVisible: boolean = true;
  public previewInfoVisible: boolean = true;

  public constructor(private readonly orchestrateService: OrchestrateService) {}

  ngOnInit(): void {
    this.retrieveImages(null);
  }

  public images: Array<Image> = [];
  public displayImage;
  public transformedImage;
  private displayId: string;
  public selectedLink: string;

  async retrieveImages(id: string) {
    let data = await this.orchestrateService.getImages();
    if (data.length > 0) {
      this.previewVisible = true;
      this.images = data;

      const showId = id != null ? id : this.images[0].id;
      this.showPreview(showId);
    } else {
      this.previewInfoVisible = true;
      this.previewVisible = false;
    }
  }

  async showPreview(id: string) {
    let data = await this.orchestrateService.getImageById(id);
    if (data != null) {
      this.displayId = id;
      const reader = new FileReader();
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
    this.transformedImageVisible = true;
    const reader = new FileReader();
    reader.addEventListener("load", () => {
      this.transformedImage = reader.result;
    }, false);
    if (data) {
      reader.readAsDataURL(data.body);
    }
  }

  private hideTransformed() {
    this.transformedImage = "";
    this.transformedImageVisible = false;
  }

  //=======
  async sendRequest() {
    this.hideTransformed()
    this.hideInfo()

    if (this.displayId == undefined) {
      this.info("No image selected", InfoType.warning);
      return
    }
    const transformationRequest = this.buildJson(this.manipulateForm.value as ManipulateFormData);
    const transformationRequestString = JSON.stringify(transformationRequest)

    let res: any;
    try {
      res = await this.orchestrateService.sendTransformationRequest(transformationRequestString)
    } catch (e) {
      console.log(e)
      this.hideTransformed()
      this.info("Transformation failed", InfoType.danger);
      return;
    }
    this.showTransformed(<HttpResponse<any>>res)
    this.retrieveImages(this.displayId)
    if (transformationRequest.persist) {
      this.info("Transformation successful. Persisted with ID: " + res.headers.get("Image-ID"), InfoType.success)
    } else {
      this.info("Transformation successful", InfoType.success)
    }
  }

  private info(text: string, type: InfoType) {
    const info = <HTMLInputElement>document.getElementById("info");
    info.hidden = false
    info.value = text
    info.className = "fade-in btn-block btn-" + InfoType[type] + " dima-btn"
  }

  private buildJson(formInput: ManipulateFormData): TransformationRequest {
    const tfr: TransformationRequest = {
      imageId: this.displayId,
      transformations: [],
      name: "",
      persist: false
    };

    // rotate
    if (formInput.rotation) {
      const tr: Transformation = {
        type: TransformationType.rotate,
        properties: {
          degrees: formInput.rotation
        }
      };
      tfr.transformations.push(tr);
    }

    // resize
    if (formInput.resize) {
      const tr: Transformation = {
        type: TransformationType.resize,
        properties: {
          factor: formInput.resize
        }
      };
      tfr.transformations.push(tr);
    }

    // grayscale
    if (formInput.enableGrayscale) {
      const tr: Transformation = {
        type: TransformationType.grayscale,
        properties: {}
      };
      tfr.transformations.push(tr);
    }

    // flip
    if (formInput.flipImage.vertical || formInput.flipImage.horizontal) {
      const tr: Transformation = {
        type: TransformationType.flip,
        properties: {
          vertical: formInput.flipImage.vertical,
          horizontal: formInput.flipImage.horizontal,
        }
      }
      tfr.transformations.push(tr);
    }

    // persist
    if (formInput.persist.enabled) {
      tfr.name = formInput.persist.imageName
    }

    return tfr;
  }

  private hideInfo() {
    document.getElementById("info").hidden = true
  }
}

enum TransformationType {
  rotate = "rotate",
  grayscale = "grayscale",
  resize = "resize",
  flip = "flip"
}

interface Transformation {
  type: TransformationType;
  properties: {};
}

interface TransformationRequest {
  imageId: string;
  transformations: Array<Transformation>;
  persist: boolean;
  name: string;
}

interface ManipulateFormData {
  rotation: number | null;
  resize: number | null;
  persist: {
    enabled: boolean;
    imageName: string | null;
  }
  enableGrayscale: boolean,
  flipImage: {
    horizontal: boolean,
    vertical: boolean,
  };
  selectedImageId: string | null
}
