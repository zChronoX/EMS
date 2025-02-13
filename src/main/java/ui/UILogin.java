package ui;

import classi.EMS;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class UILogin {

    public UILogin() {}

    private EMS ems;

    @FXML
    private Button BottoneLoginStudente;

    @FXML
    private Button BottoneLoginDocente;

    @FXML
    private Button IndietroWelcome;

    @FXML
    public void apriLoginStudente() throws IOException {
        Stage primaryStage = (Stage) BottoneLoginStudente.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginStudenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Studente");
    }

    @FXML
    public void apriLoginDocente() throws IOException {
        Stage primaryStage = (Stage) BottoneLoginDocente.getScene().getWindow();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginDocenteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Docente");
    }

    @FXML
    public void Indietro() throws IOException {
        Stage primaryStage = (Stage) IndietroWelcome.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }
}