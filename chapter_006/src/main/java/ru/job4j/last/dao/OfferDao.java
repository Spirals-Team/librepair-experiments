package ru.job4j.last.dao;

import org.apache.log4j.Logger;
import ru.job4j.last.connection.IConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 */
public class OfferDao implements IOfferDao {
	private static final Logger LOG = Logger.getLogger(OfferDao.class);
	private IConnection typeConn;

	public OfferDao(IConnection typeConn) {
		this.typeConn = typeConn;
	}

	@Override
	public void add(List<Offer> offers) {
		String query = "INSERT INTO offers (id, head, description, create_offer) "
				+ "VALUES (?, ?, ?, ?);";
		try (Connection conn = typeConn.connect()) {
			for (Offer offer : offers) {
				try (PreparedStatement st = conn.prepareStatement(query)) {
					String id = offer.getId();
					st.setString(1, id);
					st.setString(2, offer.getHead());
					st.setString(3, offer.getDescrition());
					st.setLong(4, offer.getCreate());
					st.executeUpdate();
					LOG.info(String.format("A new offer has been added: %s", id));
				} catch (SQLException e) {
					updateDate(offer);
					LOG.info("A date has chanched in an existing offer");
				}
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public void delete(String id) {
		String query = "DELETE FROM offers WHERE id = ?;";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setString(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	@Override
	public List<Offer> getOffers() {
		List<Offer> offers = new ArrayList<>();
		String query = "SELECT * FROM offers;";
		try (Connection conn = typeConn.connect();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query)) {
			while (rs.next()) {
				Offer offer = new Offer(
						rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getLong(4)
				);
				offers.add(offer);
			}
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return offers;
	}

	@Override
	public long getTheNewestDate() {
		long oldest = 0;
		String query = "SELECT max(create_offer) FROM offers;";
		try (Connection conn = typeConn.connect();
			 Statement st = conn.createStatement();
			 ResultSet rs = st.executeQuery(query)) {
			oldest = rs.getLong(1);
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
		return oldest;
	}

	private void updateDate(Offer offer) {
		String query = "UPDATE offers SET create_offer = ? WHERE id = ?;";
		try (Connection conn = typeConn.connect();
			 PreparedStatement st = conn.prepareStatement(query)) {
			st.setLong(1, offer.getCreate());
			st.setString(2, offer.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
