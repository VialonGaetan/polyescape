import { Component } from '@angular/core';
import {NavController, ToastController} from 'ionic-angular';
import {EscapeScreenPage} from "../escapeScreen/escapeScreen";
import {TeamScreenPage} from "../teamScreen/teamScreen";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  private inputName = '';
  private userName = '';
  private webSocket:WebSocket;

  constructor(public navCtrl: NavController, public toastCtrl: ToastController) {
    this.webSocket = new WebSocket("ws://localhost:15555/websockets/gameserver");
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer votre identifiant',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }

  verifyInputName() {
    if (this.inputName.length == 0) {
      this.presentToast();
      return false;
    }
    this.userName = this.inputName;
    this.inputName = "";
    return true;
  }

  goToEscapePage() {
    if (this.verifyInputName()){
      this.navCtrl.push(EscapeScreenPage,{username:this.userName,websocket:this.webSocket});
    }

  }

  goToTeamPage() {
    if (this.verifyInputName()){
      this.navCtrl.push(TeamScreenPage,{username:this.userName,websocket:this.webSocket});
    }
  }
}
