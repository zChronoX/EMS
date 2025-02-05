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
        Studente s1 = new Studente("Giovanni", "Contarino", "Maschile",
                new Date(), "CNTGNN01D07C351H",
                "Via Blanco 14, Acireale",
                "giovanni.contarino.gc@gmail.com", "3801577024",
                Utente.TipoProfilo.Studente, generatore.generaMatricola(), generatore.generaPassword()
                , "In corso", 2025);

        Studente s2 = new Studente("Nappo", "Giuseppe", "Indefinito",
                new Date(), "NPPGGPS214312PP", "Via Scaloto 12, Scala",
                "nappopippo@gmail.com", "1234567890", Utente.TipoProfilo.Studente, generatore.generaMatricola(),
                generatore.generaPassword(), "In corso", 2025);
        student_list.put(s1.getMatricola(), s1);
        student_list.put(s2.getMatricola(), s2);

        System.out.println("Caricamento studenti completato");
        return student_list;
    }

    public HashMap<String, Docente> loadProfessors(){
        HashMap<String, Docente> prof_list = new HashMap<>();
        Docente d1 = new Docente(
                "Mario",
                "Rossi",
                "M", // Sesso
                new Date(), // Data di nascita (esempio)
                "RSSMRO70E10F900X", // Codice fiscale (esempio)
                "Via Roma 1, Milano", // Residenza (esempio)
                "mario.rossi@example.com", // Email (esempio)
                "3331234567", // Telefono (esempio)
                Utente.TipoProfilo.Docente, // Tipo profilo
                generatore.generaCodiceDocente(), // Codice docente (esempio)
                generatore.generaPassword() // Password (esempio)
        );

        Docente d2 = new Docente(
                "Luigi",
                "Verdi",
                "M",
                new Date(),
                "VRDLUG65K25G800Y",
                "Via Verdi 2, Roma",
                "luigi.verdi@example.com",
                "3479876543",
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        Docente d3 = new Docente(
                "Anna",
                "Bianchi",
                "F",
                new Date(),
                "BNCANN82C08H900Z",
                "Via Bianchi 3, Torino",
                "anna.bianchi@example.com",
                "3281011122",
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );

        Docente d4 = new Docente(
                "Giovanni",
                "Neri",
                "M",
                new Date(),
                "NRIGVN78I15I000W",
                "Via Neri 4, Napoli",
                "giovanni.neri@example.com",
                "3394445555",
                Utente.TipoProfilo.Docente,
                generatore.generaCodiceDocente(),
                generatore.generaPassword()
        );


        prof_list.put(d1.getCodiceDocente(), d1);
        prof_list.put(d2.getCodiceDocente(), d2);
        prof_list.put(d3.getCodiceDocente(), d3);
        prof_list.put(d4.getCodiceDocente(), d4);
        System.out.println("Caricamento professori completato");
        return prof_list;
    }
    public static Map<String, Insegnamento> loadCourses(String nomeFile, Map<String, Docente> docenti) throws IOException {
        Map<String, Insegnamento> teaching_list = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(nomeFile));
        String riga;

        // Salta la prima riga se contiene l'intestazione
        reader.readLine(); // Opzionale: leggi e ignora l'intestazione

        while ((riga = reader.readLine()) != null) {
            String[] dati = riga.split(","); // Assumi che i dati siano separati da virgole
            if (dati.length == 6) { // Verifica che ci siano abbastanza elementi
                String id = dati[0].trim();
                String nome = dati[1].trim();
                int cfu = Integer.parseInt(dati[2].trim());
                String descrizione = dati[3].trim();
                int anno = Integer.parseInt(dati[4].trim());
                String nomeDocente = dati[5].trim(); // Ottieni il nome dal file

                Docente docente = null;

                // Prova prima a cercare per nome completo (nome e cognome)
                for (Docente d : docenti.values()) {
                    if ((d.getNome() + " " + d.getCognome()).equals(nomeDocente)) {
                        docente = d;
                        break;
                    }
                }

                // Se non trovato per nome completo, prova a cercare solo per nome
                if (docente == null) {
                    for (Docente d : docenti.values()) {
                        if (d.getNome().equals(nomeDocente)) {
                            docente = d;
                            break;
                        }
                    }
                }

                if (docente == null) {
                    System.err.println("Errore: Docente non trovato per nome: " + nomeDocente);
                    continue; // Salta questo insegnamento se il docente non viene trovato
                }

                Insegnamento insegnamento = new Insegnamento(id, nome, cfu, descrizione, anno, docente);
                teaching_list.put(id, insegnamento);
            }
        }

        reader.close();
        return teaching_list;
    }
    public void loadResults(){
        //todo
    }



}
