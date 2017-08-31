package org.hswebframework.web.service.script.simple;

import org.hswebframework.web.dao.script.ScriptDao;
import org.hswebframework.web.entity.script.ScriptEntity;
import org.hswebframework.web.service.GenericEntityService;
import org.hswebframework.web.id.IDGenerator;
import org.hswebframework.web.service.script.ScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 默认的服务实现
 *
 * @author hsweb-generator-online
 */
@Service("scriptService")
public class SimpleScriptService extends GenericEntityService<ScriptEntity, String>
        implements ScriptService {
    @Autowired
    private ScriptDao scriptDao;

    @Override
    protected IDGenerator<String> getIDGenerator() {
        return IDGenerator.MD5;
    }

    @Override
    public ScriptDao getDao() {
        return scriptDao;
    }

}
