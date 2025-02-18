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
    private Appello_esame appelloCorrente;
    private HashMap<String, Studente> student_list;
    private HashMap<String, Docente> doc_list;
    private HashMap<String, Insegnamento> teaching_list;
    private UtenteFactory utenteFactory;
    public static final int POSTI_MAX = 500;
    private HashMap<String,Appello_esame> exam_list;
    private Insegnamento insegnamentoSelezionato;
    private Appello_esame appelloSelezionato;

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

        //utility.loadaAppelli();

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

            // Verifica se la matricola esiste già nella mappa degli studenti
            if (verificaMatricolaEsistente(studenteCorrente.getMatricola())) {
                System.err.println("Errore: La matricola " + studenteCorrente.getMatricola() + " è già associata a uno studente.");
                return; // Impedisce di aggiungere lo studente
            }

            // Aggiungi lo studente nella mappa
            student_list.put(studenteCorrente.getMatricola(), studenteCorrente);

            System.out.println("Elenco Studenti:");
            System.out.println("------------------------------------");
            for (HashMap.Entry<String, Studente> entry : this.student_list.entrySet()) {
                String chiave = entry.getKey();
                Studente studente = entry.getValue();
                System.out.println("Matricola: " + chiave + ", Studente: " + studente);
            }

        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;

            // Verifica se il codice docente esiste già nella mappa dei docenti
            if (verificaCodiceDocenteEsistente(docenteCorrente.getCodiceDocente())) {
                System.err.println("Errore: Il codice docente " + docenteCorrente.getCodiceDocente() + " è già associato a un docente.");
                return; // Impedisce di aggiungere il docente
            }

            // Aggiungi il docente nella mappa
            doc_list.put(docenteCorrente.getCodiceDocente(), docenteCorrente);

            System.out.println("Elenco Docenti:");
            System.out.println("------------------------------------");
            for (HashMap.Entry<String, Docente> entry : this.doc_list.entrySet()) {
                String chiave = entry.getKey();
                Docente docente = entry.getValue();
                System.out.println("Codice Docente: " + chiave + ", Docente: " + docente);
            }
        }
    }

    public boolean verificaMatricolaEsistente(String matricola) {
        return student_list.containsKey(matricola);
    }
    public boolean verificaCodiceDocenteEsistente(String codiceDocente) {
        return doc_list.containsKey(codiceDocente);
    }
    // Metodo per verificare se il codice fiscale è già registrato nelle mappe
    public boolean verificaCodiceFiscaleEsistente(String codice_fiscale) {
        // Controllo nei docenti
        for (Docente docente : doc_list.values()) {
            if (docente.getCodice_fiscale().equalsIgnoreCase(codice_fiscale)) {
                return true; // Codice fiscale già registrato per un docente
            }
        }

        // Controllo negli studenti
        for (Studente studente : student_list.values()) {
            if (studente.getCodice_fiscale().equalsIgnoreCase(codice_fiscale)) {
                return true; // Codice fiscale già registrato per uno studente
            }
        }

        return false; // Codice fiscale non trovato, può essere usato
    }
    // Metodo per creare un nuovo profilo utente con controllo del codice fiscale
    public boolean creaProfiloUtente(String nome, String cognome, Date data_nascita, String genere,
                                     String codice_fiscale, String residenza, String email, String telefono) {

        // Controllo se il codice fiscale è già presente nelle mappe
        if (verificaCodiceFiscaleEsistente(codice_fiscale)) {
            System.err.println("Errore: Il codice fiscale è già associato a un utente esistente.");
            return false; // Blocca la creazione dell'utente
        }

        // Creazione dell'utente
        utenteCorrente.inizializzaUtente(nome, cognome, data_nascita, genere, codice_fiscale, residenza, email, telefono);
        return true;
    }

    // Metodo per generare le credenziali utente
    public void generaCredenziali() {
        utenteCorrente = ems.getUtenteCorrente();

        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            studenteCorrente.assegnaIdentificativiStudente();
        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            docenteCorrente.assegnaIdentificativiDocente();
        }
    }

    // Metodo per scegliere il tipo di profilo e creare un nuovo utente
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
        Docente docente = doc_list.get(codiceDocente);

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

    public String returnGeneraIdPrenotazione() {
        return generaIdPrenotazione();
    }

    private int prossimoProgressivo = 1; // Inizializza il contatore

    private synchronized int generaProgressivo() {
        return prossimoProgressivo++;
    }

public boolean prenotaAppello(Appello_esame appello) throws Exception {

    if (studenteCorrente == null) {
        throw new Exception("Studente non loggato.");
    }

    if (appello == null) {
        throw new Exception("Appello non valido.");
    }

    if (this.isStudentePrenotato(studenteCorrente, appello)) {
        throw new Exception("Studente già prenotato a questo appello.");
    }

    if (appello.getPostiDisponibili() <= 0) {
        return false; // Nessun posto disponibile
    }

    // Creazione della prenotazione
    Prenotazione prenotazione = new Prenotazione();
    prenotazione.setStudente(studenteCorrente);
    prenotazione.setAppello(appello);

    // Generazione ID, data, ora e progressivo
    String idPrenotazione = generaIdPrenotazione();
    prenotazione.setID_prenotazione(idPrenotazione);
    prenotazione.setData(LocalDate.now());
    prenotazione.setOra(LocalTime.now());
    prenotazione.setProgressivo(generaProgressivo());

    // Salvataggio della prenotazione
    reservation_list.put(idPrenotazione, prenotazione);

    appello.addStudente(studenteCorrente);
    studenteCorrente.addAppello(appello);

    // Aggiornamento posti disponibili
    appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);

    System.out.println("Prenotazione effettuata per " + studenteCorrente.getNome() + " all'appello " + appello.getID_appello());
    return true; // Prenotazione riuscita
}


    public boolean isStudentePrenotato(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return false; // Gestisci il caso in cui studente o appello sono null
        }
        return appello.getStudenti().contains(studente);
    }

    public List<Insegnamento> mostraInsegnamentiDocente(String codiceDocente) {
        List<Insegnamento> insegnamentiDocente = new ArrayList<>();

        if (codiceDocente == null || codiceDocente.isEmpty()) {
            return insegnamentiDocente; // Restituisce una lista vuota se il codice è nullo o vuoto
        }
        Docente docente = doc_list.get(codiceDocente);
        if (docente == null) {
            return insegnamentiDocente; // Restituisce lista vuota se il docente non esiste
        }

        for (Insegnamento insegnamento : this.teaching_list.values()) {
            List<Docente> docentiInsegnamento = insegnamento.getDocenti(); // Si ottiene la lista dei docenti

            if (docentiInsegnamento != null) { // caso di lista docenti nulla
                for (Docente docenteInsegnamento : docentiInsegnamento) {
                    if (docenteInsegnamento.equals(docente)) { // Confronto direttamente gli oggetti Docente
                        insegnamentiDocente.add(insegnamento);
                        break;
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
            for (HashMap.Entry<String, Prenotazione> entry : reservation_list.entrySet()) { // Usa entrySet()
                Prenotazione p = entry.getValue(); // Ottieni l'oggetto Prenotazione
                System.out.println("  - " + p.getStudente().getMatricola() + " - " + p.getAppello().getID_appello());
            }
        }

        for (HashMap.Entry<String, Prenotazione> entry : reservation_list.entrySet()) { // Usa entrySet() anche qui
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
        if (appello == null) {
            return new ArrayList<>();
        }
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

public void inserisciEsito(String matricola, String voto, String stato) throws Exception {
    // 1. Verifica che il docente possa gestire gli esiti per questo appello
    if (!appelloCorrente.puòGestireEsiti(docenteCorrente)) {
        throw new Exception("Errore: Non hai i permessi per inserire esiti in questo appello.");
    }

    // 2. Controllo input nulli
    if (matricola == null || matricola.isEmpty() || voto == null || voto.isEmpty() || stato == null || stato.isEmpty()) {
        throw new Exception("Errore: Tutti i campi devono essere compilati.");
    }

    // 3. Recupero studente
    Studente studente = this.getStudente(matricola);
    if (studente == null) {
        throw new Exception("Errore: Studente non trovato.");
    }

    // 4. Recupero prenotazione
    Prenotazione prenotazione = this.getPrenotazioneByStudenteAndAppello(studente, appelloCorrente);
    if (prenotazione == null) {
        throw new Exception("Errore: Lo studente non è prenotato a questo appello.");
    }

    // 5. Controllo che l'esito non sia già stato inserito
    if (prenotazione.getEsito() != null) {
        throw new Exception("Errore: Esito già registrato per questo studente.");
    }

    // 6. Se lo stato è "Approvato", controlliamo la validità del voto
    if (stato.equalsIgnoreCase("Approvato")) {
        try {
            int votoInt = Integer.parseInt(voto);
            if (votoInt < 0 || votoInt > 30) {
                throw new Exception("Errore: Il voto deve essere tra 0 e 30.");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Errore: Il voto deve essere un numero valido.");
        }
    }

    // 7. Creazione dell'esito
    Esito_esame esito = new Esito_esame(voto, stato, studente, appelloCorrente);

    // 8. Associa l'esito alla prenotazione
    prenotazione.setEsito(esito);

    // 9. Mostra conferma
    System.out.println("Esito inserito correttamente per lo studente " + matricola);

    // 10. Se tutto è andato bene, l'esito è stato inserito con successo
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
        appelloCorrente = new Appello_esame(ID_appello, Data, Orario, Luogo, postiDisponibili, tipologia,insegnamento);
        System.out.println("Appello corrente creato: " + appelloCorrente);
      //  insegnamento.aggiungiAppello(appelloCorrente);
       if (exam_list == null) {
            exam_list = new HashMap<>();
        }
        return ID_appello;

    }
    public void confermaAppello() {

        if (exam_list.containsKey(appelloCorrente.getID_appello())) {
            System.out.println("L'appello con ID " + appelloCorrente.getID_appello() + " è già stato confermato.");
            return;
        }

        exam_list.put(appelloCorrente.getID_appello(), appelloCorrente);
        System.out.println("Appello " + appelloCorrente.getID_appello() + " confermato con successo.");
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
    /*
    public HashMap<String, Appello_esame> visualizzaAppelliPerInsegnamento(String ID_insegnamento){
        if (teaching_list.containsKey(ID_insegnamento)) {
            Insegnamento insegnamento= teaching_list.get(ID_insegnamento);
            return insegnamento.cercaAppelliDisponibili(studenteCorrente);
        }
        else {
            System.out.println("Insegnamento non trovato.");
            return null;
        }
    }


     */
    public HashMap<String, Prenotazione> getReservation_list() {
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

    public boolean controlloEsistenzaAppello(LocalDate data, LocalTime orario, String luogo) {
        for (Appello_esame appelloEsistente : exam_list.values()) {
            if (appelloEsistente.getData().equals(data) &&
                    appelloEsistente.getOrario().equals(orario) &&
                    appelloEsistente.getLuogo().equals(luogo)) {

                return true; // Esiste già un appello con gli stessi dati
            }
        }
        return false; // Non esiste alcun appello con gli stessi dati
    }

    public HashMap<String, Prenotazione> getPrenotazioniNonRecensiteByStudente(Studente studente) {
    HashMap<String, Prenotazione> prenotazioniStudente = new HashMap<>();
        for (HashMap.Entry<String, Prenotazione> entry : reservation_list.entrySet()) { // Usa entrySet() anche qui
            Prenotazione p = entry.getValue();

            if (p.getStudente().equals(studente) && !p.getRecensito() ) { //controllo attributo recensito=falso
                prenotazioniStudente.put(p.getID_prenotazione(), p);
            }
        }
        return prenotazioniStudente;
    }
    public Map<String, Insegnamento> getTeaching_list() {
        return teaching_list;
    }

    public Map<String, Appello_esame> getExam_list() {
        return exam_list;
    }

    public void setAppelloCorrente(Appello_esame appelloCorrente) {
        this.appelloCorrente = appelloCorrente;
    }

    public Appello_esame getAppelloCorrente() {
        return appelloCorrente;
    }


}





