
import {NavController, NavParams} from 'ionic-angular';
import {HomePage} from "../home/home";
import {ViewChildren, Component, ViewChild, ElementRef} from '@angular/core';

@Component({
  selector: 'page-end',
  templateUrl: 'endGameScreen.html'
})
export class EndGameScreenPage {


  private score : number;


  constructor(public navCtrl: NavController,public navParams:NavParams) {
    this.score = navParams.get("score");

  }
  goToHomePage() {
    this.navCtrl.setRoot(HomePage);
  }
}
