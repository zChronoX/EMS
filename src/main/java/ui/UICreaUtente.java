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
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class UICreaUtente implements Initializable {
    private EMS ems;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems=EMS.getInstance();
    }

    String nome;
    String cognome;
    Date data_nascita; //qui bisogna capire che tipo dobbiamo mettere --> Date
    String genere;
    String codice_fiscale;
    String residenza;
    String email;
    Utente.TipoProfilo tipoProfilo;
    Studente studente;
    Docente docente;
    String telefono;
    String categoria;
    int anno_corso;

    @FXML
    private Button BottoneCreaUtente;

    @FXML
    private Button indietroButton;

    @FXML
    private Button BottoneIndietroWelcomeView;

    @FXML
    private void tornaAllaWelcomeView() throws IOException {
        Stage primaryStage = (Stage) BottoneIndietroWelcomeView.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());

        primaryStage.setScene(scene); // Imposta la scena di WelcomeView sullo Stage
        primaryStage.setTitle("EMS"); // Puoi anche reimpostare il titolo
    }

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
    private TextField CasellaTelefono;

    @FXML
    private TextField CasellaCategoria;

    @FXML
    private TextField CasellaAnnoCorso;

    @FXML
    private Button BottoneConferma;

    @FXML
    private HBox SezioneCategoria;

    @FXML
    private HBox SezioneAnnoCorso;

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

        tipoProfilo= Utente.TipoProfilo.Docente;

        ems.scegliTipoProfilo(tipoProfilo); //qui si richiama EMS che richiama la factory

        //CAPIRE SE QUESTA RIGA SERVE, SENZA ABBIAMO ERRORI IN ESECUZIONE
        docente = (Docente) ems.getUtenteCorrente();


        ContenitoreInformazioni.setVisible(true);
        //nascondo le sezioni categoria e anno corso per i docenti
        SezioneCategoria.setVisible(false);
        SezioneAnnoCorso.setVisible(false);


    }

    @FXML
    protected void onBottoneStudente() {
        if(BottoneStudente.isVisible()){
            BottoneStudente.setVisible(false); // Nascondi il bottone "Studente"
            BottoneDocente.setVisible(false); // Nascondi il bottone "Docente"
        }
        if(SezioneCategoria.isVisible()==false){
            SezioneCategoria.setVisible(true);
        }
        if(SezioneAnnoCorso.isVisible()==false){
            SezioneAnnoCorso.setVisible(true);
        }
        //settare la scelta e mostrare l'elenco con i parametri da riempire e registrare

        tipoProfilo= Utente.TipoProfilo.Studente;

        ems.scegliTipoProfilo(tipoProfilo); //qui si richiama ems che richiama la factory

    //CAPIRE SE QUESTA RIGA SERVE SOLO PER LE STAMPE, SENZA DI QUESTA ERRORI IN ESECUZIONE
        studente = (Studente) ems.getUtenteCorrente(); //questo credo serva solo per la stampa

        //elenco parametri
        ContenitoreInformazioni.setVisible(true);

    }

    //prelevo i valori dai campi
    @FXML
    protected void onBottoneConferma() {
        nome = CasellaNome.getText();
        cognome = CasellaCognome.getText();
        genere = CasellaGenere.getText();
        codice_fiscale = CasellaCodiceFiscale.getText();
        residenza = CasellaResidenza.getText();
        email = CasellaEmail.getText();
        telefono = CasellaTelefono.getText();
        LocalDate dataNascitaLocalDate = CasellaDataNascita.getValue();


        if (nome.isEmpty() || cognome.isEmpty() || genere.isEmpty() || codice_fiscale.isEmpty() ||
                residenza.isEmpty() || email.isEmpty() || telefono.isEmpty() || dataNascitaLocalDate == null) {

            mostraMessaggioErrore("Errore: Tutti i campi devono essere compilati.");
            return; // **Blocca l'esecuzione se un campo è vuoto**
        }

        // **Conversione data**
        data_nascita = Date.from(dataNascitaLocalDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());


        if (ems.verificaCodiceFiscaleEsistente(codice_fiscale)) {
            mostraMessaggioErrore("Errore: Il codice fiscale è già registrato.");
            return; // **Blocca la creazione dell'utente**
        }
        if (tipoProfilo == Utente.TipoProfilo.Studente) {
            String matricola = studente.getMatricola();
            if (ems.verificaMatricolaEsistente(matricola)) {
                mostraMessaggioErrore("Errore: La matricola " + matricola + " è già associata a un altro studente.");
                return; // **Blocca la creazione dello studente**
            }
        } else if (tipoProfilo == Utente.TipoProfilo.Docente) {
            String codiceDocente = docente.getCodiceDocente();
            if (ems.verificaCodiceDocenteEsistente(codiceDocente)) {
                mostraMessaggioErrore("Errore: Il codice docente " + codiceDocente + " è già associato a un altro docente.");
                return; // **Blocca la creazione del docente**
            }
        }
        // **Creazione utente**
        if (ems.creaProfiloUtente(nome, cognome, data_nascita, genere, codice_fiscale, residenza, email, telefono)) {
            System.out.println("Utente creato con successo");

            if (tipoProfilo == Utente.TipoProfilo.Studente) {
                categoria = CasellaCategoria.getText();


                if (categoria.isEmpty() || CasellaAnnoCorso.getText().isEmpty()) {
                    mostraMessaggioErrore("Errore: Categoria e Anno Corso sono obbligatori.");
                    return;
                }

                try {
                    anno_corso = Integer.parseInt(CasellaAnnoCorso.getText());
                    ems.AggiungiInfoStudente(categoria, anno_corso);
                    System.out.println("Categoria e anno corso aggiunti");
                } catch (NumberFormatException e) {
                    mostraMessaggioErrore("Errore: L'anno di corso deve essere un numero.");
                    return;
                }
            }


            ContenitoreInformazioni.setVisible(false);

            // **Genera credenziali**
            ems.generaCredenziali();

            // **Mostra riepilogo informazioni**
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Riepilogo Informazioni");

            if (tipoProfilo == Utente.TipoProfilo.Studente) {
                String dati = "Nome: " + studente.getNome() + "\nCognome: " + studente.getCognome() +
                        "\nData nascita: " + studente.getData_nascita() + "\nGenere: " + studente.getGenere() +
                        "\nCodice fiscale: " + studente.getCodice_fiscale() + "\nResidenza: " + studente.getResidenza() +
                        "\nEmail: " + studente.getEmail() + "\nTelefono: " + studente.getTelefono() +
                        "\nCategoria: " + studente.getCategoria() + "\nAnno corso: " + studente.getAnnoCorso() +
                        "\nMatricola: " + studente.getMatricola() + "\nPassword: " + studente.getPassword();
                dialog.setContentText(dati);
            } else {
                String dati = "Nome: " + docente.getNome() + "\nCognome: " + docente.getCognome() +
                        "\nData nascita: " + docente.getData_nascita() + "\nGenere: " + docente.getGenere() +
                        "\nCodice fiscale: " + docente.getCodice_fiscale() + "\nResidenza: " + docente.getResidenza() +
                        "\nEmail: " + docente.getEmail() + "\nTelefono: " + docente.getTelefono() +
                        "\nCodice Docente: " + docente.getCodiceDocente() + "\nPassword: " + docente.getPassword();
                dialog.setContentText(dati);
            }

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // **Conferma e aggiunta utente alla mappa**
                ems.confermaUtente();
                BottoneCreaUtente.setVisible(true);
            }
        }
    }


    private void mostraMessaggioErrore(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText("Errore di compilazione");
        alert.setContentText(messaggio);
        alert.showAndWait();
    }





}