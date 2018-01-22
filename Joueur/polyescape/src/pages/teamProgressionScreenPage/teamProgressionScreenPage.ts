import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-ProgressionTeam',
  templateUrl: 'teamProgressionScreenPage.html'
})
export class TeamProgressionScreenPage {

  players = [];
  private webSocket:WebSocket;

  constructor(public navCtrl: NavController, public navParams:NavParams, public toastCtrl: ToastController) {
    this.webSocket = navParams.get("websocket");
    for (var i = 0; i < 4; i++)
      this.players.push('Bob');

  }

  swipeEvent(e) {
    if (e.direction == 4) {
      this.navCtrl.setRoot(EnigmePage, {websocket: this.webSocket});
    }
  }



}
