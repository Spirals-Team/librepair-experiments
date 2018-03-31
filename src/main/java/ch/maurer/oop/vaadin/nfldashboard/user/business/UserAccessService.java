package ch.maurer.oop.vaadin.nfldashboard.user.business;

import ch.maurer.oop.vaadin.nfldashboard.user.db.UserRecord;
import com.vaadin.flow.server.VaadinSession;

public final class UserAccessService {

    private UserAccessService() {
    }

    public static UserRecord getCurrentUser() {
        return (UserRecord) VaadinSession.getCurrent().getAttribute(UserRecord.class.getName());
    }
}
