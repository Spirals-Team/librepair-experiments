package org.orienteer.core.component.command;

import com.google.common.base.Strings;

import java.util.Optional;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.orienteer.core.boot.loader.util.artifact.OArtifactReference;
import org.orienteer.core.boot.loader.util.artifact.OArtifact;
import org.orienteer.core.component.ICommandsSupportComponent;
import org.orienteer.core.component.property.DisplayMode;


/**
 * Command to save user's artifact
 */
public abstract class AbstractSaveOArtifactCommand extends AbstractSaveCommand<OArtifact> {

    private static final String STYLE = "style";
    private static final String ERROR_STYLE   = "color:red; font-weight:bold;";
    private static final String SUCCESS_STYLE = "color:green; font-weight:bold;";

    protected static final String SUCCESS_MSG     = "widget.artifacts.modal.window.user.artifact.feedback.success";
    protected static final String GROUP_NULL      = "widget.artifacts.modal.window.user.artifact.feedback.failed.groupId";
    protected static final String ARTIFACT_NULL   = "widget.artifacts.modal.window.user.artifact.feedback.failed.artifactId";
    protected static final String VERSION_NULL    = "widget.artifacts.modal.window.user.artifact.feedback.failed.version";
    protected static final String DOWNLOAD_ERROR  = "widget.artifacts.modal.window.user.artifact.feedback.failed.download";
    protected static final String ERROR           = "widget.artifacts.modal.window.user.artifact.feedback.failed.error";

    private final Label feedback;

    public AbstractSaveOArtifactCommand(ICommandsSupportComponent<OArtifact> component, IModel<DisplayMode> modeModel, Label feedback) {
        super(component, modeModel);
        this.feedback = feedback;
    }


    /**
     * Validate user OArtifact and send error message if OModuleConfiguration is not valid.
     * @param module - user OoArtifact
     * @return true - if user OoArtifact is valid
     *         false - if user OoArtifact is not valid
     */
    protected boolean isUserArtifactValid(Optional<AjaxRequestTarget> targetOptional, OArtifact module) {
        OArtifactReference artifact = module.getArtifactReference();
        if (Strings.isNullOrEmpty(artifact.getGroupId())) {
            sendErrorFeedback(targetOptional, new ResourceModel(GROUP_NULL));
            return false;
        }
        if (Strings.isNullOrEmpty(artifact.getArtifactId())) {
            sendErrorFeedback(targetOptional, new ResourceModel(ARTIFACT_NULL));
            return false;
        }
        if (Strings.isNullOrEmpty(artifact.getVersion())) {
            sendErrorFeedback(targetOptional, new ResourceModel(VERSION_NULL));
            return false;
        }
        if (Strings.isNullOrEmpty(artifact.getDescription())) {
            artifact.setDescription("");
        }
        if (Strings.isNullOrEmpty(artifact.getRepository())) {
            artifact.setRepository("");
        }
        return true;
    }

    protected void showFeedback(Optional<AjaxRequestTarget> targetOptional, boolean show) {
        feedback.setVisible(show);
        targetOptional.ifPresent(target -> target.add(feedback));
    }


    protected void sendErrorFeedback(Optional<AjaxRequestTarget> targetOptional, IModel<String> message) {
        showFeedback(targetOptional, true);
        feedback.add(AttributeModifier.append(STYLE, ERROR_STYLE));
        feedback.setDefaultModel(message);
    }

    protected void sendSuccessFeedback(Optional<AjaxRequestTarget> targetOptional) {
        showFeedback(targetOptional,true);
        feedback.add(AttributeModifier.append(STYLE, SUCCESS_STYLE));
        feedback.setDefaultModel(new ResourceModel(SUCCESS_MSG));
    }


}
