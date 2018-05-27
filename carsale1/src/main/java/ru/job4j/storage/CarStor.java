package ru.job4j.storage;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public enum CarStor {

    INSTANCE;

    private final UserStorage uStor = new UserStorage();
    private final BrandStorage bStor = new BrandStorage();
    private final ModelStorage mStor = new ModelStorage();
    private final AdvertStorage aStor = new AdvertStorage();

    public UserStorage getuStor() {
        return uStor;
    }

    public BrandStorage getbStor() {
        return bStor;
    }

    public ModelStorage getmStor() {
        return mStor;
    }

    public AdvertStorage getaStor() {
        return aStor;
    }
}
