/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experimentdesigner.model.wizard;

/**
 * @since May 17, 2018 11:30:15 AM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilAudioTest {

    protected String menuLabel;
    protected String title;
    protected String text;
    protected String butonLabel;
    protected String butonStyle;
    protected String audioHotkey;
    protected String audioFile;
    protected String hotkey;
    protected boolean autoPlay;
    protected boolean autoNext;
    protected String backgroundImage;
    protected String backgroundStyle;
    protected int backgroundMs;
    protected String imageStyle;
    protected String imageName;

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getButonLabel() {
        return butonLabel;
    }

    public void setButonLabel(String butonLabel) {
        this.butonLabel = butonLabel;
    }

    public String getButonStyle() {
        return butonStyle;
    }

    public void setButonStyle(String butonStyle) {
        this.butonStyle = butonStyle;
    }

    public String getAudioHotkey() {
        return audioHotkey;
    }

    public void setAudioHotkey(String audioHotkey) {
        this.audioHotkey = audioHotkey;
    }

    public String getHotkey() {
        return hotkey;
    }

    public void setHotkey(String hotkey) {
        this.hotkey = hotkey;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isAutoNext() {
        return autoNext;
    }

    public void setAutoNext(boolean autoNext) {
        this.autoNext = autoNext;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getBackgroundStyle() {
        return backgroundStyle;
    }

    public void setBackgroundStyle(String backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
    }

    public int getBackgroundMs() {
        return backgroundMs;
    }

    public void setBackgroundMs(int backgroundMs) {
        this.backgroundMs = backgroundMs;
    }

    public String getImageStyle() {
        return imageStyle;
    }

    public void setImageStyle(String imageStyle) {
        this.imageStyle = imageStyle;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }
}
