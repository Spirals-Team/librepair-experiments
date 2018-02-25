package io.ebeaninternal.server.deploy;

import io.ebeaninternal.server.core.InternString;
import io.ebeaninternal.server.deploy.meta.DeployBeanTable;
import io.ebeaninternal.server.deploy.meta.DeployTableJoin;
import io.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Used for associated beans in place of a BeanDescriptor. This is done to avoid
 * recursion issues due to the potentially bi-directional and circular
 * relationships between beans.
 * <p>
 * It holds the main deployment information and not all the detail that is held
 * in a BeanDescriptor.
 * </p>
 */
public class BeanTable {

  private static final Logger logger = LoggerFactory.getLogger(BeanTable.class);

  private final BeanDescriptorMap owner;

  private final Class<?> beanType;

  /**
   * The base table.
   */
  private final String baseTable;

  private final BeanProperty[] idProperties;

  /**
   * Create the BeanTable.
   */
  public BeanTable(DeployBeanTable mutable, BeanDescriptorMap owner) {
    this.owner = owner;
    this.beanType = mutable.getBeanType();
    this.baseTable = InternString.intern(mutable.getBaseTable());
    this.idProperties = mutable.createIdProperties(owner);
  }

  @Override
  public String toString() {
    return baseTable;
  }

  /**
   * Return the base table for this BeanTable.
   * This is used to determine the join information
   * for associations.
   */
  public String getBaseTable() {
    return baseTable;
  }

  /**
   * Gets the unqualified base table.
   *
   * @return the unqualified base table
   */
  public String getUnqualifiedBaseTable() {
    final String[] chunks = baseTable.split("\\.");
    return chunks.length == 2 ? chunks[1] : chunks[0];
  }

  /**
   * Return the Id properties.
   */
  public BeanProperty[] getIdProperties() {
    return idProperties;
  }

  /**
   * Return the class for this beanTable.
   */
  public Class<?> getBeanType() {
    return beanType;
  }

  public void createJoinColumn(String foreignKeyPrefix, DeployTableJoin join, boolean reverse, String sqlFormulaSelect) {

    boolean complexKey = false;
    BeanProperty[] props = idProperties;

    if (idProperties.length == 1) {
      if (idProperties[0] instanceof BeanPropertyAssocOne<?>) {
        BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne<?>) idProperties[0];
        props = assocOne.getProperties();
        complexKey = true;
      }
    }

    for (BeanProperty prop : props) {

      String lc = prop.getDbColumn();
      String fk = lc;
      if (foreignKeyPrefix != null) {
        fk = owner.getNamingConvention().getForeignKey(foreignKeyPrefix, fk);
      }

      if (complexKey) {
        // just to copy the column name rather than prefix with the foreignKeyPrefix.
        // I think that with complex keys this is the more common approach.
        logger.debug("On table[{}] foreign key column [{}]", baseTable, lc);
        fk = lc;
      }
      if (sqlFormulaSelect != null) {
        fk = sqlFormulaSelect;
      }

      DeployTableJoinColumn joinCol = new DeployTableJoinColumn(lc, fk);
      joinCol.setForeignSqlFormula(sqlFormulaSelect);
      if (reverse) {
        joinCol = joinCol.reverse();
      }
      join.addJoinColumn(joinCol);
    }

  }

  /**
   * Return the primary key DB column.
   */
  public String getIdColumn() {
    if (idProperties.length != 1) {
      throw new IllegalStateException("Expecting only one Id column to join to on "+beanType);
    }
    return idProperties[0].dbColumn;
  }
}
