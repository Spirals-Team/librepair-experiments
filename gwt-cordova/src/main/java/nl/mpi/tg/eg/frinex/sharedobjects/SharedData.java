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
package nl.mpi.tg.eg.frinex.sharedobjects;

/**
 * @since Oct 26, 2016 2:00:21 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class SharedData {

    // user uuid generated in the client app (GWT) which is not sent back to the clients but is used to track who has locked the object
    private String userId;
    // the uuid for this request generated in the client app (GWT) and used by the client to determin if its request was accepted or rejected
    private String requestId;
    // if set to locked and sent to the server and the object is not already locked then the user will be granted the lock on the object
    private boolean isLocked;
    // id of the dom object being animated that is should be present in all client html doms
    private String domId;
    private String text;
    private String fill;
    private String cx;
    private String width;
    private String height;
    private String left;
    private String top;

    public SharedData() {
    }

    public SharedData(String text, String fill, String cx, String width, String height, String left, String top) {
        this.text = text;
        this.fill = fill;
        this.cx = cx;
        this.width = width;
        this.height = height;
        this.left = left;
        this.top = top;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }
}
