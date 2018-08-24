package convention.rest.api.tos;

import ar.com.kfgodel.appbyconvention.tos.PersistableToSupport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import convention.persistent.TemaGeneral;
import convention.persistent.Usuario;
import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Created by sandro on 05/07/17.
 */
@JsonIgnoreProperties({"usuarioActual"})
public class TemaGeneralTo extends PersistableToSupport {

    @CopyFromAndTo(TemaGeneral.duracion_FIELD)
    private String duracion;

    @CopyFromAndTo(TemaGeneral.autor_FIELD)
    private Long idDeAutor;

    @CopyFrom(value = TemaGeneral.autor_FIELD + "." + Usuario.name_FIELD, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
    private String autor;

    @CopyFromAndTo(TemaGeneral.titulo_FIELD)
    private String titulo;

    @CopyFromAndTo(TemaGeneral.descripcion_FIELD)
    private String descripcion;

    @CopyFromAndTo(value= TemaGeneral.ultimoModificador_FIELD + "." +Usuario.name_FIELD, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
    private String ultimoModificador;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Long getIdDeAutor() {
        return idDeAutor;
    }

    public void setIdDeAutor(Long idDeAutor) {
        this.idDeAutor = idDeAutor;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getUltimoModificador() {
        return ultimoModificador;
    }

    public void setUltimoModificador(String ultimoModificador) {
        this.ultimoModificador = ultimoModificador;
    }
}
