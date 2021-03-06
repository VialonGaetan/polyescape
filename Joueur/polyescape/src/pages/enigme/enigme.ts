import {Component} from '@angular/core';
import {AlertController, NavController, NavParams, ToastController} from 'ionic-angular';
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
  private teamName;
  private webSocket:WebSocket;
  private minutes:number = 0;
  private secondes:number = 0;
  private timer:number;
  private type;
  private score:number = 0;
  private nbTry:number = 0;
  private nbEnigma:number;

  constructor(public alerCtrl: AlertController, public navCtrl: NavController, public navParams: NavParams, public toastCtrl: ToastController, public localNotifications: LocalNotifications) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    this.teamName = this.navParams.get("teamname");
    this.nomEnigme = this.navParams.get("nomenigme");
    this.enigmeInfos = this.navParams.get("infos");
    this.idPartie = this.navParams.get("idpartie");
    this.minutes = this.navParams.get("temps");
    this.type = this.navParams.get("type");
    this.timer = setInterval(this.decreaseTime.bind(this),1000);

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
      this.navCtrl.push(EndGameScreenPage,{score: this.score});
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
          this.nbTry++;
          this.presentToastIncorectAnswer();
        }
        else if(jsonData.reponse == "ok"){
          if (this.nbTry <= 3)
            this.score++;
          this.enigmeInfos = jsonData.infos;
          this.nomEnigme = jsonData.nom;
          this.inputAnswer = "";
          this.nbTry = 0;
        }
        else if(jsonData.reponse == "finish"){
          clearInterval(this.timer);
          this.navCtrl.setRoot(EndGameScreenPage,{score: this.score});
        }
      }.bind(this);
    }
  }

  pad2(number) {
    return (number < 10 ? '0' : '') + number
  }

  requestHelp() {
    var request = {request:"HELP", idpartie:this.idPartie, username:this.userName, enigme: this.enigmeInfos};
    this.webSocket.send(JSON.stringify(request));
    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
      if (jsonData.reponse == "ko"){
        this.alerCtrl.create({
          title : "Erreur",
          message: "Aucun maitre du jeu disponible",
          buttons: ['Ok']
        }).present();
      }else if (jsonData.reponse == "ok"){
        this.presentToastHelpSend();
      }
      else {
        this.alerCtrl.create({
          title : "Indice",
          message: jsonData.description,
          buttons: ['Ok']
        }).present();
        this.scheduleNotification(jsonData.description);
      }

    }.bind(this);
  }
}
