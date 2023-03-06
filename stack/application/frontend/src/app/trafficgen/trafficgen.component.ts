import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";

@Component({
  selector: 'app-trafficgen',
  templateUrl: './trafficgen.component.html',
  styleUrls: ['./trafficgen.component.css']
})

export class TrafficGeneratorComponent implements OnInit {

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  async uploadAll() {
    let res;
    try {
      res = await this.http.post(environment.backend.trafficgen + '/api/traffic/image/upload', null, { responseType: 'text' }).toPromise();
    } catch (e) {
      let info = document.getElementById("info");
      info.innerText = "Failed to trigger upload of all images";
      info.className = "btn btn-block btn-danger dima-btn"
    }
  }

  async sendTransformationRequest(formInput: any) {
    let res;
    try {
      res = await this.http.post(environment.backend.trafficgen + '/api/traffic/image/transform/start?transformationsPerSecond=' + formInput.transformationsPerSecond, null, { responseType: 'text' }).toPromise();
    } catch (e) {
      let info = document.getElementById("info");
      info.innerText = "Failed to trigger upload of all images";
      info.className = "btn btn-block btn-danger dima-btn"
    }
  }
}
