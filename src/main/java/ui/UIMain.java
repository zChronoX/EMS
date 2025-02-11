package ui;

import classi.EMS;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UIMain extends Application {

    //EMS ems = new EMS(); // Crea l'istanza di EMS *PRIMA* di caricare il FXML
    private final EMS ems = EMS.getInstance();
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(UIMain.class.getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setEMS(ems); // Passa l'istanza di EMS *PRIMA* di mostrare la finestra

        stage.setTitle("EMS");
        stage.setScene(scene);
        stage.show();

        ems.stampa_utenti(); // Stampa gli utenti (ora usa la stessa istanza)
    }

    public static void main(String[] args) {
        launch();
    }
}