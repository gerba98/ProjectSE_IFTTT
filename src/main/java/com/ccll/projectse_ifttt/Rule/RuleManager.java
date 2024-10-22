package com.ccll.projectse_ifttt.Rule;

import com.ccll.projectse_ifttt.Actions.*;
import com.ccll.projectse_ifttt.Triggers.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;

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
        rule = rules.remove(ruleIndex);
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
     * Crea una nuova Rule basata sui parametri forniti.
     *
     * @param ruleName     Il nome della regola da creare
     * @param triggerType  Il tipo di trigger per la regola
     * @param triggerValue Il valore del trigger per la regola
     * @param actionType   Il tipo di azione da eseguire quando viene attivato il trigger
     * @param actionValue  Il valore associato all'azione da eseguire
     * @return La nuova regola creata con i parametri forniti
     */
    public Rule createRule(String ruleName, String triggerType, String triggerValue, String actionType, String actionValue) {
        Trigger trigger = createTrigger(triggerType, triggerValue);
        Action action = createAction(actionType, actionValue);
        Rule newRule = new Rule(ruleName, trigger, action);
        addRule(newRule);
        return newRule;
    }

    /**
     * Crea una nuova Rule basata sui parametri forniti e sul tipo specificato.
     *
     * @param ruleName     Il nome della regola da creare
     * @param triggerType  Il tipo di trigger per la regola
     * @param triggerValue Il valore del trigger per la regola
     * @param actionType   Il tipo di azione da eseguire quando viene attivato il trigger
     * @param actionValue  Il valore associato all'azione da eseguire
     * @param ruleType     Il tipo di regola che deve essere create:
     *                     "PeriodicRule-gg:hh:mm" per creare una PeriodicRule.
     *                     (gg:hh:mm è il periodo durante il quale la regola deve rimanere disattivata espresso in giorni:ore:minuti)
     *                     "SingleRule" per creare una SingleRule
     *                     "Rule" per creare una Rule
     *                     Se questo parametro non viene specificato verrà creata una Rule.
     *                     (case-insensitive)
     * @return La nuova regola creata con i parametri forniti
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
     * Crea un'azione basata sul tipo e valore specificati.
     *
     * @param actionType  Il tipo di azione da creare. Deve essere una stringa non nulla.
     *                    I valori supportati sono "play audio" e "display message" (case-insensitive).
     * @param actionValue Il valore associato all'azione. Il significato dipende dal tipo di azione.
     *                    Es. Per "play audio": il percorso del file audio da riprodurre.
     * @return Un'istanza di Action creata in base ai parametri forniti.
     * @throws IllegalStateException se viene fornito un tipo di azione non supportato.
     */
    private Action createAction(String actionType, String actionValue) {
        ActionCreator actionCreator = switch (actionType.toLowerCase()) {
            case "play audio" -> new PlayAudioActionCreator();
            case "display message" -> new DisplayMessageActionCreator();
            case "write string" -> new WriteStringActionCreator();
            case "copy file" -> new CopyFileActionCreator();
            case "move file" -> new MoveFileActionCreator();
            case "remove file" -> new RemoveFileActionCreator();
            case "execute program" -> new ExecuteProgramActionCreator();
            default -> throw new IllegalStateException("Unsupported action type: " + actionType);
        };
        return actionCreator.createAction(actionValue);
    }

    /**
     * Crea un trigger basato sul tipo e valore specificati.
     *
     * @param triggerType  Il tipo di trigger da creare. Deve essere una stringa non nulla.
     *                     Attualmente, l'unico valore supportato è "time of the day" (case-insensitive).
     * @param triggerValue Il valore associato al trigger. Il significato dipende dal tipo di trigger.
     *                     Es. Per "time of the day": l'orario in formato stringa (es. "14:30").
     * @return Un'istanza di Trigger creata in base ai parametri forniti.
     * @throws IllegalStateException se viene fornito un tipo di trigger non supportato.
     */
    private Trigger createTrigger(String triggerType, String triggerValue) {
        TriggerCreator triggerCreator = switch (triggerType.toLowerCase()) {
            case "time of the day" -> new TOTDTrigCreator();
            case "day of the week" -> new DOTWTrigCreator();
            case "day of the month" -> new DOTMTrigCreator();
            case "date" -> new SDTrigCreator();
            case "status program" -> new EPTrigCreator();
            default -> throw new IllegalStateException("Unexpected value: " + triggerType);
        };
        return triggerCreator.createTrigger(triggerValue);
    }

}