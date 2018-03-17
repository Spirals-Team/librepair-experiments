package org.w4tracking.models.jpa;

import org.w4tracking.models.CompanyModel;
import org.w4tracking.models.CompanyProvider;
import org.w4tracking.models.jpa.entities.CompanyEntity;
import org.w4tracking.models.utils.ModelUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class JpaCompanyProvider implements CompanyProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    public CompanyModel addCompany(String name) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(ModelUtils.generateId());
        entity.setName(name);
        em.persist(entity);
        return new CompanyAdapter(em, entity);
    }

}
