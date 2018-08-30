package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.DbConnector;
import ru.job4j.dto.CatalogDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CatalogDaoImpl implements CatalogDao {

    private static final Logger LOG = LoggerFactory.getLogger(CatalogDaoImpl.class);

    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("catalog");
    private DbConnector conn = DbConnector.getInstance();

    @Override
    public CatalogDto findOne(final Long id) {
        CatalogDto catalog = new CatalogDto();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.catalog.by.id"))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    catalog = extractCatalogDto(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return catalog;
    }

    public Long findCatalogId(final Long userId, final Long genreId) {
        Long catalogId = -1L;
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.catalog.by.ids"))) {
            pstmt.setLong(1, userId);
            pstmt.setLong(2, genreId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    catalogId = rs.getLong("id");
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return catalogId;
    }

    private CatalogDto extractCatalogDto(final ResultSet rs) throws SQLException {
        return new CatalogDto().setId(rs.getLong("id"))
            .setUserId(rs.getLong("catalog_user"))
            .setGenreId(rs.getLong("catalog_music_type"));
    }

    @Override
    public List<CatalogDto> findAll() {
        List<CatalogDto> catalogs = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.catalogs"))) {
            while (rs.next()) {
                catalogs.add(extractCatalogDto(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return catalogs;
    }

    @Override
    public Long create(final CatalogDto catalog) {
        Long result = -1L;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("insert.catalog"))) {
            pstmt.setLong(1, catalog.getUserId());
            pstmt.setLong(2, catalog.getGenreId());
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
    public boolean update(final CatalogDto catalog) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("update.catalog.by.id"))) {
            pstmt.setLong(1, catalog.getUserId());
            pstmt.setLong(2, catalog.getGenreId());
            pstmt.setLong(3, catalog.getId());
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
    public boolean delete(final CatalogDto catalog) {
        return deleteById(catalog.getId());
    }

    @Override
    public boolean deleteById(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.catalog.by.id"))) {
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

    public boolean deleteAllByUserId(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.catalogs.by.user"))) {
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
