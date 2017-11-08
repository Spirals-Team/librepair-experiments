package edu.stanford.bmir.protege.web.server.app;

import edu.stanford.bmir.protege.web.server.admin.ApplicationSettingsManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 19 Mar 2017
 */
public class ApplicationPathSupplier implements Supplier<String> {

    private final ApplicationSettingsManager manager;

    @Inject
    public ApplicationPathSupplier(@Nonnull ApplicationSettingsManager manager) {
        this.manager = checkNotNull(manager);
    }

    @Nonnull
    public String get() {
        return manager.getApplicationSettings().getApplicationLocation().getPath();
    }
}
