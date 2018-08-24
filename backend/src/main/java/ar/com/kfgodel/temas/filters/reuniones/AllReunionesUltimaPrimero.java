package ar.com.kfgodel.temas.filters.reuniones;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.orm.api.SessionContext;
import ar.com.kfgodel.orm.api.operations.SessionOperation;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import convention.persistent.QReunion;
import convention.persistent.Reunion;

import java.util.List;

/**
 * Este filtro permite buscar todas las reuniones ordenando por más nueva primero
 * Created by kfgodel on 21/08/16.
 */
public class AllReunionesUltimaPrimero implements SessionOperation<Nary<Reunion>> {

  public static AllReunionesUltimaPrimero create() {
    AllReunionesUltimaPrimero filtro = new AllReunionesUltimaPrimero();
    return filtro;
  }

  @Override
  public Nary<Reunion> applyWithSessionOn(SessionContext sessionContext) {
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(sessionContext.getSession());

    QReunion reunion = QReunion.reunion;

    List<Reunion> reuniones = queryFactory.selectFrom(reunion)
      .orderBy(reunion.fecha.desc())
      .fetch();
    return Nary.create(reuniones);
  }

}
