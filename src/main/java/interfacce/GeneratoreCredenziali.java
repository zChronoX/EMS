package interfacce;

import java.util.Random;

public interface GeneratoreCredenziali {


    // Metodi di default per la generazione di matricola, password e codice docente
    default String generaMatricola() {
        Random random = new Random();
        int matricola = 100000 + random.nextInt(900000); // Numero casuale tra 100000 e 999999
        return String.valueOf(matricola);
    }

    default String generaPassword() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int indice = random.nextInt(caratteri.length());
            password.append(caratteri.charAt(indice));
        }
        return password.toString();
    }

    default String generaCodiceDocente() {
        String caratteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder codice = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            int indice = random.nextInt(caratteri.length());
            codice.append(caratteri.charAt(indice));
        }
        return codice.toString();
    }
}
