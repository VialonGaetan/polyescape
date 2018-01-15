import { Component } from '@angular/core';
import { NavController } from 'ionic-angular';
import {HomePage} from "../home/home";

@Component({
  selector: 'page-end',
  templateUrl: 'endGameScreen.html'
})
export class EndGameScreenPage {

  constructor(public navCtrl: NavController) {

  }

  goToHomePage() {
    this.navCtrl.push(HomePage);
  }

}
