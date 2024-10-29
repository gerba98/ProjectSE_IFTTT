package com.ccll.projectse_ifttt.Rule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Questa classe si occupa di controllare periodicamente le regole gestite da {@link RuleManager}.
 * Se il trigger associato a una regola è verificato, viene eseguita l'azione associata.
 * La classe utilizza un {@link ScheduledExecutorService} per eseguire controlli periodici.
 */
public class CheckRule {
    private final RuleManager ruleManager;
    private Boolean isRunning;
    private ScheduledExecutorService scheduler;

    /**
     * Costruttore che inizializza un'istanza di CheckRule.
     * Recupera l'istanza di {@link RuleManager} e crea un {@link ScheduledExecutorService}
     * per eseguire il controllo periodico delle regole.
     */
    public CheckRule() {
        this.ruleManager = RuleManager.getInstance();
        this.isRunning = false;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Restituisce lo stato di esecuzione di {@code CheckRule}.
     *
     * @return true se il controllo delle regole è attualmente in esecuzione, false altrimenti.
     */
    public Boolean getRunning() {
        return isRunning;
    }

    /**
     * Avvia il thread che controlla periodicamente le regole gestite da {@link RuleManager}.
     * Se lo scheduler è stato chiuso, ne crea uno nuovo. Il controllo delle regole viene eseguito ogni secondo.
     * Se il trigger di una regola è verificato, viene eseguita l'azione associata.
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
     * Ferma l'esecuzione del controllo delle regole e chiude lo scheduler.
     */
    public void stop() {
        if (isRunning) {
            scheduler.shutdown();
            isRunning = false;
        }
    }
}
