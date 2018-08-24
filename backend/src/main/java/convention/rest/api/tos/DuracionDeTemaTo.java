package convention.rest.api.tos;

import convention.persistent.DuracionDeTema;
import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;

/**
 * Created by fede on 23/06/17.
 */
public class DuracionDeTemaTo {

    @CopyFromAndTo(DuracionDeTema.descripcion_FIELD)
    private String descripcion;

    @CopyFrom(DuracionDeTema.cantidadDeMinutos_FIELD)
    private int cantidadDeMinutos;

    @CopyFromAndTo(DuracionDeTema.nombre_GETTER)
    private String nombre;

    public int getCantidadDeMinutos() {
        return cantidadDeMinutos;
    }

    public void setCantidadDeMinutos(int cantidadDeMinutos) {
        this.cantidadDeMinutos = cantidadDeMinutos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
