package academia.business.plan;

import academia.model.Account;
import academia.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByCustomer(Account account);
}
