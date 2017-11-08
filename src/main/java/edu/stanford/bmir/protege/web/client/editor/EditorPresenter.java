package edu.stanford.bmir.protege.web.client.editor;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import edu.stanford.bmir.protege.web.client.dispatch.DispatchServiceCallback;
import edu.stanford.bmir.protege.web.client.dispatch.DispatchServiceManager;
import edu.stanford.bmir.protege.web.client.events.UserLoggedInEvent;
import edu.stanford.bmir.protege.web.client.events.UserLoggedOutEvent;
import edu.stanford.bmir.protege.web.client.permissions.LoggedInUserProjectPermissionChecker;
import edu.stanford.bmir.protege.web.client.progress.HasBusy;
import edu.stanford.bmir.protege.web.shared.HasDispose;
import edu.stanford.bmir.protege.web.shared.dispatch.Action;
import edu.stanford.bmir.protege.web.shared.dispatch.Result;
import edu.stanford.bmir.protege.web.shared.dispatch.UpdateObjectAction;
import edu.stanford.bmir.protege.web.shared.entity.EntityDisplay;
import edu.stanford.bmir.protege.web.shared.event.HandlerRegistrationManager;
import edu.stanford.bmir.protege.web.shared.project.ProjectId;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static edu.stanford.bmir.protege.web.shared.access.BuiltInAction.EDIT_ONTOLOGY;
import static edu.stanford.bmir.protege.web.shared.permissions.PermissionsChangedEvent.ON_PERMISSIONS_CHANGED;

/**
 * Author: Matthew Horridge<br>
 * Stanford University<br>
 * Bio-Medical Informatics Research Group<br>
 * Date: 23/04/2013
 */
public class EditorPresenter implements HasDispose {

    private static final Label NOTHING_SELECTED_WIDGET = new Label("Nothing selected");

    private static final int VALUE_CHANGED_COMMIT_DELAY_MS = 2000;

    private final DispatchServiceManager dispatchServiceManager;

    private final LoggedInUserProjectPermissionChecker permissionChecker;

    private final ContextMapper contextMapper;


    private final SimplePanel editorHolder = new SimplePanel();

    private Optional<EditorState<?, ?, ?, ?>> lastEditorState = Optional.empty();

    private HandlerRegistration valueChangedReg;


    private HandlerManager handlerManager = new HandlerManager(this);


    private int counter = 0;

    private HandlerRegistrationManager handlerRegistrationManager;


    private EntityDisplay entityDisplay = entityData -> {};


    private Timer commitOnValueChangedTimer = new Timer() {
        @Override
        public void run() {
            commitCurrentValue(lastEditorState.get());
        }
    };


    private HasBusy hasBusy = busy -> {};

    @Inject
    public EditorPresenter(ProjectId projectId,
                           EventBus eventBus,
                           DispatchServiceManager dispatchServiceManager,
                           ContextMapper contextMapper,
                           LoggedInUserProjectPermissionChecker permissionChecker) {
        this.contextMapper = contextMapper;
        this.dispatchServiceManager = dispatchServiceManager;
        this.permissionChecker = permissionChecker;
        this.handlerRegistrationManager = new HandlerRegistrationManager(eventBus);

        handlerRegistrationManager.registerHandlerToProject(projectId, ON_PERMISSIONS_CHANGED, event -> updatePermissionBasedItems());
        handlerRegistrationManager.registerHandler(UserLoggedInEvent.ON_USER_LOGGED_IN, event -> updatePermissionBasedItems());
        handlerRegistrationManager.registerHandler(UserLoggedOutEvent.ON_USER_LOGGED_OUT, event -> updatePermissionBasedItems());
    }

    public HandlerRegistration addEditorContextChangedHandler(EditorContextChangedHandler handler) {
        return handlerManager.addHandler(EditorContextChangedEvent.TYPE, handler);
    }

    public Widget getView() {
        return editorHolder;
    }


    public void setHasBusy(@Nonnull HasBusy hasBusy) {
        this.hasBusy = checkNotNull(hasBusy);
    }

    public void setEntityDisplay(@Nonnull EntityDisplay entityDisplay) {
        this.entityDisplay = checkNotNull(entityDisplay);
    }

    public <C extends EditorCtx> void setEditorContext(final Optional<C> editorContext) {
        if (lastEditorState.isPresent()) {
            unbindPrevious(lastEditorState.get());
        }
        if (editorContext.isPresent()) {
            bindNext(editorContext.get());
        }
        else {
            editorHolder.setWidget(NOTHING_SELECTED_WIDGET);
        }
    }

    private void clearEditorState() {
        lastEditorState = Optional.empty();
    }

    private <C extends EditorCtx, O, A extends Action<R>, R extends Result> void setEditorState(O pristineObject, C editorContext, EditorManager<C, O, A, R> editorManager) {
        EditorState<C, O, A, R> editorState = new EditorState<>(pristineObject, editorContext, editorManager);
        lastEditorState = Optional.of(editorState);
        handlerManager.fireEvent(new EditorContextChangedEvent(editorContext, editorManager.getDescription(editorContext)));
    }


    private <C extends EditorCtx, O, A extends Action<R>, R extends Result> void unbindPrevious(EditorState<C, O, A, R> editorState) {
        valueChangedReg.removeHandler();
        commitOnValueChangedTimer.cancel();
        if (lastEditorState.isPresent()) {
            commitCurrentValue(editorState);
        }
        clearEditorState();
    }

    /**
     * Commit the current value
     *
     * @param editorState The state to be committed
     */
    private <C extends EditorCtx, O, A extends Action<R>, R extends Result> void commitCurrentValue(final EditorState<C, O, A, R> editorState) {
        if (!editorState.getEditorManager().getView(editorState.getEditorContext()).isDirty()) {
            return;
        }
        final Optional<O> value = editorState.getEditedValue();
        if (!value.isPresent()) {
            return;
        }
        final O pristineValue = editorState.getPristineObject();
        final O editedValue = value.get();
        if (pristineValue.equals(editedValue)) {
            return;
        }
        final C editorCtx = editorState.getEditorContext();
        EditorManager<C, O, A, R> editorManager = editorState.getEditorManager();
        UpdateObjectAction<O> updateAction = editorManager.createUpdateObjectAction(pristineValue, editedValue, editorCtx);
        setEditorState(editedValue, editorCtx, editorManager);
        dispatchServiceManager.execute(updateAction, new DispatchServiceCallback<Result>() {
            @Override
            public void handleSuccess(Result result) {

            }
        });
    }

    private <C extends EditorCtx, O, A extends Action<R>, R extends Result> void bindNext(final C editorCtx) {
        counter++;
        final Optional<EditorManager<C, O, A, R>> selectedMan = contextMapper.getEditorManager(editorCtx);
        if (selectedMan.isPresent()) {
            final EditorManager<C, O, A, R> editorManager = selectedMan.get();
            A action = editorManager.createAction(editorCtx);
            updatePermissionBasedItems();
            int executionCounter = counter;
            dispatchServiceManager.execute(action, hasBusy, result -> {
                if (executionCounter != counter) {
                    return;
                }
                final O value = editorManager.extractObject(result);
                final EditorView<O> editorView = editorManager.getView(editorCtx);
                editorView.setEntityDisplay(entityDisplay);
                editorView.setValue(value);
                valueChangedReg = editorView.addValueChangeHandler(event -> rescheduleCommit());
                final Widget editorWidget = editorView.asWidget();
                editorHolder.setWidget(editorWidget);
                setEditorState(value, editorCtx, editorManager);
                updatePermissionBasedItems();
            });
        }
        else {
            editorHolder.setWidget(new Label("No editor available for selection: " + editorCtx));
        }
    }

    private void rescheduleCommit() {
        commitOnValueChangedTimer.cancel();
        commitOnValueChangedTimer.schedule(VALUE_CHANGED_COMMIT_DELAY_MS);
    }

    @Override
    public void dispose() {
        handlerRegistrationManager.removeHandlers();
    }


    public void updatePermissionBasedItems() {
        if (editorHolder.getWidget() instanceof HasEnabled) {
            final HasEnabled hasEnabled = (HasEnabled) editorHolder.getWidget();
            hasEnabled.setEnabled(false);
            permissionChecker.hasPermission(EDIT_ONTOLOGY, hasEnabled::setEnabled);
        }
    }


    private static class EditorState<C extends EditorCtx, O, A extends Action<R>, R extends Result> {

        private final O pristineObject;

        private final C editorContext;

        private final EditorManager<C, O, A, R> editorManager;

        private EditorState(O pristineObject, C editorContext, EditorManager<C, O, A, R> editorManager) {
            this.pristineObject = pristineObject;
            this.editorContext = editorContext;
            this.editorManager = editorManager;
        }

        public O getPristineObject() {
            return pristineObject;
        }

        public C getEditorContext() {
            return editorContext;
        }

        public EditorManager<C, O, A, R> getEditorManager() {
            return editorManager;
        }

        public Optional<O> getEditedValue() {
            return editorManager.getView(editorContext).getEditorValue();
        }
    }


}
