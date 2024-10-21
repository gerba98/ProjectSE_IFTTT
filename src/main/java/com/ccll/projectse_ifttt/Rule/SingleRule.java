package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;

public class SingleRule extends Rule {
    /**
     * Costruttore della classe Rule.
     * Crea una nuova istanza di Rule dato il trigger, l'azione e il nome specificati.
     * lo stato della regola è inizializzato a true
     * Il numero di esecuzioni della regola è inizializzato a 0
     *
     * @param name    Il nome della regola
     * @param trigger Il trigger della regola
     * @param action  L'azione della regola
     */
    public SingleRule(String name, Trigger trigger, Action action) {
        super(name, trigger, action);
    }

    /**
     * Restituisce il tipo della regola.
     *
     * @return Il tipo della regola
     */
    @Override
    public String getType() {
        return "SingleRule";
    }

    /**
     * Esegue l'azione associata alla regola e imposta lo stato della regola a false
     * Questo metodo viene chiamato nel metodo run della classe CheckRule quando il metodo evaluate del Trigger restituisce True.
     */
    @Override
    public void executeAction() {
        super.executeAction();
        super.setState(false);
    }
}
