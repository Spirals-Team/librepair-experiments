package ru.job4j.jdbc.tracker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracker.
 *
 * @version $Id$
 * @since 0.1
 */
public class Tracker {

    private static final Logger LOG = LoggerFactory.getLogger(Tracker.class);

    private Connector conn = new Connector();
    private Config conf = new Config();

    /**
     * Add item to store
     *
     * @param item item
     * @return added item
     */
    public Item add(final Item item) {
        int id = -1;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(conf.getQuery("insertItem"))) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.executeUpdate();
            conn.connect().commit();
            id = lastRowId();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.conn.connect();
        }
        Item insertedItem = findById(id);
        LOG.info("Add item {}", insertedItem);
        return insertedItem;
    }

    /**
     * Update item
     *
     * @param id   item's id
     * @param item item
     */
    public void replace(int id, final Item item) {
        try (PreparedStatement prstmt =
                 conn.connect().prepareStatement(conf.getQuery("replaceByID"))) {
            prstmt.setString(1, item.getName());
            prstmt.setString(2, item.getDescription());
            prstmt.setLong(3, item.getCreatedDate());
            prstmt.setLong(4, id);
            prstmt.executeUpdate();
            conn.connect().commit();
            LOG.info("Update item with id { {} }", id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.conn.connect();
        }
        Item updatedItem = findById(id);
        LOG.info("New values item {}", updatedItem);
    }

    /**
     * Remove item from DB
     *
     * @param id item's id
     */
    public void delete(final int id) {
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(conf.getQuery("deleteByID"))) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.connect().commit();
            LOG.info("Delete item with id {}", id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
            try {
                conn.connect().rollback();
            } catch (SQLException e1) {
                LOG.error(e1.getMessage(), e1);
            }
        } finally {
            this.conn.disconnect();
        }
    }

    /**
     * Get all items from DB
     *
     * @return items sorted ASC
     */
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(conf.getQuery("findAll"))) {
            items = listItems(rs);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            this.conn.disconnect();
        }
        return items;
    }

    /**
     * Get items by key(name)
     *
     * @param key name
     * @return items sorted ASC
     */
    public List<Item> findByName(final String key) {
        List<Item> items = new ArrayList<>();
        try (PreparedStatement prstm =
                 conn.connect().prepareStatement(conf.getQuery("findByName"))) {
            prstm.setString(1, key);
            try (ResultSet rs = prstm.executeQuery()) {
                items = listItems(rs);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            this.conn.disconnect();
        }
        return items;
    }

    private List<Item> listItems(final ResultSet rs) throws SQLException {
        List<Item> items = new ArrayList<>();
        while (rs.next()) {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setDescription(rs.getString("description"));
            item.setCreatedDate(rs.getLong("created_date"));
            items.add(item);
            LOG.info("Select item {}.", item);
        }
        return items;
    }

    /**
     * Get item by ID
     *
     * @param id item's id
     * @return item
     */
    public Item findById(int id) {
        Item item = new Item();
        try (PreparedStatement prstm =
                 conn.connect().prepareStatement(conf.getQuery("findByID"))) {
            prstm.setInt(1, id);
            try (ResultSet rs = prstm.executeQuery()) {
                while (rs.next()) {
                    item.setId(rs.getInt("id"));
                    item.setName(rs.getString("name"));
                    item.setDescription(rs.getString("description"));
                    item.setCreatedDate(rs.getLong("created_date"));
                }
            }
            LOG.info("Select item by id {}.", item);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            this.conn.disconnect();
        }
        return item;
    }

    private int lastRowId() {
        int id = -1;
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(conf.getQuery("lastRowID"))) {
            while (rs.next()) {
                id = rs.getInt(1);
            }
            LOG.info("Get last row id {}.", id);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return id;
    }
}