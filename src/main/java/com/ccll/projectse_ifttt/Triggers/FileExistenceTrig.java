package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

public class FileExistenceTrig implements Trigger {
    private String filePath;

    /**
     * Costruisce un FileDimensionTrig con la dimensione specificata e un file da controllare.
     *
     * @param filePath è il percorso del file da controllare
     */
    public FileExistenceTrig(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Restituisce la il percorso del file controllato da questo trigger.
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
     * Questo trigger si attiva se il file esiste.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * Restituisce una rappresentazione stringa del trigger.
     *
     * @return una stringa che indica quando la dimensione è stata raggiunta e quindi il trigger che si attiva.
     */
    @Override
    public String toString() {
        return "Trigger -> esistenza file " + filePath;
    }
}
