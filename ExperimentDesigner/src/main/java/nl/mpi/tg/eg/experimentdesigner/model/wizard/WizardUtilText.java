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
 * @since Apr 23, 2018 4:18:11 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class WizardUtilText {

    protected String menuLabel;
    protected String title;
    protected String text;
    protected String butonLabel;
    protected String hotkey;
    protected boolean showBackButton = true;

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

    public String getHotkey() {
        return hotkey;
    }

    public void setHotkey(String hotkey) {
        this.hotkey = hotkey;
    }

    public boolean isShowBackButton() {
        return showBackButton;
    }

    public void setShowBackButton(boolean showBackButton) {
        this.showBackButton = showBackButton;
    }
}
