package academia.business.plan;

import academia.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository repository;

    @Override
    public Plan create(Plan plan) {
        return repository.save(plan);
    }

    @Override
    public Plan update(Plan plan) {
        return repository.save(plan);
    }

}
