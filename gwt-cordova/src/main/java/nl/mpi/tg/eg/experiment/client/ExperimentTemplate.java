package nl.mpi.tg.eg.experiment.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import nl.mpi.tg.eg.experiment.client.exception.UserIdException;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ExperimentTemplate implements EntryPoint {

    /**
     * This is the entry point method.
     */
    @Override
    public final native void onModuleLoad() /*-{
        var self = this;
        if ($doc.URL.indexOf( 'http://' ) === -1 && $doc.URL.indexOf( 'https://' ) === -1) {
            var listener = $entry(function () {
                $doc.removeEventListener("deviceready", listener, false);
                self.@nl.mpi.tg.eg.experiment.client.ExperimentTemplate::onDeviceReady()();
//              document.getElementById("widgetTag").className = device.platform; // todo: set the platform style
                //$wnd.StatusBar.hide(); // hide the status bar
                $wnd.AndroidFullScreen.immersiveMode();            
            });
            $doc.addEventListener("deviceready", listener, false);
        } else {            
            self.@nl.mpi.tg.eg.experiment.client.ExperimentTemplate::onDeviceReady()();
//            document.getElementById("widgetTag").className = device.platform; // todo: set the platform style
              //$wnd.StatusBar.hide(); // hide the status bar
        }
     }-*/;

    public void onDeviceReady() {
        final RootPanel prerunMessage = RootPanel.get("prerunMessage");
        if (prerunMessage != null) {
            prerunMessage.getElement().removeFromParent();
        }
        final RootLayoutPanel widgetTag = RootLayoutPanel.get();
        widgetTag.getElement().setId("widgetTag");
        try {
            final AppController appController = new ApplicationController(widgetTag);
            appController.start();
        } catch (UserIdException exception) {
            final Label label = new Label(exception.getMessage());
            label.setStylePrimaryName("applicationErrorMessage");
            widgetTag.add(label);
        }
    }
}
