import {Component} from "@angular/core";
import {AlertController, NavController, NavParams, ToastController} from "ionic-angular";
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";
import {FinalScreenPage} from "../finalScreen/finalScreen";
import {LocalNotifications} from "@ionic-native/local-notifications";


@Component({
  selector: 'page-enigmeTeam',
  templateUrl: 'enigmeTeamScreen.html'
})
export class EnigmeTeamPage {

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
  private progressions;

  constructor(public alerCtrl: AlertController, public navCtrl: NavController,public navParams: NavParams, public toastCtrl: ToastController, public localNotifications: LocalNotifications) {
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
    this.timer = setInterval(this.decreaseTime.bind(this),1000);
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "success"){
          this.updateProgression(jsonData);
      }
    }.bind(this);
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

  array(n:number){
    var array = new Array();
    for(let i = 0; i < n; i++){
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

  presentToastHelpSend() {
    let toast = this.toastCtrl.create({
      message: 'Demande envoyée',
      duration: 3000,
      position: 'bottom'
    });
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
        else if(jsonData.reponse == "notYet"){
          clearInterval(this.timer);
          this.navCtrl.setRoot(FinalScreenPage,{teamname:this.teamname,progressions:this.progressions});
        }
        else if(jsonData.reponse == "finish"){
          clearInterval(this.timer);
          this.navCtrl.setRoot(EndGameScreenPage,{score:jsonData.score});
        }
        else if(jsonData.reponse == "success"){
          this.updateProgression(jsonData);
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
