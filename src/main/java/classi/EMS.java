package classi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class EMS {
    private static EMS INSTANCE;
    private Utente utenteCorrente;
    private Studente studenteCorrente;
    private Docente docenteCorrente;
    private HashMap<String,Studente> student_list;
    private HashMap<String,Docente> doc_list;
    private Map<String,Insegnamento> teaching_list;
    private UtenteFactory utenteFactory;
    private Appello_esame appelloCorrente;
    public static final int POSTI_MAX = 500;
    private HashMap<String,Appello_esame> exam_list;

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
    public String creaAppelloEsame(LocalTime orario, LocalDate data, String luogo, String tipologia){

        String idGenerato = "10" + (System.currentTimeMillis() % 10000); // Usa timestamp come base per ID univoco
        appelloCorrente.setID_appello(idGenerato);
        appelloCorrente.setOrario(orario);
        appelloCorrente.setData(data);
        appelloCorrente.setLuogo(luogo);
        appelloCorrente.setPostiDisponibili(POSTI_MAX);
        appelloCorrente.setTipologia(tipologia);
        return idGenerato;
    }
    public void confermaAppello (){
        if(appelloCorrente != null){
            exam_list.put(appelloCorrente.getID_appello(), appelloCorrente);
        }
       else {
            System.out.println("Errore: Nessun appello da creare.");
        }

    }


    public void setStudentList(HashMap<String, Studente> student_list) {
        this.student_list = student_list;
    }

    public void setDocList(HashMap<String, Docente> doc_list) {
        this.doc_list = doc_list;
    }

    public void setTeachingList(Map<String, Insegnamento> teaching_list) {
        this.teaching_list = teaching_list;
    }

    public List<Insegnamento> visualizzaInsegnamenti() {
        if (studenteCorrente == null) {
            throw new IllegalStateException("Studente non loggato.");
        }

        List<Insegnamento> insegnamentiStudente = new ArrayList<>();
        for (Insegnamento insegnamento : teaching_list.values()) {
            if (insegnamento.getStudenti().contains(studenteCorrente)) {
                insegnamentiStudente.add(insegnamento);
            }
        }

        return insegnamentiStudente;
    }

    public List<Insegnamento> getInsegnamentiIscritto(Studente studente) {
        List<Insegnamento> insegnamentiIscritto = new ArrayList<>();
        for (Insegnamento insegnamento : teaching_list.values()) {
            if (insegnamento.getStudenti().contains(studente)) {
                insegnamentiIscritto.add(insegnamento);
            }
        }
        return insegnamentiIscritto;
    }

    public Studente getStudenteCorrente() {
        return studenteCorrente;
    }





    public boolean loginStudente(String matricola, String password) throws Exception {
        // 1. Recupera lo studente dal registro (database, HashMap, ecc.)
        Studente studente = student_list.get(matricola);

        // 2. Verifica se lo studente esiste
        if (studente != null) {
            // 3. Verifica se la password fornita corrisponde alla password dello studente
            if (studente.getPassword().equals(password)) {
                // 4. Se le credenziali sono corrette, memorizza lo studente corrente
                studenteCorrente = studente;
                return true;
            } else {
                // 5. Se la password è errata, lancia un'eccezione
                throw new Exception("Password errata.");
            }
        } else {
            // 6. Se lo studente non esiste, lancia un'eccezione
            throw new Exception("Studente non presente nel registro.");
        }
    }


    public boolean loginDocente(String codice_docente, String password) throws Exception {
        // 1. Recupera il docente dal registro (database, HashMap, ecc.)
        Docente docente = doc_list.get(codice_docente); // Assumo che tu abbia una mappa 'docenti'

        // 2. Verifica se il docente esiste
        if (docente != null) {
            // 3. Verifica se la password fornita corrisponde alla password del docente
            if (docente.getPassword().equals(password)) {
                // 4. Se le credenziali sono corrette, memorizza il docente corrente
                docenteCorrente = docente;
                return true;
            } else {
                // 5. Se la password è errata, lancia un'eccezione
                throw new Exception("Password errata.");
            }
        } else {
            // 6. Se il docente non esiste, lancia un'eccezione
            throw new Exception("Docente non presente nel registro.");
        }
    }

}

