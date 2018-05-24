package org.ethack.orwall;


public class BackgroundProcess extends android.app.IntentService {
    public BackgroundProcess() {
        super("BackroundProcess");
    }

    @java.lang.Override
    protected void onHandleIntent(android.content.Intent workIntent) {
        java.lang.String action = workIntent.getStringExtra(Constants.ACTION);
        if (action.equals(Constants.ACTION_PORTAL)) {
            boolean activate = workIntent.getBooleanExtra(Constants.PARAM_ACTIVATE, false);
            managePortal(activate);
        }
    }

    private void managePortal(boolean activate) {
        org.ethack.orwall.iptables.InitializeIptables initializeIptables = new org.ethack.orwall.iptables.InitializeIptables(this);
        initializeIptables.enableCaptiveDetection(activate, this);
    }
}

