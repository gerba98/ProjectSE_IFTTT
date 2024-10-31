package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.*;
import com.ccll.projectse_ifttt.Triggers.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

/**
 * Questa classe gestisce un insieme di regole nel sistema IFTTT.
 * Implementa il pattern Singleton per garantire che ci sia una sola istanza di
 * {@code RuleManager} durante l'esecuzione dell'applicazione.
 * Fornisce metodi per aggiungere, rimuovere, salvare e caricare regole, e per creare trigger e azioni.
 */
public class RuleManager {
    private static volatile RuleManager instance;
    private final ObservableList<Rule> rules;
    private CheckRule ruleChecker;
    private RulePersistence rulePersistence;
    private boolean isLoaded;
    /**
     * Costruttore privato per inizializzare {@code RuleManager} e la lista delle regole.
     * Configura l'ObservableList per tracciare i cambiamenti alle proprietà di stato delle regole.
     */
    private RuleManager() {
        this.rules = FXCollections.observableArrayList(new Callback<Rule, Observable[]>() {
            @Override
            public javafx.beans.Observable[] call(Rule rule) {
                return new javafx.beans.Observable[]{rule.stateProperty()};
            }
        });
        this.isLoaded = false;
    }

    /**
     * Restituisce l'istanza singleton di {@code RuleManager}.
     * Se l'istanza non è ancora stata creata, la crea.
     *
     * @return L'istanza singleton di {@code RuleManager}.
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
     * Aggiunge una nuova regola alla lista delle regole gestite.
     * Se è la prima regola aggiunta, avvia il thread per il controllo periodico delle regole.
     *
     * @param rule La regola da aggiungere.
     */
    public void addRule(Rule rule) {
        rules.add(rule);
        if (rules.size() == 1) {
            if (ruleChecker == null) {
                ruleChecker = new CheckRule();
                ruleChecker.start();
            }
            if (!ruleChecker.getRunning()) {
                ruleChecker.start();
            }
        }
    }

    /**
     * Rimuove una regola dalla lista delle regole gestite.
     * Se, dopo la rimozione, non ci sono più regole, ferma il controllo delle regole.
     *
     * @param ruleIndex L'indice della regola da rimuovere.
     */
    public void removeRule(int ruleIndex) {
        Rule rule;

        //rulePersistence = new RulePersistence();
        rule = rules.remove(ruleIndex);
        //rulePersistence.deleteRules(ruleIndex);
        if (rules.isEmpty() && ruleChecker != null) {
            ruleChecker.stop();
        }
    }

    /**
     * Restituisce la lista delle regole attualmente gestite.
     * Se la lista è vuota, inizializza {@code RulePersistence} e carica le regole salvate.
     *
     * @return L'observable list contenente le regole.
     */
    public synchronized ObservableList<Rule> getRules() {
        if (rules.isEmpty() && !isLoaded) {
            rulePersistence = new RulePersistence();
            rulePersistence.loadRules();
            isLoaded = true;
        }
        return rules;
    }

    /**
     * Salva le regole gestite usando {@code RulePersistence}.
     */
    public void saveRules() {
        rulePersistence = new RulePersistence();
        rulePersistence.saveRules();
    }

    /**
     * Crea una nuova regola basata sui parametri forniti.
     *
     * @param ruleName     Il nome della regola da creare.
     * @param triggerType  Il tipo di trigger per la regola.
     * @param triggerValue Il valore del trigger per la regola.
     * @param actionType   Il tipo di azione da eseguire quando viene attivato il trigger.
     * @param actionValue  Il valore associato all'azione da eseguire.
     * @return La nuova regola creata con i parametri forniti.
     */
    public Rule createRule(String ruleName, String triggerType, String triggerValue, String actionType, String actionValue) {
        Trigger trigger = createTrigger(triggerType, triggerValue);
        Action action = createAction(actionType, actionValue);
        Rule newRule = new Rule(ruleName, trigger, action);
        addRule(newRule);
        return newRule;
    }

    /**
     * Crea una nuova regola basata sui parametri forniti e sul tipo specificato.
     *
     * @param ruleName     Il nome della regola da creare.
     * @param triggerType  Il tipo di trigger per la regola.
     * @param triggerValue Il valore del trigger per la regola.
     * @param actionType   Il tipo di azione da eseguire quando viene attivato il trigger.
     * @param actionValue  Il valore associato all'azione da eseguire.
     * @param ruleType     Il tipo di regola da creare:
     *                     <ul>
     *                         <li>"PeriodicRule-gg:hh:mm" per creare una {@code PeriodicRule} (gg:hh:mm è il periodo di inattività espresso in giorni:ore:minuti).</li>
     *                         <li>"SingleRule" per creare una {@code SingleRule}.</li>
     *                         <li>"Rule" per creare una {@code Rule} generica.</li>
     *                     </ul>
     *                     Se non specificato, viene creata una {@code Rule} generica.
     * @return La nuova regola creata con i parametri forniti.
     */
    public Rule createRule(String ruleName, String triggerType, String triggerValue, String actionType, String actionValue, String ruleType) {
        Trigger trigger = createTrigger(triggerType, triggerValue);
        Action action = createAction(actionType, actionValue);
        String[] ruleInfo = ruleType.split("-");
        Rule newRule = switch (ruleInfo[0].toLowerCase()) {
            case "rule" -> new Rule(ruleName, trigger, action);
            case "singlerule" -> new SingleRule(ruleName, trigger, action);
            case "periodicrule" -> new PeriodicRule(ruleName, trigger, action, ruleInfo[1]);
            default -> throw new IllegalStateException("Unexpected value: " + ruleType);
        };
        addRule(newRule);
        return newRule;
    }

    /**
     * Crea un trigger basato sul tipo e valore specificati.
     *
     * @param triggerType  Il tipo di trigger da creare.
     * @param triggerValue Il valore associato al trigger.
     * @return Un'istanza di {@code Trigger} creata in base ai parametri forniti.
     * @throws IllegalStateException se viene fornito un tipo di trigger non supportato.
     */
    public static Trigger createTrigger(String triggerType, String triggerValue) {
        TriggerCreator triggerCreator = switch (triggerType.toLowerCase()) {
            case "time of the day" -> new TOTDTrigCreator();
            case "day of the week" -> new DOTWTrigCreator();
            case "day of the month" -> new DOTMTrigCreator();
            case "date" -> new SDTrigCreator();
            case "status program" -> new EPTrigCreator();
            case "file dimension" -> new FileDimTrigCreator();
            case "file existence" -> new FileExisTrigCreator();
            case "composite" -> new CompositeTrigCreator();
            default -> throw new IllegalStateException("Unexpected value: " + triggerType);
        };
        return triggerCreator.createTrigger(triggerValue);
    }

    /**
     * Crea un'azione basata sul tipo e valore specificati.
     *
     * @param actionType  Il tipo di azione da creare.
     * @param actionValue Il valore associato all'azione.
     * @return Un'istanza di {@code Action} creata in base ai parametri forniti.
     * @throws IllegalStateException se viene fornito un tipo di azione non supportato.
     */
    public static Action createAction(String actionType, String actionValue) {
        ActionCreator actionCreator = switch (actionType.toLowerCase()) {
            case "play audio" -> new PlayAudioActionCreator();
            case "display message" -> new DisplayMessageActionCreator();
            case "write string" -> new WriteStringActionCreator();
            case "copy file" -> new CopyFileActionCreator();
            case "move file" -> new MoveFileActionCreator();
            case "remove file" -> new RemoveFileActionCreator();
            case "execute program" -> new ExecuteProgramActionCreator();
            case "composite" -> new CompositeActionCreator();
            default -> throw new IllegalStateException("Unsupported action type: " + actionType);
        };
        return actionCreator.createAction(actionValue);
    }
}
