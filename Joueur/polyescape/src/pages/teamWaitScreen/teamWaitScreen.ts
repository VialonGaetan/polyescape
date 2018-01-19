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
  private playersNames = [];
  private playersReady = [];
  private idpartie = '';

  constructor(public navCtrl: NavController,public navParams:NavParams,public toastCtrl: ToastController) {
    this.webSocket = this.navParams.get("websocket");
    this.userName = this.navParams.get("username");
    this.teamName = this.navParams.get("teamname");
    this.escapeName = navParams.get("name");
    this.idpartie = navParams.get("idpartie");
    var jsonJoueurs = navParams.get("joueurs");
    for (let i = 0; i < jsonJoueurs.length; i++){
       this.playersNames.push(jsonJoueurs[i].nom);
       this.playersReady.push(jsonJoueurs[i].ready);
    }
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
