import {Component} from "@angular/core";
import {AlertController, NavController, NavParams, ToastController} from "ionic-angular";
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {FinalScreenPage} from "../finalScreen/finalScreen";
import {LocalNotifications} from "@ionic-native/local-notifications";


@Component({
  selector: 'page-finalEnigmeTeam',
  templateUrl: 'finalEnigmeTeamScreen.html'
})
export class finalEnigmeTeamScreen {

  private inputAnswer = '';
  private userName = '';
  private idPartie: number;
  private nomEnigme: string = '';
  private nomEscape = '';
  private enigmeInfos: string = '';
  private teamName;
  private webSocket: WebSocket;
  private minutes: number = 0;
  private secondes: number = 0;
  private timer: number;
  private progressions;
  private indices;
  private score: number;
  private nbTry: number = 0;

  constructor(public navCtrl: NavController, public navParams: NavParams, public toastCtrl: ToastController, public localNotifications: LocalNotifications) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    this.teamName = this.navParams.get("teamname");
    this.nomEnigme = this.navParams.get("nomenigme");
    this.nomEscape = this.navParams.get("name");
    this.enigmeInfos = this.navParams.get("infos");
    this.idPartie = this.navParams.get("idpartie");
    this.minutes = this.navParams.get("minutes");
    this.secondes = this.navParams.get("secondes");
    this.score = this.navParams.get("minutes");
    this.indices = this.navParams.get("indices");
    this.timer = setInterval(this.decreaseTime.bind(this), 1000);
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "finish"){
        clearInterval(this.timer);
        this.navCtrl.setRoot(EndGameScreenPage, {score: this.score});
      }
    }.bind(this);
  }


  updateIndice(jsonData: any) {
    this.indices = [];
    for (let i = 0; i < jsonData.indices.length; i++) {
      this.indices.push("Indice : " + jsonData.indices[i].indice + " decouvert par : " + jsonData.indices[i].username);
    }
  }


  updateProgression(jsonData: any) {
    this.progressions = [];
    for (let i = 0; i < jsonData.joueurs.length; i++) {
      var progression = {
        username: jsonData.joueurs[i].username,
        total: jsonData.joueurs[i].total,
        actual: jsonData.joueurs[i].actual
      };
      this.progressions.push(progression);
    }
  }

  array(n: number) {
    var array = new Array();
    for (let i = 0; i < n; i++) {
      array.push(i);
    }
    return array;
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

  pad2(number) {
    return (number < 10 ? '0' : '') + number
  }

  decreaseTime() {
    if (this.secondes == 0 && this.minutes != 0) {
      this.secondes = 59;
      this.minutes--;
    }
    else if (this.secondes == 0 && this.minutes == 0) {
      clearInterval(this.timer);
      this.navCtrl.push(EndGameScreenPage, {score: this.score});
    }
    else {
      this.secondes--;
    }

  }

  submitAnswer() {
    if (this.inputAnswer.length == 0) {
      this.presentToastNoAnswer();
    }
    else {
      var request = {
        request: "RESPONSE",
        "idpartie": this.idPartie,
        "reponse": this.inputAnswer,
        username: this.userName
      };
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function (event) {
        var jsonData = JSON.parse(event.data);
        if (jsonData.reponse == "ko") {
          this.nbTry++;
          this.presentToastIncorectAnswer();
        }
        else if (jsonData.reponse == "finish") {
          if (this.nbTry <= 3)
            this.score = this.score + 1;
          clearInterval(this.timer);
          this.navCtrl.setRoot(EndGameScreenPage, {score: this.score});;
        }
      }.bind(this);
    }
  }
}
