package convention.rest.api.tos;

import ar.com.kfgodel.appbyconvention.tos.PersistableToSupport;
import convention.persistent.Minuta;
import convention.persistent.Usuario;
import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

import java.util.List;

/**
 * Created by sandro on 07/07/17.
 */
public class MinutaTo extends PersistableToSupport {

    @CopyFromAndTo(Minuta.asistentes_FIELD)
    private List<UserTo> asistentes;

    @CopyFromAndTo(Minuta.reunion_FIELD)
    private Long reunionId;

    @CopyFrom(Minuta.fecha_FIELD)
    private String fecha;

    @CopyFrom(Minuta.temas_FIELD)
    private List<TemaDeMinutaTo> temas;

    @CopyFromAndTo(Minuta.minuteador_FIELD)
    private Long minuteadorId;

    @CopyFrom(value = Minuta.minuteador_FIELD + "." + Usuario.name_FIELD, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
    private String minuteador;

    public List<UserTo> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<UserTo> asistentes) {
        this.asistentes = asistentes;
    }

    public Long getReunionId() {
        return reunionId;
    }

    public void setReunionId(Long reunionId) {
        this.reunionId = reunionId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<TemaDeMinutaTo> getTemas() {
        return temas;
    }

    public void setTemas(List<TemaDeMinutaTo> temas) {
        this.temas = temas;
    }

    public String getMinuteador() {
        return minuteador;
    }

    public void setMinuteador(String minuteador) {
        this.minuteador = minuteador;
    }

    public Long getMinuteadorId() {
        return minuteadorId;
    }

    public void setMinuteadorId(Long minuteadorId) {
        this.minuteadorId = minuteadorId;
    }
}
