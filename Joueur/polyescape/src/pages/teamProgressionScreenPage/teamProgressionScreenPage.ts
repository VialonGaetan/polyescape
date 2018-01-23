import { Component } from '@angular/core';
import {Events, NavController, NavParams, ToastController} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-ProgressionTeam',
  templateUrl: 'teamProgressionScreenPage.html'
})
export class TeamProgressionScreenPage {

  players = [];
  private webSocket:WebSocket;
  private userName = '';
  private idPartie:number;
  private enigmeInfos:string ='';
  private nomEscape = '';
  private nomEnigme:string ='';
  private teamName;
  private type;
  private minutes:number = 0;
  private progressions;
  private arr = Array;

  constructor(public navCtrl: NavController, public navParams:NavParams, public events: Events) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    this.nomEnigme = this.navParams.get("nomenigme");
    this.teamName = this.navParams.get("teamname");
    this.enigmeInfos = this.navParams.get("infos");
    this.idPartie = this.navParams.get("idpartie");
    this.minutes = this.navParams.get("temps");
    this.type = this.navParams.get("type");
    this.progressions = this.navParams.get("progressions");
    this.arr = Array;
    alert(this.progressions[0].actual);
    for (var i = 0; i < 4; i++)
      this.players.push('Bob');

    this.webSocket.onmessage = function (event) {
      this.progressions = [];
      var jsonData = JSON.parse(event.data);
      for(let i = 0; i < jsonData.partieattente.length; i++){
        var progression = {username:jsonData.joueurs[i].username,total:jsonData.joueurs[i].total,actual:jsonData.joueurs[i].actual};
        this.progressions.push(progression);
      }
    }.bind(this);
  }



  swipeEvent(e) {
    if (e.direction == 4) {
      this.navCtrl.setRoot(EnigmePage, {teamname: this.teamName,
                                             username: this.userName,
                                             type: this.type,
                                             name: this.nomEscape,
                                             websocket: this.webSocket,
                                             infos: this.enigmeInfos,
                                             temps: this.minutes,
                                             idpartie: this.idPartie,
                                             nomenigme: this.nomEnigme,
                                             progressions:this.progressions
                                             });
    }
  }



}
