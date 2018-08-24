package ar.com.kfgodel.temas.filters.users;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.orm.api.SessionContext;
import ar.com.kfgodel.orm.api.operations.SessionOperation;
import com.querydsl.core.NonUniqueResultException;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import convention.persistent.QUsuario;
import convention.persistent.Usuario;

/**
 * This type represents a filter that looks for a single user with give backofficeId.
 * Created by ijpiantanida on 02/12/16.
 */
public class UserByBackofficeId implements SessionOperation<Nary<Usuario>> {

  private String backofficeId;

  public static UserByBackofficeId create(String backofficeId) {
    UserByBackofficeId filter = new UserByBackofficeId();
    filter.backofficeId = backofficeId;
    return filter;
  }

  @Override
  public Nary<Usuario> applyWithSessionOn(SessionContext sessionContext) {
    QUsuario usuario = QUsuario.usuario;

    try {
      Usuario foundUsuario = new HibernateQueryFactory(sessionContext.getSession())
          .selectFrom(usuario)
          .where(usuario.backofficeId.eq(backofficeId))
          .fetchOne();
      return Nary.ofNullable(foundUsuario);
    } catch (NonUniqueResultException e) {
      throw new IllegalStateException("There's more than one user with same backofficeId? " + backofficeId);
    }
  }
}
