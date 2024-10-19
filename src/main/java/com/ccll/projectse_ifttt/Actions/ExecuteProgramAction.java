package com.ccll.projectse_ifttt.Actions;

import java.io.IOException;

/**
 * Rappresenta un'azione per eseguire un'applicazione esterna con argomenti specificati.
 * Questa classe supporta l'esecuzione di applicazioni su macOS e Windows e permette
 * di specificare comandi arbitrari. Una volta eseguita l'azione, non verrà ripetuta.
 */
public class ExecuteProgramAction implements Action {
    private final String programPath; // Il percorso dell'applicazione o del file da eseguire
    private final String command; // I comandi da passare all'applicazione
    private boolean hasExecuted; // Flag per controllare se l'azione è già stata eseguita

    /**
     * Costruttore per inizializzare l'azione di esecuzione dell'applicazione.
     *
     * @param programPath il percorso dell'applicazione o del file da eseguire
     * @param command i comandi da passare all'applicazione (può essere vuoto o nullo)
     */
    public ExecuteProgramAction(String programPath, String command) {
        this.programPath = programPath;
        this.command = command;
        this.hasExecuted = false; // Inizialmente l'azione non è stata eseguita
    }

    /**
     * Esegue l'azione di avvio dell'applicazione o di esecuzione del comando specificato.
     * Se l'azione è già stata eseguita, non verrà ripetuta.
     *
     * @return true se l'azione è stata eseguita con successo, false se è già stata eseguita o si è verificato un errore
     */
    @Override
    public boolean execute() {
        if (hasExecuted) {
            return false; // Se l'azione è già stata eseguita non fare nulla
        }

        try {
            ProcessBuilder processBuilder;
            String os = System.getProperty("os.name").toLowerCase();

            // Verifica se il file è un'applicazione .app
            boolean isApp = programPath.endsWith(".app");

            // Se è macOS
            if (os.contains("mac")) {
                if (isApp) {
                    // Apri l'applicazione usando open -a
                    String[] commandArray = {"open", "-a", programPath};
                    processBuilder = new ProcessBuilder(commandArray);
                } else {
                    // Esegui il comando arbitrario
                    String[] commandArray = {"/bin/bash", "-c", command};
                    processBuilder = new ProcessBuilder(commandArray);
                }
            }
            // Se è Windows
            else if (os.contains("win")) {
                // Se il comando è nullo o vuoto, esegui solo l'applicazione
                if (command == null || command.trim().isEmpty()) {
                    processBuilder = new ProcessBuilder(programPath); // Solo l'applicazione
                } else {
                    // Esegui l'applicazione con il comando
                    processBuilder = new ProcessBuilder(programPath, command);
                }
            } else {
                throw new UnsupportedOperationException("Operating system not supported: " + os);
            }

            processBuilder.inheritIO(); // Mostra l'output del programma nella console
            Process process = processBuilder.start();

            int exitCode = process.waitFor();
            hasExecuted = true; // Imposta il flag a true dopo l'esecuzione
            return exitCode == 0; // Ritorna true se il programma è stato eseguito con successo
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false; // Ritorna false in caso di errore
        }
    }

    public String toString() {
        return "Esecuzione Programma";
    }
}
