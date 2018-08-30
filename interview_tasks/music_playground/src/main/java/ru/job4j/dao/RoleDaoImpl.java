package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.DbConnector;
import ru.job4j.model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoleDaoImpl implements RoleDao {

    private static final Logger LOG = LoggerFactory.getLogger(RoleDaoImpl.class);

    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("role");
    private DbConnector conn = DbConnector.getInstance();

    @Override
    public Role findOne(final Long id) {
        Role role = new Role();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.role.by.id"))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    role = extractRole(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return role;
    }

    public Role findOne(final String type) {
        Role role = new Role();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.role.by.role"))) {
            pstmt.setString(1, type);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    role = extractRole(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return role;
    }

    private Role extractRole(final ResultSet rs) throws SQLException {
        return new Role().setId(rs.getLong("id"))
            .setType(rs.getString("role"));
    }

    @Override
    public List<Role> findAll() {
        List<Role> roles = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.roles"))) {
            while (rs.next()) {
                roles.add(extractRole(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return roles;
    }

    @Override
    public Long create(final Role role) {
        Long result = -1L;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("insert.role"))) {
            pstmt.setString(1, role.getType());
            int rowCount = pstmt.executeUpdate();
            conn.connect().commit();
            if (rowCount == 1) {
                result = findLastId();
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            conn.disconnect();
        }
        return result;
    }

    @Override
    public boolean update(final Role role) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("update.role.by.id"))) {
            pstmt.setString(1, role.getType());
            pstmt.setLong(2, role.getId());
            int rowCount = pstmt.executeUpdate();
            conn.connect().commit();
            if (rowCount == 1) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            conn.disconnect();
        }
        return result;
    }

    @Override
    public boolean delete(final Role role) {
        return deleteById(role.getId());
    }

    @Override
    public boolean deleteById(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.role.by.id"))) {
            pstmt.setLong(1, id);
            int rowCounter = pstmt.executeUpdate();
            conn.connect().commit();
            if (rowCounter == 1) {
                result = true;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            conn.disconnect();
        }
        return result;
    }

    @Override
    public Long findLastId() {
        Long id = -1L;
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.last.id"))) {
            while (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return id;
    }
}
