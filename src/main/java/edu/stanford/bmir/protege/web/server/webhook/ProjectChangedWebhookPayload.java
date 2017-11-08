package edu.stanford.bmir.protege.web.server.webhook;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 19 May 2017
 */
public class ProjectChangedWebhookPayload {

    @Nonnull
    private final String projectId;

    @Nonnull
    private final String userId;

    private final long revisionNumber;

    private long timestamp;

    public ProjectChangedWebhookPayload(@Nonnull String projectId,
                                        @Nonnull String userId,
                                        long revisionNumber,
                                        long timestamp) {
        this.projectId = checkNotNull(projectId);
        this.userId = checkNotNull(userId);
        this.revisionNumber = revisionNumber;
        this.timestamp = timestamp;
    }

    @Nonnull
    public String getProjectId() {
        return projectId;
    }

    @Nonnull
    public String getUserId() {
        return userId;
    }

    public long getRevisionNumber() {
        return revisionNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
