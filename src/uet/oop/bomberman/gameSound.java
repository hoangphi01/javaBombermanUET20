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
        try {
            Media media;
            String soundLocation = "src/Music/ClickSound.mp3";
            media = new Media(new File(soundLocation).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void playBackgroundMusic() {
        try {
            String soundLocation = "src/Music/BackgroundSound.mp3";
            media = new Media(new File(soundLocation).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.7);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void playMenuMusic() {
        try {
            String soundLocation = "src/Music/TitleScreen.mp3";
            media = new Media(new File(soundLocation).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void startExplosion() {
        try {
            AudioClip explosion = new AudioClip(Paths.get("src/Music/BombExplosion.wav").toUri().toString());
            explosion.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void moveUpDownSound() {
        try {
            AudioClip explosion = new AudioClip(Paths.get("src/Music/SoundUD.mp3").toUri().toString());
            explosion.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void moveRightLeftSound() {
        try {
            AudioClip explosion = new AudioClip(Paths.get("src/Music/SoundLR.mp3").toUri().toString());
            explosion.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }

    public static void soundChristmas() {
        try {
            String soundLocation = "src/Music/Christmas.mp3";
            media = new Media(new File(soundLocation).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.7);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                }
            });
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Sound unavailable: " + e.getMessage());
        }
    }
}
