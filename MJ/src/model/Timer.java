package model;

import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.Notifications;

public class Timer extends Thread {

    private int hour=0;
    private int minute=0;
    private Label remindedTime;

    public Timer(int minuteTime, Label remindedTime){
        JFXPanel jfxPanel=new JFXPanel();
        this.remindedTime=remindedTime;
        if (minuteTime>0){
            this.hour = minuteTime/60;
        }
        if ((minuteTime - this.hour*60)>0){
            this.minute = minuteTime - this.hour*60;
        }
    }

    public void run(){
        try {
            display();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void display() throws InterruptedException {
        String tobeDisplayed="";
        Boolean finDuJeu=false;
        while(!finDuJeu){
            if (this.minute==0 && this.hour!=0){
                this.hour--;
                this.minute=59;
            }
            else if (this.minute>0){
                this.minute--;
            }
            if (this.hour==0 && this.minute==0){
                finDuJeu=true;
            }
            if (this.minute<10 && this.hour<10) tobeDisplayed="0"+this.hour+" : "+"0"+this.minute;
            else if (this.minute<10 && !(this.hour<10)) tobeDisplayed=this.hour+" : "+"0"+this.minute;
            else if (!(this.minute<10) && this.hour<10)tobeDisplayed="0"+this.hour+" : "+this.minute;
            else if (!(this.minute<10) && !(this.hour<10))tobeDisplayed=this.hour+" : "+this.minute;
            Notifications.create().title("Nouvelle notif").text("Temps restant :\n"+tobeDisplayed).darkStyle().position(Pos.CENTER_RIGHT).showWarning();
            Thread.sleep(1000);
        }
        Media hit = new Media(getClass().getClassLoader().getResource("sounds/sonnerie.wav").toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
