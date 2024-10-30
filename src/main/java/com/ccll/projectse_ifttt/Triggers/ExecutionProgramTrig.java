package com.ccll.projectse_ifttt.Triggers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rappresenta un trigger che si attiva ad un valore specifico ritornato da un programma specifico.
 * Questo trigger valuta se l'output restituito dal programma corrisponde con quello specificato.
 */
public class ExecutionProgramTrig extends AbstractTrigger {
    boolean isThreadRunning = false;
    AtomicBoolean evaluation = new AtomicBoolean(false);
    AtomicInteger exitCode = new AtomicInteger();
    private CompletableFuture<Integer> runningProcess = null;
    String userInfo;

    /**
     * Costruisce un ExecutionProgramTrig con comando e programma specificati e output desiderato.
     *
     * @param userInfo command-program-output -> indica l'output desiderato restituito dal programma specificato per cui il trigger si attiva.
     */
    public ExecutionProgramTrig(String userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * Restituisce comando, programma e output desiderato per cui il trigger deve attivarsi.
     *
     * @return il programma specificato dall'utente.
     */
    public String getUserInfo() {
        return userInfo;
    }


    /**
     * Imposta una nuova stringa contenente nuovi comando, prgramma e/o output.
     *
     * @param userInfo il nuovo comando per il trigger.
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }


    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva l'output del programma selezionato è uguale a quello desiderato.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean getCurrentEvaluation() {
        boolean newEvaluation = false;
        String output = this.userInfo.split("-")[2];
        if (runningProcess == null) {
            // Start a new process if there isn't one running
            startNewProcess(); // Return false immediately as the process just started
        }

        // Check if the process has completed
        if (runningProcess.isDone()) {
            try {
                int exitCode = runningProcess.get();
                newEvaluation = (String.valueOf(exitCode).equals(output));
                runningProcess = null; // Reset for next evaluation
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println();
        }
        return newEvaluation; // Process is still running or evaluation didn't change
    }

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
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica l'output e programma specificati.
     */
    @Override
    public String toString() {
        return "Status program;" + this.userInfo.split("-")[2] + " " + this.userInfo.split("-")[1];
    }

    @Override
    public void reset() {
        super.reset();
        evaluation = new AtomicBoolean(false);
        exitCode = new AtomicInteger();
        isThreadRunning = false;
        if (runningProcess != null && !runningProcess.isDone()) {
            runningProcess.cancel(true);
        }
        runningProcess = null;
    }
}
