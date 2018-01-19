import { Component } from '@angular/core';
import {List, NavController, NavParams, ToastController} from 'ionic-angular';
import {EscapeScreenPage} from "../escapeScreen/escapeScreen";
import {TeamWaitScreen} from "../teamWaitScreen/teamWaitScreen";

@Component({
  selector: 'page-team',
  templateUrl: 'teamScreen.html'
})
export class TeamScreenPage {

  private qty = '';
  private userName = '';
  private teamName = '';
  private _id:number;
  private webSocket:WebSocket;
  private vars;
  private numbers;

  constructor(public navCtrl: NavController,public navParams:NavParams,public toastCtrl: ToastController) {
    this._id = 99;
    this.userName = this.navParams.get("username");
    this.webSocket = this.navParams.get("websocket");
    this.loadTeams();
  }

  /**
   * Refresh the page content thanks to the server
   */
  loadTeams(){
    this.vars = [];
    this.numbers = [];
    this.webSocket.send(JSON.stringify({request:"GET_SALONS"}));
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      for(let i = 0; i < jsonData.partieattente.length; i++){
        var partie = {teamname:jsonData.partieattente[i].equipe,escapegame:jsonData.partieattente[i].escapegame,idpartie:jsonData.partieattente[i].idpartie,nombreJoueurs:jsonData.partieattente[i].joueurs};
        this.vars.push(partie);
        if(!this.numbers.includes(jsonData.partieattente[i].joueurs)){
          this.numbers.push(jsonData.partieattente[i].joueurs);
        }
      }
      this.numbers.sort();
    }.bind(this);
  }


  /**
   * Go to the screen to wait for partners to be ready to play
   * @param {number} p_id
   */
  joinTeam(v : any){
    this.webSocket.send(JSON.stringify({request:"JOIN_TEAM",username:this.userName,idpartie:v.idpartie}));
    this.webSocket.onmessage = function (event) {
      var jsonData = JSON.parse(event.data);
      if(jsonData.reponse == "ok"){
        this.navCtrl.push(TeamWaitScreen,{teamname:v.teamname});
      }
    }.bind(this);
  }

  /**
   * Create a new team with the specified ids
   */
  createTeam(){
    if(this.qty.length == 0){
      this.presentToast();
    }
    else {
      this.teamName = this.qty;
      this.qty = "";
      this.navCtrl.push(EscapeScreenPage,{username:this.userName,type:"equipe",teamname:this.teamName,websocket:this.webSocket})
    }
  }

  presentToast() {
    let toast = this.toastCtrl.create({
      message: 'Veuillez entrer un nom valide (au moins 1 caractère)',
      duration: 3000,
      position: 'bottom'
    });


    toast.present();
  }
}
