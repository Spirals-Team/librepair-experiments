package cz.polankam.pcrf.trafficgenerator.scenario.actions;


/**
 * @note Should not have setters! Read-only structure!
 */
public class ReceiveScenarioActionEntry implements ScenarioActionEntry {

    private final long timeout;
    private ScenarioAction gxAction;
    private ScenarioAction rxAction;


    /**
     *
     * @param timeout in milliseconds
     * @param gxAction
     * @param rxAction
     */
    public ReceiveScenarioActionEntry(long timeout, ScenarioAction gxAction, ScenarioAction rxAction) {
        this.timeout = timeout;
        this.gxAction = gxAction;
        this.rxAction = rxAction;
    }

    /**
     * New scenario entry with no timeout or timeout.
     * @param gxAction
     * @param rxAction
     */
    public ReceiveScenarioActionEntry(ScenarioAction gxAction, ScenarioAction rxAction) {
        this(0, gxAction, rxAction);
    }

    @Override
    public boolean isSending() {
        return false;
    }

    public ScenarioAction getGxAction() {
        return gxAction;
    }

    public ScenarioAction getRxAction() {
        return rxAction;
    }

    /**
     * Get timeout of this action in milliseconds.
     * @return in milliseconds
     */
    public long getTimeout() {
        return timeout;
    }

}
