package edu.stanford.bmir.protege.web.shared.frame;

import edu.stanford.bmir.protege.web.client.dispatch.actions.UpdateFrameAction;
import edu.stanford.bmir.protege.web.client.frame.LabelledFrame;
import edu.stanford.bmir.protege.web.shared.entity.OWLAnnotationPropertyData;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 23/04/2013
 */
public class UpdateAnnotationPropertyFrameAction extends UpdateFrameAction<AnnotationPropertyFrame, OWLAnnotationPropertyData> {

    private UpdateAnnotationPropertyFrameAction() {
    }

    public UpdateAnnotationPropertyFrameAction(ProjectId projectId, LabelledFrame<AnnotationPropertyFrame> from, LabelledFrame<AnnotationPropertyFrame> to) {
        super(projectId, from, to);
    }
}
