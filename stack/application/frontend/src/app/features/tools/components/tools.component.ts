import {Component} from '@angular/core';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-tools',
  templateUrl: './tools.component.html',
  styleUrls: ['./tools.component.css']
})
export class ToolsComponent {

  constructor() {
  }

  getToolURL(configValue: string) {
    if (configValue.startsWith(":")) {
      return window.location.protocol + "//" + window.location.hostname + configValue;
    } else {
      return configValue;
    }
  }

  environment = environment;
}
