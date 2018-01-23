import { Component } from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";
import {TeamWaitScreen} from "../teamWaitScreen/teamWaitScreen";
import { AlertController } from 'ionic-angular';

@Component({
  selector: 'page-escape',
  templateUrl: 'escapeScreen.html'
})
export class EscapeScreenPage {

  escapeGames : EscapeGame[] = [];
  private userName = '';
  private webSocket:WebSocket;
  private teamName = '';
  private actualise;

  constructor(public alerCtrl: AlertController,public navCtrl: NavController, public navParams: NavParams) {
    this.webSocket = this.navParams.get("websocket");
    var request = {request: "GET_ESCAPE"};
    this.webSocket.send(JSON.stringify(request));

    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      for (let i = 0; i < jsonData.escapegames.length; i++)
        this.escapeGames.push({nom:jsonData.escapegames[i].nom, infos : jsonData.escapegames[i].infos});
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
    var type = this.navParams.get("type");
    var request = {request: "CREATE_PARTIE",teamname:this.teamName,username:this.userName,escapegame:game};
    if(type == "solo"){
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "ok"){
          this.navCtrl.setRoot(EnigmePage,{teamname:this.teamName,username:this.userName, type: type, name:game, websocket:this.webSocket,infos:jsonData.infos,temps:jsonData.temps,idpartie:jsonData.idpartie,nomenigme:jsonData.nom,progressions:[]});
        }
      }.bind(this);
    }
    else{
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "actualise"){
          this.actualise = jsonData.joueurs;
        }
        else{
          this.navCtrl.setRoot(TeamWaitScreen,{teamname:this.teamName,username:this.userName, type: type, name:game, websocket:this.webSocket,idpartie:jsonData.idpartie,joueurs:this.actualise});
        }
      }.bind(this);
    }
  }

  moreInformations(game : EscapeGame){
    let alert = this.alerCtrl.create({
      title : game.nom,
      message: game.infos,
      buttons: ['Ok']
    });
    alert.present()
  }

}

interface EscapeGame {
  nom : string,
  infos : string;
}
