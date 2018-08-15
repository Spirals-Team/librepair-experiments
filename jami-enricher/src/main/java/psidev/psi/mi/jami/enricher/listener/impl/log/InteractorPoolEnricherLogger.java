package psidev.psi.mi.jami.enricher.listener.impl.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import psidev.psi.mi.jami.enricher.listener.EnrichmentStatus;
import psidev.psi.mi.jami.enricher.listener.InteractorPoolEnricherListener;
import psidev.psi.mi.jami.listener.impl.InteractorPoolChangeLogger;
import psidev.psi.mi.jami.model.InteractorPool;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since  11/06/13

 */
public class InteractorPoolEnricherLogger
        extends InteractorPoolChangeLogger
        implements InteractorPoolEnricherListener {

    private static final Logger log = LoggerFactory.getLogger(InteractorPoolEnricherLogger.class);

    /** {@inheritDoc} */
    public void onEnrichmentComplete(InteractorPool protein, EnrichmentStatus status, String message) {
        log.info(protein.toString() + " enrichment complete. " +
                "The status was: " + status + ". The message reads: " + message);
    }

    /** {@inheritDoc} */
    public void onEnrichmentError(InteractorPool object, String message, Exception e) {
        log.error(object.toString() + " enrichment error. " +
                "The message reads: " + message, e);
    }
}
