package com.epam.brest.course.dao;

import com.epam.brest.course.model.Meeting;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context-test.xml",
        "classpath:test-db.xml", "classpath:dao-context.xml"})
@Rollback
public class MeetingDaoImplTest {

    private static final Integer FIRST_TEAM = 1;
    private static final Integer SECOND_TEAM = 2;
    private static final Date MEETING_DATE = Date.valueOf("2018-12-12");

    @Autowired
    MeetingDao meetingDao;

    @Test
    public void getAllMeetingsTest(){

        Collection<Meeting> meetings = meetingDao.getAllMeetings();
        Assert.assertFalse(meetings.isEmpty());
    }

    @Test
    public void addMeetingTest(){

        Meeting meeting = new Meeting(FIRST_TEAM, SECOND_TEAM, MEETING_DATE);
        Meeting meeting1 = meetingDao.addMeeting(meeting);
        Assert.assertTrue(meeting1.getFirst_team()
                .equals(meeting.getFirst_team()));
        Assert.assertTrue(meeting1.getSecond_team()
                .equals(meeting.getSecond_team()));
        Assert.assertTrue(meeting1.getMeeting_date().toString()
                .equals(meeting.getMeeting_date().toString()));
    }

    @Test
    public void deleteMeetingTest(){

        Meeting meeting = new Meeting(FIRST_TEAM, SECOND_TEAM, MEETING_DATE);
        meetingDao.addMeeting(meeting);
        Integer meetingCounter = meetingDao.getAllMeetings().size();
        meetingDao.deleteMeeting(meeting.getMeeting_id());
        Assert.assertTrue(((Integer) meetingDao.getAllMeetings()
                .size()).equals(meetingCounter - 1));
    }
}

