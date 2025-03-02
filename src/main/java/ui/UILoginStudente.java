package ui;

import classi.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;


public class UILoginStudente implements Initializable {
    private EMS ems; // Istanza di EMS

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }

    public UILoginStudente() {}

    @FXML
    private Button Indietro;

    @FXML
    private TextField matricolaField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button BottoneConfermaLoginStudente;



    public void loginStudente() throws IOException {

        String matricola = matricolaField.getText();
        String password = passwordField.getText();

        try {
            if (ems.loginStudente(matricola, password)) {
                System.out.println("Login avvenuto con successo!");


                apriVistaDopoLoginStudente();
            } else {
                System.out.println("Login fallito.");

            }
        } catch (Exception e) {
            System.err.println("Errore durante il login: " + e.getMessage());

        }
    }

    private void apriVistaDopoLoginStudente() throws IOException {
        Stage primaryStage = (Stage) BottoneConfermaLoginStudente.getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pagina Studente");
    }

    @FXML
    public void Indietro() throws IOException {
        Stage primaryStage = (Stage) Indietro.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("EMS");
    }

}
