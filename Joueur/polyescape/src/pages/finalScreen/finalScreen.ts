import {Component} from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {finalEnigmeTeamScreen} from "../finalEnigmeTeam/finalEnigmeTeamScreen";

@Component({
  selector: 'page-final',
  templateUrl: 'finalScreen.html'
})
export class FinalScreenPage {

  private inputAnswer = '';
  private userName = '';
  private idPartie:number;
  private nomEscape = '';
  private teamName;
  private webSocket:WebSocket;
  private minutes:number;
  private secondes:number;
  private timer:number;
  private progressions;
  private indices;
  private score;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.userName = navParams.get("username");
    this.webSocket = navParams.get("websocket");
    this.teamName = this.navParams.get("teamname");
    this.idPartie = this.navParams.get("idpartie");
    this.minutes = this.navParams.get("minutes");
    this.secondes = this.navParams.get("secondes");
    this.progressions = this.navParams.get("progressions");
    this.indices = this.navParams.get("indices");
    this.score = this.navParams.get("score");
    this.timer = setInterval(this.decreaseTime.bind(this),1000);
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "success"){
        this.updateProgression(jsonData);
      }
      else if(jsonData.reponse == "finish"){
        clearInterval(this.timer);
        this.navCtrl.setRoot(EndGameScreenPage,{score:this.score});
      }
      else if(jsonData.reponse == "enigme"){
        clearInterval(this.timer);
        this.navCtrl.setRoot(finalEnigmeTeamScreen, {
          score: this.score,
          minutes: this.minutes,
          secondes: this.secondes,
          websocket: this.webSocket,
          infos: jsonData.infos,
          nomenigme: jsonData.nom,
          idpartie: this.idPartie,
          progressions: this.progressions,
          teamname: this.teamName,
          username: this.userName,
          indices: this.indices
        });
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

  pad2(number) {
    return (number < 10 ? '0' : '') + number
  }

  decreaseTime(){
    if(this.secondes == 0 && this.minutes != 0){
      this.secondes = 59;
      this.minutes--;
    }
    else if(this.secondes == 0 && this.minutes == 0){
      clearInterval(this.timer);
      this.navCtrl.push(EndGameScreenPage,{score: this.score});
    }
    else {
      this.secondes--;
    }

  }
}
