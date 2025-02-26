package classi;

public class Esito_esame {
    private String voto;
    private String stato;
    private Studente studente;
    private Appello_esame appello;


    public Esito_esame(String voto, String stato, Studente studente, Appello_esame appello) {
        this.voto = voto;
        this.stato = stato;
        this.studente = studente;
        this.appello = appello;

    }

    public Esito_esame() {
    }

    public String getVoto() {
        return voto;
    }

    public String getStato() {
        return stato;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public Studente getStudente() {
        return studente;
    }

    public Appello_esame getAppello() {
        return appello;
    }



    @Override
    public String toString() {

        return "Esito_esame{" +
                "voto=" + voto +
                ", stato=" + stato + '}';

    }

}
