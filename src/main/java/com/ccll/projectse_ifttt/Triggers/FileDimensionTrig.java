package com.ccll.projectse_ifttt.Triggers;

import java.io.File;

/**
 * Implementa un trigger che si attiva se un file ha una dimensione maggiore o uguale di una dimensione specificata
 */
public class FileDimensionTrig extends AbstractTrigger {
    private long dimension;
    private String filePath;
    private String unitDim;

    /**
     * Costruisce un FileDimensionTrig con la dimensione, l'unità di misura e il percorso del file specificati.
     *
     * @param dimension la dimensione che se è maggiore o uguale a quella del file fa attivare il trigger
     * @param unitDim l'unità di misura indicata dall'utente
     * @param filePath il percorso del file da controllare
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
    public boolean getCurrentEvaluation() {
        long dim = 0;
        switch (unitDim) {
            case "B" -> dim = dimension;
            case "KB" -> dim = dimension * 1024;
            case "MB" -> dim = dimension * 1048576;
            case "GB" -> dim = dimension * 1073741824;
        }
        File file = new File(filePath);
        boolean newEvaluation = false;
        if (file.exists() && file.isFile()) {
            long fileSize = file.length();
            newEvaluation = fileSize >= dim;
        }
        return newEvaluation;
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
