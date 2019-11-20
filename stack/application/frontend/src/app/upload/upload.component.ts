import {Component} from '@angular/core';
import {UploadService} from "./upload.service";

class ImageSnippet {
  constructor(public src: string, public file: File) {
  }
}

@Component({
  selector: 'upload.component',
  templateUrl: 'upload.component.html',
  styleUrls: ['upload.component.css']
})
export class UploadComponent {

  selectedFile: ImageSnippet;

  constructor(private uploadService: UploadService) {
  }

  processFile(imageInput: any, nameInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.selectedFile = new ImageSnippet(event.target.result, file);
    });

    reader.readAsDataURL(file);
    UploadComponent.infoHide()
    nameInput.value = ""
  }

  uploadImage(nameInput: any) {
    const name: string = nameInput.value;
    UploadComponent.infoHide()
    if (this.selectedFile == undefined) {
      UploadComponent.info("No image selected", null, InfoType.warning)
      return
    }

    this.uploadService.uploadImage(this.selectedFile.file, name).subscribe(
      (res) => {
        UploadComponent.info("Upload successful", res, InfoType.success)
      },
      (err) => {
        console.log(err)
        UploadComponent.info("Upload failed", null, InfoType.danger)
      })
  }

  private static infoHide() {
    document.getElementById("info").hidden = true
  }

  private static info(text: string, id: string, type: InfoType) {
    let info = <HTMLInputElement>document.getElementById("info");
    info.hidden = false
    if (id != null) {
      info.value = text + " with id: " + id
    } else {
      info.value = text
    }
    info.className = "fade-in btn-block btn-" + InfoType[type] + " dima-btn"
  }
}

enum InfoType {
  warning,
  danger,
  success
}


