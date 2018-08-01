/*
 Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
 
 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.
 
 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.
 
 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
/*
 * @since Dec 9, 2015 4:22:44 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */

function FieldKitRecorder() {
}

FieldKitRecorder.prototype.isRecording = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "isRecording", []);
};
FieldKitRecorder.prototype.getTime = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "getTime", []);
};
FieldKitRecorder.prototype.startTag = function (successCallback, errorCallback, tierInt) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "startTag", [tierInt]);
};
FieldKitRecorder.prototype.endTag = function (successCallback, errorCallback, tierInt, stimulusId, stimulusCode, tagString) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "endTag", [tierInt, stimulusId, stimulusCode, tagString]);
};
FieldKitRecorder.prototype.stop = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "stop", []);
};
FieldKitRecorder.prototype.record = function (successCallback, errorCallback, userId, stimulusSet, stimulusId) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "record", [userId, stimulusSet, stimulusId]);
};
FieldKitRecorder.prototype.requestFilePermissions = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "requestFilePermissions", []);
};
FieldKitRecorder.prototype.requestRecorderPermissions = function (successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "requestRecorderPermissions", []);
};
FieldKitRecorder.prototype.writeStimuliData = function (successCallback, errorCallback, userId, stimulusId, stimuliData) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "writeStimuliData", [userId, stimulusId, stimuliData]);
};
FieldKitRecorder.prototype.writeCsvLine = function (userId, screenName, dataChannel, eventTag, tagValue1, tagValue2, eventMs) {
    cordova.exec(successCallback, errorCallback, "FieldKitRecorder", "writeCsvLine", [userId, screenName, dataChannel, eventTag, tagValue1, tagValue2, eventMs]);
};

FieldKitRecorder.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.fieldKitRecorder = new FieldKitRecorder();
    return window.plugins.fieldKitRecorder;
};

cordova.addConstructor(FieldKitRecorder.install);