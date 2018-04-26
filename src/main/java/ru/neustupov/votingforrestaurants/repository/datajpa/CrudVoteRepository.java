package ru.neustupov.votingforrestaurants.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.votingforrestaurants.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Vote save(Vote vote);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v")
    List<Vote> getAll();

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId")
    List<Vote> getAllByUserId(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restId")
    List<Vote> getAllByRestaurantId(@Param("restId") int restId);

    //    https://stackoverflow.com/a/46013654/548473
    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1")
    Vote getWithRestaurant(int id, int restId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1")
    Vote getWithUser(int id, int userId);

    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT v FROM Vote v WHERE v.id=?1")
    Vote getWithRestaurantAndUser(int id, int restId, int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.restaurant.id=:restId")
    Vote getByUserIdAndRestId(@Param("userId") int userId, @Param("restId") int restId);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=CURRENT_DATE ")
    Vote getByUserIdAndDate(@Param("userId") int userId);

    @Query("SELECT v FROM Vote v WHERE v.date=CURRENT_DATE ")
    List<Vote> getAllForCurrentDate();
}
