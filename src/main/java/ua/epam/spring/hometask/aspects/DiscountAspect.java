package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.User;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Vladimir on 22.10.2017.
 */
@Aspect
@Component
public class DiscountAspect {

    private JdbcTemplate jdbcTemplate;

    public DiscountAspect(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Pointcut("execution(* *ua.epam.spring.hometask.service.strategy.DiscountStrategy.getPercentDiscount(..))")
    private void getPercentDiscount() {
    }

    @AfterReturning(pointcut = "getPercentDiscount() && args(user,..)", returning = "discount")
    public void collectStatistics(JoinPoint joinPoint, User user, byte discount) {
        if (discount > 0) {

            Class<?> strategyClass = joinPoint.getTarget().getClass();
            List<Long> userDiscountEvents = jdbcTemplate.query(
                    "SELECT * FROM user_discount_audit WHERE user_id=? AND discount_name=?",
                    new Object[]{user.getId(), strategyClass.getSimpleName()},
                    (rs, rowNum) -> {
                        Long userId = rs.getLong("user_id");
                        return userId;
                    });

            if (userDiscountEvents.size() == 0) {
                jdbcTemplate.update(
                        "INSERT INTO user_discount_audit(user_id, discount_name, count) VALUES(?, ?, 0)",
                        user.getId(),
                        strategyClass.getSimpleName());
            }
            jdbcTemplate.update(
                    "UPDATE user_discount_audit SET count = count+1 WHERE user_id=? AND discount_name=?",
                    user.getId(),
                    strategyClass.getSimpleName());
        }
    }

    public Map<String, Integer> getDiscountEvents() {

        Map<String, Integer> allEvents = new TreeMap<>();
        jdbcTemplate.query(
                "SELECT * FROM user_discount_audit",
                rs -> {
                    while (rs.next()) {
                        String discountName = rs.getString("discount_name");
                        Integer count = rs.getInt("count");
                        if (!allEvents.containsKey(discountName)) {
                            allEvents.put(discountName, 0);
                        }
                        allEvents.put(discountName, allEvents.get(discountName) + count);
                    }
                    return null;
                });

        return allEvents;
    }

    public Map<Long, Map<String, Integer>> getDiscountEventsGroupedByUser() {
        TreeMap<Long, Map<String, Integer>> usersDiscountEvents = new TreeMap<>();

        jdbcTemplate.query(
                "SELECT * FROM user_discount_audit",
                (rs, rowNum) -> {
                    Long user_id = rs.getLong("user_id");
                    String discountName = rs.getString("discount_name");
                    Integer count = rs.getInt("count");

                    if (!usersDiscountEvents.containsKey(user_id)) {
                        usersDiscountEvents.put(user_id, new TreeMap<>());
                    }
                    Map<String, Integer> userEventsInfo = usersDiscountEvents.get(user_id);
                    userEventsInfo.put(discountName, count);

                    return null;
                });
        return usersDiscountEvents;
    }


    public void printStatistics() {
        getDiscountEvents().forEach((discountName, count) -> {
            System.out.println(count + " - " + discountName);
        });

        System.out.println("-----------------");

        getDiscountEventsGroupedByUser().forEach((id, userEventInfo) -> {
            System.out.println("user_id: " + id);
            userEventInfo.forEach((discountName, count) -> System.out.println(count + " - " + discountName));
        });
    }

}
