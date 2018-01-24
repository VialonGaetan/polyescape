import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
@Component({
  selector: 'page-final',
  templateUrl: 'finalScreen.html'
})
export class FinalScreenPage {

  private userName = '';
  private webSocket:WebSocket;
  private progressions = [];
  private score;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.userName = this.navParams.get("username");
    alert(this.userName);
    this.progressions = this.navParams.get("progressions");
    this.webSocket = navParams.get("websocket");
    this.score = navParams.get("score");
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "success"){
        this.updateProgression(jsonData);
      }
      else if(jsonData.reponse == "finish"){
        clearInterval(this.timer);
        this.navCtrl.setRoot(EndGameScreenPage,{score:this.score});
      }
    }.bind(this);
  }

  array(n:number){
    var array = new Array();
    for(let i = 0; i < n; i++){
      array.push(i);
    }
    return array;
  }

  updateProgression(jsonData:any){
    this.progressions = [];
    for(let i = 0; i < jsonData.joueurs.length; i++) {
      var progression = {
        username: jsonData.joueurs[i].username,
        total: jsonData.joueurs[i].total,
        actual: jsonData.joueurs[i].actual
      };
      this.progressions.push(progression);
    }
  }
}
