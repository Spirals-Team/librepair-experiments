package asw.inci_manager.inci_manager_gest.responses;

public class RespuestaAddIncidenceREST {

    private String idIncidencia;        // id de la incidencia
    private String nombreIncidencia;    // nombre de la incidencia
    private String status;              // estado de la incidencia: (se ha añadida o no)

    public RespuestaAddIncidenceREST() {
    }

    public RespuestaAddIncidenceREST(String idIncidencia, String nombreIncidencia, String status) {
        this.idIncidencia = idIncidencia;
        this.nombreIncidencia = nombreIncidencia;
        this.status = status;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(String idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getNombreIncidencia() {
        return nombreIncidencia;
    }

    public void setNombreIncidencia(String nombreIncidencia) {
        this.nombreIncidencia = nombreIncidencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
