package ru.neustupov.votingforrestaurants.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.neustupov.votingforrestaurants.model.Vote;
import ru.neustupov.votingforrestaurants.repository.VoteRepository;

import java.util.List;

@Repository
public class DataJpaVoteRepositoryImpl implements VoteRepository {

    @Autowired
    private CrudVoteRepository crudVoteRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Autowired
    private CrudRestaurantRepository crudRestaurantRepository;

    @Transactional
    @Override
    public Vote save(Vote vote, int userId, int restId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restId));
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    @Override
    public Vote get(int id, int userId) {
        return crudVoteRepository.findById(id).filter(vote -> vote.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.getAll();
    }

    @Override
    public List<Vote> getAllByUser(int userId) {
        return crudVoteRepository.getAllByUserId(userId);
    }

    @Override
    public List<Vote> getAllByRest(int restId) {
        return crudVoteRepository.getAllByRestaurantId(restId);
    }

    @Override
    public Vote getWithRestaurant(int id, int restId) {
        return crudVoteRepository.getWithRestaurant(id, restId);
    }

    @Override
    public Vote getWithUser(int id, int userId) {
        return crudVoteRepository.getWithUser(id, userId);
    }

    @Override
    public Vote getWithRestaurantAndUser(int id, int restId, int userId) {
        return crudVoteRepository.getWithRestaurantAndUser(id, restId, userId);
    }

    @Override
    public Vote getByUserIdAndRestId(int userId, int restId) {
        return crudVoteRepository.getByUserIdAndRestId(userId, restId);
    }

    @Override
    public Vote getByUserIdAndDate(int userId) {
        return crudVoteRepository.getByUserIdAndDate(userId);
    }

    public List<Vote> getAllForCurrentDate() {
        return crudVoteRepository.getAllForCurrentDate();
    }
}
