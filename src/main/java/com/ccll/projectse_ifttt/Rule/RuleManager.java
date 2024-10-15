package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.Action;
import com.ccll.projectse_ifttt.Actions.ActionCreator;
import com.ccll.projectse_ifttt.Actions.DisplayMessageActionCreator;
import com.ccll.projectse_ifttt.Actions.PlayAudioActionCreator;
import com.ccll.projectse_ifttt.Triggers.TOTDTrigCreator;
import com.ccll.projectse_ifttt.Triggers.Trigger;
import com.ccll.projectse_ifttt.Triggers.TriggerCreator;
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
     *
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
     *
     * @return L'observable list contenente Rule
     */
    public ObservableList<Rule> getRules() {
        return rules;
    }


    /**
     * Crea una nuova regola basata sui parametri forniti.
     *
     * @param triggerType  Il tipo di trigger per la regola
     * @param triggerValue Il valore del trigger per la regola
     * @param actionType   Il tipo di azione da eseguire quando viene attivato il trigger
     * @param actionValue  Il valore dell'azione da eseguire
     * @param ruleName     Il nome della regola da creare
     * @return La nuova regola creata con i parametri forniti
     */
    public Rule createRule(String triggerType, String triggerValue, String actionType, String actionValue, String ruleName) {
        Trigger trigger = createTrigger(triggerType, triggerValue);
        Action action = createAction(actionType, actionValue);

        Rule newRule = new Rule(trigger, action, ruleName);
        addRule(newRule);

        return newRule;
    }

    /**
     * @param actionType
     * @param actionValue
     * @return
     */
    private Action createAction(String actionType, String actionValue) {
        actionType = actionType.toLowerCase();
        System.out.println(actionValue);
        ActionCreator actionCreator = switch (actionType) {
            case "play audio" -> new PlayAudioActionCreator();
            case "display message" -> new DisplayMessageActionCreator();
            default -> throw new IllegalStateException("Unexpected value: " + actionType);
        };
        return actionCreator.createAction(actionValue);
    }

    /**
     * @param triggerType
     * @param triggerValue
     * @return
     */
    private Trigger createTrigger(String triggerType, String triggerValue) {
        triggerType = triggerType.toLowerCase();
        System.out.println(triggerValue);
        TriggerCreator triggerCreator = switch (triggerType){
            case "time of the day" -> new TOTDTrigCreator();
            default -> throw new IllegalStateException("Unexpected value: " + triggerType);
        };
        return triggerCreator.createTrigger(triggerValue);
    }

}