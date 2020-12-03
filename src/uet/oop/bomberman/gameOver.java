package uet.oop.bomberman;

import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

import java.nio.file.Paths;

public class gameOver extends Pane {

    public static void overSound() {
        AudioClip gOver = new AudioClip(Paths.get("src/Music/GameOver.mp3").toUri().toString());
        gOver.play();
    }


}
