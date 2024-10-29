package com.ccll.projectse_ifttt.Triggers;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe CompositeTrigger che rappresenta un trigger composto da più trigger
 * combinati utilizzando un operatore logico specifico (AND, OR, NOT).
 */
public class CompositeTrigger extends AbstractTrigger {
    private static final int MAX_TRIGGERS = 2;
    private final LogicalOperator operator;
    private final List<Trigger> triggers;

    /**
     * Costruttore per creare un trigger composto con un operatore logico specifico.
     *
     * @param operator L'operatore logico (AND, OR, NOT) utilizzato per combinare i trigger.
     */
    public CompositeTrigger(LogicalOperator operator) {
        this.operator = operator;
        this.triggers = new ArrayList<>();
    }

    /**
     * Aggiunge un trigger alla lista di trigger da combinare, rispettando il limite
     * massimo di trigger consentiti.
     *
     * @param trigger Il trigger da aggiungere alla lista.
     * @throws IllegalStateException se vengono aggiunti più di due trigger.
     */
    public void addTrigger(Trigger trigger) {
        if (triggers.size() >= MAX_TRIGGERS) {
            throw new IllegalStateException("Cannot add more than " + MAX_TRIGGERS + " triggers to a composite trigger");
        }
        triggers.add(trigger);
    }

    /**
     * Rimuove un trigger dalla lista dei trigger associati.
     *
     * @param trigger Il trigger da rimuovere dalla lista.
     */
    public void removeTrigger(Trigger trigger) {
        triggers.remove(trigger);
    }

    /**
     * Ottiene i trigger figli di questo CompositeTrigger.
     *
     * @return La lista di trigger figli.
     */
    public List<Trigger> getChildren() {
        return this.triggers;
    }

    /**
     * Valuta il trigger composto in base all'operatore logico e al risultato
     * dei trigger associati.
     *
     * @return Il risultato della valutazione logica dei trigger.
     */
    @Override
    public boolean getCurrentEvaluation() {
        return switch (operator) {
            case AND -> evaluateAnd();
            case OR -> evaluateOr();
            case NOT -> evaluateNot();
        };
    }

    /**
     * Valuta il risultato logico dell'operatore AND applicato ai trigger associati.
     * Questo metodo richiede esattamente due trigger per funzionare correttamente.
     *
     * @return {@code true} se entrambi i trigger associati hanno una valutazione vera,
     *         {@code false} altrimenti.
     *
     * @throws IllegalStateException se il numero di trigger associati è diverso da due.
     */
    private boolean evaluateAnd() {
        if (triggers.size() != 2) {
            throw new IllegalStateException("AND operator requires exactly two triggers");
        }
        return triggers.stream().allMatch(Trigger::getCurrentEvaluation);
    }

    /**
     * Valuta il risultato logico dell'operatore OR applicato ai trigger associati.
     * Questo metodo richiede esattamente due trigger per funzionare correttamente.
     *
     * @return {@code true} se almeno uno dei trigger associati ha una valutazione vera,
     *         {@code false} altrimenti.
     *
     * @throws IllegalStateException se il numero di trigger associati è diverso da due.
     */
    private boolean evaluateOr() {
        if (triggers.size() != 2) {
            throw new IllegalStateException("OR operator requires exactly two triggers");
        }
        return triggers.stream().anyMatch(Trigger::getCurrentEvaluation);
    }

    /**
     * Valuta il risultato logico dell'operatore NOT applicato al singolo trigger associato.
     * Questo metodo richiede esattamente un solo trigger per funzionare correttamente.
     *
     * @return {@code true} se il trigger associato ha una valutazione falsa,
     *         {@code false} se ha una valutazione vera.
     *
     * @throws IllegalStateException se il numero di trigger associati è diverso da uno.
     */
    private boolean evaluateNot() {
        if (triggers.size() != 1) {
            throw new IllegalStateException("NOT operator requires exactly one trigger");
        }
        return !triggers.getFirst().getCurrentEvaluation();
    }

    /**
     * Reimposta lo stato di tutti i trigger associati a questo CompositeTrigger.
     */
    @Override
    public void reset() {
        super.reset();
        triggers.forEach(Trigger::reset);
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger composto.
     *
     * @return Una stringa che rappresenta il trigger composto.
     */
    @Override
    public String toString() {
        return "COMPOSITE;" + getTriggerExpression();
    }

    /**
     * Crea un espressione logica del trigger composto.
     *
     * <p>L'espressione segue la seguente sintassi:</p>
     * <ul>
     *     <li>Per ogni trigger semplice viene indicato il tipo e il valore separato da "#":
     *      TriggerType#TriggerValue </li>
     *     <li>I trigger vengono separati dal carattere "@":
     *      TriggerType#TriggerValue@TriggerType#TriggerValue </li>
     *     <li>Le espressioni composte sono formate dall'operatore AND | OR | NOT seguito dai trigger semplici
     *      e/o composti da cui è composta l'espressione. Questi trigger devono essere racchiusi tra parentesi per
     *      indicare l'ordine di valutazione:
     *      AND(TriggerType#TriggerValue@NOT(TriggerType#TriggerValue))</li>
     *     <li>Per l'operatore AND e OR devono essere specificati 2 trigger semplici/composti mentre per l'operatore
     *      NOT deve essere specificato un trigger semplice/composto </li>
     * </ul>
     * <p>Esempi di espressioni:</p>
     * <ul>
     *     <li><code>NOT(Time of the day#11:30)</code> - Un trigger che si attiva se non è l'ora specificata.</li>
     *     <li><code>AND(Day of the week#MONDAY@Time of the day#09:00)</code> - Un trigger che si attiva
     *         solo se è lunedì alle 09:00.</li>
     *     <li><code>OR(Day of the week#FRIDAY@NOT(Time of the day#17:00))</code> - Un trigger che si attiva
     *         se è venerdì o se non è l'ora specificata.</li>
     *     <li><code>COMPOSITE;NOT(OR(Day of the week#MONDAY@NOT(AND(Day of the week#MONDAY@NOT(Time of the day#11:30)))))</code></li>
     * </ul>
     *
     * @return La stringa che rappresenta l'espressione logica attraverso la quale sono descritti
     * i trigger semplici e/o composti che compongono il trigger composto.
     */
    private String getTriggerExpression() {
        StringBuilder sb = new StringBuilder();
        sb.append(operator);
        sb.append("(");

        for (int i = 0; i < triggers.size(); i++) {
            String triggerStr = getTriggerStr(i);
            sb.append(triggerStr);
            if (i < triggers.size() - 1) {
                sb.append("@");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    /**
     * Restituisce una rappresentazione in stringa del trigger situato alla posizione specificata
     * nella lista dei trigger. La stringa risultante varia a seconda che il trigger sia un
     * CompositeTrigger o un trigger semplice.
     *
     * @param i L'indice del trigger nella lista dei trigger, la cui rappresentazione deve essere ottenuta.
     *          Deve essere un valore valido compreso nell'intervallo della lista dei trigger.
     *
     * @return La rappresentazione in stringa del trigger:
     *
     *  <ul>
     *      <li>Se il trigger è di tipo {@code CompositeTrigger}, la stringa restituita è priva della
     *      parte iniziale contenente "COMPOSITE;".</li>
     *      <li>Se il trigger è semplice, il delimitatore ";" viene sostituito con "#".</li>
     *  </ul>
     *
     */
    private String getTriggerStr(int i) {
        Trigger trigger = triggers.get(i);

        String triggerStr = trigger.toString();
        if (trigger instanceof CompositeTrigger) {
            triggerStr = triggerStr.split(";")[1];
        } else {
            triggerStr = triggerStr.replace(";", "#");
        }
        return triggerStr;
    }
}