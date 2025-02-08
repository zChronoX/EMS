package classi;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {

        Utility utility = new Utility();
        HashMap<String, Docente> docenti = utility.loadProfessors();
        HashMap<String, Studente> studenti = utility.loadStudents();

        HashMap<String, Insegnamento> teaching_list = utility.loadCourses("C:/Users/Gio/IdeaProjects/ExamManagmentSystem/src/main/files/insegnamenti.txt", docenti, studenti);
        EMS ems = new EMS();
        ems.setStudentList(studenti);
        ems.setDocList(docenti);
        ems.setTeachingList(teaching_list);


        String idInsegnamento = "MAT001"; // Sostituisci con un ID valido
        Insegnamento insegnamento = teaching_list.get(idInsegnamento);

        if (insegnamento == null) {
            System.out.println("Insegnamento non trovato.");
            return;
        }

// 2. Definisci i parametri dell'appello
        LocalTime orario = LocalTime.of(9, 0);
        LocalDate data = LocalDate.of(2024, 6, 20);
        String luogo = "Aula A1";
        String tipologia = "Scritto";

// 3. Crea l'appello
        String idAppello = ems.creaAppelloEsame(insegnamento, orario, data, luogo, tipologia);

// 4. Conferma l'appello
        if (idAppello != null) {
            Appello_esame nuovoAppello = new Appello_esame(); // Crea un nuovo oggetto Appello_esame
            nuovoAppello.setID_appello(idAppello);
            nuovoAppello.setInsegnamento(insegnamento);
            nuovoAppello.setOrario(orario);
            nuovoAppello.setData(data);
            nuovoAppello.setLuogo(luogo);
            nuovoAppello.setPostiDisponibili(100); // Imposta i posti disponibili
            nuovoAppello.setTipologia(tipologia);

            ems.confermaAppello(nuovoAppello);

            System.out.println("Appello creato e confermato con ID: " + idAppello);
        } else {
            System.out.println("Errore nella creazione dell'appello.");
        }

        for (Studente currentStudent : studenti.values()) {
            System.out.println("Matricola: " + currentStudent.getMatricola() + ", Password: " + currentStudent.getPassword());
        }


        Scanner scanner = new Scanner(System.in);

        String matricola;
        String password;
        Studente studente = null;
        boolean loginSuccess = false;

        try {
            do {  // Outer loop for matricola
                System.out.print("Inserisci matricola: ");
                matricola = scanner.nextLine();
                studente = studenti.get(matricola);

                if (studente != null) {
                    do { // Inner loop for password (retry logic here)
                        System.out.print("Inserisci password: ");
                        password = scanner.nextLine();
                        loginSuccess = ems.loginStudente(matricola, password);

                        if (loginSuccess) {
                            System.out.println("Login effettuato con successo!");
                            break; // Exit inner loop (password correct)
                        } else {
                            System.out.println("Password errata. Reinserisci la password.");
                        }
                    } while (!loginSuccess); // Retry password until correct

                    if (loginSuccess) {
                        break; // Exit outer loop (matricola and password correct)
                    }
                } else {
                    System.out.println("Matricola non trovata. Reinserisci matricola e password.");
                }
            } while (studente == null || !loginSuccess);

            if (studente != null && loginSuccess) {
                List<Insegnamento> insegnamentiIscritto = ems.getInsegnamentiIscritto(ems.getStudenteCorrente());

                if (insegnamentiIscritto.isEmpty()) {
                    System.out.println("Non sei iscritto a nessun insegnamento.");
                } else {
                    System.out.println("Sei iscritto ai seguenti insegnamenti:");
                    for (Insegnamento ins : insegnamentiIscritto) {
                        System.out.println("- " + ins.getNome() + " ID : " + ins.getID_insegnamento());
                    }

                    String risposta;
                    do {
                        System.out.print("Inserisci l'ID dell'insegnamento per visualizzare gli appelli: ");
                        String idInsegnamentoScelto = scanner.nextLine();

                        List<Appello_esame> appelli = ems.visualizzaAppelli(idInsegnamentoScelto, ems.getStudenteCorrente());

                        if (appelli.isEmpty()) {
                            System.out.println("Non ci sono appelli disponibili per questo insegnamento.");
                        } else {
                            System.out.println("Appelli disponibili per l'insegnamento " + idInsegnamentoScelto + ":");
                            for (Appello_esame appello : appelli) {
                                System.out.println(appello.toString());
                            }

                            System.out.print("Inserisci l'ID dell'appello a cui vuoi prenotarti: ");
                            String idAppelloScelto = scanner.nextLine();

                            Prenotazione prenotazione = ems.prenotaAppello(idAppelloScelto, ems.getStudenteCorrente());

                            if (prenotazione != null) {
                                System.out.println("Prenotazione effettuata con successo!");
                                System.out.println("Dettagli della prenotazione:");
                                System.out.println(prenotazione.toString());
                            } else {
                                System.out.println("Errore durante la prenotazione.");
                            }
                        }

                        System.out.print("Vuoi visualizzare gli appelli per un altro insegnamento? (s/n): ");
                        risposta = scanner.nextLine();
                    } while (risposta.equalsIgnoreCase("s"));
                }
            }

        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}









/*
try {


            System.out.println("Docenti presenti nella mappa:");
            for (Docente docente : docenti.values()) {
                System.out.println(docente.getNome());
            }



Map<String, Insegnamento> teaching_list = utility.loadCourses("C:/Users/Gio/IdeaProjects/ExamManagmentSystem/src/main/files/insegnamenti.txt", docenti);
            System.out.println("Insegnamenti caricati");

// Stampa gli insegnamenti
            for (Insegnamento insegnamento : teaching_list.values()) {
        System.out.println(insegnamento);
            }

// Puoi accedere a un insegnamento specifico tramite il suo codice
Insegnamento algebraLineare = teaching_list.get("MAT001");
            if (algebraLineare != null) {
        // Modifica qui per stampare solo il nome del docente
        System.out.println("Docente di Algebra Lineare: " + algebraLineare.getDocente().getNome() + " " + algebraLineare.getDocente().getCognome());
        }

        } catch (IOException e) {
        System.err.println("Errore durante la lettura del file: " + e.getMessage());
        }*/


/*
for (Studente studente : studenti.values()) {
        System.out.println("Matricola: " + studente.getMatricola() + ", Password: " + studente.getPassword());
        }
Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci matricola: ");
String matricola = scanner.nextLine();
        System.out.print("Inserisci password: ");
String password = scanner.nextLine();

Studente studente = studenti.get(matricola);

// Usa student_list

        try {
                if (studente != null && ems.loginStudente(matricola, password)) { // Chiama loginStudente
        System.out.println("Login effettuato con successo!");
            } else {
                    System.out.println("Credenziali errate o studente non trovato.");
            }
                    } catch (Exception e) {
        System.out.println("Errore durante il login: " + e.getMessage());
        }

        scanner.close();
        */



/*
        for (Docente docente : docenti.values()) {
            System.out.println("Codice Docente: " + docente.getCodiceDocente() + ", Password: " + docente.getPassword());
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Inserisci codice docente: ");
        String codiceDocente = scanner.nextLine();
        System.out.print("Inserisci password docente: ");
        String password = scanner.nextLine();

        try {
            if (ems.loginDocente(codiceDocente, password)) {
                System.out.println("Login docente effettuato con successo!");
                // Azioni da eseguire dopo il login del docente
            } else {
                System.out.println("Credenziali docente errate o docente non trovato.");
            }
        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
        }

        scanner.close();
*/




/*if (teaching_list.isEmpty()) {
            System.out.println("Non ci sono insegnamenti da visualizzare.");
        } else {
            for (Insegnamento insegnamento : teaching_list.values()) {
                System.out.println(insegnamento);
            }
        }



        if (studenti.isEmpty()) {
            System.out.println("No students loaded. Please check the student data file.");
            return;
        }

        for (Studente currentStudent : studenti.values()) {
            System.out.println("Matricola: " + currentStudent.getMatricola() + ", Password: " + currentStudent.getPassword());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci matricola: ");
        String matricola = scanner.nextLine();
        System.out.print("Inserisci password: ");
        String password = scanner.nextLine();

// Recupera lo studente dalla mappa studenti
        Studente studente = studenti.get(matricola);

// Tentativo di login
        try {
            if (studente != null && ems.loginStudente(matricola, password)) {
                System.out.println("Login effettuato con successo!");

                // Visualizzazione degli insegnamenti a cui è iscritto lo studente
                if (ems.getStudenteCorrente() != null) { // Verifica che studenteCorrente sia stato impostato
                    List<Insegnamento> insegnamentiIscritto = ems.getInsegnamentiIscritto(ems.getStudenteCorrente());

                    if (insegnamentiIscritto.isEmpty()) {
                        System.out.println("Non sei iscritto a nessun insegnamento.");
                    } else {
                        System.out.println("Sei iscritto ai seguenti insegnamenti:");
                        for (Insegnamento insegnamento : insegnamentiIscritto) {
                            System.out.println("- " + insegnamento.getNome());
                        }
                    }
                } else {
                    System.out.println("Errore: studenteCorrente non è stato impostato dopo il login.");
                }
            } else {
                System.out.println("Credenziali errate o studente non trovato.");
            }
        } catch (Exception e) {
            System.out.println("Errore durante il login: " + e.getMessage());
        }

        scanner.close();
    }
}*/


/*String idInsegnamento = "MAT001"; // Sostituisci con un ID valido
        Insegnamento insegnamento = teaching_list.get(idInsegnamento);

        if (insegnamento == null) {
            System.out.println("Insegnamento non trovato.");
            return;
        }

// 2. Definisci i parametri dell'appello
        LocalTime orario = LocalTime.of(9, 0);
        LocalDate data = LocalDate.of(2024, 6, 20);
        String luogo = "Aula A1";
        String tipologia = "Scritto";

// 3. Crea l'appello
        String idAppello = ems.creaAppelloEsame(insegnamento, orario, data, luogo, tipologia);

// 4. Conferma l'appello
        if (idAppello != null) {
            Appello_esame nuovoAppello = new Appello_esame(); // Crea un nuovo oggetto Appello_esame
            nuovoAppello.setID_appello(idAppello);
            nuovoAppello.setInsegnamento(insegnamento);
            nuovoAppello.setOrario(orario);
            nuovoAppello.setData(data);
            nuovoAppello.setLuogo(luogo);
            nuovoAppello.setPostiDisponibili(100); // Imposta i posti disponibili
            nuovoAppello.setTipologia(tipologia);

            ems.confermaAppello(nuovoAppello);

            System.out.println("Appello creato e confermato con ID: " + idAppello);
        } else {
            System.out.println("Errore nella creazione dell'appello.");
        }

// 5. Verifica (esempio)
        System.out.println("Numero di appelli: " + ems.getExam_list().size()); // Stampa il numero di appelli
// Oppure cerca un appello specifico per ID
        Appello_esame appelloVerifica = ems.getExam_list().get(idAppello);
        if (appelloVerifica != null) {
            System.out.println("Appello trovato: " + appelloVerifica.toString());
        }


*/