package ui;

import classi.EMS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        EMS ems = EMS.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(UIMain.class.getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        ems.stampa_utenti(); // Stampa gli utenti
    }

    public static void main(String[] args) {
        launch();
    }
}