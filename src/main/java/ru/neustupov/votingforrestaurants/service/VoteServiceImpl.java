package ru.neustupov.votingforrestaurants.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.neustupov.votingforrestaurants.model.Vote;
import ru.neustupov.votingforrestaurants.repository.VoteRepository;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService{

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository){
        this.repository = repository;
    }

    @Override
    public Vote create(Vote vote, int userId, int restId) {
        Assert.notNull(vote, "vote must not be null");
        return repository.save(vote, userId, restId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Vote get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void update(Vote vote, int userId, int restId) {
        Assert.notNull(vote, "vote must not be null");
        checkNotFoundWithId(repository.save(vote, userId,restId), vote.getId());
    }

    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Vote> getAllByUser(int userId) {
        Assert.notNull(userId, "userId must not be null");
        return repository.getAllByUser(userId);
    }

    @Override
    public List<Vote> getAllByRest(int restId) {
        Assert.notNull(restId, "restId must not be null");
        return repository.getAllByRest(restId);
    }

    @Override
    public Vote getWithUser(int id, int userId) {
        return checkNotFoundWithId(
                repository.getWithUser(id, userId), id);
    }

    @Override
    public Vote getWithRestaurant(int id, int restId) {
        return checkNotFoundWithId(repository.getWithRestaurant(id, restId), id);
    }

    @Override
    public Vote getWithRestaurantAndUser(int id, int restId, int userId) {
        return checkNotFoundWithId(repository.getWithRestaurantAndUser(id, restId, userId), id);
    }

    public Vote getByUserIdAndRestId(int userId, int restId) {
        Assert.notNull(userId, "userId must not be null");
        Assert.notNull(restId, "restId must not be null");
        return repository.getByUserIdAndRestId(userId, restId);
    }

    public Vote getByUserIdAndDate(int userId) {
        Assert.notNull(userId, "userId must not be null");
        return repository.getByUserIdAndDate(userId);
    }

    public List<Vote> getAllForCurrentDate(){
        return repository.getAllForCurrentDate();
    }
}
