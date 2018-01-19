import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-ProgressionTeam',
  templateUrl: 'teamProgressionScreenPage.html'
})
export class TeamProgressionScreenPage {

  players = [];

  constructor(public navCtrl: NavController, public navParams:NavParams, public toastCtrl: ToastController) {
    for (var i = 0; i < 4; i++)
      this.players.push('Bob');

  }



}
