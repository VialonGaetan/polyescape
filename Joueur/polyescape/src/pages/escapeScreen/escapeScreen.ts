import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-escape',
  templateUrl: 'escapeScreen.html'
})
export class EscapeScreenPage {

  escapeGames = [];

  constructor(public navCtrl: NavController) {

    var webSocket = new WebSocket("ws://localhost:15555/websockets/gameserver");
    webSocket.onopen = function (ev) {
      var request = {request: "GET_ESCAPE"};
      webSocket.send(JSON.stringify(request));
    };

    webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);

      for (var i = 0; i < jsonData.escapesGame.length; i++)
        this.escapeGames.push(jsonData.escapesGame[i].nom);
    }.bind(this);
  }

  startGame() {
    this.navCtrl.push(EnigmePage);
  }


}
