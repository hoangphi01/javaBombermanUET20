package uet.oop.bomberman;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

public class gameSound {

    private static Media media;

    public static void playClick() {
        Media media;
        String soundLocation = "src/Music/ClickSound.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void playBackgroundMusic() {
        // Tao Nhac nen
        String soundLocation = "src/Music/BackgroundSound.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }

    public static void playMenuMusic() {
        // Tao Nhac nen
        String soundLocation = "src/Music/TitleScreen.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }

    public static void startExplosion() {
        AudioClip explosion = new AudioClip(Paths.get("src/Music/BombExplosion.wav").toUri().toString());
        explosion.play();
    }

    public static void soundChristmas() {
        String soundLocation = "src/Music/Christmas.mp3";
        media = new Media(new File(soundLocation).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }
}
