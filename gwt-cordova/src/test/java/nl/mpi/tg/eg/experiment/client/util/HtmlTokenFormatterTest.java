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
package nl.mpi.tg.eg.experiment.client.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import nl.mpi.tg.eg.experiment.client.model.UserData;
import nl.mpi.tg.eg.experiment.client.service.GroupScoreService;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @since Jul 19, 2017 3:37:34 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class HtmlTokenFormatterTest {

    public HtmlTokenFormatterTest() {
    }

    /**
     * Test of formatString method, of class HtmlTokenFormatter.
     */
    @Test
    public void testFormatString() {
        System.out.println("formatString");

        String inputString = "Q<groupScore>W<channelScore>E"
                + "R<channelLoop>Duo <channelLabel> heeft <channelScore> punten.<br/><br/></channelLoop>Y"
                + "C<groupOtherMemberCodes>D";
        // todo: implement the channelLoop
        final String expectedString = "QGroupScoreWChannelScoreERDuo A-B heeft 6 punten.<br/><br/>Duo C-D heeft 2 punten.<br/><br/>YCA,B,C,D,E,FD";
        final HashMap<String, String> channelScores = new HashMap<>();
        channelScores.put("A-B", "6");
        channelScores.put("C-D", "2");
        HtmlTokenFormatter instance = new HtmlTokenFormatter(new GroupScoreService() {

            @Override
            public String getActiveChannel() {
                return "ActiveChannel";
            }

            @Override
            public String getAllMemberCodes() {
                return ",A,B,,C,D,,X,E,F,";
            }

            @Override
            public String getChannelScore() {
                return "ChannelScore";
            }

            @Override
            public String getChannelScore(String channel) {
                return channelScores.get(channel);
            }

            @Override
            public Set<String> getChannelScoreKeys() {
                return channelScores.keySet();
            }

            @Override
            public String getGroupCommunicationChannels() {
                return "GroupCommunicationChannels";
            }

            @Override
            public String getGroupScore() {
                return "GroupScore";
            }

            @Override
            public String getMemberCode() {
                return "X";
            }

            @Override
            public String getMessageString() {
                return "MessageString";
            }

            @Override
            public String getUserLabel() {
                return "UserLabel";
            }

            @Override
            public String getGroupId() {
                return "GroupId";
            }

        }, new UserData());
        final String formattedString = instance.formatString(inputString);
        System.out.println("expectedString:" + expectedString);
        System.out.println("formattedString: " + formattedString);
        assertEquals(expectedString, formattedString);
    }
}
