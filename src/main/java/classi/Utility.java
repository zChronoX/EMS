package classi;

import java.time.LocalTime;
import java.util.*;
import interfacce.GeneratoreCredenziali;
import java.time.LocalDate;
import java.io.*;


public class Utility {

    GeneratoreCredenziali generatore = new GeneratoreCredenziali(){};

    //private HashMap<String, Studente> studenti;
  //  public Utility() {
    //    this.studenti = new HashMap<>(); // Inizializzazione nel costruttore
    //}

    public HashMap<String, Studente> loadStudents() {
        HashMap<String, Studente> student_list = new HashMap<>();

            Studente s1 = new Studente(
                    "Giovanni", // Nome (come nel controller)
                    "Contarino", // Cognome (come nel controller)
                    "M", // Genere (come nel controller)
                    new Date(), // Data di nascita (come nel controller)
                    "CNTGNN01D07C351H", // Codice fiscale (come nel controller)
                    "Via Blanco 14, Acireale", // Residenza (come nel controller)
                    "giovanni.contarino.gc@gmail.com", // Email (come nel controller)
                    "3801577024", // Telefono (come nel controller)
                    Utente.TipoProfilo.Studente, // Tipo Profilo
                    generatore.generaMatricola(), // Matricola
                    generatore.generaPassword(), // Password
                    "In corso", // Categoria (come nel controller)
                    2025 // Anno corso (come nel controller)
            );

        Studente s2 = new Studente(
                "Giuseppe", // Nome
                "Nappo", // Cognome
                "M", // Genere
                new Date(), // Data di nascita
                "NPPGGPS214312PP", // Codice fiscale
                "Via Scaloto 12, Scala", // Residenza
                "nappopippo@gmail.com", // Email
                "1234567890", // Telefono
                Utente.TipoProfilo.Studente,
                generatore.generaMatricola(),
                generatore.generaPassword(),
                "In corso", // Categoria
                2025 // Anno corso
        );


            student_list.put(s1.getMatricola(), s1);
            student_list.put(s2.getMatricola(), s2);

            return student_list;
    }

    public HashMap<String, Docente> loadProfessors(){
        HashMap<String, Docente> prof_list = new HashMap<>();

        Docente d1 = new Docente(
                "Mario", // Nome
                "Rossi", // Cognome
                "M", // Genere
                new Date(), // Data di nascita
                "RSSMRO70E10F900X", // Codice fiscale
                "Via Roma 1, Milano", // Residenza
                "mario.rossi@example.com", // Email
                "3331234567", // Telefono
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        Docente d2 = new Docente(
                "Luigi", // Nome
                "Verdi", // Cognome
                "M", // Genere
                new Date(), // Data di nascita
                "VRDLUG65K25G800Y", // Codice fiscale
                "Via Verdi 2, Roma", // Residenza
                "luigi.verdi@example.com", // Email
                "3479876543", // Telefono
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        Docente d3 = new Docente(
                "Anna", // Nome
                "Bianchi", // Cognome
                "F", // Genere
                new Date(), // Data di nascita
                "BNCANN82C08H900Z", // Codice fiscale
                "Via Bianchi 3, Torino", // Residenza
                "anna.bianchi@example.com", // Email
                "3281011122", // Telefono
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        Docente d4 = new Docente(
                "Giovanni", // Nome
                "Neri", // Cognome
                "M", // Genere
                new Date(), // Data di nascita
                "NRIGVN78I15I000W", // Codice fiscale
                "Via Neri 4, Napoli", // Residenza
                "giovanni.neri@example.com", // Email
                "3394445555", // Telefono
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        prof_list.put(d1.getCodiceDocente(), d1);
        prof_list.put(d2.getCodiceDocente(), d2);
        prof_list.put(d3.getCodiceDocente(), d3);
        prof_list.put(d4.getCodiceDocente(), d4);

        return prof_list;
    }


    public HashMap<String, Insegnamento> loadCourses(String nomeFile, HashMap<String, Docente> docenti, HashMap<String, Studente> studenti) throws IOException {
        HashMap<String, Insegnamento> teaching_list = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nomeFile))) {
            reader.readLine(); // Salta la prima riga (intestazione)

            String riga;
            while ((riga = reader.readLine()) != null) {
                String[] dati = riga.split(",");
                if (dati.length >= 6) { // Verifica che ci siano almeno 6 elementi (e opzionalmente studenti)
                    try {
                        String id = dati[0].trim();
                        String nome = dati[1].trim();
                        int cfu = Integer.parseInt(dati[2].trim());
                        String descrizione = dati[3].trim();
                        int anno = Integer.parseInt(dati[4].trim());
                        String[] nomiDocenti = dati[5].trim().split(";");

                        Insegnamento insegnamento = new Insegnamento(id, nome, cfu, descrizione, anno);

                        for (String nomeDocente : nomiDocenti) { // Itera sui nomi dei docenti
                            Docente docente = trovaDocente(docenti, nomeDocente.trim()); // Trova il docente
                            if (docente != null) { // Aggiungi controllo null
                                //System.out.println("Aggiungo docente " + docente.getNome() + " all'insegnamento " + insegnamento.getNome());
                                insegnamento.aggiungiDocente(docente);
                               // System.out.println("agaga"+insegnamento.getDocenti().toString());
                            } else {
                                System.out.println("Docente " + nomeDocente + " non trovato!");
                            }
                        }

                        // Iscrizione automatica di tutti gli studenti
                        for (Studente studente : studenti.values()) {
                            insegnamento.iscriviStudente(studente);
                        }

                        teaching_list.put(id, insegnamento);
                    } catch (NumberFormatException e) {
                        System.err.println("Errore di formato numerico: " + e.getMessage());
                    }
                } else {
                    System.err.println("Errore: Numero di elementi non corretto.");
                }
            }
        }
        return teaching_list; //lista degli insegnamenti
    }


    private Docente trovaDocente(HashMap<String, Docente> docenti, String nomeDocente) {
        //System.out.println("Cerco docente: " + nomeDocente);
        if (docenti != null) { // Aggiungi controllo null
            for (Docente docente : docenti.values()) {
                //System.out.println("Docente presente nella mappa: " + docente.getNome() + " " + docente.getCognome());
                if ((docente.getNome() + " " + docente.getCognome()).equals(nomeDocente)) { // Confronta SOLO nome completo
                    //System.out.println("Docente trovato!");
                    return docente;
                }
            }
        } else {
            System.out.println("Mappa docenti vuota o null!");
        }
        return null; // Restituisci null se il docente non viene trovato
    }
/*
    public void loadResults(){
        //todo
    }

*/
    /* Commento perché questo metodo è relativo all'estensione del caso d'uso 1 di CreazioneUtente evitabile in quanto la matricola viene creata in maniera randomica
         e quindi la probabilità che vengano creati due utenti con la stessa matricola è bassissima.

public static boolean verificaEAggiungiMatricola(String filePath, String nuovaMatricola) {
    // Lista per memorizzare le matricole lette dal file
    List<String> matricole = new ArrayList<>();

    // Leggere le matricole esistenti dal file
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            matricole.add(line.trim()); // Aggiunge ogni riga (senza spazi) alla lista
        }
    } catch (FileNotFoundException e) {
        System.out.println("File non trovato. Verrà creato un nuovo file.");
    } catch (IOException e) {
        System.out.println("Errore durante la lettura del file: " + e.getMessage());
    }

    // Controlla se la nuova matricola esiste già
    if (matricole.contains(nuovaMatricola)) {
        System.out.println("La matricola esiste già.");
        return false; // Restituisce false se la matricola è già presente
    }

    // Se non esiste, aggiungila al file
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
        writer.write(nuovaMatricola);
        writer.newLine(); // Aggiunge una nuova riga
    } catch (IOException e) {
        System.out.println("Errore durante la scrittura nel file: " + e.getMessage());
        return false; // Restituisce false in caso di errore
    }

    System.out.println("Matricola aggiunta con successo.");
    return true; // Restituisce true se la matricola è stata aggiunta
}
*/

  /*  public HashMap<String, Appello_esame> loadaAppelli() {
        HashMap<String, Appello_esame> exam_list = new HashMap<>();

        // Creiamo un'istanza di Insegnamento (assicurati che la classe Insegnamento abbia un costruttore appropriato)
        Docente docente = new Docente("ciccio","balordo", "female",
                new Date(1920, 4, 15),
                "fusaufausfb",
                "casa di roberta",
                "ciccio@gmail.com",
                "1234567890",
                Utente.TipoProfilo.Docente,
                "ciccio",
                "ciccio");
        Insegnamento insegnamento = new Insegnamento("MAT001", "Algebra Lineare",6,"Corso di algebra lineare", 2023);
        insegnamento.aggiungiDocente(docente);
        // Creiamo un'istanza di Appello_esame con parametri di esempio:
        // ID_appello: "APP001"
        // Data: 15 aprile 2025
        // Orario: 9:30
        // Luogo: "Aula 101"
        // postiDisponibili: 30
        // Tipologia: "Scritto"
        // insegnamento: l'istanza appena creata
        Appello_esame a = new Appello_esame(
                "APP001",
                LocalDate.of(2025, 4, 15),
                LocalTime.of(9, 30),
                "Aula 101",
                30,
                "Scritto",
                insegnamento
        );

        // Inseriamo l'appello nella mappa utilizzando l'ID come chiave
        exam_list.put("APP001", a);

        return exam_list;
    }
*/

}

