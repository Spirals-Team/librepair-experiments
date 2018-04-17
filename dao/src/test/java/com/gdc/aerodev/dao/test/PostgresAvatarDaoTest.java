package com.gdc.aerodev.dao.test;

import com.gdc.aerodev.dao.exception.DaoException;
import com.gdc.aerodev.dao.postgres.PostgresAvatarDao;
import com.gdc.aerodev.model.Avatar;
import com.opentable.db.postgres.embedded.FlywayPreparer;
import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.PreparedDbRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PostgresAvatarDaoTest {

    private final String tableName = "avatar_test";
    private Long id = 1L;
    private Long owner = 5L;
    private final String PATH_TO_RESOURCES = "D:/Projects/aero-dev/dao/src/test/resources/";

    @Rule
    public PreparedDbRule db = EmbeddedPostgresRules.preparedDatabase(FlywayPreparer.forClasspathLocation("avatar"));

    @Test
    public void testInsertImage() throws IOException {
        PostgresAvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        assertTrue(avatar.getAvatarData().length > 0);
        assertEquals(id, dao.save(avatar));
    }

    @Test (expected = DaoException.class)
    public void testInsertNullImage() {
        PostgresAvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, null, "image");
        dao.save(avatar);
    }

    @Test
    public void testGetImage() throws IOException {
        PostgresAvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        Long id = dao.save(avatar);
        Avatar received = dao.getById(owner);
        assertEquals(avatar.getAvatarData().length, received.getAvatarData().length);
        assertEquals(id, received.getAvatarId());
    }

    @Test
    public void testGetNonExistentImage(){
        PostgresAvatarDao dao = getDao();
        assertNull(dao.getById(owner));
    }

    @Test
    public void testUpdateImage() throws IOException {
        Long newOwner = 77L;
        PostgresAvatarDao dao = getDao();
        Avatar avatar = new Avatar(owner, getImage(), "image");
        dao.save(avatar);
        Avatar update = dao.getById(owner);
        update.setAvatarOwner(newOwner);
        assertEquals(id, dao.save(update));
        assertEquals(newOwner, dao.getById(newOwner).getAvatarOwner());
    }

    private PostgresAvatarDao getDao() {
        return new PostgresAvatarDao(new JdbcTemplate(db.getTestDatabase()), tableName);
    }

    private byte[] getImage() throws IOException {
        try (
                BufferedInputStream in =
                        new BufferedInputStream(
                                new FileInputStream(PATH_TO_RESOURCES + "java.png")
                        );
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int a;
            while ((a = in.read()) != -1) {
                out.write(a);
            }
            return out.toByteArray();
        }
    }
}
