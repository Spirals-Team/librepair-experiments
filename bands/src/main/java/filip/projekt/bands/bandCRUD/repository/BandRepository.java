package filip.projekt.bands.bandCRUD.repository;

import filip.projekt.bands.bandCRUD.domain.Band;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public interface BandRepository {
  public Connection getConnection();
  public void setConnection(Connection con ) throws SQLException;
  public List<Band> getAll();
  public int add(Band b);
  public Band getById(int Id) throws SQLException;
  public String getInfo();
  public void createTableBand() throws SQLException;
  public void dropTableBand() throws SQLException;
  public Band selectFromBand(int Id) throws SQLException;
  public int deleteFromBand(Band b);
  public int updateBand(Band b) throws SQLException;
  public int deleteAll();

}
