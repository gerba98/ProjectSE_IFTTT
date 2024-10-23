package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

public class FileDimensionTrig implements Trigger {
    private long dimension;
    private String filePath;
    private String unitDim;
    private boolean lastEvaluation = false;

    /**
     * Costruisce un FileDimensionTrig con la dimensione specificata e un file da controllare.
     *
     * @param dimension la dimensione specifica per cui questo trigger deve attivarsi.
     * @param unitDim specifica l'unità di misura indicata dall'utente
     * @param filePath è il percorso del file da controllare
     */
    public FileDimensionTrig(int dimension, String unitDim, String filePath) {
        this.dimension = dimension;
        this.unitDim = unitDim;
        this.filePath = filePath;
    }

    /**
     * Restituisce la dimensione specifica per questo trigger.
     *
     * @return la dimensione alla quale questo trigger si attiva.
     */
    public long getDimension() {
        return dimension;
    }

    /**
     * Imposta una nuova dimensione specifica per questo trigger.
     *
     * @param dimension la nuova dimensione alla quale questo trigger deve attivarsi.
     */
    public void setDimension(long dimension) {
        this.dimension = dimension;
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
     * Restituisce l'unità di misura della dimensione del file controllato da questo trigger.
     *
     * @return l'unità di misura usata per la dimensione da controllare.
     */
    public String getUnitDim() {
        return unitDim;
    }

    /**
     * Imposta l'unità di misura associata alla dimensione del file che deve essere controllato.
     *
     * @param unitDim l'unità di misura associata alla dimensione del file che deve essere controllato.
     */
    public void setUnitDim(String unitDim) {
        this.unitDim = unitDim;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo trigger si attiva se le dimensioni in byte del file sono uguali o superiori alla dimensione specificata.
     *
     * @return true se il trigger è attivo, false altrimenti.
     */
    @Override
    public boolean evaluate() {
        long dim = 0;
        switch (unitDim) {
            case "B" -> dim = dimension;
            case "KB" -> dim = dimension * 1024;
            case "MB" -> dim = dimension * 1048576;
            case "GB" -> dim = dimension * 1073741824;
        }

        File file = new File(filePath);
        boolean evaluation = false;

        if (file.exists() && file.isFile()) {
            long fileSize = file.length();
            boolean newEvaluation = fileSize >= dim;

            // Il trigger si attiva solo quando la condizione passa da false a true
            if (newEvaluation && !lastEvaluation) {
                evaluation = true;
            }

            lastEvaluation = newEvaluation;
        }

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
     * @return una stringa che indica quando la dimensione è stata raggiunta e quindi il trigger che si attiva.
     */
    @Override
    public String toString() {
        return "File dimension;" + filePath + "-" + dimension + "-" + unitDim;
    }
}
