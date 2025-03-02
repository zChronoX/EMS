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

public class UILoginDocente implements Initializable {
    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }

    @FXML
    private Button Indietro;

    @FXML
    private TextField codiceDocenteField;

    @FXML
    private TextField passwordDocenteField;

    @FXML
    private Button BottoneConfermaLoginDocente;

    public void loginDocente() throws IOException {

        String codiceDocente = codiceDocenteField.getText();
        String password = passwordDocenteField.getText();


        try {
            if (ems.loginDocente(codiceDocente, password)) {
                System.out.println("Login docente avvenuto con successo!");
                apriVistaDopoLoginDocente();
            } else {
                System.out.println("Login docente fallito.");
            }
        } catch (Exception e) {
            System.err.println("Errore durante il login docente: " + e.getMessage());
        }
    }

    private void apriVistaDopoLoginDocente() throws IOException {
        Stage primaryStage = (Stage) BottoneConfermaLoginDocente.getScene().getWindow();


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DocenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pagina Docente");
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
