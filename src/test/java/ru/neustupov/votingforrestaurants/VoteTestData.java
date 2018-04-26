package ru.neustupov.votingforrestaurants;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.neustupov.votingforrestaurants.model.Vote;

import java.sql.Date;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static ru.neustupov.votingforrestaurants.RestaurantTestData.*;
import static ru.neustupov.votingforrestaurants.UserTestData.ADMIN;
import static ru.neustupov.votingforrestaurants.UserTestData.USER;
import static ru.neustupov.votingforrestaurants.model.AbstractBaseEntity.START_SEQ;
import static ru.neustupov.votingforrestaurants.web.json.JsonUtil.writeIgnoreProps;

public class VoteTestData {

    public static final int VOTE1_ID = START_SEQ + 21;
    public static final int VOTE2_ID = START_SEQ + 22;
    public static final int VOTE3_ID = START_SEQ + 23;
    public static final int VOTE4_ID = START_SEQ + 24;
    public static final int VOTE5_ID = START_SEQ + 25;
    public static final int VOTE6_ID = START_SEQ + 26;
    public static final int VOTE_FOR_GET_BY_USER_ID_AND_DATE_ID = START_SEQ + 27;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, USER, Date.valueOf("2015-05-01"), RUSSIA);
    public static final Vote VOTE2 = new Vote(VOTE2_ID, USER, Date.valueOf("2015-05-02"), UKRAINE);
    public static final Vote VOTE3 = new Vote(VOTE3_ID, USER, Date.valueOf("2015-05-03"), U_KOLYANA);
    public static final Vote VOTE4 = new Vote(VOTE4_ID, ADMIN, Date.valueOf("2015-05-01"), ALMAZ);
    public static final Vote VOTE5 = new Vote(VOTE5_ID, ADMIN, Date.valueOf("2015-05-02"), FART);
    public static final Vote VOTE6 = new Vote(VOTE6_ID, ADMIN, Date.valueOf("2015-05-03"), RUSSIA);

    public static final Vote VOTE_FOR_GET_BY_USER_ID_AND_DATE = new Vote(VOTE_FOR_GET_BY_USER_ID_AND_DATE_ID, ADMIN, Date.from(Instant.now()), RUSSIA);

    public static final List<Vote> VOTES = Arrays.asList(VOTE2, VOTE3, VOTE4, VOTE5, VOTE6);

    public static Vote getCreated() {
        return new Vote(null, USER, Date.valueOf("2015-05-04"), RUSSIA);
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static ResultMatcher contentJson(Vote... expected) {
        return content().json(writeIgnoreProps(Arrays.asList(expected), "date", "restaurant", "user"));
    }

    public static ResultMatcher contentJson(Vote expected) {
        return content().json(writeIgnoreProps(expected, "date", "restaurant", "user"));
    }
}
