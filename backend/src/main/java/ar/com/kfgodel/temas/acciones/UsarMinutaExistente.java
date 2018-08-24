package ar.com.kfgodel.temas.acciones;

import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.orm.api.operations.TransactionOperation;
import convention.persistent.Minuta;

public class UsarMinutaExistente implements TransactionOperation<Minuta> {
  private Minuta minuta;

  public static TransactionOperation<Minuta> create(Minuta minuta) {
    UsarMinutaExistente accion = new UsarMinutaExistente();
    accion.minuta = minuta;
    return accion;
  }

  @Override
  public Minuta applyWithTransactionOn(TransactionContext transactionContext) {
    return minuta;
  }

}
