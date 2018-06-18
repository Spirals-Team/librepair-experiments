package academia.business.plan;

import academia.model.Plan;

import java.util.List;

public interface PlanService {

    Plan create(Plan plan);

    Plan update(Plan plan);

    List<Plan> all(Long accountId);
}
