package convention.persistent;

/**
 * Created by sandro on 27/06/17.
 */
public enum ObligatoriedadDeTema {

    NO_OBLIGATORIO(true, 3),
    OBLIGATORIO(false, 2),
    OBLIGATORIO_GENERAL(false, 1);

    private Boolean permiteRecibirVotos;
    private Integer nivelDePrioridad;

    ObligatoriedadDeTema(Boolean recibeVotos, Integer unNivelDePrioridad){
        this.permiteRecibirVotos = recibeVotos;
        this.nivelDePrioridad = unNivelDePrioridad;
    }
    public boolean permiteRecibirVotos() {
        return permiteRecibirVotos;
    }

    public Integer prioridad() {
        return nivelDePrioridad;
    }
}
