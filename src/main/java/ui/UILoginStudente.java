package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;


public class UILoginStudente {
    public UILoginStudente() {}

    private EMS ems; // Istanza di EMS

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    @FXML
    private TextField matricolaField; // Corrisponde all'fx:id nel FXML

    @FXML
    private TextField passwordField; // Corrisponde all'fx:id nel FXML

    @FXML
    private Button BottoneConfermaLoginStudente; // Corrisponde all'fx:id nel FXML



    public void loginStudente() throws IOException {
        String matricola = matricolaField.getText();
        String password = passwordField.getText();

        try {
            if (ems.loginStudente(matricola, password)) {
                System.out.println("Login avvenuto con successo!");
                apriVistaDopoLoginStudente();
            } else {
                System.out.println("Login fallito.");
                // Puoi anche mostrare un messaggio di errore all'utente qui
            }
        } catch (Exception e) {
            System.err.println("Errore durante il login: " + e.getMessage());
            // Gestisci l'eccezione, ad esempio mostrando un messaggio di errore all'utente
        }
    }

    private void apriVistaDopoLoginStudente() throws IOException {
        Stage primaryStage = (Stage) BottoneConfermaLoginStudente.getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        UIStudente controller = fxmlLoader.getController();
        controller.setEMS(ems);
        Studente studenteLoggato = ems.getStudenteCorrente();
        controller.setStudente(studenteLoggato);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pagina Studente");
    }

}
