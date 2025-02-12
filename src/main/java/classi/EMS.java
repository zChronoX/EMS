package classi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class EMS {
    private static EMS ems; //CAMPO PER MEMORIZZARE L'UNICA ISTANZA
    private Utente utenteCorrente;
    private Studente studenteCorrente;
    private Docente docenteCorrente;
    private HashMap<String,Studente> student_list;
    private HashMap<String,Docente> doc_list;
    private Map<String,Insegnamento> teaching_list;
    private UtenteFactory utenteFactory;
    public static final int POSTI_MAX = 500;
    private List<Esito_esame> esitiList = new ArrayList<>();
    private List<Prenotazione> prenotazioniList = new ArrayList<>();
   // private HashMap<String,Appello_esame> exam_list;

    public EMS() {
        //this.exam_list = new HashMap<>(); // Initialization in the constructor
        this.student_list = new HashMap<>();
        this.doc_list = new HashMap<>();
        this.teaching_list = new HashMap<>();


        Utility utility = new Utility(); // Crea un'istanza di Utility

        // Carica gli studenti statici
        HashMap<String, Studente> studentiStatici = utility.loadStudents();
        this.student_list.putAll(studentiStatici); // Aggiungi gli studenti statici alla student_list

        // Carica i docenti statici
        HashMap<String, Docente> docentiStatici = utility.loadProfessors(); // Assumi che tu abbia un metodo del genere
        if(docentiStatici != null) { //controllo per evitare null pointer exception nel caso in cui non ci siano docenti statici
            this.doc_list.putAll(docentiStatici); // Aggiungi i docenti statici alla doc_list
        }

        Path currentFilePath = Paths.get("").toAbsolutePath(); // Ottenere il path della directory corrente
        Path filePath = currentFilePath.resolve("src\\main\\files\\insegnamenti.txt");
        teaching_list= utility.loadCourses(filePath.toAbsolutePath().toString(),doc_list,student_list);


    }

    public void stampa_studenti() {
        // Stampa la student_list
        System.out.println("Studenti caricati:");
        for (Map.Entry<String, Studente> entry : this.student_list.entrySet()) {
            String chiave = entry.getKey();
            Studente studente = entry.getValue();
            System.out.println("Matricola: " + chiave + ", Studente: " + studente);
        }
    }

    public void stampa_docenti() {
        // Stampa la doc_list
        System.out.println("\nDocenti caricati:"); // Aggiunto un newline per separare le stampe
        for (Map.Entry<String, Docente> entry : this.doc_list.entrySet()) {
            String chiave = entry.getKey();
            Docente docente = entry.getValue();
            System.out.println("Codice Docente: " + chiave + ", Docente: " + docente);
        }
    }
    public void stampa_insegnamenti() {
        System.out.println("Lista degli insegnamenti:");
        for (Insegnamento insegnamento : teaching_list.values()) {
            System.out.println(insegnamento);
        }
    }

    public void stampa_tutti_gli_appelli() {
        System.out.println("Elenco di tutti gli appelli:");

        if (teaching_list == null || teaching_list.isEmpty()) {
            System.out.println("Non ci sono insegnamenti registrati.");
            return;
        }

        for (Insegnamento insegnamento : teaching_list.values()) {
            System.out.println("\nInsegnamento: " + insegnamento.getNome());

            if (insegnamento.getExam_list() == null || insegnamento.getExam_list().isEmpty()) {
                System.out.println("  Non ci sono appelli per questo insegnamento.");
            } else {
                for (Appello_esame appello : insegnamento.getExam_list().values()) {
                    System.out.println("  - " + appello); // Stampa l'oggetto Appello_esame (richiede toString())
                }
            }
        }
    }

    public void stampa_utenti(){
        stampa_studenti();
        stampa_docenti();
        stampa_insegnamenti();
        stampa_tutti_gli_appelli();
    }
    public Map<String, Insegnamento> getInsegnamenti() {
        return teaching_list;
    }
    /*public static void EMS() {
        //todo
    };*/
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
    public static EMS getInstance(){
        //todo
        if(ems == null){
            ems = new EMS();
            return ems;
        }
        else
            return ems;

    };
    //come fa questa funzione a capire qual è l'utente corrente? penso ci voglia una get
    public void AggiungiInfoStudente(String categoria, int anno_corso){
        utenteCorrente=ems.getUtenteCorrente(); //aggiunta da me, non so se è giusto
        if(utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) { //NON FA QUESTO IF
            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.setCategoria(categoria);
            studenteCorrente.setAnnoCorso(anno_corso);
        }
    }



    public void confermaUtente() {
        utenteCorrente = ems.getUtenteCorrente();
        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            student_list.put(studenteCorrente.getMatricola(), studenteCorrente);

            System.out.println("Elenco Studenti:");
            System.out.println("------------------------------------");
            for (Map.Entry<String, Studente> entry : this.student_list.entrySet()) {
                String chiave = entry.getKey();
                Studente studente = entry.getValue();
                System.out.println("Matricola: " + chiave + ", Studente: " + studente);
            }
        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            doc_list.put(docenteCorrente.getCodiceDocente(), docenteCorrente);

            System.out.println("Elenco Docenti:");
            System.out.println("------------------------------------");
            for (Map.Entry<String, Docente> entry : this.doc_list.entrySet()) {
                String chiave = entry.getKey();
                Docente docente = entry.getValue();
                System.out.println("Codice Docente: " + chiave + ", Docente: " + docente);
            }
        }
    }

    public boolean creaProfiloUtente(String nome, String cognome, Date data_nascita, String genere, String codice_fiscale, String residenza, String email, String telefono) {

        utenteCorrente.inizializzaUtente(nome,cognome, data_nascita, genere,codice_fiscale,residenza,email, telefono);
        return true;
    }
    public void generaCredenziali(){
        if(utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            //PROVA:
            utenteCorrente=ems.getUtenteCorrente();

            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.assegnaIdentificativiStudente();

            //System.out.println("CODICE: " + studenteCorrente.getMatricola());
            //System.out.println("PASSWORD: " + studenteCorrente.getPassword());
        }
        else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            docenteCorrente.assegnaIdentificativiDocente();

            //System.out.println("CODICE: " + docenteCorrente.getCodiceDocente());
            //System.out.println("PASSWORD: " + docenteCorrente.getPassword());
        }
        //QUESTA STAMPA VIENE FATTA

        //System.out.println("CODICE: " + studenteCorrente.getMatricola());
        //System.out.println("PASSWORD: " + studenteCorrente.getPassword());
    }

    //USATA
    public void scegliTipoProfilo(Utente.TipoProfilo tipoProfilo) {
        utenteFactory = new UtenteFactory();

        utenteCorrente= utenteFactory.newUser(tipoProfilo);
        //System.out.println("STAMPA TIPO PROFILO DOPO ScegliTipoProfilo: " + utenteCorrente.getTipoProfilo()); //STAMPA NULL!!!!!
    }


    public Studente getStudenteCorrente() {
        return studenteCorrente;
    }

    public void setStudenteCorrente(Studente studente) {
        this.studenteCorrente = studente;
    }

    public void setUtenteCorrente(Utente utente) {
        this.utenteCorrente = utente;
    }
    public Utente getUtenteCorrente() {
        return utenteCorrente;
    }

    public Docente getDocenteCorrente() {
        return docenteCorrente;
    }

    public void setDocenteCorrente(Docente docente) {
        this.docenteCorrente = docente;
    }

    public Studente getStudente(String matricola) {
        for (Studente studente : this.student_list.values()) {
            if (studente.getMatricola().equals(matricola)) {
                return studente;
            }
        }
        return null;
    }




  public boolean loginStudente(String matricola, String password) throws Exception {
      Studente studente = student_list.get(matricola);

      if (studente == null) {
          throw new Exception("Studente non presente nel registro.");
      }

      if (!studente.getPassword().equals(password)) {
          throw new Exception("Password errata.");
      }

      studenteCorrente = studente;
      return true;
  }
    public boolean loginDocente(String codiceDocente, String password) throws Exception {
        Docente docente = doc_list.get(codiceDocente); // Assumi che tu abbia una mappa docenti_list

        if (docente == null) {
            throw new Exception("Docente non presente nel registro.");
        }

        if (!docente.getPassword().equals(password)) {
            throw new Exception("Password errata.");
        }

        docenteCorrente = docente; // Memorizza il docente corrente
        return true;
    }
    public String stampa_studentiView() {
        if (student_list == null || student_list.isEmpty()) {
            return "Non ci sono studenti registrati."; // Gestione del caso in cui la mappa è vuota
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Studente> entry : student_list.entrySet()) {
            String matricola = entry.getKey();
            Studente studente = entry.getValue();
            sb.append("Matricola: ").append(matricola).append("\n"); // Includi la matricola
            sb.append(studente.toString()).append("\n\n"); // Aggiungi una riga vuota per chiarezza
        }
        return sb.toString();
    }
    public String stampa_docentiView() {
        if (doc_list == null || doc_list.isEmpty()) {
            return "Non ci sono docenti registrati.";
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Docente> entry : doc_list.entrySet()) {
            String codiceDocente = entry.getKey();
            Docente docente = entry.getValue();
            sb.append("Codice Docente: ").append(codiceDocente).append("\n");
            sb.append(docente.toString()).append("\n\n");
        }
        return sb.toString();
    }


    public boolean cancellaStudente(String matricola, String password) throws Exception {
        Studente studente = student_list.get(matricola);

        if (studente == null) {
            throw new Exception("Studente non presente nel registro.");
        }

        if (!studente.getPassword().equals(password)) {
            throw new Exception("Password errata.");
        }

        student_list.remove(matricola); // Rimozione dalla mappa
        return true;
    }
    public HashMap<String, Studente> getStudenti() {
        return student_list;
    }

    public List<Appello_esame> getAppelliByInsegnamento(Insegnamento insegnamento) {
        List<Appello_esame> appelliFiltrati = new ArrayList<>();

        if (insegnamento != null) {
            Map<String, Appello_esame> appelli = insegnamento.getExam_list();
            if (appelli != null) {
                for (Appello_esame appello : appelli.values()) {
                    appelliFiltrati.add(appello);
                }
            }
        }

        return appelliFiltrati;
    }

    public Insegnamento getInsegnamento(String codice) {
        if (this.teaching_list != null) {
            return this.teaching_list.get(codice);
        }
        return null;
    }

    public Appello_esame getAppelloById(String idAppello) {
        for (Insegnamento insegnamento : this.teaching_list.values()) { // Itera sugli insegnamenti
            List<Appello_esame> appelli = this.getAppelliByInsegnamento(insegnamento); // Recupera gli appelli per l'insegnamento

            if (appelli != null) {
                for (Appello_esame appello : appelli) {
                    if (String.valueOf(appello.getID_appello()).equals(idAppello)) {
                        return appello;
                    }
                }
            }
        }
        return null; // Nessun appello trovato con questo ID
    }

    private String generaIdPrenotazione() {
        Random random = new Random();
        int idPrenotazione = 100000 + random.nextInt(900000); // Numero casuale tra 100000 e 999999
        return String.valueOf(idPrenotazione);
    }

    private int prossimoProgressivo = 1; // Inizializza il contatore

    private synchronized int generaProgressivo() {
        return prossimoProgressivo++;
    }

    public void prenotaAppello(Studente studente, Appello_esame appello) throws Exception {
        if (studente == null) {
            throw new Exception("Studente non loggato.");
        }

        if (appello == null) {
            throw new Exception("Appello non valido.");
        }

        if (this.isStudentePrenotato(studente, appello)) {
            throw new Exception("Studente già prenotato a questo appello.");
        }

        // Controllo posti disponibili
        if (appello.getPostiDisponibili() <= 0) {
            throw new Exception("Non ci sono posti disponibili per questo appello.");
        }

        // Crea la Prenotazione *PRIMA* di aggiungere appello e studente
        Prenotazione prenotazione = new Prenotazione(); // Usa il costruttore senza parametri
        prenotazione.setStudente(studente);
        prenotazione.setAppello(appello);

        // Genera e imposta ID, Data, Ora e Progressivo
        String idPrenotazione = generaIdPrenotazione(); // Implementa questa funzione
        prenotazione.setID_prenotazione(idPrenotazione);
        prenotazione.setData(LocalDate.now()); // Imposta la data corrente
        prenotazione.setOra(LocalTime.now()); // Imposta l'ora corrente
        prenotazione.setProgressivo(generaProgressivo()); // Implementa questa funzione

        // Aggiungi la prenotazione alla lista in EMS
        prenotazioniList.add(prenotazione);

        appello.addStudente(studente);
        studente.addAppello(appello);

        // Decrementa i posti disponibili
        appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);

        // Stampa a console per debug
        System.out.println("Prenotazione effettuata con successo per " + studente.getNome() + " all'appello " + appello.getID_appello());
        System.out.println("ID Prenotazione: " + prenotazione.getID_prenotazione());

        // Stampa la lista di prenotazioni *dopo* aver aggiunto la nuova prenotazione
        System.out.println("Elenco prenotazioni dopo l'aggiunta:");
        for (Prenotazione p : prenotazioniList) {
            System.out.println("  - " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
        }
    }

    public boolean isStudentePrenotato(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return false; // Gestisci il caso in cui studente o appello sono null
        }
        return appello.getStudenti().contains(studente);
    }

    public void cancellaPrenotazione(Studente studente, Appello_esame appello) throws Exception {
        if (studente == null || appello == null) {
            throw new Exception("Studente o appello non validi.");
        }

        if (!appello.getStudenti().contains(studente)) {
            throw new Exception("Studente non prenotato a questo appello.");
        }

        appello.removeStudente(studente); // Implementa questo metodo in Appello_esame
        studente.removeAppello(appello); // Implementa questo metodo in Studente

        // Incrementa i posti disponibili
        appello.setPostiDisponibili(appello.getPostiDisponibili() + 1);

        System.out.println("Prenotazione cancellata con successo per " + studente.getNome() + " all'appello " + appello.getID_appello());
    }


    public List<Insegnamento> getInsegnamentiByDocente(Docente docente) {
        List<Insegnamento> insegnamentiDocente = new ArrayList<>();

        if (docente == null) {
            return insegnamentiDocente;
        }

        for (Insegnamento insegnamento : this.teaching_list.values()) {
            List<Docente> docentiInsegnamento = insegnamento.getDocenti(); // Ottieni la lista dei docenti

            if (docentiInsegnamento != null) { // Gestisci il caso di lista docenti nulla
                for (Docente docenteInsegnamento : docentiInsegnamento) {
                    if (docenteInsegnamento.equals(docente)) { // Confronta direttamente gli oggetti Docente
                        insegnamentiDocente.add(insegnamento);
                        break; // Esci dal ciclo interno se il docente è stato trovato
                    }
                }
            }
        }

        return insegnamentiDocente;
    }

    public List<Studente> getStudentiByAppello(Appello_esame appello) {
        return appello.getStudenti();
    }

    public Esito_esame getEsitoByStudente(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return null; // Gestisci il caso di input nulli
        }

        for (Esito_esame esito : this.esitiList) { // Itera sulla lista di esiti in EMS
            if (esito.getStudente().equals(studente) && esito.getAppello().equals(appello)) {
                return esito;
            }
        }

        return null; // Esito non trovato
    }
    public void inserisciEsito(String idPrenotazione, Esito_esame esito) {
        if (idPrenotazione == null || esito == null) {
            return; // Gestisci il caso di input nulli
        }

        for (Prenotazione prenotazione : prenotazioniList) {
            if (prenotazione.getID_prenotazione().equals(idPrenotazione)) {
                prenotazione.setEsito(esito); // Associa l'esito alla prenotazione
                esito.setPrenotazione(prenotazione); //Associa la prenotazione all'esito
                return; // Esci dalla funzione dopo aver trovato e aggiornato la prenotazione
            }
        }

        // Gestisci il caso in cui non viene trovata alcuna prenotazione con l'ID specificato
        System.out.println("Nessuna prenotazione trovata con ID: " + idPrenotazione);
    }

    public void rifiutaEsito(Esito_esame esito) {
        if (esito != null) {
            esito.setStato("Rifiutato");

        }


    }
    public List<Prenotazione> getPrenotazioniList() { // Aggiungi il getter per la lista
        return prenotazioniList;
    }

    public Prenotazione getPrenotazioneByStudenteAndAppello(Studente studente, Appello_esame appello) {
        System.out.println("getPrenotazioneByStudenteAndAppello() chiamata per studente: " + studente.getMatricola() + " e appello: " + appello.getID_appello());

        if (studente == null || appello == null) {
            System.out.println("Studente o appello null");
            return null;
        }

        System.out.println("Lista prenotazioni:");
        if (prenotazioniList.isEmpty()) {
            System.out.println("  La lista è vuota.");
        } else {
            for (Prenotazione p : prenotazioniList) {
                System.out.println("  - " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            }
        }

        for (Prenotazione p : prenotazioniList) {
            System.out.println("Confronto con prenotazione: " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            if (p.getStudente().equals(studente) && p.getAppello().equals(appello)) {
                System.out.println("Prenotazione trovata!");
                return p;
            }
        }

        System.out.println("Prenotazione non trovata per studente: " + studente.getMatricola() + " e appello: " + appello.getID_appello());
        return null;
    }

    public Esito_esame getEsitoByPrenotazione(Prenotazione prenotazione) {
        for (Esito_esame esito : esitiList) {
            if (esito.getPrenotazione().equals(prenotazione)) {
                return esito;
            }
        }
        return null;
    }

    }











