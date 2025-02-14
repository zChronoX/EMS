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
    private void modificaProfilo(){
        String residenza=CasellaResidenza.getText();
        String email=CasellaEmail.getText();
        String telefono=CasellaTelefono.getText();

        tipoProfilo=utente.getTipoProfilo();

        // Controlli sui campi
        if (residenza.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Campi mancanti");
            alert.setContentText("Compila tutti i campi.");
            alert.showAndWait();
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Email non valida");
            alert.setContentText("Inserisci un'email valida.");
            alert.showAndWait();
            return;
        }

        if (!telefono.matches("^\\d{10}$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Errore");
            alert.setHeaderText("Telefono non valido");
            alert.setContentText("Inserisci un numero di telefono valido (10 cifre).");
            alert.showAndWait();
            return;
        }


        if(tipoProfilo.equals(Utente.TipoProfilo.Studente)){
            //VOGLIO MODIFICARE UNO STUDENTE
            studente=ems.getStudenteCorrente();

            //SCORRO LISTA STUDENTI GRAZIE ALLA MATRICOLA E MI FERMO QUANDO L'HO TROVATA
            String matricola=studente.getMatricola();
            for (Studente studente : student_list.values()) {
                if (matricola.equals(studente.getMatricola())) {
                    studente.setResidenza(residenza);
                    studente.setEmail(email);
                    studente.setTelefono(telefono);
                    break;
                }
            }
            ems.stampa_studenti();
        }else{
            //VOGLIO MODIFICARE UN DOCENTE
            docente=ems.getDocenteCorrente();

            String codice_docente=docente.getCodiceDocente();
            for (Docente docente : doc_list.values()) {
                if (codice_docente.equals(docente.getCodiceDocente())) {
                    docente.setResidenza(residenza);
                    docente.setEmail(email);
                    docente.setTelefono(telefono);
                    break;
                }
            }
            ems.stampa_docenti();
        }
        // Alert di conferma
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successo");
        alert.setHeaderText("Modifica completata");
        alert.setContentText("La modifica dei campi è andata a buon fine.");
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
