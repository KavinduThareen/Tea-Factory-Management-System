package lk.ijse.teaFactory.dto;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ErrorAnimation {

    public static void animateError(TextField textField) {
        // Get the original border or use Border.EMPTY as a fallback
        Border originalBorder = textField.getBorder() != null ? textField.getBorder() : Border.EMPTY;

        // Create a Timeline for the animation
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(textField.borderProperty(), originalBorder)),
                new KeyFrame(Duration.millis(500), new KeyValue(textField.borderProperty(), createRedBorder())),
                new KeyFrame(Duration.millis(900), new KeyValue(textField.borderProperty(), originalBorder))
        );

        // Add a translation animation for vibration effect
        double originalTranslateX = textField.getTranslateX();
        double vibrationDistance = 60.0; // Adjust the distance of the vibration
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), new KeyValue(textField.translateXProperty(), originalTranslateX)),
                new KeyFrame(Duration.millis(50), new KeyValue(textField.translateXProperty(), originalTranslateX + vibrationDistance)),
                new KeyFrame(Duration.millis(100), new KeyValue(textField.translateXProperty(), originalTranslateX - vibrationDistance)),
                new KeyFrame(Duration.millis(150), new KeyValue(textField.translateXProperty(), originalTranslateX + vibrationDistance)),
                new KeyFrame(Duration.millis(200), new KeyValue(textField.translateXProperty(), originalTranslateX))
        );

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(210), event -> textField.clear()));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(50), new KeyValue(textField.borderProperty(), createRedBorder())));
        // Play the animation
        timeline.play();
    }

    private static Border createRedBorder() {
        // Create a red border
        return new Border(new BorderStroke(
                Color.RED, BorderStrokeStyle.SOLID,
                javafx.scene.layout.CornerRadii.EMPTY, javafx.scene.layout.BorderWidths.DEFAULT
        ));
    }

}
