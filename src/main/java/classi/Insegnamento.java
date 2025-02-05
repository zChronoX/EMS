package classi;

public class Insegnamento {
    private String ID_insegnamento;
    private String Nome;
    private int CFU;
    private String Descrizione;
    private int Anno;
    private Docente docente;

    public Insegnamento(String ID_insegnamento, String nome, int CFU, String descrizione, int anno, Docente docente) {
        this.ID_insegnamento = ID_insegnamento;
        Nome = nome;
        this.CFU = CFU;
        Descrizione = descrizione;
        Anno = anno;
        this.docente = docente;
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

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        String docenteNomeCompleto = (docente != null) ? docente.getNome() + " " + docente.getCognome() : "Sconosciuto";
        return "Insegnamento{" +
                "ID_insegnamento='" + ID_insegnamento + '\'' + // Corretto: ID_insegnamento
                ", Nome='" + Nome + '\'' + // Corretto: Nome
                ", CFU=" + CFU + // Corretto: CFU
                ", Descrizione='" + Descrizione + '\'' + // Corretto: Descrizione
                ", Anno=" + Anno + // Corretto: Anno
                ", docente=" + docenteNomeCompleto +
                '}';
    }
}
