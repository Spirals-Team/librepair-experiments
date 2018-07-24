/*
 * Copyright (C) 2015 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import nl.mpi.tg.eg.experiment.client.model.AnnotationData;

/**
 * @since Jan 29, 2014 3:34:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class VideoPanel extends VerticalPanel {

    final Video video;
// <video poster="Screen Shot 2014-01-16 at 7.46.33 PM.png" controls preload="none">
//                                    <source src="SSL_LM_lex_b.mp4" type="video/mp4" />
//                                </video>

    // GWT video is not yet fully supported and does not allow for event listeners to be registered so for now we are using HTML5 and JS directly
    public VideoPanel(String width, String poster, String src) {
        video = Video.createIfSupported();
        if (video != null) {
            video.setPoster(poster);
            video.setControls(true);
            video.setPreload(MediaElement.PRELOAD_AUTO);
            this.add(video);
        } else {
            this.add(new Label("Video is not supported"));
        }
        addSource(src + ".mp4", "video/mp4");
        addSource(src + ".ogg", "video/ogg");
        addSource(src + ".webm", "video/webm");
    }

    public final void addSource(String source, String type) {
        if (source != null && !source.isEmpty()) {
            video.addSource(source, type); // add multiple formats with the format type so that more devices will be supported
        }
    }

    public void setCurrentTime(double time) {
        if (video != null) {
            video.setCurrentTime(time);
        }
    }

    public double getCurrentTime() {
        if (video != null) {
            return video.getCurrentTime();
        } else {
            return 0;
        }
    }

    public double getDurationTime() {
        if (video != null) {
            return video.getDuration();
        } else {
            return 1;
        }
    }

    public void playSegment(AnnotationData annotationData) {
        video.setCurrentTime(annotationData.getInTime());
    }
}
