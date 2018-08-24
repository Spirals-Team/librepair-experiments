package ar.com.kfgodel.temas.apiRest;

import ar.com.kfgodel.webbyconvention.impl.auth.adapters.JettyIdentityAdapter;

/**
 * Created by fede on 22/06/17.
 */
public class JettyIdentityAdapterTest extends JettyIdentityAdapter {
    protected Long principal;
    @Override
    public <T> T getApplicationIdentification(){
        return (T) principal;
    }
    public JettyIdentityAdapterTest(Long principal){
        this.principal=principal;
    }
}
