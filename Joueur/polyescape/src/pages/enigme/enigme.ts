import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';
import {EndGameScreenPage} from "../endGameScreen/endGameScreen";

@Component({
  selector: 'page-enigme',
  templateUrl: 'enigme.html'
})
export class EnigmePage {

  private inputAnswer = '';
  private userName = '';
  private idPartie:number = 0;
  private nomEnigme:string ='';
  private nomEscape = '';
  private enigmeInfos:string = '';
  private webSocket:WebSocket;


  constructor(public navCtrl: NavController,public navParams: NavParams, public toastCtrl: ToastController) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    if(navParams.get("infos") == ""){
      var request = {request: "CREATE_PARTIE", type:"SOLO",username:this.userName,escapegame:this.nomEscape};
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "ok"){
          this.nomEnigme = jsonData.nom;
          this.enigmeInfos = jsonData.infos;
          this.idPartie = jsonData.idpartie;
        }
      }.bind(this);
    }
    else {
      this.nomEnigme = navParams.get("nomEnigme");
      this.enigmeInfos = navParams.get("infos");
    }
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

  submitAnswer() {
    if (this.inputAnswer.length == 0){
      this.presentToastNoAnswer();
    }
    else {
      var request = {request:"RESPONSE", "idpartie":this.idPartie,"reponse":this.inputAnswer,username:this.userName};
      this.webSocket.send(JSON.stringify(request));
      this.webSocket.onmessage = function(event) {
        var jsonData = JSON.parse(event.data);
        if(jsonData.reponse == "ko") {
          this.presentToastIncorectAnswer();
        }
        else if(jsonData.reponse == "ok"){
          this.navCtrl.push(EnigmePage,{username:this.userName,name:this.nomEscape,websocket:this.webSocket,infos:jsonData.infos,nomEnigme:jsonData.nom});
        }
        else if(jsonData.reponse == "finish"){
          this.navCtrl.push(EndGameScreenPage,{score:jsonData.score});
        }
      }.bind(this);

    }
  }

  label(){
    return this.userName;
  }
}
