import { Component } from '@angular/core';
import {EchoService} from "./echo.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Angular Test Page';
  echoText = '';

  constructor( private echoService: EchoService) {}

  onClick(input: string) {
    this.echoService.getEcho(input).subscribe(result =>
      this.echoText = result.name
    )
  }


}
