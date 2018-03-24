package filip.projekt.bands.bandCRUD.repository;

import filip.projekt.bands.bandCRUD.domain.Band;
import java.util.*;
public interface BandRepository {
  public void initDB();
  public List<Band> getAll();
  public void add(Band b);
  public Band getBy_Id(int _Id);
  public String getInfo();
  public void createTableBand();
  public void dropTableBand();
  public Band selectFromBand(int _Id);
  public void deleteFromBand(int _Id);
  public void insertToBand(Band b);
  public void updateById(int _Id,Band b);

}
