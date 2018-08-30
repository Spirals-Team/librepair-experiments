package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.DbConnector;
import ru.job4j.model.Address;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddressDaoImpl implements AddressDao {

    private static final Logger LOG = LoggerFactory.getLogger(AddressDaoImpl.class);

    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("address");
    private DbConnector conn = DbConnector.getInstance();

    @Override
    public Address findOne(final Long id) {
        Address address = new Address();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.address.by.id"))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    address = extractAddress(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return address;
    }

    private Address extractAddress(final ResultSet rs) throws SQLException {
        return new Address().setId(rs.getLong("id"))
            .setCountry(rs.getString("country"))
            .setCity(rs.getString("city"))
            .setStreetAddress(rs.getString("street_address"));
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.addresses"))) {
            while (rs.next()) {
                addresses.add(extractAddress(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return addresses;
    }

    @Override
    public Long create(final Address address) {
        Long result = -1L;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("insert.address"))) {
            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getCity());
            pstmt.setString(3, address.getStreetAddress());
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
    public boolean update(final Address address) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("update.address.by.id"))) {
            pstmt.setString(1, address.getCountry());
            pstmt.setString(2, address.getCity());
            pstmt.setString(3, address.getStreetAddress());
            pstmt.setLong(4, address.getId());
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
    public boolean delete(final Address address) {
        return deleteById(address.getId());
    }

    @Override
    public boolean deleteById(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.address.by.id"))) {
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
