package convention.services;

import ar.com.kfgodel.temas.filters.users.FindAllUsersOrderedByName;
import convention.persistent.Usuario;

import java.util.List;

/**
 * Created by sandro on 26/06/17.
 */
public class UsuarioService extends Service<Usuario> {

    public UsuarioService() {
        setClasePrincipal(Usuario.class);
    }

    public List<Usuario> getAll() {

        return getAll(FindAllUsersOrderedByName.create());
    }

    @Override
    public Usuario update(Usuario newState){
      return super.update(newState);
    }
}
