package ch.maurer.oop.vaadin.nfldashboard.common.business;

import ch.maurer.oop.vaadin.nfldashboard.user.db.UserRecord;

public interface DataProvider {

    String getDataFromUrl(String fromUrl);

    UserRecord authenticate(String userName, String password);

}
