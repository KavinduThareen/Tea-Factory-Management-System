package lk.ijse.teaFactory.dto;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NotificationAnimation {

    public  void showNotification(String message) {
        Notifications.create()
                .title("Notification")
                .text(message)
                .position(Pos.TOP_RIGHT)
                .hideAfter(Duration.seconds(5))
                .darkStyle()
                .onAction(event -> System.out.println("Notification clicked!"))
                .showInformation();
    }
}
