import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';
import { LocalNotifications } from '@ionic-native/local-notifications';
import { EscapeScreenPage } from '../pages/escapeScreen/escapeScreen';
import { EndGameScreenPage } from '../pages/endGameScreen/endGameScreen'
import { TeamScreenPage } from '../pages/teamScreen/teamScreen';
import { HomePage } from '../pages/home/home';
import {EnigmePage} from "../pages/enigme/enigme";
import {TeamWaitScreen} from "../pages/teamWaitScreen/teamWaitScreen";

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import {EnigmeTeamPage} from "../pages/enigmeTeamScreen/enigmeTeamScreen";

@NgModule({
  declarations: [
    MyApp,
    TeamScreenPage,
    EscapeScreenPage,
    EndGameScreenPage,
    HomePage,
    EnigmePage,
    TeamWaitScreen,
    EnigmeTeamPage


  ],
  imports: [
    BrowserModule,
    IonicModule.forRoot(MyApp)
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    TeamScreenPage,
    EscapeScreenPage,
    EndGameScreenPage,
    HomePage,
    EnigmePage,
    TeamWaitScreen,
    EnigmeTeamPage


  ],
  providers: [
    LocalNotifications,
    StatusBar,
    SplashScreen,
    LocalNotifications,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
