package ar.com.kfgodel.temas.filters.reuniones;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import convention.persistent.QReunion;
import convention.persistent.Reunion;

import java.time.LocalDate;

/**
 * Este filtro permite la proxima reunion (unica que no esta en el pasado)
 * Created by kfgodel on 21/08/16.
 */
public class ProximaReunion implements TransactionOperation<Nary<Reunion>> {

  public static ProximaReunion create() {
    ProximaReunion filtro = new ProximaReunion();
    return filtro;
  }

  @Override
  public Nary<Reunion> applyWithTransactionOn(TransactionContext transactionContext) {
    HibernateQueryFactory queryFactory = new HibernateQueryFactory(transactionContext.getSession());

    QReunion reunion = QReunion.reunion;

    Reunion proxima = queryFactory.selectFrom(reunion)
      .where(reunion.fecha.goe(LocalDate.now()))
      .orderBy(reunion.fecha.asc())
      .limit(1)
      .fetchOne();
    return Nary.ofNullable(proxima);
  }

}
