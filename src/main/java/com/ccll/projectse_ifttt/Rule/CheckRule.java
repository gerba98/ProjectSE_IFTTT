package com.ccll.projectse_ifttt.Rule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Questa classe si occupa di controllare periodicamente
 * le regole gestite da RuleManager. Se il trigger associato ad una regola
 * è verificato viene eseguita l'azione associata alla regola.
 */
public class CheckRule {
    private final RuleManager ruleManager;



    private Boolean isRunning;
    private ScheduledExecutorService scheduler;

    /**
     * Costruttore che inizializza un'istanza di CheckRule.
     * Recupera l'istanza del RuleManager e crea un ScheduledExecutorService
     * per eseguire il controllo periodico delle regole.
     */
    public CheckRule() {
        this.ruleManager = RuleManager.getInstance();
        this.isRunning = false;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }


    /**
     * Getter per ottenere lo status dell'oggetto CheckerRule
     * @return la variabile booleana isRunning
     */
    public Boolean getRunning() {
        return isRunning;
    }


    /**
     * Avvia il thread che controlla periodicamente le regole gestite da RuleManager.
     * Se lo scheduler è stato chiuso, ne crea uno nuovo.
     * Se il trigger di una regola è verificato, viene eseguita l'azione associata.
     * Il controllo delle regole viene eseguito ogni secondo.
     * Questo metodo può essere chiamato solo se il thread non è già in esecuzione.
     */
    public void start() {
        if (!isRunning) {
            if (scheduler.isShutdown()) {
                this.scheduler = Executors.newSingleThreadScheduledExecutor();
            }
            isRunning = true;
            scheduler.scheduleAtFixedRate(() -> {
                for (Rule rule : ruleManager.getRules()) {
                    if (rule.evaluateTrigger()) {
                        rule.executeAction();
                    }
                }
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    /**
     * Ferma l'esecuzione del controllo delle regole
     */
    public void stop() {
        if (isRunning) {
            scheduler.shutdown();
            isRunning = false;
        }
    }

}