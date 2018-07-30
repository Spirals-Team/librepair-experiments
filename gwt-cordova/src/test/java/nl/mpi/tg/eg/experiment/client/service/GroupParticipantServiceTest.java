/*
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.service;

import nl.mpi.tg.eg.experiment.client.listener.GroupActivityListener;
import nl.mpi.tg.eg.frinex.common.listener.TimedStimulusListener;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Nov 21, 2016 4:48:36 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class GroupParticipantServiceTest {

    public GroupParticipantServiceTest() {
    }

    private void groupNetworkActivity(final StringBuilder stringBuilder, final String id, final GroupParticipantService instance, final String groupRole, final int requiredMessageCount, final String description) {
        instance.addGroupActivity(new GroupActivityListener(id, groupRole) {
            @Override
            public void triggerActivityListener(int callerPhase, String expectedRespondents) {
                stringBuilder.append(instance.getRequestedPhase());
                stringBuilder.append("-");
                stringBuilder.append(instance.getMemberCode());
                if (description != null) {
                    stringBuilder.append("-");
                    stringBuilder.append(description);
                }
                stringBuilder.append("[");
                stringBuilder.append(groupRole);
                stringBuilder.append("]\n");
            }
        });
    }

    /**
     * Test of addGroupActivity method, of class GroupParticipantService.
     */
    @Test
    public void testAddGroupActivity() {
        System.out.println("addGroupActivity");
        GroupParticipantService instance = new GroupParticipantService("userId", "screenId", "A,B,C,D,E,F,G,H", "A,B|C,D|E,F|G,H", 2, "stimuliList", new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
            }
        }, new TimedStimulusListener() {
            @Override
            public void postLoadTimerFired() {
            }
        });
        final StringBuilder stringBuilder = new StringBuilder();
        groupNetworkActivity(stringBuilder, "a", instance, "A,C,E,G:-:-:B,D,F,H:-:-", 1, null);
        groupNetworkActivity(stringBuilder, "b", instance, "-:A,C,E,G:-:-:B,D,F,H:-", 1, null);
        groupNetworkActivity(stringBuilder, "c", instance, "B,D,F,H:-:-:A,C,E,G:-:-", 1, null);
        groupNetworkActivity(stringBuilder, "d", instance, "-:B,D,F,H:-:-:A,C,E,G:-", 1, null);
        groupNetworkActivity(stringBuilder, "e", instance, "-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H", 1, null);
        for (int phaseCounter = 0; phaseCounter < 10; phaseCounter++) {
            for (int memberIndex = 0; memberIndex < 8; memberIndex++) {
                instance.clearLastFiredListner();
                instance.handleGroupMessage("userId", "screenId", "userLabel", "groupId", "A,B,C,D,E,F,G,H", "A,B,C,D,E,F,G,H".split(",")[memberIndex], "A,B,C,D,E,F,G,H".split(",")[memberIndex],
                        "expectedRespondents",
                        "actualRespondents  ",
                        "stimulusId", "0", "stimuliList", instance.getRequestedPhase().toString(), Integer.toString(phaseCounter), "messageString", Boolean.TRUE, "responseStimulusOptions", "groupScore", "channelScore", "responseStimulusId");
            }
        }
        assertEquals("0-A[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "0-B[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "0-C[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "0-D[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "0-E[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "0-F[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "0-G[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "0-H[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "1-A[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "1-B[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "1-C[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "1-D[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "1-E[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "1-F[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "1-G[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "1-H[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "2-A[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-B[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-C[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-D[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-E[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-F[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-G[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "2-H[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "3-A[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "3-B[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "3-C[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "3-D[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "3-E[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "3-F[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "3-G[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "3-H[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "4-A[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "4-B[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "4-C[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "4-D[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "4-E[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "4-F[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "4-G[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "4-H[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "5-A[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-B[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-C[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-D[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-E[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-F[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-G[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "5-H[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "6-A[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "6-B[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "6-C[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "6-D[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "6-E[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "6-F[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "6-G[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "6-H[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "7-A[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "7-B[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "7-C[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "7-D[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "7-E[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "7-F[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "7-G[-:A,C,E,G:-:-:B,D,F,H:-]\n"
                + "7-H[-:B,D,F,H:-:-:A,C,E,G:-]\n"
                + "8-A[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-B[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-C[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-D[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-E[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-F[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-G[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "8-H[-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H]\n"
                + "9-A[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "9-B[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "9-C[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "9-D[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "9-E[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "9-F[A,C,E,G:-:-:B,D,F,H:-:-]\n"
                + "9-G[B,D,F,H:-:-:A,C,E,G:-:-]\n"
                + "9-H[A,C,E,G:-:-:B,D,F,H:-:-]\n", stringBuilder.toString());
    }

    private GroupParticipantService groupParticipantService;

    /**
     * Test of handleGroupMessage method, of class GroupParticipantService.
     */
    @Test
    public void testHandleGroupMessage() {
        System.out.println("handleGroupMessage");
        for (String[] expectedData : new TestData().getExpectedData()) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(expectedData[0]);
            stringBuilder.append("\n");
            groupParticipantService = new GroupParticipantService(expectedData[0], "Round_0", "A,B,C,D,E,F,G,H", "A,B,C,D,E,F,G,H", 2,
                    "4-7:medium-2-5:small-2-3:small-1-2:medium-1-4:small-2-1:large-1-6:small-1-7:small", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // connectedListener
                    stringBuilder.append("connectedListener\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // groupNotReadyListener
                    stringBuilder.append("groupNotReadyListener\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // screenResetRequestListner
                    stringBuilder.append("screenResetRequestListner\n");
                    groupParticipantService.setStimuliListLoaded(groupParticipantService.getStimuliListGroup());
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // stimulusSyncListner
                    stringBuilder.append("stimulusSyncListner\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // groupInfoChangeListner
                    stringBuilder.append("groupInfoChangeListner\n");
                }
            });

            groupNetworkActivity(stringBuilder, "1", groupParticipantService, "A:-:B:-:C:-:D:-:E:-:F:-:G:-:H:-", 1, "producer");
            groupNetworkActivity(stringBuilder, "2", groupParticipantService, "-:A:-:B:-:C:-:D:-:E:-:F:-:G:-:H", 7, "producer wait");
            groupNetworkActivity(stringBuilder, "3", groupParticipantService, "B,C,D,E,F,G,H:-:A,C,D,E,F,G,H:-:B,A,D,E,F,G,H:-:B,C,A,E,F,G,H:-:B,C,D,A,F,G,H:-:B,C,D,E,A,G,H:-:B,C,D,E,F,A,H:-:B,C,D,E,F,G,A:-",
                    1, "guesser wait");
            groupNetworkActivity(stringBuilder, "4", groupParticipantService, "", 1, "not used");
            groupNetworkActivity(stringBuilder, "5", groupParticipantService, "-:B,C,D,E,F,G,H:-:A,C,D,E,F,G,H:-:B,A,D,E,F,G,H:-:B,C,A,E,F,G,H:-:B,C,D,A,F,G,H:-:B,C,D,E,A,G,H:-:B,C,D,E,F,A,H:-:B,C,D,E,F,G,A",
                    1, "guesser");
            new TestData().processTestMessages(groupParticipantService);
//            System.out.println("Expecting: " + expectedData[1]);
//            System.out.println("Found: " + stringBuilder.toString());
            assertEquals(expectedData[1], stringBuilder.toString());
        }
    }

    /**
     * Test of handleGroupMessage Round 1 method, of class
     * GroupParticipantService.
     */
    @Test
    public void testHandleGroupMessageRound1() {
        System.out.println("handleGroupMessageRound1");
        for (String[] expectedData : new TestData().getExpectedDataRound1()) {
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(expectedData[0]);
            stringBuilder.append("\n");

            groupParticipantService = new GroupParticipantService(expectedData[0], "Round_1", "A,B,C,D,E,F,G,H", "A,B|C,D|E,F|G,H", 2,
                    "2-4:medium-2-2:medium-4-6:medium-1-3:small-2-3:small-4-6:large-2-7:medium-1-1:medium", new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // connectedListener
                    stringBuilder.append("connectedListener\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // groupNotReadyListener
                    stringBuilder.append("groupNotReadyListener\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // screenResetRequestListner
                    stringBuilder.append("screenResetRequestListner\n");
                    groupParticipantService.setStimuliListLoaded(groupParticipantService.getStimuliListGroup());
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // stimulusSyncListner
                    stringBuilder.append("stimulusSyncListner\n");
                }
            }, new TimedStimulusListener() {
                @Override
                public void postLoadTimerFired() {
                    // groupInfoChangeListner
                    stringBuilder.append("groupInfoChangeListner\n");
                }
            });

            groupNetworkActivity(stringBuilder, "q", groupParticipantService, "-:-:A,B,C,D,E,F,G,H:-:-:A,B,C,D,E,F,G,H", 1, "feedback");
            groupNetworkActivity(stringBuilder, "w", groupParticipantService, "-:B,D,F,H:-:-:A,C,E,G:-", 1, "guesser");
            groupNetworkActivity(stringBuilder, "e", groupParticipantService, "B,D,F,H:-:-:A,C,E,G:-:-", 1, "guesser wait");
            groupNetworkActivity(stringBuilder, "r", groupParticipantService, "-:A,C,E,G:-:-:B,D,F,H:-", 1, "producer wait");
            groupNetworkActivity(stringBuilder, "t", groupParticipantService, "A,C,E,G:-:-:B,D,F,H:-:-", 1, "producer");
            new TestData().processTestMessagesRound1(groupParticipantService);
//            System.out.println("Expecting: " + expectedData[1]);
//            System.out.println("Found: " + stringBuilder.toString());
            assertEquals(expectedData[1], stringBuilder.toString());
        }
    }
}
