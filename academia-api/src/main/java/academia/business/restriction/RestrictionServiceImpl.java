package academia.business.restriction;

import academia.model.Restriction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class RestrictionServiceImpl implements RestrictionService {

    @Autowired
    private RestrictionRepository restrictionRepository;

    private Restriction save(Restriction restriction) {
        validate(restriction);

        Restriction toSave = new Restriction();
        toSave.setId(restriction.getId());
        toSave.setName(restriction.getName());

        return restrictionRepository.save(toSave);
    }

    @Override
    public Restriction create(Restriction restriction) {
        return this.save(restriction);
    }

    @Override
    public Restriction update(Restriction restriction) {
        return this.save(restriction);
    }

    private static void validate(Restriction restriction){
        String name = restriction.getName();
        if (name == null || name.isEmpty()) {
            throw new ValidationException("Restrição deve ter nome");
        }
    }
}
