package classi;

import java.time.LocalTime;
import java.util.*;
import interfacce.GeneratoreCredenziali;
import java.time.LocalDate;
import java.io.*;


public class Utility {

    GeneratoreCredenziali generatore = new GeneratoreCredenziali(){};

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
                            if (docente != null) {
                                insegnamento.aggiungiDocente(docente);
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
        if (docenti != null) {
            for (Docente docente : docenti.values()) {
                if ((docente.getNome() + " " + docente.getCognome()).equals(nomeDocente)) { // Confronta SOLO nome completo
                    return docente;
                }
            }
        } else {
            System.out.println("Mappa docenti vuota o null!");
        }
        return null; // Restituisci null se il docente non viene trovato
    }


}

