import {Component, OnInit} from '@angular/core';

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

  changeImage() {
    document.getElementById("image").setAttribute("src", "http://localhost:8080/api/images/random?decache=" + new Date().getTime())
    // todo: nice to have would be displaying the id of the randomly selected image
  }
}
