package com.ccll.projectse_ifttt.Triggers;

public abstract class AbstractTrigger implements Trigger {
    boolean LastEvaluation = false;//

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo metodo controlla se il trigger passa da uno stato disattivato a uno attivato.
     *
     * @return true se la condizione del trigger è soddisfatta (passando da "false" a "true"),
     *         false altrimenti.
     */
    @Override
    public boolean evaluate(){
        return edgeTriggeredEvaluate(getCurrentEvaluation());
    }


    /**
     * Valuta se il trigger è stato attivato da un cambiamento di stato, passando da "false" a "true".
     *
     * @param newEvaluation Valore booleano che rappresenta l'attuale stato del trigger.
     * @return true se il trigger è attivato da una transizione di stato da "false" a "true",
     *         false altrimenti.
     */
    public boolean edgeTriggeredEvaluate(boolean newEvaluation) {
        boolean evaluation = newEvaluation && !LastEvaluation;

        LastEvaluation = newEvaluation;
        return evaluation;
    }

    /**
     * Valuta se la condizione del trigger è soddisfatta.
     * Questo metodo è implementato nelle classi concrete.
     *
     * @return true se la condizione è soddisfatta, false altrimenti.
     */
    @Override
    public abstract boolean getCurrentEvaluation();

    /**
     * Reimposta lo stato del trigger, azzerando l'ultima valutazione.
     * Questo metodo consente di reimpostare la condizione di attivazione per un successivo utilizzo.
     */
    @Override
    public void reset() {
        LastEvaluation = false;
    }

}