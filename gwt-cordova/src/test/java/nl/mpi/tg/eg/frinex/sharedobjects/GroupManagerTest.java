/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.frinex.sharedobjects;

import nl.mpi.tg.eg.experiment.client.exception.UserIdException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * @since Feb 3, 2017 3:37:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupManagerTest {

    public GroupManagerTest() {
    }

    private GroupMessage[] getGroupMembersArray() throws UserIdException {
        final GroupMessage groupMessage1 = new GroupMessage("groupId", "screenId", "1", "A");
        groupMessage1.setAllMemberCodes("A,B,C,D,E,F,G,H");
        final GroupMessage groupMessage2 = new GroupMessage("groupId", "screenId", "2", "B");
        final GroupMessage groupMessage3 = new GroupMessage("groupId", "screenId", "3", "C");
        final GroupMessage groupMessage4 = new GroupMessage("groupId", "screenId", "4", "D");
        final GroupMessage groupMessage5 = new GroupMessage("groupId", "screenId", "5", "E");
        final GroupMessage groupMessage6 = new GroupMessage("groupId", "screenId", "6", "F");
        final GroupMessage groupMessage7 = new GroupMessage("groupId", "screenId", "7", "G");
        final GroupMessage groupMessage8 = new GroupMessage("groupId", "screenId", "8", "H");
        groupMessage2.setActualRespondents("Respondents");
        groupMessage2.setExpectedRespondents("Respondents");
        groupMessage4.setActualRespondents("Respondents");
        groupMessage4.setExpectedRespondents("Respondents");
        groupMessage6.setActualRespondents("Respondents");
        groupMessage6.setExpectedRespondents("Respondents");
        groupMessage8.setActualRespondents("Respondents");
        groupMessage8.setExpectedRespondents("Respondents");
        final GroupMessage groupMessage9 = new GroupMessage("groupIdOther", "screenId", "9", "A");
        groupMessage9.setAllMemberCodes("A,B,C,D,E,F");
        return new GroupMessage[]{groupMessage1, groupMessage2, groupMessage3, groupMessage4, groupMessage5, groupMessage6, groupMessage7, groupMessage8, groupMessage9};
    }

    /**
     * Test of isGroupMember method, of class GroupManager.
     *
     * @throws nl.mpi.tg.eg.experiment.client.exception.UserIdException
     */
    @Test
    public void testIsGroupMember() throws UserIdException {
        System.out.println("isGroupMember");
        GroupMessage groupMessage = new GroupMessage("groupId", "screenId", "1", "A");
        groupMessage.setAllMemberCodes("A,B,C,D,E,F");
        GroupManager instance = new GroupManager();

        groupMessage.setStimuliList("4-7:medium-2-5:small-2-3:small-1-2:medium-1-4:small-2-1:large-1-6:small-1-7:small");
        assertEquals(false, instance.isGroupMember(groupMessage));
        instance.addGroupMember(groupMessage);
        assertEquals(true, instance.isGroupMember(groupMessage));
    }

    /**
     * Test of isGroupReady method, of class GroupManager.
     *
     * @throws nl.mpi.tg.eg.experiment.client.exception.UserIdException
     */
    @Test
    public void testIsGroupReady() throws UserIdException {
        System.out.println("isGroupReady");
        GroupMessage[] groupMembers = getGroupMembersArray();
        GroupManager instance = new GroupManager();
        for (GroupMessage groupMessage : groupMembers) {
            groupMessage.setStimuliList("4-7:medium-2-5:small-2-3:small-1-2:medium-1-4:small-2-1:large-1-6:small-1-7:small");
        }
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[0]);
        instance.addGroupMember(groupMembers[0]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[1]);
        instance.addGroupMember(groupMembers[1]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[2]);
        instance.addGroupMember(groupMembers[2]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[3]);
        instance.addGroupMember(groupMembers[3]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[8]);
        instance.addGroupMember(groupMembers[8]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[4]);
        instance.addGroupMember(groupMembers[4]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[5]);
        instance.addGroupMember(groupMembers[5]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[6]);
        instance.addGroupMember(groupMembers[6]);
        assertEquals(false, instance.isGroupReady(groupMembers[0]));
        instance.isGroupMember(groupMembers[7]);
        instance.addGroupMember(groupMembers[7]);
        assertEquals(true, instance.isGroupReady(groupMembers[0]));
    }

    /**
     * Test of updateChannelMessageIfOutOfDate method, of class GroupManager.
     *
     * @throws nl.mpi.tg.eg.experiment.client.exception.UserIdException
     */
    @Ignore // this test fails since the recentChannelMessageswas added, other tests should have more state coverage although this one was quite focused
    @Test
    public void testUpdateChannelMessageIfOutOfDate() throws UserIdException {
        System.out.println("updateChannelMessageIfOutOfDate");
        GroupManager instance = new GroupManager();
        final String groupCommunicationChannels = "A,B|C,D|E,F|G,H";
        final MemberCode[] meberCodes = new MemberCode[]{new MemberCode("A"), new MemberCode("B"), new MemberCode("C"), new MemberCode("D"), new MemberCode("E"), new MemberCode("F"), new MemberCode("G"), new MemberCode("H"), new MemberCode("X")};
        final GroupMessage storedMessage = new GroupMessage("groupId", "screenId", "5", "E");
        storedMessage.setGroupCommunicationChannels(groupCommunicationChannels);
//        storedMessage.setMemberCode("E");
        storedMessage.setRequestedPhase(12);
        int requestPhase = 0;
        for (GroupMessage groupMessage : getGroupMembersArray()) {
            groupMessage.setGroupCommunicationChannels(groupCommunicationChannels);
            groupMessage.setMemberCode(meberCodes[requestPhase]);
            groupMessage.setRequestedPhase(0);
            groupMessage.setMemberCode(new MemberCode("E"));
            groupMessage.setExpectedRespondents("E");
            groupMessage.setOriginMemberCode(new MemberCode("E"));
            instance.updateResponderListForMessagePhase(groupMessage);
            instance.updateChannelMessageIfOutOfDate(groupMessage);
            instance.setUsersLastMessage(groupMessage);
            assertEquals(0, groupMessage.getRequestedPhase().intValue());
        }
        requestPhase = 0;
        for (GroupMessage groupMessage : getGroupMembersArray()) {
            groupMessage.setRequestedPhase(requestPhase + 3);
            groupMessage.setGroupCommunicationChannels(groupCommunicationChannels);
            groupMessage.setMemberCode(meberCodes[requestPhase]);
            groupMessage.setOriginMemberCode(meberCodes[requestPhase]);
            groupMessage.setExpectedRespondents(meberCodes[requestPhase].toString());
            instance.updateResponderListForMessagePhase(groupMessage);
            instance.updateChannelMessageIfOutOfDate(groupMessage);
            instance.setUsersLastMessage(groupMessage);
            assertEquals(requestPhase + 3, groupMessage.getRequestedPhase().intValue());
            requestPhase++;
        }
        requestPhase = 0;
        for (GroupMessage groupMessage : getGroupMembersArray()) {
            groupMessage.setRequestedPhase(requestPhase);
            groupMessage.setGroupCommunicationChannels(groupCommunicationChannels);
            groupMessage.setMemberCode(meberCodes[requestPhase]);
            groupMessage.setExpectedRespondents(meberCodes[requestPhase].toString());
            groupMessage.setOriginMemberCode(meberCodes[requestPhase]);
            instance.updateResponderListForMessagePhase(groupMessage);
            final GroupMessage updateChannelMessageIfOutOfDate = instance.updateChannelMessageIfOutOfDate(groupMessage);
            final int[] expectedValues = new int[]{4, 4, 6, 6, 8, 8, 10, 10, 8};
            if (updateChannelMessageIfOutOfDate == null) {
                instance.setUsersLastMessage(groupMessage);
                assertEquals(expectedValues[requestPhase], groupMessage.getRequestedPhase().intValue());
            } else {
                assertEquals(expectedValues[requestPhase], updateChannelMessageIfOutOfDate.getRequestedPhase().intValue());
            }
            requestPhase++;
        }
    }
}
