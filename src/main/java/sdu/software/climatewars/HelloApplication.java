package sdu.software.climatewars;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("room.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Climate Wars");
        stage.setScene(scene);
        stage.show();

        // How to access labels etc in controllers from start method
        RoomController rc = fxmlLoader.getController();
        rc.setStatsText("Yeet");

    }

    public static void main(String[] args) {
        launch();
    }
}