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
  private teamName = '';

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.webSocket = this.navParams.get("websocket");
    var request = {request: "GET_ESCAPE"};
    this.webSocket.send(JSON.stringify(request));

    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      for (let i = 0; i < jsonData.escapegames.length; i++)
        this.escapeGames.push(jsonData.escapegames[i].nom);
    }.bind(this);
    this.userName = this.navParams.get("username");
    this.teamName = this.navParams.get("teamname");
  }

  logo(){
    if(this.navParams.get("type") == "solo"){
      return "person"
    }
    else {
      return "people"
    }
  }

  name(){
    if(this.navParams.get("type") == "solo"){
      return this.userName;
    }
    else {
      return this.teamName;
    }
  }

  startGame(game : any) {
    this.webSocket.send(JSON.stringify({request:"CREATE_PARTIE",teamname:this.userName,escapegame:game}))
    this.navCtrl.setRoot(EnigmePage,{username:this.userName, name:game, websocket:this.webSocket});
  }

}
