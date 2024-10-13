package com.ccll.projectse_ifttt.Rule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Questa classe gestisce un insieme di regole nel sistema IFTTT.
 * Implementa il pattern Singleton per garantire che ci sia una sola istanza di
 * RuleManager durante l'esecuzione dell'applicazione.
 */
public class RuleManager {
    private static RuleManager instance;
    private final ObservableList<Rule> rules;
    private final CheckRule ruleChecker;

    /**
     * Costruttore privato per inizializzare il RuleManager.
     * Crea un'istanza di CheckRule e avvia il thread per il controllo delle regole.
     */
    private RuleManager() {
        ruleChecker = new CheckRule(this);
        ruleChecker.start();
        this.rules = FXCollections.observableArrayList();
    }

    /**
     * Restituisce l'istanza singleton di RuleManager.
     * @return L'istanza di RuleManager.
     */
    public static RuleManager getInstance() {
        if (instance == null) {
            instance = new RuleManager();
        }
        return instance;
    }

    /**
     * Aggiunge una nuova regola alla lista delle regole gestite.
     * Se è la prima regola aggiunta, avvia il controllo delle regole definito in CheckRule.
     * @param rule La regola da aggiungere.
     */
    public void addRule(Rule rule) {
        rules.add(rule);
        if (rules.size() == 1) {
            ruleChecker.setRunning(true);
        }
    }

    /**
     * Restituisce la lista delle regole attualmente gestite.
     * @return L'observable list contenente Rule
     */
    public ObservableList<Rule> getRules() {
        return rules;
    }

//        public void removeRule(Rule rule) {
//        rules.remove(rule);
//    }

}