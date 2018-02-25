package io.ebeaninternal.server.persist.dmlbind;

import io.ebean.bean.EntityBean;
import io.ebeaninternal.server.core.PersistRequestBean;
import io.ebeaninternal.server.deploy.BeanProperty;
import io.ebeaninternal.server.persist.dml.GenerateDmlRequest;

import java.sql.SQLException;
import java.util.List;

/**
 * Bindable for a Version BeanProperty. Obtains value from 'old values'.
 */
public class BindablePropertyVersion implements Bindable {

  protected final BeanProperty prop;

  public BindablePropertyVersion(BeanProperty prop) {
    this.prop = prop;
  }

  @Override
  public String toString() {
    return prop.toString();
  }

  @Override
  public boolean isDraftOnly() {
    return prop.isDraftOnly();
  }

  @Override
  public void addToUpdate(PersistRequestBean<?> request, List<Bindable> list) {
    if (request.isAddToUpdate(prop)) {
      list.add(this);
    }
  }

  @Override
  public void dmlAppend(GenerateDmlRequest request) {
    request.appendColumn(prop.getDbColumn());
  }

  /**
   * Normal binding of a property value from the bean.
   */
  @Override
  public void dmlBind(BindableRequest request, EntityBean bean) throws SQLException {

    // get prior version value from 'old values'
    Object value = bean._ebean_getIntercept().getOrigValue(prop.getPropertyIndex());
    request.bind(value, prop);
  }
}
