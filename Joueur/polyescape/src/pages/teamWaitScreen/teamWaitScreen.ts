import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';

@Component({
  selector: 'page-WaitTeam',
  templateUrl: 'teamWaitScreen.html'
})
export class TeamWaitScreen {

  constructor(public navCtrl: NavController, public navParams:NavParams, public toastCtrl: ToastController) {
  }
}
