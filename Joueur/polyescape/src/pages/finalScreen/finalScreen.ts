import { Component } from '@angular/core';
import {NavController, ToastController} from 'ionic-angular';
@Component({
  selector: 'page-final',
  templateUrl: 'finalScreen.html'
})
export class FinalScreenPage {

  private inputName = '';
  private userName = '';
  private webSocket:WebSocket;

  constructor(public navCtrl: NavController, public toastCtrl: ToastController) {
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer votre identifiant',
      duration: 3000,
      position: 'bottom'
    });
    toast.present();
  }
}
