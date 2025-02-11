package classi;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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
        teaching_list = utility.loadCourses("C:\\Users\\Gio\\IdeaProjects\\EMS_UI\\src\\main\\files\\insegnamenti.txt", doc_list, student_list);



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


 /*   public void confermaUtente() {
        utenteCorrente=ems.getUtenteCorrente();
        if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Studente) {
            studenteCorrente = (Studente) utenteCorrente;
            student_list.put(studenteCorrente.getMatricola(), studenteCorrente);

            System.out.println(student_list.toString());

        } else if (utenteCorrente.getTipoProfilo() == Utente.TipoProfilo.Docente) {
            docenteCorrente = (Docente) utenteCorrente;
            doc_list.put(docenteCorrente.getCodiceDocente(), docenteCorrente);

            System.out.println(doc_list.toString());
        }
    }*/

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
   // public String creaAppelloEsame(Insegnamento insegnamento, LocalTime orario, LocalDate data, String luogo, String tipologia){

        /*Appello_esame nuovoAppello = new Appello_esame();
        String idGenerato = "10" + (System.currentTimeMillis() % 10000); // Usa timestamp come base per ID univoco
        nuovoAppello.setID_appello(idGenerato);
        nuovoAppello.setInsegnamento(insegnamento); // associa l'appello all'insegnamento
        nuovoAppello.setOrario(orario);
        nuovoAppello.setData(data);
        nuovoAppello.setLuogo(luogo);
        nuovoAppello.setPostiDisponibili(POSTI_MAX);
        nuovoAppello.setTipologia(tipologia);
        return idGenerato;
    }*/
 /*   public void confermaAppello (Appello_esame nuovoAppello){
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
    } */

    public Studente getStudenteCorrente() {
        return studenteCorrente;
    }

    public void setStudenteCorrente(Studente studente) {
        this.studenteCorrente = studente;
    }

    public void setUtenteCorrente(Utente utente) {
        this.utenteCorrente = utente;
    }

    public Studente getStudente(String matricola) {
        for (Studente studente : this.student_list.values()) {
            if (studente.getMatricola().equals(matricola)) {
                return studente;
            }
        }
        return null;
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

        appello.addStudente(studente);
        studente.addAppello(appello);

        // Decrementa i posti disponibili
        appello.setPostiDisponibili(appello.getPostiDisponibili() - 1);

        System.out.println("Prenotazione effettuata con successo per " + studente.getNome() + " all'appello " + appello.getID_appello());
    }

    public boolean isStudentePrenotato(Studente studente, Appello_esame appello) {
        if (studente == null || appello == null) {
            return false; // Gestisci il caso in cui studente o appello sono null
        }
        return appello.getStudenti().contains(studente);
    }




    }





