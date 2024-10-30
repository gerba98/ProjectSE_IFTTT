package com.ccll.projectse_ifttt.Triggers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rappresenta un trigger che si attiva quando l'output di un programma specificato corrisponde a un valore desiderato.
 * Questo trigger esegue un programma e confronta il suo codice di uscita con il valore specificato per attivare il trigger.
 */
public class ExecutionProgramTrig extends AbstractTrigger {
    private boolean isThreadRunning = false;
    private AtomicBoolean evaluation = new AtomicBoolean(false);
    private AtomicInteger exitCode = new AtomicInteger();
    private CompletableFuture<Integer> runningProcess = null;
    private String userInfo;

    /**
     * Costruisce un trigger che monitora l'output di un programma specifico.
     *
     * @param userInfo una stringa nel formato "command-program-output", dove:
     *                 <ul>
     *                     <li>{@code command} è il comando opzionale da eseguire (può essere vuoto se non necessario).</li>
     *                     <li>{@code program} è il programma da eseguire.</li>
     *                     <li>{@code output} è il valore dell'output atteso per cui il trigger si attiva.</li>
     *                 </ul>
     */
    public ExecutionProgramTrig(String userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Restituisce il comando, programma e output desiderato per cui il trigger si attiva.
     *
     * @return il comando e il programma specificati dall'utente.
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * Imposta una nuova stringa contenente un nuovo comando, programma e/o output.
     *
     * @param userInfo il nuovo comando per il trigger.
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se l'output del programma selezionato corrisponde all'output desiderato.
     *
     * @return true se l'output del programma corrisponde a quello specificato, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        boolean newEvaluation = false;
        String output = this.userInfo.split("-")[2];

        if (runningProcess == null) {
            // Avvia un nuovo processo se non è già in esecuzione
            startNewProcess();
        }

        // Controlla se il processo è completato
        if (runningProcess.isDone()) {
            try {
                int exitCode = runningProcess.get();
                newEvaluation = (String.valueOf(exitCode).equals(output));
                runningProcess = null; // Reset per la prossima valutazione
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newEvaluation;
    }

    /**
     * Avvia un nuovo processo in base ai dettagli specificati dall'utente.
     */
    private void startNewProcess() {
        String command = this.userInfo.split("-")[0];
        String program = this.userInfo.split("-")[1];
        ProcessBuilder processBuilder;

        if (Objects.equals(command, " ")) {
            processBuilder = new ProcessBuilder(program);
        } else {
            processBuilder = new ProcessBuilder(command, program);
        }

        runningProcess = CompletableFuture.supplyAsync(() -> {
            try {
                Process process = processBuilder.start();
                return process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return -1;
            }
        });
    }

    /**
     * Fornisce una rappresentazione in stringa di questo trigger.
     *
     * @return una stringa che indica l'output e il programma specificati.
     */
    @Override
    public String toString() {
        return "Status program;" + this.userInfo.split("-")[0] + "-" + this.userInfo.split("-")[1]+"-"+this.userInfo.split("-")[2];
    }

    /**
     * Reimposta lo stato del trigger e interrompe qualsiasi processo in esecuzione.
     */
    @Override
    public void reset() {
        super.reset();
        evaluation.set(false);
        exitCode.set(0);
        isThreadRunning = false;

        if (runningProcess != null && !runningProcess.isDone()) {
            runningProcess.cancel(true);
        }
        runningProcess = null;
    }
}
