package classi;

import java.time.LocalDate;
import java.time.LocalTime;



public class Prenotazione {
    private String ID_prenotazione;
    private LocalDate Data;
    private LocalTime Ora;
    private int Progressivo;
    private Studente studente;
    private Appello_esame appello;
    private Esito_esame esito;
    private boolean recensito;

    public Prenotazione(String ID_prenotazione, LocalDate data, LocalTime ora, int progressivo,
                        Studente studente, Appello_esame appello) {
        this.ID_prenotazione = ID_prenotazione;
        Data = data;
        Ora = ora;
        Progressivo = progressivo;
        this.studente = studente;
        this.appello = appello;
        recensito = false;
    }
    public Prenotazione() {};

    public String getID_prenotazione() {
        return ID_prenotazione;
    }

    public void setID_prenotazione(String ID_prenotazione) {
        this.ID_prenotazione = ID_prenotazione;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public void setOra(LocalTime ora) {
        Ora = ora;
    }

    public void setProgressivo(int progressivo) {
        Progressivo = progressivo;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public Appello_esame getAppello() {
        return appello;
    }

    public void setAppello(Appello_esame appello) {
        this.appello = appello;
    }

    public Esito_esame getEsito() {
        return esito;
    }

    public void setEsito(Esito_esame esito) {
        this.esito = esito;
    }


    @Override
    public String toString() {
        return "Prenotazione{" +
                "ID_prenotazione='" + ID_prenotazione + '\'' +
                ", Data=" + Data +
                ", Ora=" + Ora +
                ", Progressivo=" + Progressivo +
                ", studente=" + studente +
                ", appello=" + appello +
                '}';
    }

    public boolean getRecensito() {
        return recensito;
    }

    public void setRecensito(boolean recensito) {
        this.recensito = recensito;
    }
}

