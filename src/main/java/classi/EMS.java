package classi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

public class EMS {
    private static EMS INSTANCE;
    private Utente utenteCorrente;
    private Studente studenteCorrente;
    private Docente docenteCorrente;
    private Map<String,Studente> student_list;
    private Map<String,Docente> doc_list;
    private UtenteFactory utenteFactory;
    public static final int POSTI_MAX = 500;
    private Map<String,Appello_esame> exam_list;

    public static void EMS() {
        //todo
    };
   /* public void loadStudents () {
        //todo
    };
    public void loadProfessors () {
        //todo
    };
    public void loadCourses() {
        //todo
    };
    public void loadExams () {
        //todo
    };
    public void loadResults () {
        //todo
    }; */
    //Pattern Singleton
    public static EMS getIstance(){
        //todo
        if(INSTANCE == null){
            INSTANCE = new EMS();
            return INSTANCE;
        }
        else
            return INSTANCE;

    };
    public void AggiungiInfoStudente(String categoria, int anno_corso){
        if(utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.setCategoria(categoria);
            studenteCorrente.setAnnoCorso(anno_corso);
        }
    }


    public void confermaUtente() {
        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            student_list.put(studenteCorrente.getMatricola(), studenteCorrente);
        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) docenteCorrente;
            doc_list.put(docenteCorrente.getCodiceDocente(), docenteCorrente);
        }
    }
    public boolean creaProfiloUtente(String nome, String cognome, Date data_nascita, String genere, String codice_fiscale, String residenza, String email, String telefono, Utente.TipoProfilo tipoProfilo) {

        utenteCorrente.inizializzaUtente(nome,cognome,genere,data_nascita,codice_fiscale,residenza,email,telefono);
        return true;
    }
    public void generaCredenziali(){
        if(utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.assegnaIdentificativiStudente();
        }
        else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            docenteCorrente.assegnaIdentificativiDocente();
        }
    }
    public void scegliTipoProfilo(Utente.TipoProfilo tipoProfilo) {
        utenteFactory = new UtenteFactory();
        utenteCorrente= utenteFactory.newUser(tipoProfilo);
    }
    public String creaAppelloEsame(Insegnamento insegnamento, LocalTime orario, LocalDate data, String luogo, String tipologia){

        Appello_esame nuovoAppello = new Appello_esame();
        String idGenerato = "10" + (System.currentTimeMillis() % 10000); // Usa timestamp come base per ID univoco
        nuovoAppello.setID_appello(idGenerato);
        nuovoAppello.setInsegnamento(insegnamento); // associa l'appello all'insegnamento
        nuovoAppello.setOrario(orario);
        nuovoAppello.setData(data);
        nuovoAppello.setLuogo(luogo);
        nuovoAppello.setPostiDisponibili(POSTI_MAX);
        nuovoAppello.setTipologia(tipologia);
        return idGenerato;
    }
    public void confermaAppello (Appello_esame nuovoAppello){
        if(nuovoAppello != null){
            exam_list.put(nuovoAppello.getID_appello(), nuovoAppello); //aggiunge l'appello alla lista
        }
       else {
            System.out.println("Errore: Nessun appello da creare.");
        }

    }


}

