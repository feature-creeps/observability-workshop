import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { environment } from "../../environments/environment";

@Component({
  selector: 'app-trafficgen',
  templateUrl: './trafficgen.component.html',
  styleUrls: ['./trafficgen.component.css']
})

export class TrafficGeneratorComponent {

  constructor(private http: HttpClient) {
  }

  async uploadAll() {
    try {
      await this.http.post(environment.backend.trafficgen + '/api/traffic/image/upload', null, { responseType: 'text' }).toPromise();
      TrafficGeneratorComponent.info("Upload of all images triggered successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      TrafficGeneratorComponent.info("Failed to trigger upload of all images", InfoType.danger);
    }
  }

  async sendTransformationRequest(formInput: any) {
    try {
      let transformationsPerSecond = formInput.querySelectorAll("#transformationsPerSecond")[0].value;
      if (!transformationsPerSecond) {
        transformationsPerSecond = 1
        console.log("Defaulting to 1 transformation request per second.")
      }
      await this.http.post(environment.backend.trafficgen + '/api/traffic/image/transform/start?transformationsPerSecond=' + transformationsPerSecond, null, { responseType: 'text' }).toPromise();
      TrafficGeneratorComponent.info("Transformation traffic request sent successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      TrafficGeneratorComponent.info("Failed to send transformation traffic request", InfoType.danger);
    }
  }

  async stopTransformationTraffic() {
    try {
      await this.http.post(environment.backend.trafficgen + '/api/traffic/image/transform/stop', null, { responseType: 'text' }).toPromise();
      TrafficGeneratorComponent.info("Stop transformation request sent successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      TrafficGeneratorComponent.info("Failed to send stop transformation traffic request", InfoType.danger);
    }
  }

  private static info(text: string, type: InfoType) {
    let info = <HTMLInputElement>document.getElementById("info");
    info.hidden = false
    info.value = text
    info.className = "fade-in btn-block btn-" + InfoType[type] + " dima-btn"
  }
}

enum InfoType {
  warning,
  danger,
  success
}
