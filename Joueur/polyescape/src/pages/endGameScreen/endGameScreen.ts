import {NavController, NavParams} from 'ionic-angular';
import {HomePage} from "../home/home";
import {Component, ViewChild} from '@angular/core';

@Component({
  selector: 'page-end',
  templateUrl: 'endGameScreen.html'
})
export class EndGameScreenPage {


  @ViewChild('doughnutCanvas') doughnutCanvas;

  private score;
  private doughnutChart: any;


  constructor(public navCtrl: NavController,public navParams:NavParams) {
    this.score = navParams.get("score");

  }

  /*ionViewDidLoad() {
    this.doughnutChart = new Chart(this.doughnutCanvas.nativeElement, {

      type: 'doughnut',
      data: {
        labels: ["Bonnes réponses", "Mauvaises réponses"],
        datasets: [{
          label: '# of Votes',
          data: [12, 19],
          backgroundColor: [
            'rgba(243, 6, 6, 0.2)',
            'rgba(6, 243, 6, 0.2)'
          ],
          hoverBackgroundColor: [
            "#F30606",
            "#06F306"
          ]
        }]
      }

    });
  }*/

  goToHomePage() {
    this.navCtrl.setRoot(HomePage);
  }
}
