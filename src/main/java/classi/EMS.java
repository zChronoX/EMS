package classi;

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

    public static void EMS() {
        //todo
    };

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
    public void loadStudents(){
        //todo
    }
    public void loadProfessors(){
        //todo
    }
    public void loadCourses(){
        //todo
    }
    public void loadResults(){
        //todo
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
}

