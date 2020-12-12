import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'DIMa: Distributed Image Manipulation';

  ngAfterViewInit(): void {
    this.setUserCookie()
  }

  private setUserCookie() {
    const cookieKey = "user";
    const path = "/";
    const now = new Date();
    var yesterday = new Date()
    yesterday.setDate(now.getDay() - 1)
    const random = now.getTime() - yesterday.getTime();

    function randomUserName() {
      return "user " + random;
    }

    function hasValidCookie(userKey) {
      var cookies = document.cookie.split(";")
      for (var index = 0; index < cookies.length; index++) {
        var id = cookies[index].split('=')[0];
        if (id == userKey) {
          return true;
        }
      }
      return false;
    }

    if (hasValidCookie(cookieKey)) {
      return;
    }

    var userReturn = window.prompt("Enter your username", randomUserName());
    var username = userReturn != null ? userReturn : "user" + " " + random;

    var expires = now.setDate(now.getDay() + 1);

    var cookie = cookieKey + "=" + username + "; path=" + path + "; expires=" + expires + ";";

    document.cookie = cookie;
  }

  ngOnInit(): void {
  }
}
