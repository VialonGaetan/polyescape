import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {LocalNotifications} from "@ionic-native/local-notifications";

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
  private webSocket:WebSocket;
  private minutes:number = 0;
  private secondes:number = 0;
  private timer:number;


  constructor(public navCtrl: NavController, public navParams: NavParams, public toastCtrl: ToastController, public localNotifications: LocalNotifications) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    var request = {request: "CREATE_PARTIE", type:"SOLO",username:this.userName,escapegame:this.nomEscape};
    this.webSocket.send(JSON.stringify(request));
    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "ok"){
        this.nomEnigme = jsonData.nom;
        this.enigmeInfos = jsonData.infos;
        this.idPartie = jsonData.idpartie;
        this.minutes = jsonData.temps;
        this.timer = setInterval(this.decreaseTime.bind(this),1000);
      }
    }.bind(this);


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

  presentToastHelpSend() {
    let toast = this.toastCtrl.create({
      message: 'Demande envoyée',
      duration: 3000,
      position: 'bottom'
    });

    toast.present();
  }

  scheduleNotification(text) {
    this.localNotifications.schedule({
      id: 1,
      title: 'Aide',
      text: text
    });
  }

  decreaseTime(){
    if(this.secondes == 0 && this.minutes != 0){
      this.secondes = 59;
      this.minutes--;
    }
    else if(this.secondes == 0 && this.minutes == 0){
      clearInterval(this.timer);
      this.navCtrl.push(EndGameScreenPage,{score:0});
    }
    else {
      this.secondes--;
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

  requestHelp() {
    var request = {request:"HELP", idpartie:this.idPartie, username:this.userName, enigme: this.enigmeInfos};
    this.webSocket.send(JSON.stringify(request));
    this.presentToastHelpSend();


    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      this.scheduleNotification(jsonData.description);
    }.bind(this);
  }
}
