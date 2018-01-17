import { Component } from '@angular/core';
import {NavController, ToastController} from 'ionic-angular';
import {EscapeScreenPage} from "../escapeScreen/escapeScreen";

@Component({
  selector: 'page-team',
  templateUrl: 'teamScreen.html'
})
export class TeamScreenPage {

  private qty = '';
  private teamName = '';
  private _id: number;

  constructor(public navCtrl: NavController,public toastCtrl: ToastController) {
    this._id = 99;
  }

  joinTeam(p_id : number){
    this.navCtrl.push(EscapeScreenPage);
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
