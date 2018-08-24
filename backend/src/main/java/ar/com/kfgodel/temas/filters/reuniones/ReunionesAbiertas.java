package ar.com.kfgodel.temas.filters.reuniones;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.orm.api.SessionContext;
import ar.com.kfgodel.orm.api.operations.SessionOperation;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import convention.persistent.QReunion;
import convention.persistent.Reunion;
import convention.persistent.StatusDeReunion;

import java.util.List;

/**
 * Created by sandro on 04/07/17.
 */
public class ReunionesAbiertas implements SessionOperation<Nary<Reunion>> {

    public static ReunionesAbiertas create() {
        ReunionesAbiertas filtro = new ReunionesAbiertas();
        return filtro;
    }

    @Override
    public Nary<Reunion> applyWithSessionOn(SessionContext sessionContext) {
        HibernateQueryFactory queryFactory = new HibernateQueryFactory(sessionContext.getSession());

        QReunion reunion = QReunion.reunion;

        List<Reunion> reuniones = queryFactory.selectFrom(reunion)
                .where(reunion.status.eq(StatusDeReunion.PENDIENTE))
                .fetch();
        return Nary.create(reuniones);
    }
}
