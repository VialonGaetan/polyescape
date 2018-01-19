import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';

@Component({
  selector: 'page-WaitTeam',
  templateUrl: 'teamWaitScreen.html'
})
export class TeamWaitScreen {

  private userName ='';
  private webSocket:WebSocket;
  private teamName = '';
  private escapeName = '';
  private players = [];
  private idpartie = '';

  constructor(public navCtrl: NavController, public navParams:NavParams, public toastCtrl: ToastController) {
    this.webSocket = this.navParams.get("websocket");
    this.userName = this.navParams.get("username");
    this.teamName = this.navParams.get("teamname");
    this.escapeName = navParams.get("name");
    this.idpartie = navParams.get("idpartie");
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "actualise"){
          this.actualise(jsonData.joueurs);
        }
    }.bind(this);
    var jsonJoueurs = navParams.get("joueurs");
    this.actualise(jsonJoueurs);
  }

  actualise(jsonJoueurs:any){
    this.players = [];
    for (let i = 0; i < jsonJoueurs.length; i++) {
      var player = {name: jsonJoueurs[i].nom, ready: jsonJoueurs[i].ready};
      this.players.push(player);
    }
  }

  ready(){
    this.webSocket.send(JSON.stringify({request:"SET_READY",idpartie:this.idpartie,username:this.userName}));
  }

  setIcon(ready:boolean){
    if(ready){
      return "checkmark-circle";
    }
    else {
      return "close-circle";
    }
  }
}
