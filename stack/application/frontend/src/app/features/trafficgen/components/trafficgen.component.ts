import { Component } from '@angular/core';
import { TrafficgenService } from '../services/trafficgen.service';
import { InfoType } from '../../../shared/enums';

@Component({
  selector: 'app-trafficgen',
  templateUrl: './trafficgen.component.html',
  styleUrls: ['./trafficgen.component.css']
})

export class TrafficGeneratorComponent {

  public constructor(private readonly trafficgenService: TrafficgenService) {
  }

  async uploadAll() {
    try {
      await this.trafficgenService.uploadAll();
      this.info("Upload of all images triggered successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      this.info("Failed to trigger upload of all images", InfoType.danger);
    }
  }

  async sendTransformationRequest(formInput: any) {
    try {
      let transformationsPerSecond = formInput.querySelectorAll("#transformationsPerSecond")[0].value;
      if (!transformationsPerSecond) {
        transformationsPerSecond = 1
        console.log("Defaulting to 1 transformation request per second.")
      }
      await this.trafficgenService.sendTransformationRequest(transformationsPerSecond);
      this.info("Transformation traffic request sent successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      this.info("Failed to send transformation traffic request", InfoType.danger);
    }
  }

  async stopTransformationTraffic() {
    try {
      await this.trafficgenService.stopTransformationTraffic();
      this.info("Stop transformation request sent successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      this.info("Failed to send stop transformation traffic request", InfoType.danger);
    }
  }

  private info(text: string, type: InfoType) {
    let info = <HTMLInputElement>document.getElementById("info");
    info.hidden = false
    info.value = text
    info.className = "fade-in btn-block btn-" + InfoType[type] + " dima-btn"
  }
}
