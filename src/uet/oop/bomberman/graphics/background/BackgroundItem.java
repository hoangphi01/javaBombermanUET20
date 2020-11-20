package uet.oop.bomberman.graphics.background;

import javafx.beans.binding.Bindings;
import javafx.scene.Cursor;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BackgroundItem extends Pane {
    private Text text;
    private Effect shadow = new DropShadow(5, Color.BLACK);
    private Effect blur = new BoxBlur(1, 1, 3);

    public BackgroundItem(String name) {

        text = new Text(name);
        text.setFont(Font.loadFont(Background.class.getResource("/font/PixelFont.ttf").toExternalForm(), 15));
        text.setTranslateX(5);
        text.setTranslateY(20);
        text.setFill(Color.WHITE);
        //text.setUnderline(true);

        text.effectProperty().bind(
                Bindings.when(hoverProperty())
                        .then(shadow)
                        .otherwise(blur)
        );

        text.cursorProperty().bind(
                Bindings.when(hoverProperty())
                        .then(Cursor.HAND)
                        .otherwise(Cursor.DEFAULT)
        );

        getChildren().addAll(text);
    }

    public void setOnAction(Runnable action) {
        setOnMouseClicked(e -> action.run());
    }

}
