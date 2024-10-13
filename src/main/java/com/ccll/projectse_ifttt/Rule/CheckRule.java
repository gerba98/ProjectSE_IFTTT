package com.ccll.projectse_ifttt.Rule;

/**
 * Questa classe estende Thread e si occupa di controllare periodicamente
 * le regole gestite da RuleManager. Se il trigger associato ad una regola
 * è verificato viene eseguita l'azione associata alla regola.
 */
public class CheckRule extends Thread {
    private final RuleManager ruleManager;
    private volatile Boolean isRunning;

    /**
     * Costruttore per inizializzare un'istanza di CheckRule.
     * @param ruleManager L'istanza di RuleManager a cui è associato questo thread.
     */
    public CheckRule(RuleManager ruleManager) {
        this.ruleManager = ruleManager;
        this.isRunning = false;
    }

    /**
     * Il metodo avvia il thread. Fino a quando il thread non è interrotto, controlla
     * periodicamente le regole gestite da RuleManager. Se il trigger di una regola è
     * verificato, viene eseguita l'azione associata alla regola.
     * Il controllo viene eseguito ogni secondo.
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isRunning) {
                for (Rule rule : ruleManager.getRules()) {
                    if (rule.getTrigger().evaluate()) {
                        rule.executeAction();
                    }
                }
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * @return True se deve essere eseguito il controlo, altrimenti false.
     */
    public Boolean getRunning() {
        return isRunning;
    }

    /**
     * @param running True per avviare il controllo delle regole, altrimenti false.
     */
    public void setRunning(Boolean running) {
        isRunning = running;
    }

//    public void stopThread() {
//        isRunning = false;
//        interrupt();
//    }
}


