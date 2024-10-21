package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

public class FileExistenceTrig implements Trigger {
    private String filePath;
    private boolean lastEvaluation = false; // Per tracciare lo stato dell'ultima valutazione

    /**
     * Costruisce un FileExistenceTrig con il percorso specificato e un file da controllare.
     *
     * @param filePath è il percorso del file da controllare
     */
    public FileExistenceTrig(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Restituisce il percorso del file controllato da questo trigger.
     *
     * @return il percorso del file che deve essere controllato.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Imposta il percorso del file da controllare per questo trigger.
     *
     * @param filePath il percorso del file che deve essere controllato.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se il file inizia ad esistere.
     *
     * @return true se il trigger si attiva (quando il file comincia a esistere), false altrimenti.
     */
    @Override
    public boolean evaluate() {
        File file = new File(filePath);
        boolean newEvaluation = file.exists();  // Valutazione corrente

        boolean evaluation = false;
        // Il trigger si attiva solo se il file esiste ora, ma non esisteva prima
        if (newEvaluation && !lastEvaluation) {
            evaluation = true;
        }

        lastEvaluation = newEvaluation;  // Aggiorna lo stato della valutazione
        return evaluation;
    }

    /**
     * Reimposta lo stato della valutazione.
     */
    @Override
    public void reset() {
        lastEvaluation = false;
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che rappresenta lo stato del trigger di esistenza del file.
     */
    @Override
    public String toString() {
        return "File existence; " + filePath;
    }
}
