import {Component, OnInit} from '@angular/core';
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-random',
  templateUrl: './random.component.html',
  styleUrls: ['./random.component.css']
})
export class RandomComponent implements OnInit {

  constructor() {
  }

  ngOnInit() {
    this.changeImage()
  }

  randomImageUrl: string = environment.backend.imageholder + '/api/images/random';

  changeImage() {
    document.getElementById("image").setAttribute("src", this.randomImageUrl + '?decache=' + new Date().getTime())
    // todo: nice to have would be displaying the id of the randomly selected image
  }
}
