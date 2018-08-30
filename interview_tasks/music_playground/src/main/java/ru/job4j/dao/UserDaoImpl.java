package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.DbConnector;
import ru.job4j.dto.UserDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserDaoImpl implements UserDao {

    private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("users");
    private DbConnector conn = DbConnector.getInstance();

    @Override
    public UserDto findOne(final Long id) {
        UserDto user = new UserDto();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.user.by.id"))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = extractUserDto(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    private UserDto extractUserDto(final ResultSet rs) throws SQLException {
        return new UserDto().setId(rs.getLong("id"))
            .setEmail(rs.getString("email"))
            .setPassw(rs.getString("passw"))
            .setRoleId(rs.getLong("user_role"))
            .setAddressId(rs.getLong("user_address"))
            .setCreateDate(rs.getDate("create_date"));
    }

    @Override
    public List<UserDto> findAll() {
        List<UserDto> users = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.users"))) {
            while (rs.next()) {
                users.add(extractUserDto(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return users;
    }

    @Override
    public Long create(final UserDto user) {
        Long result = -1L;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("insert.user"))) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassw());
            pstmt.setLong(3, user.getRoleId());
            pstmt.setLong(4, user.getAddressId());
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
    public boolean update(final UserDto user) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("update.user.by.id"))) {
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassw());
            pstmt.setLong(3, user.getRoleId());
            pstmt.setLong(4, user.getAddressId());
            pstmt.setDate(5, user.getCreateDate());
            pstmt.setLong(6, user.getId());
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
    public boolean delete(final UserDto user) {
        return deleteById(user.getId());
    }

    @Override
    public boolean deleteById(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.user.by.id"))) {
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
