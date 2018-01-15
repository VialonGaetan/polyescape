import { NgModule, ErrorHandler } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { EscapeScreenPage } from '../pages/escapeScreen/escapeScreen';
import { EndGameScreenPage } from '../pages/endGameScreen/endGameScreen'
import { TeamScreenPage } from '../pages/teamScreen/teamScreen';
import { HomePage } from '../pages/home/home';
import { TabsPage } from '../pages/tabs/tabs';


import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import {EnigmePage} from "../pages/enigme/enigme";

@NgModule({
  declarations: [
    MyApp,
    TeamScreenPage,
    EscapeScreenPage,
    EndGameScreenPage,
    HomePage,
    EnigmePage,
    TabsPage
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
    TabsPage
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
