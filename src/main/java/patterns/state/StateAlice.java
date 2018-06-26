
package patterns.state;

class StateAlice extends AbstractState {

    /*
     * (non-Javadoc)
     *
     * @see patterns.gof.behavioural.state.State#handle()
     */
    @Override
    public void handle() {
        this.log.info("handle()");
    }

}
