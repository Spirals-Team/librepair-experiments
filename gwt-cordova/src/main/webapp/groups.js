/* 
 * Copyright (C) 2016 Max Planck Institute for Psycholinguistics
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
/*
 * @since Oct 26, 2016 2:08:45 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */

var stompClient = null;
var userId = Math.floor((1 + Math.random()) * 0x10000);
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#greetings").html("");
    $("#groupTarget").html("");
    $("#animateTarget").html("");
    if (connected) {
        $("#conversation").show();
        $("#groupTarget").append("<tr>" +
                "<td>userId</td>" +
                "<td>Group</td>" +
                "<td>groupUUID</td>" +
                "<td>screenId</td>" +
                "<td>Label</td>" +
                "<td>Members</td>" +
                "<td>Channels</td>" +
                "<td>MemberCode</td>" +
                "<td>OriginCode</td>" +
                "<td>Stimulus</td>" +
                "<td>Options</td>" +
                "<td>Response</td>" +
                "<td>ExpectedRespondents</td>" +
                "<td>ActualRespondents</td>" +
                "<td>stimulusIndex</td>" +
                "<td>stimuliList</td>" +
                "<td>originPhase</td>" +
                "<td>requestedPhase</td>" +
                "<td>message</td>" +
                "<td>Ready</td>" +
                "<td>eventMs</td>" +
                "</tr>");
        $("#unittestdata").append(
                "<tr><td>userId</td>" +
                "<td>screenId</td>" +
                "<td>userLabel</td>" +
                "<td>groupId</td>" +
                "<td>allMemberCodes</td>" +
                "<td>Channels</td>" +
                "<td>memberCode</td>" +
                "<td>originMemberCode</td>" +
                "<td>stimulusId</td>" +
                "<td>stimulusIndex</td>" +
                "<td>stimuliList</td>" +
                "<td>originPhase</td>" +
                "<td>requestedPhase</td>" +
                "<td>messageString</td>" +
                "<td>groupReady</td>" +
                "<td>responseStimulusId</td>" +
                "<td>expectedRespondents</td>" +
                "<td>actualRespondents</td>" +
                "<td>groupUUID</td></tr>");
    } else {
        $("#conversation").hide();
    }
}

function connect() {
    var socket = new SockJS('/multiparticipant/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        var progressDivBar = $("<div style='background: grey;' class='progressDivBar'>&nbsp;</div>");
        $("#animateTarget").append(progressDivBar);
        stompClient.subscribe('/shared/animation', function (greeting) {
            $("#startBar").prop("disabled", true);
            var contentData = JSON.parse(greeting.body);
            showData(contentData);
            progressDivBar.css("width", contentData.width + "px");
//            $("#send").hide();
        });
        stompClient.subscribe('/shared/group', function (greeting) {
            var contentData = JSON.parse(greeting.body);
//            console.log('greeting.body: ' + greeting.body);
//            console.log('contentData: ' + contentData);
            var usersTableRow = $("#userId" + contentData.userId);
            if (!usersTableRow.length) {
                $("#groupTarget").append("<tr id=\"userId" + contentData.userId + "\"></tr>");
                usersTableRow = $("#userId" + contentData.userId);
            }
            var usersTableCells =
                    "<td>" + contentData.userId +
                    "</td><td>" + contentData.groupId +
                    "</td><td>" + contentData.groupUUID +
                    "</td><td>" + contentData.screenId +
                    "</td><td>" + contentData.userLabel +
                    "</td><td>" + contentData.allMemberCodes +
                    "</td><td>" + contentData.groupCommunicationChannels +
                    "</td><td>" + contentData.memberCode +
                    "</td><td>" + contentData.originMemberCode +
                    "</td><td>" + contentData.stimulusId +
                    "</td><td>" + contentData.responseStimulusOptions +
                    "</td><td>" + contentData.responseStimulusId +
                    "</td><td>" + contentData.expectedRespondents +
                    "</td><td>" + contentData.actualRespondents +
                    "</td><td>" + contentData.stimulusIndex +
                    "</td><td>" + contentData.stimuliList +
                    "</td><td>" + contentData.originPhase +
                    "</td><td>" + contentData.requestedPhase +
                    "</td><td>" + contentData.messageString +
                    "</td><td>" + contentData.groupReady +
                    "</td><td>" + contentData.eventMs +
                    "</td>";
            var messageButtonCell = "<td><button class='btn btn-default' type='submit' onClick=\"messageGroup('" + contentData.userId + "','" + contentData.originPhase + "','" + contentData.requestedPhase + "','" + contentData.screenId + "','" + contentData.userLabel + "','" + contentData.groupId + "','" + contentData.allMemberCodes + "','" + contentData.memberCode + "','" + contentData.originMemberCode + "','" + contentData.stimulusId + "')\">message</button></td>";
            var addButtonCell = "<td><button class='btn btn-default' type='submit' onClick=\"messageGroup(Math.floor((1 + Math.random()) * 0x10000),'" + contentData.originPhase + "','" + contentData.requestedPhase + "','" + contentData.screenId + "',null,'" + contentData.groupId + "','" + contentData.allMemberCodes + "',null,null)\">add member</button></td>";
            usersTableRow.html(usersTableCells + messageButtonCell + addButtonCell);
            usersTableRow.css("outline-style", "solid");
            usersTableRow.css("outline-width", "5px");
            usersTableRow.css("outline-color", "green");
            usersTableRow.animate({outlineWidth: 0}, "slow");
//            var groupMemberDiv = $("<div style='background: grey;' class='progressDivBar'>&nbsp;</div>");
//            $("#groupTarget").append(groupMemberDiv);
            $("#unittestdata").append(
                    "<tr><td>\"" +
                    contentData.userId + "\", </td><td>\"" +
                    contentData.screenId + "\", </td><td>\"" +
                    contentData.userLabel + "\", </td><td>\"" +
                    contentData.groupId + "\", </td><td>\"" +
                    contentData.allMemberCodes + "\", </td><td>\"" +
                    contentData.groupCommunicationChannels + "\", </td><td>\"" +
                    contentData.memberCode + "\", </td><td>\"" +
                    contentData.originMemberCode + "\", </td><td>\"" +
                    contentData.stimulusId + "\", </td><td>\"" +
                    contentData.stimulusIndex + "\", </td><td>\"" +
                    contentData.stimuliList + "\", </td><td>\"" +
                    contentData.originPhase + "\", </td><td>\"" +
                    contentData.requestedPhase + "\", </td><td>\"" +
                    contentData.messageString + "\", </td><td>" +
                    contentData.groupReady + ", </td><td>\"" +
                    contentData.responseStimulusId + "\", </td><td>\"" +
                    contentData.expectedRespondents + "\", </td><td>\"" +
                    contentData.actualRespondents + "\", </td><td>\"" +
                    contentData.groupUUID + "\"</td></tr>");
            //String userId, 
            //String screenId,
            // String userLabel,
            //  String groupId, 
            //  String allMemberCodes,
            //   String memberCode, 
            //   String stimulusId, 
            //   String stimulusIndex, 
            //   String stimuliList, 
            //   String requestedPhase,
            //    String messageString,
            //     Boolean groupReady, 
            //     String responseStimulusId, 
            //     String groupUUID
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
    $("#groupTarget").append("<tr><td>Disconnected</td></tr>");
}
var stepCounter = 0;
function startBar() {
    stompClient.send("/app/shared", {}, JSON.stringify({
        'text': '',
        'fill': '',
        'cx': '',
        'width': stepCounter,
        'height': '',
        'left': '',
        'top': ''
    }));
    stepCounter++;
    if (stepCounter > 300)
        stepCounter = 0;
    setTimeout(startBar, 10);
}
function updateGroup() {
    stompClient.send("/app/group", {}, JSON.stringify({
        'userId': userId,
        'userLabel': null,
        'allMemberCodes': 'A,B,C,D,E,F,G',
        'memberCode': null,
        'stimulusId': Math.floor((1 + Math.random()) * 0x10000),
        'messageString': $("#messageString").val(),
        'groupReady': null
    }));
}
function messageGroup(currentUserId, requestedPhase, screenId, userLabel, groupId, allMemberCodes, memberCode, stimulusId) {
    stompClient.send("/app/group", {}, JSON.stringify({
        'userId': currentUserId,
        'userLabel': userLabel,
        'groupId': groupId,
        'screenId': screenId,
        'allMemberCodes': allMemberCodes,
        'memberCode': memberCode,
        'stimulusId': stimulusId,
        'messageString': $("#messageString").val(),
        'groupReady': null,
        'requestedPhase': requestedPhase
    }));
}

function showData(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#startBar").click(function () {
        startBar();
    });
    $("#join").click(function () {
        updateGroup();
    });
    $("#messageString").change(function () {
        updateGroup();
    });
    connect();
});
