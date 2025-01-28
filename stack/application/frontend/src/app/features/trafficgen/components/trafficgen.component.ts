import { Component } from '@angular/core';
import { TrafficgenService } from '../services/trafficgen.service';
import { InfoType } from '../../../shared/enums';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-trafficgen',
  templateUrl: './trafficgen.component.html',
  styleUrls: ['./trafficgen.component.css']
})

export class TrafficGeneratorComponent {

  public trafficgenForm = new FormGroup({
    transformationsPerSecond: new FormControl<number | null>(null),
  });

  public constructor(private readonly trafficgenService: TrafficgenService) {}

  async uploadAll() {
    try {
      await this.trafficgenService.uploadAll();
      this.info("Upload of all images triggered successfully.", InfoType.success);
    } catch (e) {
      console.log(e)
      this.info("Failed to trigger upload of all images", InfoType.danger);
    }
  }

  async sendTransformationRequest() {
    try {
      let transformationsPerSecond = this.trafficgenForm.value.transformationsPerSecond;
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
