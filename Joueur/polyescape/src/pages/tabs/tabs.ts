import { Component } from '@angular/core';

import { TeamScreenPage } from '../teamScreen/teamScreen';
import { EscapeScreenPage } from '../escapeScreen/escapeScreen';
import { EndGameScreenPage } from '../endGameScreen/endGameScreen';
import { HomePage } from '../home/home';
import {EnigmePage} from '../enigme/enigme';


@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = HomePage;
  tab2Root = TeamScreenPage;
  tab3Root = EscapeScreenPage;
  tab4Root = EnigmePage;
  tab5Root = EndGameScreenPage;

  constructor() {

  }
}
