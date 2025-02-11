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

public class UILoginDocente {

    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    @FXML
    private TextField codiceDocenteField; // Corrisponde all'fx:id nel FXML

    @FXML
    private TextField passwordDocenteField; // Corrisponde all'fx:id nel FXML

    @FXML
    private Button BottoneConfermaLoginDocente; // Corrisponde all'fx:id nel FXML

    public void loginDocente() throws IOException {
        String codiceDocente = codiceDocenteField.getText(); // Ottieni il codice docente dal campo di testo
        String password = passwordDocenteField.getText(); // Ottieni la password dal campo di testo

        try {
            if (ems.loginDocente(codiceDocente, password)) {
                System.out.println("Login docente avvenuto con successo!");
                apriVistaDopoLoginDocente(); // Apri la vista appropriata
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
        UIDocente controller = fxmlLoader.getController();
        controller.setEMS(ems);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pagina Docente");
    }
}
