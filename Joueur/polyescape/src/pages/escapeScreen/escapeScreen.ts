import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {EnigmePage} from "../enigme/enigme";

@Component({
  selector: 'page-escape',
  templateUrl: 'escapeScreen.html'
})
export class EscapeScreenPage {

  escapeGames: any;

  constructor(public navCtrl: NavController) {
    this.escapeGames = [
      'Escape Game 1',
      'Escape Game 2',
      'Escape Game 3'
    ];
  }

  startGame() {
    this.navCtrl.push(EnigmePage);
  }
}
