package uet.oop.bomberman;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class gameSnow extends AnchorPane {

    Random random = new Random();
    //AnchorPane root = new AnchorPane();


    public gameSnow() {
        Circle c[] = new Circle[2000];

        for (int i = 0; i < 2000; i++) {
            c[i] = new Circle(1, 1, 1);
            c[i].setRadius(random.nextDouble() * 3);
            Color color = Color.rgb(255, 255, 255, random.nextDouble());
            c[i].setFill(color);
            getChildren().add(c[i]);
            Raining(c[i]);
        }
    }

    public void Raining(Circle c) {
        int time = 10 + random.nextInt(50);

        c.setTranslateY(-200);
        c.setTranslateX(100);
        TranslateTransition tt = new TranslateTransition();
        tt.setNode(c);
        //tt.setFromY(-200);
        tt.setToY(520+200);
        tt.setToX(random.nextDouble() * c.getCenterX());
        tt.setDuration(Duration.seconds(time));
        /*tt.setCycleCount((int) Double.POSITIVE_INFINITY);
        tt.setAutoReverse(true);*/
        c.setCenterX(random.nextInt(800));

        tt.setOnFinished(e -> {
            c.setTranslateY(-200);
            tt.playFromStart();
        });

        tt.play();
//        Animation walk = TranslateTransitionBuilder.create();
//                .node(c)
//                .fromY(-200)
//                .toY(520+200)
//                .toX(random.nextDouble() * c.getCenterX())
//                .duration(Duration.seconds(time))
//                .onFinished(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent t) {
//                        Raining(c);
//                    }
//                }).build();
//        walk.play();
    }
}