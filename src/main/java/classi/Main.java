package classi;


import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {

        Utility utility = new Utility();
        HashMap<String, Docente> docenti = utility.loadProfessors();
        HashMap<String, Studente> studenti = utility.loadStudents();
        Map<String, Insegnamento> teaching_list = utility.loadCourses("C:/Users/Gio/IdeaProjects/ExamManagmentSystem/src/main/files/insegnamenti.txt", docenti, studenti);
        EMS ems = new EMS();
        ems.setStudentList(studenti);
        ems.setDocList(docenti);
        ems.setTeachingList(teaching_list);



        if (teaching_list.isEmpty()) {
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