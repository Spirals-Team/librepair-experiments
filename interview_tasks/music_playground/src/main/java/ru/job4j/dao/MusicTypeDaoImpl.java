package ru.job4j.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.DbConnector;
import ru.job4j.model.MusicType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MusicTypeDaoImpl implements MusicTypeDao {

    private static final Logger LOG = LoggerFactory.getLogger(MusicTypeDaoImpl.class);

    private static final ResourceBundle RB_SQL = ResourceBundle.getBundle("musictype");
    private DbConnector conn = DbConnector.getInstance();

    @Override
    public MusicType findOne(final Long id) {
        MusicType genre = new MusicType();
        try (PreparedStatement pstmt = conn.connect().prepareStatement(
            RB_SQL.getString("select.genre.by.id"))) {
            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    genre = extractMusicType(rs);
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return genre;
    }

    private MusicType extractMusicType(final ResultSet rs) throws SQLException {
        return new MusicType().setId(rs.getLong("id"))
            .setGenre(rs.getString("genre"));
    }

    @Override
    public List<MusicType> findAll() {
        List<MusicType> genres = new ArrayList<>();
        try (Statement st = conn.connect().createStatement();
             ResultSet rs = st.executeQuery(RB_SQL.getString("select.all.genres"))) {
            while (rs.next()) {
                genres.add(extractMusicType(rs));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return genres;
    }

    @Override
    public Long create(final MusicType musicType) {
        Long result = -1L;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("insert.genre"))) {
            pstmt.setString(1, musicType.getGenre());
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
    public boolean update(final MusicType musicType) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("update.genre.by.id"))) {
            pstmt.setString(1, musicType.getGenre());
            pstmt.setLong(2, musicType.getId());
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
    public boolean delete(final MusicType genre) {
        return deleteById(genre.getId());
    }

    @Override
    public boolean deleteById(final Long id) {
        boolean result = false;
        try (PreparedStatement pstmt =
                 conn.connect().prepareStatement(RB_SQL.getString("delete.genre.by.id"))) {
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
