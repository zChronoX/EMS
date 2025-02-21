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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UIListaAppelli implements Initializable {
    private EMS ems;
    private Insegnamento insegnamento;
    private Studente studente;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ems = EMS.getInstance();
        studente = ems.getStudenteCorrente();

        //codice inserito in UIPrenotazioneAppello, come faccio a recuperarlo?
        insegnamento=ems.getInsegnamentoSelezionato();

        visualizzaAppelli(); // Visualizza gli appelli subito dopo aver ricevuto l'insegnamento

    }

    public UIListaAppelli() {}

    @FXML
    private TextField idAppelloTextField; // Nuova casella di testo per l'ID dell'appello

    @FXML
    private Button confermaPrenotazioneButton;
    @FXML
    private ListView<String> appelliListView;
    @FXML
    private Button BottoneIndietroCercaInsegnamento;


    private void visualizzaAppelli() {
        if (insegnamento != null && ems != null && studente != null) {
            //non penso ci sia bisogno di passare l'insegnamento a EMS perché abbiamo fatto la set su insegnamentoSelezionato
            //List<Appello_esame> appelli = ems.getAppelliByInsegnamento(insegnamento);
            List<Appello_esame> appelli = ems.getAppelliByInsegnamento(); //recupero gli appelli di un determinato insegnamento

            appelliListView.getItems().clear();

            if (appelli == null || appelli.isEmpty()) {
                System.out.println("Nessun appello trovato per questo insegnamento.");
                appelliListView.getItems().add("Nessun appello trovato per questo insegnamento.");
                return;
            }

            //FORSE BISOGNA RICHIAMARE EMS.GETAPPELLI() ???
            List<Appello_esame> appelliPrenotati = studente.getAppelli(); //recupera gli appelli relativi allo studente corrente per vedere se è già prenotato

            for (Appello_esame appello : appelli) {
                // Verifica se lo studente è già prenotato a questo appello
                boolean giaPrenotato = false;
                if (appelliPrenotati != null) {
                    for (Appello_esame appelloPrenotato : appelliPrenotati) {
                        if (appello.equals(appelloPrenotato)) {
                            giaPrenotato = true;
                            break;
                        }
                    }
                }

                // Verifica posti disponibili e se lo studente non è già prenotato
                if (!giaPrenotato && appello.getPostiDisponibili() > 0 &&
                        appello.getTipologia() != null && studente.getCategoria() != null &&
                        appello.getTipologia().equals(studente.getCategoria())) {
                    appelliListView.getItems().add(appello.toString());
                }
            }

            if (appelliListView.getItems().isEmpty()) {
                System.out.println("Nessun appello trovato per questa categoria a cui non sei ancora prenotato e con posti disponibili.");
                appelliListView.getItems().add("Nessun appello trovato per questa categoria a cui non sei ancora prenotato e con posti disponibili.");
            }

        } else {
            System.out.println("Insegnamento, EMS o Studente sono null!");
            appelliListView.getItems().add("Errore: Dati non disponibili.");
        }
    }


    @FXML
    private void handleConfermaPrenotazione(ActionEvent event) {
        String idAppello = idAppelloTextField.getText();

        if (idAppello.isEmpty()) {
            showAlert("Errore", "Inserisci l'ID dell'appello.");
            return;
        }

        Appello_esame appelloSelezionato = ems.getAppelloById(idAppello); // Passa direttamente la stringa

        if (appelloSelezionato == null) {
            showAlert("Errore", "Nessun appello trovato con questo ID.");
            return;
        }

        // Mostra il pop-up di riepilogo
        showRiepilogoAppello(appelloSelezionato);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
    private void showRiepilogoAppello(Appello_esame appello) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Riepilogo Appello");

        // Crea il contenuto del pop-up
        VBox vbox = new VBox();
        vbox.getChildren().add(new Label("ID: " + appello.getID_appello()));
        vbox.getChildren().add(new Label("Insegnamento: " + appello.getInsegnamento().getNome()));
        vbox.getChildren().add(new Label("Data: " + appello.getData()));
        vbox.getChildren().add(new Label("Tipologia: " + appello.getTipologia()));
        vbox.getChildren().add(new Label("Orario: " + appello.getOrario()));
        vbox.getChildren().add(new Label("Luogo: " + appello.getLuogo()));


        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);


        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    ems.prenotaAppello(studente, appello); // Metodo da implementare in EMS
                    showAlert("Successo", "Prenotazione effettuata con successo.");
                    visualizzaAppelli();

                } catch (Exception e) {
                    showAlert("Errore", "Errore durante la prenotazione: " + e.getMessage());
                }
            }
        });
    }
*/
    private void showRiepilogoAppello(Appello_esame appello) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Riepilogo Appello");

        VBox vbox = new VBox();
        vbox.getChildren().add(new Label("ID: " + appello.getID_appello()));

        // Verifica se l'insegnamento non è null
        Insegnamento insegnamento = appello.getInsegnamento();
        if (insegnamento != null) {
            vbox.getChildren().add(new Label("Insegnamento: " + insegnamento.getNome()));
        } else {
            vbox.getChildren().add(new Label("Insegnamento: Non disponibile"));
        }

        vbox.getChildren().add(new Label("Data: " + appello.getData()));
        vbox.getChildren().add(new Label("Tipologia: " + appello.getTipologia()));
        vbox.getChildren().add(new Label("Orario: " + appello.getOrario()));
        vbox.getChildren().add(new Label("Luogo: " + appello.getLuogo()));

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    boolean success = ems.prenotaAppello(appello);
                    if (success) {
                        showAlert("Successo", "Prenotazione effettuata con successo.");
                        visualizzaAppelli();
                    } else {
                        showAlert("Posti esauriti", "Non ci sono posti disponibili per questo appello.");
                    }
                } catch (Exception e) {
                    showAlert("Errore", "Errore durante la prenotazione: " + e.getMessage());
                }
            }
        });
    }


    @FXML
    public void IndietroCercaInsegnamento() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PrenotazioneAppelloView.fxml")); // Assicurati che il nome del file sia corretto
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        stage.setTitle("Prenotazione Appello");
        stage.setScene(scene);
        stage.show();

        Stage currentStage = (Stage) BottoneIndietroCercaInsegnamento.getScene().getWindow();
        currentStage.close();
    }
}
