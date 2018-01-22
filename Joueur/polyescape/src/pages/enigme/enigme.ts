import { Component } from '@angular/core';
import {Events, NavController, NavParams, ToastController} from 'ionic-angular';
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {TeamProgressionScreenPage} from "../teamProgressionScreenPage/teamProgressionScreenPage";
import {TimerService} from "../../app/timerservice";

@Component({
  selector: 'page-enigme',
  templateUrl: 'enigme.html'
})
export class EnigmePage {

  private inputAnswer = '';
  private userName = '';
  private idPartie:number;
  private nomEnigme:string ='';
  private nomEscape = '';
  private enigmeInfos:string = '';
  private teamName;
  private webSocket:WebSocket;
  private minutes:number = 0;
  private secondes:number = 0;
  private timer:number;
  private type;

  constructor(public navCtrl: NavController,public navParams: NavParams, public toastCtrl: ToastController, public timerService: TimerService) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    this.teamName = this.navParams.get("teamname");
    this.nomEnigme = this.navParams.get("nomenigme");
    this.enigmeInfos = this.navParams.get("infos");
    this.idPartie = this.navParams.get("idpartie");
    this.minutes = this.navParams.get("temps");
    this.type = this.navParams.get("type");

    //this.timer = setInterval(this.decreaseTime.bind(this.minutes, this.secondes, this),1000);
    this.timer = setInterval(this.timerService.decreaseTime(this.minutes, this.secondes),1000);
  }

  presentToastNoAnswer() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer une réponse',
      duration: 3000,
      position: 'bottom'
    });

    toast.present();
  }

  presentToastIncorectAnswer() {
    let toast = this.toastCtrl.create({
      message: 'Réponse incorrecte',
      duration: 3000,
      position: 'bottom'
    });

    toast.present();
  }


  decreaseTime(minutes, secondes){
    if(secondes == 0 && minutes != 0){
      secondes = 59;
      minutes--;
    }
    else if(secondes == 0 && minutes == 0){
      clearInterval(this.timer);
      this.navCtrl.push(EndGameScreenPage,{score:0});
    }
    else {
      secondes--;
    }

  }

  submitAnswer() {
    if (this.inputAnswer.length == 0){
      this.presentToastNoAnswer();
    }
    else {
      var request = {request:"RESPONSE", "idpartie":this.idPartie, "reponse":this.inputAnswer, username:this.userName};
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "ko") {
          this.presentToastIncorectAnswer();
        }
        else if(jsonData.reponse == "ok"){
          this.enigmeInfos = jsonData.infos;
          this.nomEnigme = jsonData.nom;
          this.inputAnswer = "";
        }
        else if(jsonData.reponse == "finish"){
          clearInterval(this.timer);
          this.navCtrl.setRoot(EndGameScreenPage,{score:jsonData.score});
        }
      }.bind(this);
    }
  }

  swipeEvent(e) {
    if (e.direction == 2 && this.type != "solo") {
      this.navCtrl.setRoot(TeamProgressionScreenPage, {teamname: this.teamName,
                                                            username: this.userName,
                                                            type: this.type,
                                                            name:this.nomEscape,
                                                            websocket:this.webSocket,
                                                            infos:this.enigmeInfos,
                                                            idpartie:this.idPartie,
                                                            temps: this.minutes,
                                                            nomenigme: this.nomEnigme});
    }
  }

}
