package edu.stanford.bmir.protege.web.shared.event;

import com.google.common.base.Objects;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.InvocationException;
import edu.stanford.bmir.protege.web.shared.HasProjectId;
import edu.stanford.bmir.protege.web.shared.dispatch.Action;
import edu.stanford.bmir.protege.web.shared.dispatch.ProjectAction;
import edu.stanford.bmir.protege.web.shared.dispatch.InvocationExceptionTolerantAction;
import edu.stanford.bmir.protege.web.shared.events.EventTag;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;
import edu.stanford.bmir.protege.web.shared.user.UserId;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 20/03/2013
 */
public class GetProjectEventsAction implements Action<GetProjectEventsResult>, InvocationExceptionTolerantAction, HasProjectId {

    private ProjectId projectId;

    private UserId userId;

    private EventTag sinceTag;

    /**
     * For serialization purposes only.
     */
    private GetProjectEventsAction() {
    }

    public GetProjectEventsAction(EventTag sinceTag, ProjectId projectId, UserId userId) {
        this.sinceTag = sinceTag;
        this.projectId = projectId;
        this.userId = userId;
    }

    public EventTag getSinceTag() {
        return sinceTag;
    }

    @Nonnull
    @Override
    public ProjectId getProjectId() {
        return projectId;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public Optional<String> handleInvocationException(InvocationException ex) {
        GWT.log("Could not retrieve events due to server connection problems.");
        return Optional.empty();
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("GetProjectEventsAction")
                .addValue(projectId)
                .addValue(userId)
                .add("since", sinceTag).toString();
    }
}
