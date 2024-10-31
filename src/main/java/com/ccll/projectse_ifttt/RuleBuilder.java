package com.ccll.projectse_ifttt;

import com.ccll.projectse_ifttt.Rule.Rule;
import com.ccll.projectse_ifttt.Rule.RuleManager;

/**
 * Classe builder per la creazione di istanze di {@link Rule} con parametri configurabili.
 * Questa classe consente di personalizzare diversi aspetti della regola, come il nome,
 * il tipo di trigger, il tipo di azione e dettagli specifici per trigger e azioni.
 * Supporta anche la configurazione per esecuzioni periodiche o singole.
 */
public class RuleBuilder {

    private String name;
    private String triggerType;
    private String actionType;
    private String triggerDetails;
    private String actionDetails;
    private boolean isPeriodic = false;
    private boolean isSingle = false;
    private String period;

    /**
     * Imposta il nome della regola.
     *
     * @param name il nome della regola
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Imposta il tipo di trigger per la regola.
     *
     * @param triggerType il tipo di trigger
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setTriggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    /**
     * Imposta il tipo di azione da eseguire nella regola.
     *
     * @param actionType il tipo di azione
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    /**
     * Imposta i dettagli aggiuntivi per la configurazione del trigger.
     *
     * @param triggerDetails dettagli aggiuntivi del trigger
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setTriggerDetails(String triggerDetails) {
        this.triggerDetails = triggerDetails;
        return this;
    }

    /**
     * Imposta i dettagli aggiuntivi per la configurazione dell'azione.
     *
     * @param actionDetails dettagli aggiuntivi dell'azione
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setActionDetails(String actionDetails) {
        this.actionDetails = actionDetails;
        return this;
    }

    /**
     * Configura la regola per un'esecuzione periodica e specifica il periodo.
     *
     * @param period il periodo di esecuzione
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setPeriodic(String period) {
        this.isPeriodic = true;
        this.isSingle = false;
        this.period = period;
        return this;
    }

    /**
     * Configura la regola per un'esecuzione singola.
     *
     * @return l'istanza corrente di {@link RuleBuilder} per consentire il chaining dei metodi
     */
    public RuleBuilder setSingle() {
        this.isSingle = true;
        this.isPeriodic = false;
        return this;
    }

    /**
     * Costruisce e restituisce un'istanza di {@link Rule} basata sui parametri configurati.
     *
     * @return una nuova istanza di {@link Rule}
     * @throws IllegalArgumentException se i parametri obbligatori non sono impostati
     */
    public Rule build() {
        validate();

        RuleManager ruleManager = RuleManager.getInstance();
        if (isPeriodic) {
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails, "periodicrule-" + period);
        } else if (isSingle) {
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails, "singlerule");
        } else {
            return ruleManager.createRule(name, triggerType, triggerDetails, actionType, actionDetails);
        }
    }

    /**
     * Valida i parametri impostati per la costruzione della regola.
     *
     * @throws IllegalArgumentException se alcuni parametri obbligatori sono nulli o vuoti
     */
    private void validate() {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name non può essere nullo o vuoto");
        }
        if (triggerType == null || triggerType.isEmpty()) {
            throw new IllegalArgumentException("TriggerType non può essere nullo o vuoto");
        }
        if (actionType == null || actionType.isEmpty()) {
            throw new IllegalArgumentException("ActionType non può essere nullo o vuoto");
        }
        if (triggerDetails == null || triggerDetails.isEmpty()) {
            throw new IllegalArgumentException("TriggerDetails non può essere nullo o vuoto");
        }
        if (actionDetails == null || actionDetails.isEmpty()) {
            throw new IllegalArgumentException("ActionDetails non può essere nullo o vuoto");
        }
    }
}
