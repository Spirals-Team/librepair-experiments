package ar.com.kfgodel.temas.application.initializers;

import ar.com.kfgodel.appbyconvention.operation.api.ApplicationOperation;
import ar.com.kfgodel.orm.api.TransactionContext;
import ar.com.kfgodel.temas.application.Application;

/**
 * Esta clase representa el inicializador de datos que sabe definir el estado inicial de la base
 * Created by kfgodel on 21/08/16.
 */
public class InicializadorDeDatos {

  private Application application;

  public static InicializadorDeDatos create(Application application) {
    InicializadorDeDatos inicializador = new InicializadorDeDatos();
    inicializador.application = application;
    return inicializador;
  }

  public void inicializar() {
    ApplicationOperation.createFor(application.injector())
      .insideATransaction()
      .apply(this::inicializarBase);
  }

  private Void inicializarBase(TransactionContext transactionContext) {
    return application.getConfiguration().inicializarDatos();

  }

}
