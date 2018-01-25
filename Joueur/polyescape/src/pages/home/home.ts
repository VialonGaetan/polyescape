import {Component} from '@angular/core';
import {AlertController, NavController, ToastController} from 'ionic-angular';
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

  constructor(public navCtrl: NavController, public toastCtrl: ToastController, public alerCtrl: AlertController) {
    this.webSocket = new WebSocket("ws://localhost:15555/websockets/gameserver");
    this.webSocket.onerror = function (ev) {
      this.alerCtrl.create({
        title : "Error",
        message: "Le serveur du jeu est actuellement indisponible",
        buttons: ['Ok']
      }).present();
    }.bind(this);
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer votre identifiant',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }

  /**
   * Get the username specified in the right textfield
   * @returns {boolean}
   */
  verifyInputName() {
    if (this.inputName.length == 0) {
      this.presentToast();
      return false;
    }
    this.userName = this.inputName;
    this.inputName = "";
    return true;
  }


  /**
   * If you choose to play solo you directly go to the escape game choose page
   */
  goToEscapePage() {
    if (this.verifyInputName()){
      this.navCtrl.push(EscapeScreenPage,{username:this.userName,websocket:this.webSocket,type:"solo",teamname:""});
    }

  }

  /**
   * If you choose to play with partners go to the team selection page
   */
  goToTeamPage() {
    if (this.verifyInputName()){
      this.navCtrl.push(TeamScreenPage,{username:this.userName,websocket:this.webSocket});
    }
  }
}
