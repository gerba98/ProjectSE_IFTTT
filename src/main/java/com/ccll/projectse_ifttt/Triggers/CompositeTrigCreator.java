package com.ccll.projectse_ifttt.Triggers;

import com.ccll.projectse_ifttt.Rule.RuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe CompositeTrigCreator per creare trigger composti che combinano trigger di base
 * e\o altri trigger composti utilizzando gli operatori logici AND, OR e NOT.
 * Estende la classe astratta TriggerCreator e implementa la logica per interpretare espressioni complesse di trigger.
 */
public class CompositeTrigCreator extends TriggerCreator {

    /**
     * Crea un trigger composto a partire dalla stringa in input.
     *
     * @param triggerValue La stringa che rappresenta l'espressione logica attraverso la quale sono descritti
     * i trigger semplici e/o composti che compongono il trigger composto.
     *
     * <p>L'espressione deve seguire la sintassi:</p>
     * <ul>
     *     <li>Per ogni trigger semplice deve essere indicato il tipo e il valore separato da "#":
     *      TriggerType#TriggerValue </li>
     *     <li>I trigger devono essere separati dal carattere "@":
     *      TriggerType#TriggerValue@TriggerType#TriggerValue </li>
     *     <li>Le espressioni composte devono essere formate dall'operatore AND | OR | NOT seguito dai trigger semplici
     *      e/o composti da cui è composta l'espressione. Questi trigger devono essere racchiusi tra parentesi per
     *      indicare l'ordine di valutazione:
     *      AND(TriggerType#TriggerValue@NOT(TriggerType#TriggerValue))</li>
     *     <li>Per l'operatore AND e OR devono essere specificati 2 trigger semplici/composti mentre per l'operatore
     *      NOT deve essere specificato un trigger semplice/composto </li>
     * </ul>
     * <p>Esempi di espressioni valide:</p>
     * <ul>
     *     <li><code>NOT(Time of the day#11:30)</code> - Un trigger che si attiva se non è l'ora specificata.</li>
     *     <li><code>AND(Day of the week#MONDAY@Time of the day#09:00)</code> - Un trigger che si attiva
     *         solo se è lunedì alle 09:00.</li>
     *     <li><code>OR(Day of the week#FRIDAY@NOT(Time of the day#17:00))</code> - Un trigger che si attiva
     *         se è venerdì o se non è l'ora specificata.</li>
     *     <li><code>COMPOSITE;NOT(OR(Day of the week#MONDAY@NOT(AND(Day of the week#MONDAY@NOT(Time of the day#11:30)))))</code></li>
     * </ul>
     *
     * @return un oggetto CompositeTrigger
     */
    @Override
    public Trigger createTrigger(String triggerValue) {
        LogicalOperator operator;
        String remaining;

        // individuo l'operatore
        if (triggerValue.startsWith("NOT")) {
            operator = LogicalOperator.NOT;
            remaining = triggerValue.substring(3);
        } else if (triggerValue.startsWith("AND")) {
            operator = LogicalOperator.AND;
            remaining = triggerValue.substring(3);
        } else if (triggerValue.startsWith("OR")) {
            operator = LogicalOperator.OR;
            remaining = triggerValue.substring(2);
        } else {
            throw new IllegalArgumentException("Invalid expression: " + triggerValue);
        }

        CompositeTrigger compositeTrigger = new CompositeTrigger(operator);

        remaining = remaining.substring(1, remaining.length() - 1);

        List<String> triggerStrings = splitTriggers(remaining);

        for (String triggerStr : triggerStrings) {
            if (triggerStr.startsWith("AND") || triggerStr.startsWith("OR") || triggerStr.startsWith("NOT")) {
                compositeTrigger.addTrigger(createTrigger(triggerStr));
            } else {
                String[] parts = triggerStr.split("#", 2);
                compositeTrigger.addTrigger(RuleManager.createTrigger(parts[0], parts[1]));
            }
        }

        return compositeTrigger;
    }

    /**
     * Divide l'espressione nelle sotto-stringhe che rappresentano i trigger semplici/composti che andranno a comporre
     * il trigger composto
     *
     * @param expression La stringa che rappresenta l'espressione logica attraverso la quale sono descritti
     * i trigger semplici e/o composti che compongono il trigger composto.
     * @return Una lista di 1 0 2 stringhe. Ogni stringa rappresenta un trigger semplice/composto
     * che compone l'espressione
     */
    private List<String> splitTriggers(String expression) {
        List<String> triggers = new ArrayList<>();
        int parenthesesCount = 0;
        StringBuilder currentTrigger = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
            }

            if (c == '@' && parenthesesCount == 0) {
                triggers.add(currentTrigger.toString());
                currentTrigger = new StringBuilder();
            } else {
                currentTrigger.append(c);
            }
        }

        if (!currentTrigger.isEmpty()) {
            triggers.add(currentTrigger.toString());
        }

        return triggers;
    }

    @Override
    public String getType() {
        return "Composite";
    }
}