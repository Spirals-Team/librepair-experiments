/*
 * fassets - Project for light-weight tracking of fixed assets
 * Copyright © 2018 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.fasset.fasset.kernel.notifications;

import java.util.Objects;

/**
 * Notification object containing properties of a file recently uploaded into the back end
 */
public class FileUploadNotification {

    /* the name and location of file*/
    private String fileName;

    /* month to which the file relates*/
    private String month;

    /* time of upload */
    private String timeUploaded;


    public FileUploadNotification(String fileName, String month, String timeUploaded) {
        this.fileName = fileName;
        this.month = month;
        this.timeUploaded = timeUploaded;
    }

    public FileUploadNotification() {
    }

    public String getFileName() {
        return fileName;
    }

    public String getMonth() {
        return month;
    }

    public String getTimeUploaded() {
        return timeUploaded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FileUploadNotification that = (FileUploadNotification) o;
        return Objects.equals(fileName, that.fileName) && Objects.equals(month, that.month) && Objects.equals(timeUploaded, that.timeUploaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, month, timeUploaded);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FileUploadNotification{");
        sb.append("fileName='").append(fileName).append('\'');
        sb.append(", month='").append(month).append('\'');
        sb.append(", timeUploaded='").append(timeUploaded).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
