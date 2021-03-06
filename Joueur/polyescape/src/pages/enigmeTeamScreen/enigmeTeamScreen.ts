import {Component} from "@angular/core";
import {AlertController, NavController, NavParams, ToastController} from "ionic-angular";
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {FinalScreenPage} from "../finalScreen/finalScreen";
import {LocalNotifications} from "@ionic-native/local-notifications";
import {finalEnigmeTeamScreen} from "../finalEnigmeTeam/finalEnigmeTeamScreen";


@Component({
  selector: 'page-enigmeTeam',
  templateUrl: 'enigmeTeamScreen.html'
})
export class EnigmeTeamPage {

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
  private type;
  private progressions;
  private indices;
  private score: number = 0;
  private nbTry: number = 0;

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
    this.progressions = this.navParams.get("progressions");
    this.timer = setInterval(this.decreaseTime.bind(this), 1000);
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if (jsonData.reponse == "success")
        this.updateProgression(jsonData);
      else if (jsonData.reponse == "indices")
        this.updateIndice(jsonData)
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
    this.toastCtrl.create({
      message: 'Veuillez entrer une réponse',
      duration: 3000,
      position: 'bottom'
    }).present();

  }

  presentToastHelpSend() {
    this.toastCtrl.create({
      message: 'Demande envoyée',
      duration: 3000,
      position: 'bottom'
    }).present();
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
        else if (jsonData.reponse == "ok") {
          if (this.nbTry <= 3)
            this.score = this.score + 1;
          this.enigmeInfos = jsonData.infos;
          this.nomEnigme = jsonData.nom;
          this.inputAnswer = "";
          this.nbTry = 0;
        }
        else if (jsonData.reponse == "notyet") {
          clearInterval(this.timer);
          this.navCtrl.setRoot(FinalScreenPage, {
            websocket: this.webSocket,
            username: this.userName,
            teamname: this.teamName,
            progressions: this.progressions,
            score: this.score,
            idpartie: this.idPartie,
            indices: this.indices,
            minutes: this.minutes,
            secondes: this.secondes,
            name: this.nomEscape
          });
        }
        else if (jsonData.reponse == "finish") {
          clearInterval(this.timer);
          this.navCtrl.setRoot(EndGameScreenPage, {score: this.score});
        }
        else if (jsonData.reponse == "success") {
          this.updateProgression(jsonData);
        } else if (jsonData.reponse == "indices")
          this.updateIndice(jsonData);
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
            indices: this.indices,
            name: this.nomEscape
          });
        }else {
          this.alerCtrl.create({
            title: "Indice",
            message: jsonData.description,
            buttons: ['Ok']
          }).present();
          this.scheduleNotification(jsonData.description);
        }

      }.bind(this);
    }
  }

  scheduleNotification(text) {
    this.localNotifications.schedule({
      id: 1,
      title: 'Aide',
      text: text
    });
  }

  requestHelp() {
    var request = {request: "HELP", idpartie: this.idPartie, username: this.userName, enigme: this.enigmeInfos};
    this.webSocket.send(JSON.stringify(request));
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if (jsonData.reponse == "ko") {
        this.alerCtrl.create({
          title: "Erreur",
          message: "Aucun maitre du jeu disponible",
          buttons: ['Ok']
        }).present();
      } else if (jsonData.reponse == "ok") {
        this.presentToastHelpSend();
      }
      else {
        this.alerCtrl.create({
          title: "Indice",
          message: jsonData.description,
          buttons: ['Ok']
        }).present();
        this.scheduleNotification(jsonData.description);
      }

    }.bind(this);
  }
}
