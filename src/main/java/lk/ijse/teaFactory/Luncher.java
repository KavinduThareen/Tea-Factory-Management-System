package lk.ijse.teaFactory;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Luncher extends Application {
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_page.fxml"));

        Scene scene = new Scene(rootNode);



        //set scene to the primary stage
        stage.setScene(scene);

        //set title and get center on screen stage
        stage.setTitle("GreenLeaf Software");
        stage.centerOnScreen();
        stage.isFullScreen();


        //show stage to the crowd
        stage.show();

    }
}
