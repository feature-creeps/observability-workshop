import { Component } from '@angular/core';
import { InfoType } from '../../../shared/enums';
import { ImageSnippet } from '../models/image-snippet.model';
import { UploadService } from "../services/upload.service";

@Component({
  selector: 'upload.component',
  templateUrl: 'upload.component.html',
  styleUrls: ['upload.component.css']
})
export class UploadComponent {

  public selectedFile: ImageSnippet;

  public constructor(private uploadService: UploadService) {}

  public processFile(imageInput: any, nameInput: any) {
    const file: File = imageInput.files[0];
    const reader = new FileReader();

    reader.addEventListener('load', (event: any) => {
      this.selectedFile = {
        src: event.target.result,
        file: file
      }
    });

    reader.readAsDataURL(file);
    this.infoHide()
    nameInput.value = ""
  }

  public uploadImage(nameInput: any) {
    const name: string = nameInput.value;
    this.infoHide()
    if (this.selectedFile == undefined) {
      this.info("No image selected", null, InfoType.warning)
      return
    }

    this.uploadService.uploadImage(this.selectedFile.file, name).subscribe(
      (res) => {
        this.info("Upload successful", res, InfoType.success)
      },
      (err) => {
        console.log(err)
        this.info("Upload failed", null, InfoType.danger)
      })
  }

  private infoHide(): void {
    document.getElementById("info").hidden = true
  }

  private info(text: string, id: string, type: InfoType) {
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




