import {Injectable} from "@angular/core";
import {EndGameScreenPage} from "../pages/endGameScreen/endGameScreen";

@Injectable()
export class TimerService {

  private minutes;
  private secondes;


  constructor() {

  }

  decreaseTime(minutes, secondes){
    /*if(secondes == 0 && minutes != 0){
      secondes = 59;
      minutes--;
    }
    else if(secondes == 0 && minutes == 0){
      //clearInterval(this.timer);
      //this.navCtrl.push(EndGameScreenPage,{score:0});
    }
    else {
      secondes--;
    }*/
    alert(minutes);

  }

}
