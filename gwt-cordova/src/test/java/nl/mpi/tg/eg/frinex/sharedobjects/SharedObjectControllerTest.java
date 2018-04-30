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

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * @since Feb 3, 2017 3:36:50 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SharedObjectControllerTest {

//    String lastScreenId = "";
//    int lastPhase = 0;
    protected void processMessage(SharedObjectController instance,
            String InMessagegroupId, String InMessagescreenId, String InMessageallMemberCodes, String InMessagegroupCommunicationChannels, String InMessagememberCode, String InMessageoriginMemberCode,
            String InMessageexpectedRespondents, String InMessageactualRespondents, int InMessagestimulusIndex, int InMessageoriginPhase, int InMessagerequestedPhase, String InMessagemessageString,
            boolean InMessagegroupReady, int InMessagememberScore, int InMessagechannelScore, int InMessagegroupScore, int InMessageeventMs,
            String OutMessagegroupId, String OutMessagescreenId, String OutMessageallMemberCodes, String OutMessagegroupCommunicationChannels, String OutMessagememberCode, String OutMessageoriginMemberCode,
            String OutMessageexpectedRespondents, String OutMessageactualRespondents, int OutMessagestimulusIndex, int OutMessageoriginPhase, int OutMessagerequestedPhase, String OutMessagemessageString,
            boolean OutMessagegroupReady, int OutMessagememberScore, int OutMessagechannelScore, int OutMessagegroupScore, int OutMessageeventMs
    ) throws Exception {
        GroupMessage groupMessage = new GroupMessage(InMessagegroupId, InMessagescreenId, InMessagememberCode, InMessagememberCode);
        groupMessage.setAllMemberCodes(InMessageallMemberCodes);
        groupMessage.setGroupCommunicationChannels(InMessagegroupCommunicationChannels);
        groupMessage.setStimulusIndex(InMessagestimulusIndex);
        groupMessage.setStimuliList("StimuliList");
        groupMessage.setOriginPhase(InMessageoriginPhase);
        groupMessage.setRequestedPhase(InMessagerequestedPhase);
        groupMessage.setExpectedRespondents(InMessageexpectedRespondents);
        final GroupMessage result = instance.getGroupData(groupMessage);
        assertEquals(OutMessageoriginPhase, result.getOriginPhase().intValue());
        assertEquals(OutMessagerequestedPhase, result.getRequestedPhase().intValue());
        assertEquals(OutMessagestimulusIndex, result.getStimulusIndex().intValue());
//        if (result.isGroupReady()) {
//            assertTrue("ActualRespondents should contain InMessagememberCode", result.getActualRespondents().contains(InMessagememberCode));
//            if (lastScreenId.equals(result.getScreenId())) {
//                if (result.getOriginPhase() != null) {
//                    assertTrue(result.getOriginPhase() > 0);
//                }
//                lastPhase = result.getOriginPhase();
//            } else {
//                lastScreenId = result.getScreenId();
//                lastPhase = result.getOriginPhase();
//            }
//        }
    }

    private GroupMessage processMessage(SharedObjectController instance,
            String userId,
            String screenId,
            String userLabel,
            String groupId,
            String allMemberCodes,
            String communicationChannels,
            String memberCode,
            String originMemberCode,
            String stimulusId,
            String stimulusIndex,
            String stimuliList,
            String requestedPhase,
            String messageString,
            boolean groupReady,
            String responseStimulusId,
            String expectedRespondents,
            String actualRespondents,
            String groupUUID) throws Exception {
        GroupMessage groupMessage = new GroupMessage(groupId, screenId, userId, memberCode);
        groupMessage.setAllMemberCodes(allMemberCodes);
        groupMessage.setGroupCommunicationChannels(communicationChannels);
        groupMessage.setRequestedPhase(Integer.parseInt(requestedPhase));
        groupMessage.setExpectedRespondents(expectedRespondents);
        return instance.getGroupData(groupMessage);
    }

    /**
     * Test of getGroupData method, of class SharedObjectController.
     */
    @Test
    public void testGetGroupData() throws Exception {
        System.out.println("getGroupData");
        SharedObjectController instance = new SharedObjectController();

        GroupMessage result = processMessage(instance, "testuser-0", "Round_0", "A : robot group at 5:36:59 PM", "robot group at 5:36:59 PM", ABCDEFGH, ABCDEFGH,
                "A", "A", "4-1:medium", "7", "3-2:large-2-7:medium-3-1:medium-1-5:medium-1-2:small-3-7:large-3-2:medium-4-1:medium", "8", "", true, null, "B,C,A,E,F,G,H", "G", "155557d6-83a5-4fb6-af3c-67fd40635b75");
        GroupId expectedGroupId = result.getGroupId();
        result = processMessage(instance, "testuser-0", "Round_0", "A : robot group at 5:36:59 PM", "robot group at 5:36:59 PM", ABCDEFGH, ABCDEFGH,
                "A", "A", "4-1:medium", "7", "3-2:large-2-7:medium-3-1:medium-1-5:medium-1-2:small-3-7:large-3-2:medium-4-1:medium", "8", "", true, null, "B,C,A,E,F,G,H", "G", "155557d6-83a5-4fb6-af3c-67fd40635b75");
        assertEquals(expectedGroupId, result.getGroupId());
        result = processMessage(instance, "testuser-0", "Round_0", "A : robot group at 10:23:42 AM", "robot group at 10:23:42 AM", ABCDEFGH, ABCDEFGH,
                "A", "A", "2-7:small", "0", "2-7:small-4-5:small-3-1:large-1-5:medium-3-6:medium-3-1:medium-3-4:large-1-7:medium", "0", null, false, null, null, "A", "0502002b-84d6-4e9c-86e9-1b81a7be81ca");
        assertNotEquals(expectedGroupId, result.getGroupId());
        result = processMessage(instance, "testuser-1", "Round_0", "B : robot group at 10:23:42 AM", "robot group at 10:23:42 AM", ABCDEFGH, ABCDEFGH,
                "B", "B", "2-1:medium", "0", "2-7:small-4-5:small-3-1:large-1-5:medium-3-6:medium-3-1:medium-3-4:large-1-7:medium", "0", null, false, null, null, null, "0502002b-84d6-4e9c-86e9-1b81a7be81ca");
        assertNotEquals(expectedGroupId, result.getGroupId());
        result = processMessage(instance, "testuser-0", "Round_0", "A : robot group at 5:36:59 PM", "robot group at 5:36:59 PM", ABCDEFGH, ABCDEFGH,
                "A", "A", "4-1:medium", "7", "3-2:large-2-7:medium-3-1:medium-1-5:medium-1-2:small-3-7:large-3-2:medium-4-1:medium", "8", "", true, null, "B,C,A,E,F,G,H", "G", "155557d6-83a5-4fb6-af3c-67fd40635b75");
        assertEquals(expectedGroupId, result.getGroupId());
        result = processMessage(instance, "testuser-0", "Round_0", "A : robot group at 10:23:42 AM", "robot group at 10:23:42 AM", ABCDEFGH, ABCDEFGH,
                "A", "A", "2-7:small", "0", "2-7:small-4-5:small-3-1:large-1-5:medium-3-6:medium-3-1:medium-3-4:large-1-7:medium", "0", null, false, null, null, "A", "0502002b-84d6-4e9c-86e9-1b81a7be81ca");
        assertNotEquals(expectedGroupId, result.getGroupId());
    }

     @Ignore //@todo: complete this test and make sure ActualRespondents is correct and that the user code does not switch
    @Test
    public void testResumeGroupData() throws Exception {
        System.out.println("getGroupData");
        SharedObjectController instance = new SharedObjectController();
        final String stimuliList = "2-3:large-3-4:small-4-4:small-1-6:large-2-2:large-3-7:large-1-7:large-3-4:medium";
        processMessage(instance, "testuser-0", "Round_0", "A : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "A", "A", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A", GROUP_UUID);
        processMessage(instance, "testuser-1", "Round_0", "B : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "B", "4-1:small", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "C : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "C", "1-1:medium", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-1", "Round_0", "B : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "B", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A,B,C", GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "C : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "C", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A,B,C", GROUP_UUID);
        processMessage(instance, "testuser-2", "Round_0", "D : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "D", "D", "3-7:small", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-2", "Round_0", "D : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "D", "D", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A,B,C,D", GROUP_UUID);
        processMessage(instance, "testuser-4", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "E", "E", "1-2:small", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-5", "Round_0", "F : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "F", "F", "2-6:large", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-6", "Round_0", "G : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "G", "G", "4-2:large", "0", stimuliList, "0", null, false, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-5", "Round_0", "F : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "F", "F", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A,B,C,D,E,F,G", GROUP_UUID);
        processMessage(instance, "testuser-4", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "E", "E", "2-3:large", "0", stimuliList, "0", null, false, null, null, "A,B,C,D,E,F,G", GROUP_UUID);
        processMessage(instance, "testuser-7", "Round_0", "H : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "H", "H", "1-3:large", "0", stimuliList, "0", null, true, null, null, null, GROUP_UUID);
        processMessage(instance, "testuser-6", "Round_0", "G : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "G", "G", "2-3:large", "0", stimuliList, "0", null, true, null, "B,C,D,E,F,G,H", ABCDEFGH, GROUP_UUID);
        processMessage(instance, "testuser-7", "Round_0", "H : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "H", "H", "2-3:large", "0", stimuliList, "0", null, true, null, null, ABCDEFGH, GROUP_UUID);
        processMessage(instance, "testuser-0", "Round_0", "A : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "A", "A", "2-3:large", "0", stimuliList, "1", "qq", true, null, "A", "A", GROUP_UUID);
        processMessage(instance, "testuser-1", "Round_0", "B : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "B", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B", GROUP_UUID);
        processMessage(instance, "testuser-2", "Round_0", "D : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "D", "D", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,D", GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "C : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "C", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D", GROUP_UUID);
        processMessage(instance, "testuser-7", "Round_0", "H : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "H", "H", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D,H", GROUP_UUID);
        processMessage(instance, "testuser-6", "Round_0", "G : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "G", "G", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D,G,H", GROUP_UUID);
        processMessage(instance, "testuser-5", "Round_0", "F : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "F", "F", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-4", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "E", "E", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D,E,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-1", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "E", "2-3:large", "0", stimuliList, "2", "", true, null, "B,C,D,E,F,G,H", "B,C,D,E,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-1", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "E", "2-3:large", "0", stimuliList, "3", "qqqqqqq", true, null, "B", "E", GROUP_UUID);
        processMessage(instance, "testuser-2", "Round_0", "D : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "D", "D", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "D", GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "C : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "C", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "C,D", GROUP_UUID);
        processMessage(instance, "testuser-7", "Round_0", "H : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "H", "H", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "C,D,H", GROUP_UUID);
        processMessage(instance, "testuser-6", "Round_0", "G : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "G", "G", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "C,D,G,H", GROUP_UUID);
        processMessage(instance, "testuser-5", "Round_0", "F : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "F", "F", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "C,D,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-4", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "B", "E", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "C,D,E,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-0", "Round_0", "A : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "A", "A", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "A,C,D,E,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "E", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "A,C,D,E,F,G,H", GROUP_UUID);
        processMessage(instance, "testuser-3", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "C", "E", "2-3:large", "0", stimuliList, "5", "qqqqq", true, null, "C", "E", GROUP_UUID);
        GroupMessage result = processMessage(instance, "testuser-2", "Round_0", "E : robot group at 3:30:01 PM", ROBOT, ABCDEFGH, ABCDEFGH, "D", "E", "2-3:large", "0", stimuliList, "4", "", true, null, "A,C,D,E,F,G,H", "A,D,E,F,G,H", GROUP_UUID);
        assertEquals("A,C,D,E,F,G,H", result.getExpectedRespondents());
        assertEquals("A,D,E,F,G,H", result.getActualRespondents());
    }
    protected static final String GROUP_UUID = "bbe1ae0c-1568-49dd-aa6d-20bbfc2dc495";
    protected static final String ABCDEFGH = "A,B,C,D,E,F,G,H";
    protected static final String ROBOT = "robot group at 3:30:01 PM";

}
