import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EscapeScreenPage} from "../escapeScreen/escapeScreen";
import {TeamWaitScreen} from "../teamWaitScreen/teamWaitScreen"
import {TeamProgressionScreenPage} from "../teamProgressionScreenPage/teamProgressionScreenPage";

@Component({
  selector: 'page-team',
  templateUrl: 'teamScreen.html'
})
export class TeamScreenPage {

  private qty = '';
  private userName = '';
  private teamName = '';
  private _id: number;
  private webSocket:WebSocket;

  constructor(public navCtrl: NavController,public navParams:NavParams,public toastCtrl: ToastController) {
    this._id = 99;
    this.userName = this.navParams.get("username");
    this.webSocket = this.navParams.get("websocket");
    this.webSocket.send(JSON.stringify({request:"GET_SALONS"}));
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
    }
  }

  joinTeam(p_id : number){
    this.navCtrl.push(TeamProgressionScreenPage);
  }

  createTeam(){
    if(this.qty.length == 0){
      this.presentToast();
    }
    else {
      this.teamName = this.qty;
      this.qty = "";
      this.navCtrl.push(EscapeScreenPage)
    }
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer un nom valide (au moins 1 caractère)',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }
}
