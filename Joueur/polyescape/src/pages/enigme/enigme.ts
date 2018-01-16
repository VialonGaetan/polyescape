import { Component } from '@angular/core';
import {NavController, NavParams, ToastController} from 'ionic-angular';

@Component({
  selector: 'page-enigme',
  templateUrl: 'enigme.html'
})
export class EnigmePage {

  private inputAnswer = '';
  private userName = '';
  private numEnigme:number = 0;
  private nomEnigme:string ='';
  private nomEscape = '';
  private enigmeDescription:string = '';
  private webSocket:WebSocket;


  constructor(public navCtrl: NavController,public navParams: NavParams, public toastCtrl: ToastController) {
    this.userName = navParams.get("username");
    this.nomEscape = navParams.get("name");
    this.webSocket = navParams.get("websocket");
    var request = {request: "CREATE_PARTIE", type:"SOLO",username:this.userName,escapegame:this.nomEscape};
    this.webSocket.send(JSON.stringify(request));
    this.webSocket.onmessage = function(event) {
      var jsonData = JSON.parse(event.data);
        this.nomEnigme = jsonData.nom;
        this.enigmeDescription = jsonData.description;
        this.numEnigme = jsonData.ID;
    }.bind(this);
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer une réponse',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }

  submitAnswer() {
    if (this.inputAnswer.length == 0){
      this.presentToast();
    }
  }

  label(){
    return this.userName;
  }
}
