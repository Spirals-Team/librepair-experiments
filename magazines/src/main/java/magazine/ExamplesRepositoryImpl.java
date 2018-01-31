package magazine;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/** @author danis.tazeev@gmail.com */
@Repository
final class ExamplesRepositoryImpl implements ExamplesRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void exampleInKeyword() {
        System.out.println("em = " + em);
        List result = em.createQuery("SELECT DISTINCT art.author FROM Magazine mag, IN(mag.articles) art")
                .getResultList();
        System.out.println(result);
    }
}
