package com.epam.brest.course.service;

import com.epam.brest.course.model.Meeting;

import java.util.Collection;

public interface MeetingService {

    /**
     * Adds new meeting into DB
     * @param meeting meeting to add
     * @return added meeting
     */
    Meeting addMeeting(Meeting meeting);

    /**
     * Gets all meetings
     * @return meeting's collection
     */
    Collection<Meeting> getAllMeetings();

    /**
     * Deletes meeting with required id
     * @param meetingId required meeting's id
     */
    void deleteMeeting(Integer meetingId);
}
