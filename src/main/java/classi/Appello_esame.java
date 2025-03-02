package classi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Appello_esame {
    private String ID_appello;
    private LocalTime Orario;
    private LocalDate Data;
    private String Luogo;
    private int postiDisponibili;
    private String Tipologia;
    private Insegnamento insegnamento;
    private List<Studente> studenti = new ArrayList<>();
    private List<String> feedbacks = new ArrayList<>();

   public Appello_esame(String ID_appello, LocalDate Data, LocalTime Orario, String Luogo, int postiDisponibili, String Tipologia, Insegnamento insegnamento) {
       this.ID_appello = ID_appello;
       this.Data = Data;
       this.Orario = Orario;
       this.Luogo = Luogo;
       this.postiDisponibili = postiDisponibili;
       this.Tipologia = Tipologia;
       this.insegnamento = insegnamento;

   }

    public List<Studente> getStudenti() {
        return studenti;
    }

    public void removeStudente(Studente studente) {
        this.studenti.remove(studente);
    }

    public boolean isStudentePrenotato(Studente studente) {
        return studenti.contains(studente);
    }

    public Appello_esame() {};

    public String getID_appello() {
        return ID_appello;
    }

    public void setID_appello(String ID_appello) {
        this.ID_appello = ID_appello;
    }

    public LocalTime getOrario() {
        return Orario;
    }

    public void setOrario(LocalTime orario) {
        Orario = orario;
    }

    public LocalDate getData() {
        return Data;
    }

    public void setData(LocalDate data) {
        Data = data;
    }

    public String getLuogo() {
        return Luogo;
    }

    public void setLuogo(String luogo) {
        Luogo = luogo;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public String getTipologia() {
        return Tipologia;
    }

    public Insegnamento getInsegnamento() {
        return insegnamento;
    }

    public void setInsegnamento(Insegnamento insegnamento) { this.insegnamento = insegnamento; }

    public List<String> getFeedbacks() {
        return feedbacks;
    }

    @Override
    public String toString() {
        String insegnamentoNome = (insegnamento != null) ? insegnamento.getNome() : "Sconosciuto";

        String docentiString;
        if (insegnamento != null && insegnamento.getDocenti() != null && !insegnamento.getDocenti().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Docente docente : insegnamento.getDocenti()) {
                sb.append(docente.getNome()).append(" ").append(docente.getCognome()).append(", ");
            }
            docentiString = sb.substring(0, sb.length() - 2); // Rimuove l'ultima virgola e spazio
        } else {
            docentiString = "Sconosciuto";
        }

        return "Appello_esame{" +
                "ID_appello='" + ID_appello + '\'' +
                ", Orario=" + Orario +
                ", Data=" + Data +
                ", Luogo='" + Luogo + '\'' +
                ", postiDisponibili=" + postiDisponibili +
                ", Tipologia='" + Tipologia + '\'' +
                ", insegnamento=" + insegnamentoNome +
                ", docenti=" + docentiString + // Usa direttamente la stringa
                '}';
    }

    public void addStudente(Studente studente) throws Exception {
        if (studente == null) {
            throw new Exception("Studente non valido.");
        }
        if (this.studenti.contains(studente)) {
            throw new Exception("Studente già prenotato a questo appello.");
        }
        this.studenti.add(studente);
    }

    public  void addFeedback(String recensione){
        if(recensione != null && !recensione.isEmpty()){
            this.feedbacks.add(recensione);
        }
        else {
            System.out.println("non hai scritto niente");
        }
    }
    public boolean puòGestireEsiti(Docente docenteCorrente) {
        // Verifica se il docente è nella lista dei docenti dell'insegnamento
        if (insegnamento != null && insegnamento.getDocenti() != null) {
            return insegnamento.getDocenti().contains(docenteCorrente);
        }
        return false; // Se l'insegnamento o la lista dei docenti è nulla, il docente non può gestire
    }
}