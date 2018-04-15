package com.epam.brest.course.service;

import com.epam.brest.course.dao.MeetingDao;
import com.epam.brest.course.model.Meeting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

public class MeetingServiceImpl implements MeetingService{

    private static final Logger LOGGER = LogManager.getLogger();

    private MeetingDao meetingDao;

    public MeetingServiceImpl(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    /**
     * Adds new meeting into DB
     *
     * @param meeting meeting to add
     * @return added meeting
     */
    @Override
    public Meeting addMeeting(Meeting meeting) {

        LOGGER.debug("addMeeting({})", meeting);
        return meetingDao.addMeeting(meeting);
    }

    /**
     * Gets all meetings
     * @return meeting's collection
     */
    @Override
    public Collection<Meeting> getAllMeetings() {

        LOGGER.debug("getAllMeetings()");
        return meetingDao.getAllMeetings();
    }

    /**
     * Deletes meeting with required id
     *
     * @param meetingId required meeting's id
     */
    @Override
    public void deleteMeeting(Integer meetingId) {

        LOGGER.debug("deleteMeeting()");
        meetingDao.deleteMeeting(meetingId);
    }
}
