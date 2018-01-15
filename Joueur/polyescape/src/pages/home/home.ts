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

  constructor(public navCtrl: NavController, public toastCtrl: ToastController) {

  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer un nom',
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
    return true;
  }

  goToEscapePage() {
    if (this.verifyInputName())
      this.navCtrl.push(EscapeScreenPage);
  }

  goToTeamPage() {
    if (this.verifyInputName())
      this.navCtrl.push(TeamScreenPage);
  }

}
