package convention.rest.api.tos;

import ar.com.kfgodel.appbyconvention.tos.PersistableToSupport;
import convention.persistent.ActionItem;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;

import java.util.List;

/**
 * Created by fede on 13/07/17.
 */
public class ActionItemTo  extends PersistableToSupport {
    @CopyFromAndTo(ActionItem.descripcion_FIELD)
    private String descripcion;

    @CopyFromAndTo(ActionItem.responsables_FIELD)
    private List<UserTo> responsables;

    @CopyFromAndTo(ActionItem.tema_FIELD)
    private Long Tema;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<UserTo> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<UserTo> responsables) {
        this.responsables = responsables;
    }

    public Long getTema() {
        return Tema;
    }

    public void setTema(Long tema) {
        Tema = tema;
    }
}
