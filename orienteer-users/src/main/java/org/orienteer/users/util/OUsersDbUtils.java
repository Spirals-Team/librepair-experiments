package org.orienteer.users.util;

import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.metadata.security.ORole;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import org.orienteer.core.OrienteerWebApplication;
import org.orienteer.core.module.PerspectivesModule;
import org.orienteer.users.model.OrienteerUser;
import org.orienteer.users.module.OrienteerUsersModule;
import ru.ydn.wicket.wicketorientdb.utils.DBClosure;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.orienteer.core.util.CommonUtils.getDocument;
import static org.orienteer.core.util.CommonUtils.getFromIdentifiables;

/**
 * Specialized utils for DB
 */
public final class OUsersDbUtils {
	
	private OUsersDbUtils() {}

    /**
     * @return {@link Optional<ODocument>} which contains perspective {@link OrienteerUsersModule#ORIENTEER_USER_PERSPECTIVE}
     */
    public static Optional<ODocument> getDefaultOrienteerUserPerspective() {
        return DBClosure.sudo(db -> {
            String sql = String.format("select from %s where name.en = ?", PerspectivesModule.OCLASS_PERSPECTIVE);
            String name = OUsersCommonUtils.getString(OrienteerWebApplication.lookupApplication(), OrienteerUsersModule.ORIENTEER_USER_PERSPECTIVE, Locale.ENGLISH);
            List<OIdentifiable> identifiables = db.query(new OSQLSynchQuery<>(sql, 1), name);
            return getDocument(identifiables);
        });
    }

    /**
     * @param name {@link String} role name
     * @return {@link Optional<ORole>} role
     */
    public static Optional<ORole> getRoleByName(String name) {
        return DBClosure.sudo(db -> ofNullable(db.getMetadata().getSecurity().getRole(name)));
    }

    /**
     * Search user by given restore id
     * @param restoreId {@link String} restore id
     * @return {@link Optional< OrienteerUser >} user with given restore id
     */
    public static Optional<OrienteerUser> getUserByRestoreId(String restoreId) {
	    return getUserBy(OrienteerUser.PROP_RESTORE_ID, restoreId);
    }

    /**
     * Search user by given id
     * @param id {@link String} user id
     * @return {@link Optional<OrienteerUser>} user with given id
     */
    public static Optional<OrienteerUser> getUserById(String id) {
	    return getUserBy(OrienteerUser.PROP_ID, id);
    }

    /**
     * Check if user exists with given restore id
     * @param restoreId {@link String} user restore id
     * @return true if user with given restoreId exists in database
     */
    public static boolean isUserExistsWithRestoreId(String restoreId) {
	    return isUserExistsBy(OrienteerUser.PROP_RESTORE_ID, restoreId);
    }

    /**
     * Check if user exists with given email
     * @param email {@link String} user email
     * @return true if user exists with given email
     */
    public static boolean isUserExistsWithEmail(String email) {
        return isUserExistsBy(OrienteerUser.PROP_EMAIL, email);
    }

    /**
     * Check if user exists with given id
     * @param id {@link String} user id
     * @return true if user exists with given id
     */
    public static boolean isUserExistsWithId(String id) {
        return isUserExistsBy(OrienteerUser.PROP_ID, id);
    }

    /**
     * Search user by given field and value
     * @param field {@link String} field
     * @param value {@link String} value
     * @return {@link Optional<OrienteerUser>} user which field contains given value
     */
    private static Optional<OrienteerUser> getUserBy(String field, String value) {
        return DBClosure.sudo(db -> {
            String sql = String.format("select from %s where %s = ?", OrienteerUser.CLASS_NAME, field);
            List<OIdentifiable> identifiables  = db.query(new OSQLSynchQuery<>(sql, 1), value);
            return getFromIdentifiables(identifiables, OrienteerUser::new);
        });
    }

    /**
     * Check if user exists with given field and value
     * @param field {@link String} field
     * @param value {@link String} value
     * @return true if user with given field and value exists in database
     */
    private static boolean isUserExistsBy(String field, String value) {
        return DBClosure.sudo(db -> {
            String sql = String.format("select count(*) from %s where %s = ?", OrienteerUser.CLASS_NAME, field);
            List<OIdentifiable> documents = db.query(new OSQLSynchQuery<>(sql, 1), value);
            return getDocument(documents)
                    .map(d -> (long) d.field("count") == 1)
                    .orElse(false);
        });
    }
}
