package com.ccll.projectse_ifttt.Triggers;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rappresenta un trigger che si attiva ad un valore specifico ritornato da un programma specifico.
 * Questo trigger valuta se l'output restituito dal programma corrisponde con quello specificato.
 */
public class ExecutionProgramTrig implements Trigger {
    boolean isThreadRunning = false;
    boolean lastEvaluation = false;
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
    public String getUserInfo() {return userInfo;}


    /**
     * Imposta una nuova stringa contenente nuovi comando, prgramma e/o output.
     *
     * @param userInfo il nuovo comando per il trigger.
     */
    public void setUserInfo(String userInfo) {this.userInfo = userInfo;}


    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva l'output del programma selezionato è uguale a quello desiderato.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        AtomicBoolean evaluation = new AtomicBoolean(false);
        AtomicInteger exitCode = new AtomicInteger();
        synchronized (this) {
            if (!isThreadRunning) {
                Thread myThread = new Thread(() -> {

                    String command = this.userInfo.split("-")[0];
                    String program = this.userInfo.split("-")[1];

                    try {
                        ProcessBuilder pb;
                        if (Objects.equals(command, " ")) {
                            pb = new ProcessBuilder(program);
                        } else {
                            pb = new ProcessBuilder(command, program);
                        }
                        pb.redirectErrorStream(true);

                        Process p = pb.start();
                        exitCode.set(p.waitFor());
                        System.out.println(exitCode.get());

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                String output = this.userInfo.split("-")[2];
                boolean newEvaluation = Objects.equals(String.valueOf(exitCode.get()), output);

                if (!lastEvaluation && newEvaluation) {
                    evaluation.set(true);
                }

                lastEvaluation = newEvaluation;
                isThreadRunning = true;
                myThread.setDaemon(true);
                myThread.start();
            }

        }
        return evaluation.get();
    }
    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica l'output e programma specificati.
     */
    @Override
    public String toString(){return "Trigger attivato a: " + this.userInfo.split("-")[2] + " " + this.userInfo.split("-")[1];}

    @Override
    public void reset(){
        lastEvaluation = false;
        isThreadRunning = false;
    }
}
