package com.ccll.projectse_ifttt.Rule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Questa classe gestisce un insieme di regole nel sistema IFTTT.
 * Implementa il pattern Singleton per garantire che ci sia una sola istanza di
 * RuleManager durante l'esecuzione dell'applicazione.
 */
public class RuleManager {
    private static volatile RuleManager instance;
    private final ObservableList<Rule> rules;
    private CheckRule ruleChecker;

    /**
     * Costruttore privato per inizializzare il RuleManager  e la lista delle regole.
     */
    private RuleManager() {
        this.rules = FXCollections.observableArrayList();
    }

    /**
     * Restituisce l'istanza singleton di RuleManager.
     * Se l'istanza non è ancora stata creata, la crea.
     * @return L'istanza di RuleManager.
     */
    public static RuleManager getInstance() {
        RuleManager result = instance;
        if (result == null) {
            synchronized (RuleManager.class) {
                result = instance;
                if (result == null) {
                    instance = result = new RuleManager();
                }
            }
        }
        return result;
    }

    /**
     * Aggiunge una nuova regola alla lista delle regole.
     * Se è la prima regola aggiunta, istanzia un nuovo thread per il controllo delle regole e
     * inizia il controllo.
     * @param rule La regola da aggiungere.
     */
    public void addRule(Rule rule) {
        rules.add(rule);
        if (rules.size() == 1) {
            if (ruleChecker == null) {
                ruleChecker =  new CheckRule();
                ruleChecker.start();
            }
        }
    }

    /**
     * Rimuove una regola dalla lista delle regole gestite.
     * Se, dopo la rimozione, non ci sono più regole, ferma il controllo delle regole.
     *
     * @param rule La regola da rimuovere.
     */
    public void removeRule(Rule rule) {
        rules.remove(rule);
        if (rules.isEmpty() && ruleChecker != null) {
            ruleChecker.stop();
        }
    }

    /**
     * Restituisce la lista delle regole attualmente gestite.
     * @return L'observable list contenente Rule
     */
    public ObservableList<Rule> getRules() {
        return rules;
    }

}