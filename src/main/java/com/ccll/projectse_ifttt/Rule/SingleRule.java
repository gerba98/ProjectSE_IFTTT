package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Triggers.Trigger;

/**
 * Questa classe rappresenta una regola che si attiva una sola volta.
 * Estende la classe {@link Rule} e disattiva automaticamente la regola dopo la prima esecuzione.
 */
public class SingleRule extends Rule {

    /**
     * Costruttore della classe SingleRule.
     * Crea una nuova istanza di {@code SingleRule} dato il trigger, l'azione e il nome specificati.
     * Lo stato della regola è inizializzato a true, e il numero di esecuzioni è inizializzato a 0.
     *
     * @param name    Il nome della regola.
     * @param trigger Il trigger della regola.
     * @param action  L'azione della regola.
     */
    public SingleRule(String name, Trigger trigger, Action action) {
        super(name, trigger, action);
    }

    /**
     * Restituisce il tipo della regola.
     *
     * @return Una stringa che indica il tipo della regola, in questo caso "SingleRule".
     */
    @Override
    public String getType() {
        return "SingleRule";
    }

    /**
     * Esegue l'azione associata alla regola e imposta lo stato della regola a false,
     * disattivando la regola dopo la prima esecuzione.
     * Questo metodo viene chiamato dalla classe {@link CheckRule} quando il metodo {@code evaluate} del trigger restituisce true.
     */
    @Override
    public void executeAction() {
        super.executeAction();
        super.setState(false);
    }
}
