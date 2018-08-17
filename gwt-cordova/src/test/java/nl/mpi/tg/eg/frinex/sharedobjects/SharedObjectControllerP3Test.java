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
public class SharedObjectControllerP3Test {

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

    /**
     * Test of getGroupData method, of class SharedObjectController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetGroupData2() throws Exception {
        System.out.println("getGroupData2");
        SharedObjectController instance = new SharedObjectController();

        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 11, 35, 36, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 11, 35, 36, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 11, 35, 36, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 11, 35, 36, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 12, 36, 37, "etudfgt eae", true, 2, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 12, 36, 37, "etudfgt eae", true, 2, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 12, 37, 38, "etudfgt eae", true, 0, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 12, 37, 38, "etudfgt eae", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C,B,D", "C", 12, 38, 39, "", true, 2, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C,B,D", "C", 12, 38, 39, "", true, 2, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "C,D", 12, 38, 39, "", true, 0, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "C,D", 12, 38, 39, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A", 0, 0, 0, null, true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,B,C,D", 11, 35, 36, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 13, 39, 40, "etudfgt eae", true, 0, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 13, 39, 40, "etudfgt eae", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 12, 36, 37, "etudfgt eae", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 12, 36, 37, "etudfgt eae", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 13, 40, 41, "etudfgt eae", true, 2, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 13, 40, 41, "etudfgt eae", true, 2, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C,B,D", "C", 13, 41, 42, "", true, 2, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C,B,D", "C", 13, 41, 42, "", true, 2, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "C,D", 13, 41, 42, "", true, 0, 2, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "C,D", 13, 41, 42, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 12, 37, 38, "etudfgt eae", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 12, 37, 38, "etudfgt eae", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 12, 38, 39, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 12, 38, 39, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 12, 38, 39, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 12, 38, 39, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 13, 39, 40, "etudfgt eae", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 13, 39, 40, "etudfgt eae", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 13, 40, 41, "etudfgt eae", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 13, 40, 41, "etudfgt eae", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 13, 41, 42, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 13, 41, 42, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 13, 41, 42, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 13, 41, 42, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "D", "A,C,B,D", "A,B,C,D", 13, 41, 42, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 14, 43, 44, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 14, 43, 44, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 14, 44, 45, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 14, 44, 45, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 14, 44, 45, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 14, 44, 45, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "D", "A,C,B,D", "A,B,C,D", 13, 41, 42, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 15, 45, 46, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 15, 45, 46, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 15, 46, 47, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 15, 46, 47, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 15, 47, 48, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 15, 47, 48, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 15, 47, 48, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 15, 47, 48, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 14, 43, 44, "etudfgt eae", true, 1, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 14, 43, 44, "etudfgt eae", true, 1, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "D", 14, 44, 45, "", true, 1, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "D", 14, 44, 45, "", true, 1, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 14, 43, 44, null, true, 0, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 48, 49, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 48, 49, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "B", 16, 50, 51, "", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "B", 16, 50, 51, "", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,B,C,D", "A,B,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,B,C,D", "A,B,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 13, 40, 41, "etudfgt eae", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 49, 50, "etudfgt eae", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 13, 41, 42, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A,C,D", 16, 49, 50, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 13, 41, 42, "", true, 0, 0, 2, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B,C,D", 16, 49, 50, "", true, 0, 0, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "D", "A,C,B,D", "A,B,C,D", 14, 43, 44, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 49, 50, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 14, 43, 44, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 14, 44, 45, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 16, 49, 50, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 14, 44, 45, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 16, 49, 50, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "D", "A,C,B,D", "A,B,C,D", 14, 43, 44, "", true, 0, 2, 2, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 15, 45, 46, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 14, 42, 43, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,C", "C", 14, 43, 44, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 15, 46, 47, "etudfgt eae", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 49, 50, "etudfgt eae", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 15, 47, 48, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C,B,D", "A", 16, 49, 50, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 15, 47, 48, "", true, 0, 0, 0, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "A,B", 16, 49, 50, "", true, 0, 0, 0, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 14, 43, 44, "etudfgt eae", true, 1, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "B,D", "D", 14, 43, 44, "etudfgt eae", true, 1, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "D", 14, 44, 45, "", true, 1, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "D", "D", "A,C,B,D", "D", 14, 44, 45, "", true, 1, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 0, 0, 0, null, true, 0, 1, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "C", "C", "A,B,C,D", "C", 14, 43, 44, null, true, 0, 1, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 48, 49, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "B", 16, 50, 51, "", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,C,B,D", "B", 16, 50, 51, "", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,B,C,D", "A,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,B,C,D", "A,B,C", 0, 0, 0, null, true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "A,B,C,D", "A,B,C", 16, 49, 50, null, true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 0, 0, 1, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "A", "A", "A,C", "A", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
        processMessage(instance, "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 0, 1, 2, "etudfgt eae", true, 0, 0, 1, 0,
                "test3", "Round_1___4", "A,B,C,D", "A,B|C,D", "B", "B", "B,D", "B", 16, 49, 50, "etudfgt eae", true, 0, 0, 1, 0);//processMessage
    }
}
