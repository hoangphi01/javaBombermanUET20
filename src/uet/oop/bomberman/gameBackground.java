package uet.oop.bomberman;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;


public class gameBackground extends Pane {
    public static final int BWIDTH = 800;
    public static final int BHEIGHT = 520;

    private List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("PLAY", BombermanGame::startGame),
            new Pair<String, Runnable>("PLAY CHRISTMAS VERSION", BombermanGame::Christmas),
            new Pair<String, Runnable>("HOW TO PLAY", BombermanGame::loadTutorial),
            new Pair<String, Runnable>("EXIT TO DESKTOP", Platform::exit)
    );

    private VBox menuBox = new VBox(-5);
    private Line line;

    private void addBackground() {
        Image img;
        ImageView imageView = new ImageView(new Image(getClass().getResource("/textures/BACKGR.gif").toExternalForm()));
        imageView.setFitWidth(BWIDTH);
        imageView.setFitHeight(BHEIGHT);

        getChildren().add(imageView);
    }

    private void addLine(double x, double y) {
        line = new Line(x, y, x, y + 130);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);

        getChildren().add(line);
    }

    private void startAnimation() {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {

            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    private void addMenu(double x, double y) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            BackgroundItem item = new BackgroundItem(data.getKey());
            item.setOnAction(data.getValue());
            item.setTranslateX(-300);

            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());

            item.setClip(clip);

            menuBox.getChildren().addAll(item);
        });

        getChildren().add(menuBox);
    }

    public gameBackground() {
        addBackground();
        addLine(75,50);
        addMenu(80,50);
        startAnimation();
    }

}
