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
    private Insegnamento insegnamentoCorrente;
    private Appello_esame appelloCorrente;
    private Prenotazione prenotazioneCorrente;
    private HashMap<String,Studente> student_list;
    private HashMap<String,Docente> doc_list;
    private Map<String,Insegnamento> teaching_list;
    private UtenteFactory utenteFactory;
    public static final int POSTI_MAX = 500;
    private HashMap<String,Appello_esame> exam_list;
    private HashMap<String, Prenotazione> reservation_list;

    public EMS() {
        this.exam_list = new HashMap<>();
        this.reservation_list = new HashMap<>();// Initialization in the constructor
    }

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
    public Map<String, Appello_esame> getExam_list() {
        return exam_list;
    }

    public List<Appello_esame> visualizzaAppelli(String idInsegnamento, Studente studente) {
        List<Appello_esame> appelliDisponibili = new ArrayList<>();
        for (Appello_esame appello : exam_list.values()) {
            if (appello.getInsegnamento().getID_insegnamento().equals(idInsegnamento) && !studente.getAppelliPrenotati().contains(appello)) {
                appelliDisponibili.add(appello);
            }
        }
        return appelliDisponibili;
    }
    public Map<String, Prenotazione> getReservation_list() {
        return reservation_list;
    }


    public Prenotazione prenotaAppello(String idAppello, Studente studente) {
        Appello_esame appello = exam_list.get(idAppello);

        if (appello == null) {
            System.err.println("Appello non trovato.");
            return null; // Appello non trovato
        }

        if (appello.getPostiDisponibili() <= 0) {
            System.err.println("Non ci sono posti disponibili per questo appello.");
            return null; // Posti esauriti
        }

        // Controllo doppia prenotazione (essenziale)
        if (studente.getAppelliPrenotati().contains(appello)) {
            System.err.println("Hai già prenotato questo appello.");
            return null; // Gia prenotato
        }

        // Prenotazione
        appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);
        studente.aggiungiAppelloPrenotato(appello);
        System.out.println("Posti disponibili dopo la prenotazione: "+appello.getPostiDisponibili());

        // Creazione della prenotazione
        String idPrenotazione = generaIdPrenotazione(); // Funzione da implementare
        Prenotazione prenotazione = new Prenotazione(idPrenotazione, LocalDate.now(), LocalTime.now(), 1, studente, appello);
        reservation_list.put(idPrenotazione, prenotazione);

        return prenotazione;
    }

    private String generaIdPrenotazione() {
        // Implementa la logica per generare un ID univoco per la prenotazione
        // Puoi usare un timestamp, un contatore, un UUID, ecc.
        return "PREN" + System.currentTimeMillis(); // Esempio: PREN seguito da un timestamp
    }




    public void setStudentList(HashMap<String, Studente> student_list) {
        this.student_list = student_list;
    }

    public void setDocList(HashMap<String, Docente> doc_list) {
        this.doc_list = doc_list;
    }

    public void setTeachingList(HashMap<String, Insegnamento> teaching_list) {
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


    public HashMap<String,Appello_esame> selezionaInsegnamento (String ID_insegnamento) {

        if(!teaching_list.containsKey(ID_insegnamento)) { //si verifica se l'ID_insegnamento è presente nella mappa degli insegnamenti
            System.err.println("Errore: Insegnamento non trovato.");
            return null;
        }
        else {
            insegnamentoCorrente = teaching_list.get(ID_insegnamento);
            exam_list = insegnamentoCorrente.getListaAppelli(ID_insegnamento);
            return exam_list;
        }
    }

    public HashMap<String, Prenotazione> selezionaAppello (String ID_appello) {

        if(!exam_list.containsKey(ID_appello)) {
            System.err.println("Errore: Appello non trovato.");
            return null;
        }
        else {
            appelloCorrente =exam_list.get(ID_appello);
            reservation_list= appelloCorrente.getPrenotazioniStudenti(ID_appello);
            return reservation_list;
        }
    }

    public void inserisciEsitoStudente(String Matricola,String voto, String stato ){

        if(!reservation_list.containsKey(Matricola)) {
            System.err.println("Errore: Matricola non trovato.");
        }
        else {
            Prenotazione prenotazione = reservation_list.get(Matricola);
            prenotazione.inserisciEsito(voto, stato);
            }
        }

    public void confermaInserimentoEsiti(String ID_insegnamento,String ID_appello) {
        List<Esito_esame> result_list = new ArrayList<>();  // Lista per raccogliere gli esiti

        // Cicliamo su tutte le prenotazioni nella mappa
        for (Map.Entry<String, Prenotazione> entry : reservation_list.entrySet()) {
            Prenotazione prenotazione = entry.getValue();  // Otteniamo la prenotazione dalla mappa
            Esito_esame esito = prenotazione.getEsito();  // Otteniamo l'esito della prenotazione

            if (esito != null) {
                // Aggiungiamo l'esito alla lista se è valido
                result_list.add(esito);
            } else {
                System.err.println("Errore: Nessun esito presente per la prenotazione con ID " +
                        prenotazione.getID_prenotazione());
            }
        }

        // Qui puoi anche scegliere di fare qualcosa con la result_list, come ad esempio stamparla
        for (Esito_esame esito : result_list) {
            System.out.println(esito);
        }
    }
}



