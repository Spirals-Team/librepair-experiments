package org.w4tracking.models.jpa;

import org.w4tracking.models.UserModel;
import org.w4tracking.models.UserProvider;
import org.w4tracking.models.jpa.entities.UserEntity;
import org.w4tracking.models.utils.ModelUtils;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
public class JpaUserProvider implements UserProvider {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserModel addUser(String username, String identityId, String identityProvider) {
        UserEntity entity = new UserEntity();
        entity.setId(ModelUtils.generateId());
        entity.setUsername(username);
        entity.setIdentityId(identityId);
        entity.setIdentityProvider(identityProvider);
        em.persist(entity);
        return new UserAdapter(em, entity);
    }

    @Override
    public Optional<UserModel> getUser(String id) {
        UserEntity userEntity = em.find(UserEntity.class, id);
        if (userEntity == null) return Optional.empty();
        return Optional.of(new UserAdapter(em, userEntity));
    }

    @Override
    public Optional<UserModel> getUserByIdentityId(String identityId) {
        TypedQuery<UserEntity> query = em.createNamedQuery("GetUserByIdentityId", UserEntity.class);
        query.setParameter("identityId", identityId);
        List<UserEntity> resultList = query.getResultList();
        if (resultList.size() == 1) {
            return Optional.of(new UserAdapter(em, resultList.get(0)));
        } else if (resultList.size() == 0) {
            return Optional.empty();
        } else {
            throw new IllegalStateException("Found more than one user with equal identityId:" + identityId);
        }
    }
}
