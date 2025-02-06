package classi;


import java.util.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        try {
            Utility utility = new Utility();
            HashMap<String, Docente> docenti = utility.loadProfessors();

            /*System.out.println("Docenti presenti nella mappa:");
            for (Docente docente : docenti.values()) {
                System.out.println(docente.getNome());
            }*/

            Map<String, Insegnamento> teaching_list = utility.loadCourses("C:\\Users\\rober\\IdeaProjects\\EMS\\src\\main\\files\\insegnamenti.txt", docenti);

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
        }
    }
}
