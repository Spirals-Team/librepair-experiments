package ru.job4j.interview;

import org.apache.log4j.Logger;

import java.sql.*;

/**
 * VacancySaver.
 *
 * @version $Id$
 * @since 0.1
 */
public class VacancySaver {

    private static final Logger LOG = Logger.getLogger(VacancySaver.class);

    private Connector conn = new Connector();
    private Config conf = new Config();

    /**
     * Add vacancy to store
     *
     * @param vacancy vacancy
     */
    public void add(final Vacancy vacancy) {
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(conf.getQuery("insertVacancy"))) {
            pstmt.setInt(1, vacancy.getId());
            pstmt.setString(2, vacancy.getTitle());
            pstmt.setString(3, vacancy.getLink());
            pstmt.setString(4, vacancy.getDescription());
            pstmt.setTimestamp(5, vacancy.getLastUpdate());
            pstmt.executeUpdate();
            conn.connect().commit();
            LOG.info(String.format("Add vacancy %s", vacancy.toString()));
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
    }

    /**
     * Find vacancy by id
     *
     * @param id id
     * @return vacancy
     */
    public Vacancy findById(final int id) {
        Vacancy vacancy = new Vacancy();
        try (PreparedStatement prstm =
                 conn.connect().prepareStatement(conf.getQuery("findByID"))) {
            prstm.setInt(1, id);
            try (ResultSet rs = prstm.executeQuery()) {
                while (rs.next()) {
                    vacancy.setId(rs.getInt("id"));
                    vacancy.setTitle(rs.getString("title"));
                    vacancy.setLink(rs.getString("link"));
                    vacancy.setDescription(rs.getString("description"));
                    vacancy.setLastUpdate(rs.getTimestamp("last_update"));
                }
                LOG.info(String.format("Select vacancy by id { %s }.", vacancy.toString()));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            conn.disconnect();
        }
        return vacancy;
    }

    /**
     * Check if vacancy already present in DB
     *
     * @param id vacancy id
     * @return {@code true} or {@code false} as result
     */
    public boolean isVacancyPresent(final int id) {
        LOG.info(String.format("Find vacancy with id: %d", id));
        Vacancy vacancy = findById(id);
        return !vacancy.isEmpty();
    }

    /**
     * Find recent create date
     *
     * @return create date
     */
    public Timestamp lastUpdateDate() {
        Timestamp timestamp = null;
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(conf.getQuery("lastUpdateDate"))) {
            while (rs.next()) {
                timestamp = rs.getTimestamp(1);
            }
            LOG.info(String.format("Get last create date { %s }.", timestamp));
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            conn.disconnect();
        }
        return timestamp;
    }
}