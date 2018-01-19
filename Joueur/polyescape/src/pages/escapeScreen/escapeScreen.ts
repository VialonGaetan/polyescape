import { Component } from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";
import {TeamWaitScreen} from "../teamWaitScreen/teamWaitScreen";

@Component({
  selector: 'page-escape',
  templateUrl: 'escapeScreen.html'
})
export class EscapeScreenPage {

  escapeGames = [];
  private userName = '';
  private webSocket:WebSocket;
  private teamName = '';
  private actualise;

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
    if(this.navParams.get("type") == "solo"){
      this.navCtrl.push(EnigmePage,{teamname:this.teamName,username:this.userName, name:game, websocket:this.webSocket});
    }
    else{
      var request = {request: "CREATE_PARTIE",teamname:this.teamName,username:this.userName,escapegame:game};
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "actualise"){
          this.actualise = jsonData.joueurs;
        }
        else{
          this.navCtrl.push(TeamWaitScreen,{teamname:this.teamName,username:this.userName, name:game, websocket:this.webSocket,idpartie:jsonData.idpartie,joueurs:this.actualise});
        }
      }.bind(this);
    }
  }

}
