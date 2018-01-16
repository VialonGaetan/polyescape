import { Component } from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-escape',
  templateUrl: 'escapeScreen.html'
})
export class EscapeScreenPage {

  escapeGames = [];
  private userName = '';
  private webSocket:WebSocket;

  constructor(public navCtrl: NavController, public navParams: NavParams) {

    this.webSocket = new WebSocket("ws://localhost:15555/websockets/gameserver");
    var webs = this.webSocket;
    this.webSocket.onopen = function (ev) {
      var request = {request: "GET_ESCAPE"};
      webs.send(JSON.stringify(request));
    };

    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      for (var i = 0; i < jsonData.escapesGame.length; i++)
        this.escapeGames.push(jsonData.escapesGame[i].nom);
    }.bind(this);
    this.userName = this.navParams.get("username");
  }

  startGame(game : any) {
    this.navCtrl.push(EnigmePage,{username:this.userName,name:game,websocket:this.webSocket});
  }

}
