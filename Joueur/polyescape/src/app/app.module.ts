import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { EscapeScreenPage } from '../pages/escapeScreen/escapeScreen';
import { EndGameScreenPage } from '../pages/endGameScreen/endGameScreen'
import { TeamScreenPage } from '../pages/teamScreen/teamScreen';
import { HomePage } from '../pages/home/home';
import {EnigmePage} from "../pages/enigme/enigme";
import {TeamWaitScreen} from "../pages/teamWaitScreen/teamWaitScreen";

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {TeamProgressionScreenPage} from "../pages/teamProgressionScreenPage/teamProgressionScreenPage";

@NgModule({
  declarations: [
    MyApp,
    TeamScreenPage,
    EscapeScreenPage,
    EndGameScreenPage,
    HomePage,
    EnigmePage,
    TeamWaitScreen,
    TeamProgressionScreenPage
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
    TeamProgressionScreenPage

  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
