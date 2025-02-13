package classi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Insegnamento {
    private String ID_insegnamento;
    private String Nome;
    private int CFU;
    private String Descrizione;
    private int Anno;
    private Docente docente;
    private List<Studente> studenti = new ArrayList<>();
    private List<Docente> docenti = new ArrayList<>();
    private HashMap<String,Appello_esame> exam_list;

  /*  public Insegnamento(String ID_insegnamento, String Nome, int CFU, String Descrizione, int Anno, Docente docente) {
        this.ID_insegnamento = ID_insegnamento;
        this.Nome = Nome;
        this.CFU = CFU;
        this.Descrizione = Descrizione;
        this.Anno = Anno;
        this.docenti = new ArrayList<>();
        this.exam_list = new HashMap<>();
    }*/

    public Insegnamento(String ID_insegnamento, String Nome, int CFU, String Descrizione, int Anno) {
        this.ID_insegnamento = ID_insegnamento;
        this.Nome = Nome;
        this.CFU = CFU;
        this.Descrizione = Descrizione;
        this.Anno = Anno;
        this.exam_list = new HashMap<>();
    }

    public Insegnamento() {};

    public void aggiungiDocente(Docente docente) {
        docenti.add(docente);
    }

    public void aggiungiAppello(Appello_esame appello) {

        exam_list.put(appello.getID_appello(), appello);
    }

    public void iscriviStudente(Studente studente) {
        studenti.add(studente);
    }

    public boolean isStudenteIscritto(Studente studente) {
        return studenti.contains(studente);
    }

    public List<Studente> getStudenti() {
        return studenti;
    }


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

    public void setStudenti(List<Studente> studenti) {
        this.studenti = studenti;
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public HashMap<String, Appello_esame> getExam_list() {
        return exam_list;
    }

    @Override
    public String toString() {

        StringBuilder docentiString = new StringBuilder();
        for (Docente docente : docenti) {
            docentiString.append(docente.getNome()).append(" ").append(docente.getCognome()).append(", ");
        }
        if (!docentiString.isEmpty()) {
            docentiString.delete(docentiString.length() - 2, docentiString.length()); // Rimuovi l'ultima virgola e spazio
        } else {
            docentiString.append("Sconosciuto");
        }
        return "Insegnamento{" +
                "ID_insegnamento='" + ID_insegnamento + '\'' + // Corretto: ID_insegnamento
                ", Nome='" + Nome + '\'' + // Corretto: Nome
                ", CFU=" + CFU + // Corretto: CFU
                ", Descrizione='" + Descrizione + '\'' + // Corretto: Descrizione
                ", Anno=" + Anno + // Corretto: Anno
                ", docenti=" + docentiString.toString() + // Stampa la lista di docenti
                '}';
    }
}
