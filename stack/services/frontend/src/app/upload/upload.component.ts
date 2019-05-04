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
    document.getElementById("info").hidden = true
    nameInput.value = ""
  }

  uploadImage(nameInput: any) {
    const name: string = nameInput.value;

    if (this.selectedFile == undefined) {
      let info = document.getElementById("info");
      info.hidden = false
      info.innerText = "No image selected"
      info.className = "btn btn-sm btn-block btn-warning"
      return
    }

    this.uploadService.uploadImage(this.selectedFile.file, name).subscribe(
      (res) => {
        let info = document.getElementById("info");
        info.hidden = false
        info.innerText = "Upload successful"
        info.className = "btn btn-sm btn-block btn-success"
      },
      (err) => {
        console.log(err)
        let info = document.getElementById("info");
        info.hidden = false
        info.innerText = "Upload failed"
        info.className = "btn btn-sm btn-block btn-danger"
      })
  }
}
