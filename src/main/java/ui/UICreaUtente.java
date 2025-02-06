package ui;

import classi.EMS;
import classi.Studente;
import classi.Utente;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Date;

public class UICreaUtente {
    String nome;
    String cognome;
    LocalDate data_nascita; //qui bisogna capire che tipo dobbiamo mettere
    String genere;
    String codice_fiscale;
    String residenza;
    String email;

    EMS ems;
    ems = EMS.getIstance();

    @FXML
    private Button BottoneCreaUtente;

    @FXML
    private Button BottoneStudente;

    @FXML
    private Button BottoneDocente;

    @FXML
    private VBox ContenitoreInformazioni;

    @FXML
    private TextField CasellaNome;

    @FXML
    private TextField CasellaCognome;

    @FXML
    private DatePicker CasellaDataNascita;

    @FXML
    private TextField CasellaGenere;

    @FXML
    private TextField CasellaCodiceFiscale;

    @FXML
    private TextField CasellaResidenza;

    @FXML
    private TextField CasellaEmail;

    @FXML
    private Button BottoneConferma;

    @FXML
    protected void onBottoneCreaUtente() {
        BottoneCreaUtente.setVisible(false); // Nascondi il bottone "Crea Utente"
        BottoneStudente.setVisible(true); // Mostra il bottone "Studente"
        BottoneDocente.setVisible(true); // Mostra il bottone "Docente"
    }

    @FXML
    protected void onBottoneDocente() {
        if(BottoneDocente.isVisible()){
            BottoneDocente.setVisible(false); // Nascondi il bottone "Docente"
            BottoneStudente.setVisible(false); // Nascondi il bottone "Studente"
        }
        //settare la scelta e mostrare l'elenco con i parametri da riempire e registrare

        //elenco parametri
        ContenitoreInformazioni.setVisible(true);

    }

    @FXML
    protected void onBottoneStudente() {
        if(BottoneStudente.isVisible()){
            BottoneStudente.setVisible(false); // Nascondi il bottone "Studente"
            BottoneDocente.setVisible(false); // Nascondi il bottone "Studente"
        }
        //settare la scelta e mostrare l'elenco con i parametri da riempire e registrare


        //elenco parametri
        ContenitoreInformazioni.setVisible(true);

    }

    //prelevo i valori dai campi
    @FXML
    protected void onBottoneConferma() {
    nome=CasellaNome.getText();
    cognome=CasellaCognome.getText();
    data_nascita=CasellaDataNascita.getValue();
    genere=CasellaGenere.getText();
    codice_fiscale=CasellaCodiceFiscale.getText();
    residenza=CasellaResidenza.getText();
    email=CasellaEmail.getText();

    // Momentaneamente stampo i dati in console --> FUNZIONA
        System.out.println("Nome: " + nome);
        System.out.println("Cognome: " + cognome);
        System.out.println("Data nascita: " + data_nascita);
        System.out.println("Genere: " + genere);
        System.out.println("Codice fiscale: " + codice_fiscale);
        System.out.println("Residenza: " + residenza);
        System.out.println("Email: " + email);
    }

}