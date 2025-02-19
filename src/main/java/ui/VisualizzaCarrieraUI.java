package ui;

import classi.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizzaCarrieraUI implements Initializable {

    private EMS ems;
    private Studente studente;


    public VisualizzaCarrieraUI() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        studente = ems.getStudenteCorrente();
        mostraDatiStudente();
        caricaAppelliApprovati();
        mostraMedia();

    }

    @FXML
    private Label matricolaLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Label cognomeLabel;
    @FXML
    private Label categoriaLabel;
    @FXML
    private Label annoCorsoLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label telefonoLabel;
    @FXML
    private Label residenzaLabel;
    @FXML
    private Label codiceFiscaleLabel;
    @FXML
    private Label dataNascitaLabel;

    @FXML
    private Label mediaLabel;

    @FXML
    private Button BottoneIndietroVistaStudente;

    @FXML
    private ListView<String> appelliApprovatiListView;



    private void mostraDatiStudente() {
        if (studente != null) {
            matricolaLabel.setText("Matricola: " + studente.getMatricola());
            nomeLabel.setText("Nome: " + studente.getNome());
            cognomeLabel.setText("Cognome: " + studente.getCognome());
            categoriaLabel.setText("Categoria: " + studente.getCategoria());
            annoCorsoLabel.setText("Anno Corso: " + String.valueOf(studente.getAnnoCorso()));
            emailLabel.setText("Email: "  + studente.getEmail());
            telefonoLabel.setText("Telefono: " + studente.getTelefono());
            residenzaLabel.setText("Residenza: " + studente.getResidenza());
            codiceFiscaleLabel.setText("Codice Fiscale: " + studente.getCodice_fiscale());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Formato data desiderato
            String dataNascitaFormattata = formatter.format(studente.getData_nascita());
            dataNascitaLabel.setText("Data di nascita: " + dataNascitaFormattata);
        }




    }

    private void caricaAppelliApprovati() {
        // 1. Recupera la lista di appelli approvati dallo studente
//        List<String> appelli = new ArrayList<>();
//        for (Prenotazione prenotazione : ems.getReservation_list().values()) { // Itera sui valori della mappa
//            if (prenotazione.getStudente().equals(studente)) {
//                Esito_esame esito = prenotazione.getEsito();
//                if (esito != null && esito.getStato().equalsIgnoreCase("Approvato")) {
//                    Appello_esame appelloEsame = prenotazione.getAppello();
//                    Insegnamento insegnamento = appelloEsame.getInsegnamento();
//                    String appelloString = "ID Appello: (" + appelloEsame.getID_appello() + ")" + " Nome Insegnamento :" + insegnamento.getNome() + " - Voto: " + esito.getVoto() + " Esito: " + esito.getStato(); // Formatta l'appello come stringa
//                    appelli.add(appelloString);
//                }
//            }
//        }
//
//
//
//        // 2. Popola la ListView con i dati
//        appelliApprovatiListView.getItems().clear(); // Pulisci la ListView prima di aggiungere nuovi elementi
//        appelliApprovatiListView.getItems().addAll(appelli);

        List<String> appelli = ems.getAppelliApprovati(studente);

// 2. Popola la ListView con i dati
        appelliApprovatiListView.getItems().clear(); // Pulisci la ListView prima di aggiungere nuovi elementi
        appelliApprovatiListView.getItems().addAll(appelli);
    }


    private void mostraMedia() {
        double media = ems.calcolaMediaVoti(studente);
        mediaLabel.setText("Media Aritmetica: " + String.format("%.2f", media)); // Formatta la media con due decimali
    }






    @FXML
    public void IndietroVistaStudente() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudenteView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Pagina Studente");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroVistaStudente.getScene().getWindow();
        currentStage.close();
    }


}

