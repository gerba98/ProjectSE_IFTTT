package com.ccll.projectse_ifttt.Actions;

import java.io.IOException;

/**
 * Rappresenta un'azione per eseguire un'applicazione esterna con comandi specificati.
 * Supporta l'esecuzione di applicazioni su sistemi operativi macOS e Windows, consentendo
 * di aprire applicazioni e di passare comandi opzionali.
 */
public class ExecuteProgramAction implements Action {
    private final String programPath; // Il percorso dell'applicazione o del file da eseguire
    private final String command; // I comandi da passare all'applicazione

    /**
     * Costruttore per inizializzare l'azione di esecuzione dell'applicazione.
     *
     * @param programPath il percorso dell'applicazione o del file da eseguire
     * @param command     i comandi da passare all'applicazione (può essere vuoto o nullo)
     */
    public ExecuteProgramAction(String programPath, String command) {
        this.programPath = programPath;
        this.command = command;
    }

    /**
     * Esegue l'azione di avvio dell'applicazione o di esecuzione del comando specificato.
     * Verifica il sistema operativo e configura il comando di esecuzione in base a esso.
     *
     * @return {@code true} se l'azione è stata eseguita con successo, {@code false} in caso di errore
     */
    @Override
    public boolean execute() {
        try {
            ProcessBuilder processBuilder;
            String os = System.getProperty("os.name").toLowerCase();

            // Verifica se il file è un'applicazione .app
            boolean isApp = programPath.endsWith(".app");

            // Configurazione per macOS
            if (os.contains("mac")) {
                if (isApp) {
                    // Apri l'applicazione usando il comando "open -a"
                    String[] commandArray = {"open", "-a", programPath};
                    processBuilder = new ProcessBuilder(commandArray);
                } else {
                    // Esegui un comando arbitrario con bash
                    String[] commandArray = {"/bin/bash", "-c", command};
                    processBuilder = new ProcessBuilder(commandArray);
                }
            }
            // Configurazione per Windows
            else if (os.contains("win")) {
                // Se il comando è nullo o vuoto, esegui solo l'applicazione
                if (command == null || command.trim().isEmpty()) {
                    processBuilder = new ProcessBuilder(programPath); // Solo l'applicazione
                } else {
                    // Esegui l'applicazione con il comando
                    processBuilder = new ProcessBuilder(programPath, command);
                }
            } else {
                throw new UnsupportedOperationException("Sistema operativo non supportato: " + os);
            }

            processBuilder.inheritIO(); // Mostra l'output del programma nella console
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            return exitCode == 0; // Ritorna true se il programma è stato eseguito con successo
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Ritorna false in caso di errore
        }
    }

    /**
     * Restituisce una rappresentazione testuale dell'azione.
     *
     * @return una stringa che descrive l'azione come "Esecuzione Programma"
     */
    @Override
    public String toString() {
        return "Execute program;"+programPath+"-"+command;
    }
}
