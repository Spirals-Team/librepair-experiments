package edu.stanford.bmir.protege.web.shared.event;

import com.google.web.bindery.event.shared.Event;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.user.UserId;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 18/12/2012
 */
public class AnnotationPropertyFrameChangedEvent extends EntityFrameChangedEvent<OWLAnnotationProperty, AnnotationPropertyFrameChangedEventHandler> {

    public static final transient Event.Type<AnnotationPropertyFrameChangedEventHandler> TYPE = new Event.Type<AnnotationPropertyFrameChangedEventHandler>();

    public AnnotationPropertyFrameChangedEvent(OWLAnnotationProperty entity, ProjectId projectId, UserId userId) {
        super(entity, projectId, userId);
    }

    /**
     * For serialization purposes only
     */
    @SuppressWarnings("unused")
    private AnnotationPropertyFrameChangedEvent() {
    }

    /**
     * Returns the {@link com.google.web.bindery.event.shared.Event.Type} used to register this event, allowing an
     * {@link com.google.web.bindery.event.shared.EventBus} to find handlers of the appropriate class.
     * @return the type
     */
    @Override
    public Event.Type<AnnotationPropertyFrameChangedEventHandler> getAssociatedType() {
        return TYPE;
    }

    /**
     * Implemented by subclasses to to invoke their handlers in a type safe
     * manner. Intended to be called by {@link com.google.web.bindery.event.shared.EventBus#fireEvent(
     *com.google.web.bindery.event.shared.Event)} or
     * {@link com.google.web.bindery.event.shared.EventBus#fireEventFromSource(com.google.web.bindery.event.shared.Event,
     * Object)}.
     * @param handler handler
     * @see com.google.web.bindery.event.shared.EventBus#dispatchEvent(com.google.web.bindery.event.shared.Event,
     *      Object)
     */
    @Override
    protected void dispatch(AnnotationPropertyFrameChangedEventHandler handler) {
        handler.annotationPropertyFrameChanged(this);
    }
}
