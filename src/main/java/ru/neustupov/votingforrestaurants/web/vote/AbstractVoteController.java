package ru.neustupov.votingforrestaurants.web.vote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.neustupov.votingforrestaurants.AuthorizedUser;
import ru.neustupov.votingforrestaurants.model.Vote;
import ru.neustupov.votingforrestaurants.service.VoteService;
import ru.neustupov.votingforrestaurants.util.ValidationUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.neustupov.votingforrestaurants.util.ValidationUtil.checkNew;

public abstract class AbstractVoteController {

    static final LocalTime STOP_TIME = LocalTime.of(11,00,00,00);

    private static final Logger log = LoggerFactory.getLogger(AbstractVoteController.class);

    @Autowired
    private VoteService service;

    public Vote get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get vote {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        log.info("delete vote {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<Vote> getAll() {
        log.info("getAll votes");
        return service.getAll();
    }

    public List<Vote> getAllByRest(int restId){
        log.info("getAllByRest {}", restId);
        return service.getAllByRest(restId);
    }

    public Vote create(Vote vote, int restId) {
        int userId = AuthorizedUser.id();
        checkNew(vote);
        log.info("create {} for user {}", vote, userId);
        return service.create(vote, userId, restId);
    }

    public void update(int id, Vote vote, int restId) {
        int userId = AuthorizedUser.id();
        vote.setId(id);
        ValidationUtil.checkTimeForVote(STOP_TIME);
        log.info("update {} for user {} and restaurant {}", vote, userId, restId);
        service.update(vote, userId, restId);
    }

    public Vote getByUserIdAndRestId(int restId) {
        int userId = AuthorizedUser.id();
        log.info("getByUserIdAndRestId userId {} restId {}", restId, userId);
        return service.getByUserIdAndRestId(userId, restId);
    }

    public Vote getByUserIdAndDate() {
        int userId = AuthorizedUser.id();
        return service.getByUserIdAndDate(userId);
    }
}
