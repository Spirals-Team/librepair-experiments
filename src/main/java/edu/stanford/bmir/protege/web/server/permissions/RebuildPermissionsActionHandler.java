package edu.stanford.bmir.protege.web.server.permissions;

import edu.stanford.bmir.protege.web.server.access.AccessManager;
import edu.stanford.bmir.protege.web.server.dispatch.ApplicationActionHandler;
import edu.stanford.bmir.protege.web.server.dispatch.ExecutionContext;
import edu.stanford.bmir.protege.web.server.dispatch.RequestContext;
import edu.stanford.bmir.protege.web.server.dispatch.RequestValidator;
import edu.stanford.bmir.protege.web.server.dispatch.validators.ApplicationPermissionValidator;
import edu.stanford.bmir.protege.web.shared.permissions.RebuildPermissionsAction;
import edu.stanford.bmir.protege.web.shared.permissions.RebuildPermissionsResult;

import javax.inject.Inject;

import static edu.stanford.bmir.protege.web.shared.access.BuiltInAction.REBUILD_PERMISSIONS;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 11 Apr 2017
 */
public class RebuildPermissionsActionHandler implements ApplicationActionHandler<RebuildPermissionsAction, RebuildPermissionsResult> {

    private final AccessManager accessManager;

    @Inject
    public RebuildPermissionsActionHandler(AccessManager accessManager) {
        this.accessManager = accessManager;
    }

    @Override
    public Class<RebuildPermissionsAction> getActionClass() {
        return RebuildPermissionsAction.class;
    }

    @Override
    public RequestValidator getRequestValidator(RebuildPermissionsAction action, RequestContext requestContext) {
        return new ApplicationPermissionValidator(accessManager, requestContext.getUserId(), REBUILD_PERMISSIONS.getActionId());
    }

    @Override
    public RebuildPermissionsResult execute(RebuildPermissionsAction action, ExecutionContext executionContext) {
        accessManager.rebuild();
        return new RebuildPermissionsResult();
    }
}
