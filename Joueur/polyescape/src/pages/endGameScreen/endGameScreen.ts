import { Component } from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {HomePage} from "../home/home";

@Component({
  selector: 'page-end',
  templateUrl: 'endGameScreen.html'
})
export class EndGameScreenPage {

  private score;

  constructor(public navCtrl: NavController,public navParams:NavParams) {
    this.score = navParams.get("score");
  }

  goToHomePage() {
    this.navCtrl.push(HomePage);
  }
}
