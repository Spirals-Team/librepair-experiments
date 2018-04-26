package ru.neustupov.votingforrestaurants.repository;

import ru.neustupov.votingforrestaurants.model.Vote;

import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restId);

    // false if not found
    boolean delete(int id, int userId);

    // null if not found
    Vote get(int id, int userId);

    List<Vote> getAll();

    List<Vote> getAllByUser(int userId);

    List<Vote> getAllByRest(int restId);

    default Vote getWithRestaurant(int id, int restId){
        throw new UnsupportedOperationException();
    }

    default Vote getWithUser(int id, int userId){
        throw new UnsupportedOperationException();
    }

    default Vote getWithRestaurantAndUser(int id, int restId, int userId){throw new UnsupportedOperationException();}

    Vote getByUserIdAndRestId(int userId, int restId);

    Vote getByUserIdAndDate(int userId);

    List<Vote> getAllForCurrentDate();
}
