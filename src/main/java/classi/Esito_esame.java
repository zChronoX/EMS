package classi;

public class Esito_esame {
    private String voto;
    private String stato;

    public Esito_esame(String voto, String stato) {

        this.voto = voto;
        this.stato = stato;

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

    @Override
    public String toString() {

        return "Esito_esame{" +
                "voto=" + voto +
                ", stato=" + stato + '}';

    }

}
