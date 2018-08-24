package convention.persistent;

/**
 * Este enum representa uno de los estados posibles de un reunion de roots
 * Created by kfgodel on 10/10/16.
 */
public enum StatusDeReunion {
  /**
   * Estado en el cual todavía se pueden agregar temas y votar
   */
  PENDIENTE,
  /**
   * Estado post cierre de votacion para ser realizada
   */
  CERRADA,

  CON_MINUTA,


}
