package codeu.model.store.basic;

import codeu.model.data.User;
import codeu.model.store.persistence.PersistentStorageAgent;
import codeu.model.data.ProfilePage;
import codeu.model.store.basic.DefaultDataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Store class that uses in-memory data structures to hold values and automatically loads from and
 * saves to PersistentStorageAgent. It's a singleton so all servlet classes can access the same
 * instance.
 */

public class ProfileStore {

	/** Singleton instance of UserStore. */

  private static ProfileStore instance;

  /**
   * Returns the singleton instance of UserStore that should be shared between all servlet classes.
   * Do not call this function from a test; use getTestInstance() instead.
   */

  public static ProfileStore getInstance() {
    if (instance == null) {
      instance = new ProfileStore(PersistentStorageAgent.getInstance());
    }
    return instance;
  }

  /**
   * The PersistentStorageAgent responsible for loading Profile Pages from and saving Profile Pages to Datastore.
   */
  private PersistentStorageAgent persistentStorageAgent;

  /** The in-memory list of Users. */
  private List<ProfilePage> profilepages;

  /** This class is a singleton, so its constructor is private. Call getInstance() instead. */
  private ProfileStore(PersistentStorageAgent persistentStorageAgent) {
    this.persistentStorageAgent = persistentStorageAgent;
    profilepages = new ArrayList<>();
  }

  /** Load a set of randomly-generated Profile Page objects. */
  public void loadTestData() {
    profilepages.addAll(DefaultDataStore.getInstance().getAllProfiles());
  }

 	public void addProfile(ProfilePage profilepage) {
 		profilepages.add(profilepage);
 		persistentStorageAgent.writeThrough(profilepage);
 	}

 	/**
   * Sets the List of Users stored by this UserStore. This should only be called once, when the data
   * is loaded from Datastore.
   */
  public void setProfilePages(List<ProfilePage> profilepages) {
    this.profilepages = profilepages;
  }

}