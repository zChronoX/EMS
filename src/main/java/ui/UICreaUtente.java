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

public class UICreaUtente {
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


    private EMS ems;

    public void setEMS(EMS ems) {
        this.ems = EMS.getInstance();
    }

    @FXML
    private Button BottoneCreaUtente;

    @FXML
    private Button BottoneIndietroWelcomeView;

    @FXML
    private void tornaAllaWelcomeView() throws IOException {
        Stage primaryStage = (Stage) BottoneIndietroWelcomeView.getScene().getWindow(); // Ottieni lo Stage

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomeView.fxml")); // Carica WelcomeView.fxml
        Scene scene = new Scene(fxmlLoader.load());
        WelcomeController controller = fxmlLoader.getController();
        controller.setEMS(ems);
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
        BottoneIndietroWelcomeView.setVisible(false);
    }

    @FXML
    protected void onBottoneDocente() {
        if(BottoneDocente.isVisible()){
            BottoneDocente.setVisible(false); // Nascondi il bottone "Docente"
            BottoneStudente.setVisible(false); // Nascondi il bottone "Studente"
        }
        //settare la scelta e mostrare l'elenco con i parametri da riempire e registrare
        ems=EMS.getInstance();
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
        //settare la scelta e mostrare l'elenco con i parametri da riempire e registrare
        ems=EMS.getInstance();
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
        nome=CasellaNome.getText();
        cognome=CasellaCognome.getText();

        // Converti LocalDate in Date
        // Date dataNascitaDate = java.util.Date.from(dataNascitaLocalDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
        LocalDate dataNascitaLocalDate = CasellaDataNascita.getValue();
        data_nascita = java.util.Date.from(dataNascitaLocalDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());


        genere=CasellaGenere.getText();
        codice_fiscale=CasellaCodiceFiscale.getText();
        residenza=CasellaResidenza.getText();
        email=CasellaEmail.getText();
        telefono=CasellaTelefono.getText();
        if(tipoProfilo==Utente.TipoProfilo.Studente){
            categoria=CasellaCategoria.getText();
            anno_corso=Integer.parseInt(CasellaAnnoCorso.getText());
            }

        ems=EMS.getInstance();
        if (ems.creaProfiloUtente(nome, cognome, data_nascita, genere, codice_fiscale, residenza, email, telefono))
            System.out.println("Utente creato con successo");

        //devo capire come memorizzare categoria e anno corso, credo ci sia un problema con l'utente corrente
            //System.out.println("Categoria: " + categoria); //qui sono memorizzati correttamente
            //System.out.println("Anno corso: " + anno_corso);
        if(tipoProfilo==Utente.TipoProfilo.Studente){
           ems.AggiungiInfoStudente(categoria, anno_corso);
           System.out.println("Categoria e anno corso aggiunti");
        }

        //nascondo i campi dopo averli acquisiti
        ContenitoreInformazioni.setVisible(false);

        //Il sistema genera una password temporanea e assegna un ID utente come credenziali di accesso per il nuovo utente.
        //RICHIAMARE generaCredenziali ???
        ems.generaCredenziali();

        // Per gli studenti l’ID utente corrisponde alla matricola, mentre per i docenti corrisponde al codice utente.

        //L’Amministratore indica di aver finito. --> capire se serve la funzione confermaUtente in EMS

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Riepilogo Informazioni");
        if(tipoProfilo==Utente.TipoProfilo.Studente){
            //dialog.setHeaderText("LALALA"); //CAPIRE COME VISUALIZZARE LE INFORMAZIONI
            String dati = "Nome: " + studente.getNome() + "\nCognome: " + studente.getCognome() + "\nData nascita: " + studente.getData_nascita() + "\nGenere: " + studente.getGenere() + "\nCodice fiscale: " + studente.getCodice_fiscale() + "\nResidenza: " + studente.getResidenza() + "\nEmail: " + studente.getEmail() + "\nTelefono: " + studente.getTelefono() + "\nCategoria: " + studente.getCategoria() + "\nAnno corso: " + studente.getAnnoCorso() + "\nMatricola: " + studente.getMatricola() + "\nPassword: " + studente.getPassword();
            dialog.setContentText(dati);
        }else{
            String dati = "Nome: " + docente.getNome() + "\nCognome: " + docente.getCognome() + "\nData nascita: " + docente.getData_nascita() + "\nGenere: " + docente.getGenere() + "\nCodice fiscale: " + docente.getCodice_fiscale() + "\nResidenza: " + docente.getResidenza() + "\nEmail: " + docente.getEmail() + "\nTelefono: " + docente.getTelefono() + "\nCodice Docente: " + docente.getCodiceDocente() + "\nPassword: " + docente.getPassword();
            dialog.setContentText(dati);
        }

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // L'utente ha detto OK, fai qualcosa --> richiama funzione conferma??
            //ems.student
            ems.confermaUtente(); //QUI è dove si deve gestire l'hashmap

            BottoneCreaUtente.setVisible(true); //setto visibile BottoneCreaUtente
            BottoneIndietroWelcomeView.setVisible(true);
            //problema con visibilità campi categoria e anno corso se si prova a creare un altro utente
            //ne segue che memorizza nella mappa le cose inserite per lo studente precedente

        }


    }

}