package io.ebeaninternal.server.type;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Used to map enum values to database integer values.
 */
public class EnumToDbIntegerMap extends EnumToDbValueMap<Integer> {

  @Override
  public int getDbType() {
    return Types.INTEGER;
  }

  /**
   * Construct with allowNulls defaulting to false and integerType=true
   */
  public EnumToDbIntegerMap() {
    this(true);
  }
  /**
   * Construct with  integerType=true
   */
  public EnumToDbIntegerMap(boolean allowNulls) {
    super(allowNulls, true);
  }
  
  public void add(Object beanValue, Integer dbValue, String name) {
    addInternal(beanValue, dbValue, name);
  }

  @Override
  public EnumToDbIntegerMap add(Object beanValue, String stringDbValue, String name) {

    try {
      Integer value = Integer.valueOf(stringDbValue);
      addInternal(beanValue, value, name);

      return this;

    } catch (Exception e) {
      String msg = "Error converted enum type[" + beanValue.getClass().getName();
      msg += "] enum value[" + beanValue + "] string value [" + stringDbValue + "]";
      msg += " to an Integer.";
      throw new PersistenceException(msg, e);
    }
  }

  @Override
  public void bind(DataBind b, Object value) throws SQLException {
    if (value == null) {
      b.setNull(Types.INTEGER);
    } else {
      Integer s = getDbValue(value);
      b.setInt(s);
    }

  }

  @Override
  public Object read(DataReader dataReader) throws SQLException {
    Integer i = dataReader.getInt();
    if (i == null) {
      return null;
    } else {
      return getBeanValue(i);
    }
  }

}
