package classi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.nio.file.Paths;
import java.util.*;


public class EMS {
    private static EMS ems; //CAMPO PER MEMORIZZARE L'UNICA ISTANZA
    private Utente utenteCorrente;
    private Studente studenteCorrente;
    private Docente docenteCorrente;
    private HashMap<String, Studente> student_list;
    private HashMap<String, Docente> doc_list;
    private HashMap<String, Insegnamento> teaching_list;
    private UtenteFactory utenteFactory;
    public static final int POSTI_MAX = 500;
    private HashMap<String,Appello_esame> exam_list;
    private Insegnamento insegnamentoSelezionato;
    private Appello_esame appelloSelezionato;
    //private List<Prenotazione> prenotazioniList = new ArrayList<>();
    private HashMap<String, Prenotazione> reservation_list;
    private List<Esito_esame> result_list = new ArrayList<>();

    public EMS() {

        this.student_list = new HashMap<>();
        this.doc_list = new HashMap<>();
        this.teaching_list = new HashMap<>(); //lista insegnamenti
        this.reservation_list = new HashMap<>(); //lista prenotazioni
        this.exam_list = new HashMap<>(); //lista appelli

        Utility utility = new Utility(); // Crea un'istanza di Utility

        // Carica gli studenti statici
        HashMap<String, Studente> studentiStatici = utility.loadStudents();
        this.student_list.putAll(studentiStatici); // Aggiunge gli studenti statici alla student_list

        // Carica i docenti statici
        HashMap<String, Docente> docentiStatici = utility.loadProfessors();
        if (docentiStatici != null) { //controllo per evitare null pointer exception nel caso in cui non ci siano docenti statici
            this.doc_list.putAll(docentiStatici); // Aggiunge i docenti statici alla doc_list
        }

        Path filePath = Paths.get("src/main/files/insegnamenti.txt");

        // Verifica se il file esiste prima di tentare di caricarlo
        if (!Files.exists(filePath)) {
            System.err.println("Errore: il file insegnamenti.txt non esiste nel percorso: " + filePath.toAbsolutePath());
        } else {
            try {
                teaching_list = utility.loadCourses(filePath.toString(), doc_list, student_list);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println(teaching_list);
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

    public void stampa_studenti() {
        // Stampa la student_list
        System.out.println("Studenti caricati:");
        for (HashMap.Entry<String, Studente> entry : this.student_list.entrySet()) {
            String chiave = entry.getKey();
            Studente studente = entry.getValue();
            System.out.println("Matricola: " + chiave + ", Studente: " + studente);
        }
    }

    public void stampa_docenti() {
        // Stampa la doc_list
        System.out.println("\nDocenti caricati:"); // Aggiunto un newline per separare le stampe
        for (HashMap.Entry<String, Docente> entry : this.doc_list.entrySet()) {
            String chiave = entry.getKey();
            Docente docente = entry.getValue();
            System.out.println("Codice Docente: " + chiave + ", Docente: " + docente);
        }
    }

    public void stampa_utenti() {
        stampa_studenti();
        stampa_docenti();
    }

    //Pattern Singleton
    public static EMS getInstance() {

        if (ems == null) {
            ems = new EMS();
            return ems;
        } else
            return ems;

    }

    ;

    //come fa questa funzione a capire qual è l'utente corrente? penso ci voglia una get
    public void AggiungiInfoStudente(String categoria, int anno_corso) {
        utenteCorrente = ems.getUtenteCorrente();
        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
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

        utenteCorrente.inizializzaUtente(nome, cognome, data_nascita, genere, codice_fiscale, residenza, email, telefono);
        return true;
    }

    public void generaCredenziali() {
        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            //PROVA:
            utenteCorrente = ems.getUtenteCorrente();

            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.assegnaIdentificativiStudente();

        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            docenteCorrente.assegnaIdentificativiDocente();

        }
    }

    public void scegliTipoProfilo(Utente.TipoProfilo tipoProfilo) {
        utenteFactory = new UtenteFactory();

        utenteCorrente = utenteFactory.newUser(tipoProfilo);
    }

    public Studente getStudenteCorrente() {
        return studenteCorrente;
    }

    public Docente getDocenteCorrente() {
        return docenteCorrente;
    }

    public void setUtenteCorrente(Utente utente) {
        this.utenteCorrente = utente;
    }

    public Utente getUtenteCorrente() {
        return utenteCorrente;
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
        for (HashMap.Entry<String, Studente> entry : student_list.entrySet()) {

            //
            String matricola = entry.getKey();
            Studente studente = entry.getValue();
            // System.out.println("Matricola: " + matricola + ", Studente: " + studente);
            //


            // String matricola = entry.getKey();
            //  Studente studente = entry.getValue();

            sb.append("Matricola: ").append(matricola).append("\n"); // Includi la matricola

            sb.append(studente.toString()).append("\n\n"); // Aggiungi una riga vuota per chiarezza
            System.out.println(sb.toString());
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

    public HashMap<String, Docente> getDoc_list() {
        return doc_list;
    }

    public HashMap<String, Insegnamento> getInsegnamenti() {
        return teaching_list;
    }

    public Insegnamento getInsegnamento(String codice) {
        if (this.teaching_list != null) {
            return this.teaching_list.get(codice);
        }
        return null;
    }

    public HashMap<String, Studente> getStudenti() {
        return student_list;
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
/*
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

        // Aggiungi la prenotazione alla mappa in EMS
        reservation_list.put(idPrenotazione, prenotazione); // Usa la mappa per memorizzare la prenotazione

        appello.addStudente(studente);
        studente.addAppello(appello);

        // Decrementa i posti disponibili
        appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);

        // Stampa a console per debug
        System.out.println("Prenotazione effettuata con successo per " + studente.getNome() + " all'appello " + appello.getID_appello());
        System.out.println("ID Prenotazione: " + prenotazione.getID_prenotazione());

        // Stampa la mappa di prenotazioni *dopo* aver aggiunto la nuova prenotazione
        System.out.println("Elenco prenotazioni dopo l'aggiunta:");
        if (reservation_list.isEmpty()) {
            System.out.println("  La mappa è vuota.");
        } else {
            for (Map.Entry<String, Prenotazione> entry : reservation_list.entrySet()) {
                Prenotazione p = entry.getValue();
                System.out.println("  - " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            }
        }
    }
*/
public boolean prenotaAppello(Studente studente, Appello_esame appello) throws Exception {
    if (studente == null) {
        throw new Exception("Studente non loggato.");
    }

    if (appello == null) {
        throw new Exception("Appello non valido.");
    }

    if (this.isStudentePrenotato(studente, appello)) {
        throw new Exception("Studente già prenotato a questo appello.");
    }

    if (appello.getPostiDisponibili() <= 0) {
        return false; // Nessun posto disponibile
    }

    // Creazione della prenotazione
    Prenotazione prenotazione = new Prenotazione();
    prenotazione.setStudente(studente);
    prenotazione.setAppello(appello);

    // Generazione ID, data, ora e progressivo
    String idPrenotazione = generaIdPrenotazione();
    prenotazione.setID_prenotazione(idPrenotazione);
    prenotazione.setData(LocalDate.now());
    prenotazione.setOra(LocalTime.now());
    prenotazione.setProgressivo(generaProgressivo());

    // Salvataggio della prenotazione
    reservation_list.put(idPrenotazione, prenotazione);

    appello.addStudente(studente);
    studente.addAppello(appello);

    // Aggiornamento posti disponibili
    appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);

    System.out.println("Prenotazione effettuata per " + studente.getNome() + " all'appello " + appello.getID_appello());
    return true; // Prenotazione riuscita
}


    public boolean isStudentePrenotato(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return false; // Gestisci il caso in cui studente o appello sono null
        }
        return appello.getStudenti().contains(studente);
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


    public void setInsegnamentoSelezionato(Insegnamento insegnamento) {
        this.insegnamentoSelezionato = insegnamento;
    }

    public Insegnamento getInsegnamentoSelezionato() {
        return this.insegnamentoSelezionato;
    }

    public void setAppelloSelezionato(Appello_esame appello) {
        this.appelloSelezionato = appello;
    }

    public Appello_esame getAppelloSelezionato() {
        return this.appelloSelezionato;
    }

    public Prenotazione getPrenotazioneByStudenteAndAppello(Studente studente, Appello_esame appello) {
        System.out.println("getPrenotazioneByStudenteAndAppello() chiamata per studente: " + studente.getMatricola() + " e appello: " + appello.getID_appello());

        if (studente == null || appello == null) {
            System.out.println("Studente o appello null");
            return null;
        }

        System.out.println("Lista prenotazioni:");
        if (reservation_list.isEmpty()) {
            System.out.println("  La lista è vuota.");
        } else {
            for (Map.Entry<String, Prenotazione> entry : reservation_list.entrySet()) { // Usa entrySet()
                Prenotazione p = entry.getValue(); // Ottieni l'oggetto Prenotazione
                System.out.println("  - " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            }
        }

        for (Map.Entry<String, Prenotazione> entry : reservation_list.entrySet()) { // Usa entrySet() anche qui
            Prenotazione p = entry.getValue();
            System.out.println("Confronto con prenotazione: " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            if (p.getStudente().equals(studente) && p.getAppello().equals(appello)) {
                System.out.println("Prenotazione trovata!");
                return p;
            }
        }

        System.out.println("Prenotazione non trovata per studente: " + studente.getMatricola() + " e appello: " + appello.getID_appello());
        return null;
    }

    public void rifiutaEsito(Esito_esame esito) {
        if (esito != null) {
            esito.setStato("Rifiutato");

        }
    }

    public List<Studente> getStudentiByAppello(Appello_esame appello) {
        return appello.getStudenti();
    }

    public Studente getStudente(String matricola) {
        for (Studente studente : this.student_list.values()) {
            if (studente.getMatricola().equals(matricola)) {
                return studente;
            }
        }
        return null;
    }

    public Esito_esame getEsitoByStudente(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return null; // Gestisci il caso di input nulli
        }

        for (Esito_esame esito : this.result_list) { // Itera sulla lista di esiti in EMS
            if (esito.getStudente().equals(studente) && esito.getAppello().equals(appello)) {
                return esito;
            }
        }

        return null; // Esito non trovato
    }

    public Esito_esame getEsitoByPrenotazione(Prenotazione prenotazione) {
        for (Esito_esame esito : result_list) {
            if (esito.getPrenotazione().equals(prenotazione)) {
                return esito;
            }
        }
        return null;
    }

    public void inserisciEsito(String idPrenotazione, Esito_esame esito) throws Exception {
        if (idPrenotazione == null || esito == null) {
            return; // Gestisci il caso di input nulli
        }

        if (reservation_list.containsKey(idPrenotazione)) { // Verifica se la mappa contiene la chiave
            Prenotazione prenotazione = reservation_list.get(idPrenotazione); // Ottieni la prenotazione dalla mappa

            // Verifica se la prenotazione ha già un esito
            if (prenotazione.getEsito() != null) {
                throw new Exception("Esito già presente per questa prenotazione."); // Lancia un'eccezione
            }

            prenotazione.setEsito(esito); // Associa l'esito alla prenotazione
            esito.setPrenotazione(prenotazione); // Associa la prenotazione all'esito
            return; // Esci dalla funzione dopo aver trovato e aggiornato la prenotazione
        }

        // Gestisci il caso in cui non viene trovata alcuna prenotazione con l'ID specificato
        System.out.println("Nessuna prenotazione trovata con ID: " + idPrenotazione);


    }
    public  String creazioneAppello(String ID_insegnamento, LocalDate Data, LocalTime Orario, String Luogo,int postiDisponibili,String tipologia){
        Insegnamento insegnamento = teaching_list.get(ID_insegnamento);
        if (insegnamento == null) {
            throw new IllegalArgumentException("Insegnamento non trovato.");
        }
        // Controllo se esiste già un appello con gli stessi dati
        for (Appello_esame appelloEsistente : exam_list.values()) {
            if (appelloEsistente.getData().equals(Data) &&
                    appelloEsistente.getOrario().equals(Orario) &&
                    appelloEsistente.getLuogo().equals(Luogo) &&
                    appelloEsistente.getTipologia().equals(tipologia)) {

                System.out.println("Errore: Esiste già un appello con gli stessi dati.");
                return null;
            }
            else if  (appelloEsistente.getData().equals(Data) &&
                    appelloEsistente.getOrario().equals(Orario) &&
                    appelloEsistente.getLuogo().equals(Luogo)) {

                throw new IllegalArgumentException("Esiste già un appello nello stesso luogo, alla stessa data e ora.");
            }
        }
        String ID_appello = "APP-" + (System.currentTimeMillis() % 100000);
        Appello_esame appello = new Appello_esame(ID_appello, Data, Orario, Luogo, postiDisponibili, tipologia,insegnamento);
        insegnamento.aggiungiAppello(appello);
        if (exam_list == null) {
            exam_list = new HashMap<>();
        }
        return ID_appello;

    }
    public void confermaAppello(String ID_appello, Appello_esame appello) {
        if (ID_appello == null || appello == null) {
            throw new IllegalArgumentException("ID appello o appello nulli.");
        }

        if (exam_list == null) { // Inizializza se null
            exam_list = new HashMap<>();
        }

        if (exam_list.containsKey(ID_appello)) {
            System.out.println("L'appello con ID " + ID_appello + " è già stato confermato.");
            return;
        }

        exam_list.put(ID_appello, appello);
        System.out.println("Appello " + ID_appello + " confermato con successo.");
    }

    public HashMap<String, Insegnamento> visualizzaInsegnamenti() {
        return teaching_list;
    }

    public HashMap<String, Appello_esame> visualizzaAppelliPerInsegnamento(String ID_insegnamento) {
        HashMap<String, Appello_esame> appelliFiltrati = new HashMap<>();

        for (Map.Entry<String, Appello_esame> entry : exam_list.entrySet()) {
            if (entry.getValue().getInsegnamento().getID_insegnamento().equals(ID_insegnamento)) {
                appelliFiltrati.put(entry.getKey(), entry.getValue());
            }
        }
        return appelliFiltrati;
    }

    public Map<String, Prenotazione> getReservation_list() {
        return reservation_list;
    }




    public double calcolaMediaVoti(Studente studente) {
        double sommaVoti = 0;
        int numeroAppelli = 0;

        for (Prenotazione prenotazione : ems.getReservation_list().values()) {
            if (prenotazione.getStudente().equals(studente)) {
                Esito_esame esito = prenotazione.getEsito();
                if (esito != null && esito.getStato().equalsIgnoreCase("Approvato")) {
                    try {
                        int voto = Integer.parseInt(esito.getVoto()); // Converti il voto in intero
                        sommaVoti += voto;
                        numeroAppelli++;
                    } catch (NumberFormatException e) {
                        // Gestisci l'errore se il voto non è un numero valido
                        System.err.println("Errore: voto non valido per l'appello " + prenotazione.getAppello().getID_appello());
                    }
                }
            }
        }

        if (numeroAppelli > 0) {
            return sommaVoti / numeroAppelli;
        } else {
            return 0; // Se non ci sono appelli approvati, la media è 0
        }
    }



}





