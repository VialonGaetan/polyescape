import { Component } from '@angular/core';
import {NavController, ToastController} from 'ionic-angular';

@Component({
  selector: 'page-enigme',
  templateUrl: 'enigme.html'
})
export class EnigmePage {

  private inputAnswer = '';

  constructor(public navCtrl: NavController, public toastCtrl: ToastController) {

  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer une réponse',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }

  submitAnswer() {
    if (this.inputAnswer.length == 0){
      this.presentToast();
    }
  }
}
