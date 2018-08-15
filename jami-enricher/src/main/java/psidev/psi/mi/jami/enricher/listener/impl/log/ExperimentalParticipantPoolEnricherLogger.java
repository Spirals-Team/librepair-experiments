package psidev.psi.mi.jami.enricher.listener.impl.log;

import psidev.psi.mi.jami.enricher.listener.ExperimentalParticipantPoolEnricherListener;
import psidev.psi.mi.jami.model.CvTerm;
import psidev.psi.mi.jami.model.ExperimentalParticipantPool;
import psidev.psi.mi.jami.model.ParticipantCandidate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A logging listener. It will display a message when each event if fired.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 22/07/13

 */
public class ExperimentalParticipantPoolEnricherLogger
        extends ParticipantEvidenceEnricherLogger<ExperimentalParticipantPool> implements ExperimentalParticipantPoolEnricherListener {

    private static final Logger log = Logger.getLogger("ExperimentalParticipantPoolEnricherLogger");

    /** {@inheritDoc} */
    public void onTypeUpdate(ExperimentalParticipantPool entity, CvTerm oldTerm) {
        log.log(Level.INFO, "The pool type " + oldTerm + " has been updated with " + entity.getBiologicalRole() + " in the participant pool " + entity.toString());
    }

    /** {@inheritDoc} */
    public void onAddedCandidate(ExperimentalParticipantPool protein, ParticipantCandidate added) {
        log.log(Level.INFO, "The candidate " + added.toString() + " has been added to the participant pool " + protein.toString());
    }

    /** {@inheritDoc} */
    public void onRemovedCandidate(ExperimentalParticipantPool protein, ParticipantCandidate removed) {
        log.log(Level.INFO, "The candidate " + removed.toString() + " has been removed from the participant pool " + protein.toString());
    }
}
