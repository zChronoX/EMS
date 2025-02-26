package ui;

import classi.Docente;
import classi.EMS;
import classi.Studente;
import classi.Utente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UIModificaProfilo implements Initializable {
    private EMS ems;
    private Utente utente;
    private Studente studente;
    private Docente docente;
    Utente.TipoProfilo tipoProfilo;
    private HashMap<String, Studente> student_list;
    private HashMap<String, Docente> doc_list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
        utente=ems.getUtenteCorrente();

        System.out.println(utente.toString());

        // Verifica il tipo di utente e assegna studente o docente di conseguenza
        if (utente instanceof Studente) {
            studente = (Studente) utente;
            student_list=ems.getStudenti();
        } else if (utente instanceof Docente) {
            docente = (Docente) utente;
            doc_list=ems.getDoc_list();
        }
        //studente=ems.getStudenteCorrente();
        //docente=ems.getDocenteCorrente();
    }

    @FXML
    private Button BottoneConferma;
    @FXML
    private Button BottoneIndietro;
    @FXML
    private TextField CasellaResidenza;
    @FXML
    private TextField CasellaEmail;
    @FXML
    private TextField CasellaTelefono;





    @FXML
    private void modificaProfilo() {
        String residenza = CasellaResidenza.getText();
        String email = CasellaEmail.getText();
        String telefono = CasellaTelefono.getText();

        tipoProfilo = utente.getTipoProfilo();

        // Controlli sui campi
        if (residenza.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mostraErrore("Campi mancanti", "Compila tutti i campi.");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostraErrore("Email non valida", "Inserisci un'email valida.");
            return;
        }

        if (!telefono.matches("^\\d{10}$")) {
            mostraErrore("Telefono non valido", "Inserisci un numero di telefono valido (10 cifre).");
            return;
        }

        // Chiamata a EMS per modificare il profilo
        ems.modificaProfilo(residenza, email, telefono);

        // Alert di conferma
        mostraMessaggio("Modifica completata", "La modifica dei campi è andata a buon fine.");
    }

    private void mostraErrore(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void mostraMessaggio(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void Indietro() throws IOException {
        Stage primaryStage = (Stage) BottoneIndietro.getScene().getWindow(); // Ottieni lo Stage
        tipoProfilo=utente.getTipoProfilo();
        FXMLLoader fxmlLoader;
        if(tipoProfilo.equals(Utente.TipoProfilo.Studente)){
            fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml")); // Carica WelcomeView.fxml
        }else{
            fxmlLoader = new FXMLLoader(getClass().getResource("DocenteView.fxml")); // Carica WelcomeView.fxml
        }

        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }
}
