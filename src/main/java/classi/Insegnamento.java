package classi;

public class Insegnamento {
    private String ID_insegnamento;
    private String Nome;
    private int CFU;
    private String Descrizione;
    private int Anno;

    public Insegnamento(String ID_insegnamento, String nome, int CFU, String descrizione, int anno) {
        this.ID_insegnamento = ID_insegnamento;
        Nome = nome;
        this.CFU = CFU;
        Descrizione = descrizione;
        Anno = anno;
    }

    public Insegnamento() {};

    public String getID_insegnamento() {
        return ID_insegnamento;
    }

    public void setID_insegnamento(String ID_insegnamento) {
        this.ID_insegnamento = ID_insegnamento;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getCFU() {
        return CFU;
    }

    public void setCFU(int CFU) {
        this.CFU = CFU;
    }

    public String getDescrizione() {
        return Descrizione;
    }

    public void setDescrizione(String descrizione) {
        Descrizione = descrizione;
    }

    public int getAnno() {
        return Anno;
    }

    public void setAnno(int anno) {
        Anno = anno;
    }

    @Override
    public String toString() {
        return "Insegnamento{" +
                "ID_insegnamento='" + ID_insegnamento + '\'' +
                ", Nome='" + Nome + '\'' +
                ", CFU=" + CFU +
                ", Descrizione='" + Descrizione + '\'' +
                ", Anno=" + Anno +
                '}';
    }
}
