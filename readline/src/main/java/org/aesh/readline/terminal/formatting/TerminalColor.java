/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aesh.readline.terminal.formatting;

import org.aesh.utils.ANSI;

import java.io.PrintStream;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class TerminalColor {

    private Color textColor = Color.DEFAULT;
    private int intTextColor = -1;
    private Color backgroundColor = Color.DEFAULT;
    private int intBackgroundColor = -1;
    private int length = -1;
    private String cache;

    private Color.Intensity intensity = Color.Intensity.NORMAL;

    public TerminalColor() {
    }

    public TerminalColor(Color text, Color background) {
        if(text != null)
            this.textColor = text;
        if(background != null)
            this.backgroundColor = background;
    }

    public TerminalColor(Color textColor, Color background, Color.Intensity intensity) {
        this(textColor, background);
        this.intensity = intensity;
    }

    /**
     * 0x00-0x07:  standard colors (as in ESC [ 30..37 m)
     * 0x08-0x0f:  high intensity colors (as in ESC [ 90..97 m)
     * 0x10-0xe7:  6*6*6=216 colors: 16 + 36*r + 6*g + b (0≤r,g,b≤5)
     * 0xe8-0xff:  grayscale from black to white in 24 steps
     *
     * @param text
     * @param background
     */
    public TerminalColor(int text, int background) {
        this.intTextColor = text;
        this.intBackgroundColor = background;
    }

    /**
     * 0x00-0x07:  standard colors (as in ESC [ 30..37 m)
     * 0x08-0x0f:  high intensity colors (as in ESC [ 90..97 m)
     * 0x10-0xe7:  6*6*6=216 colors: 16 + 36*r + 6*g + b (0≤r,g,b≤5)
     * 0xe8-0xff:  grayscale from black to white in 24 steps
     *
     * @param text
     * @param background
     */
    public TerminalColor(int text, Color background) {
        this.intTextColor = text;
        this.backgroundColor = background;
    }

    public TerminalColor(int text, Color background, Color.Intensity intensity) {
        this.intTextColor = text;
        this.backgroundColor = background;
        this.intensity = intensity;
    }

    public TerminalColor(Color text, int background) {
        this.textColor = text;
        this.intBackgroundColor = background;
    }

    public TerminalColor(Color text, int background, Color.Intensity intensity) {
        this.textColor = text;
        this.intBackgroundColor = background;
        this.intensity = intensity;
    }

    public boolean isFormatted() {
        return !(textColor == Color.DEFAULT && backgroundColor == Color.DEFAULT && intensity == Color.Intensity.NORMAL);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TerminalColor)) return false;

        TerminalColor that = (TerminalColor) o;

        return intBackgroundColor == that.intBackgroundColor &&
                intTextColor == that.intTextColor &&
                backgroundColor == that.backgroundColor &&
                intensity == that.intensity &&
                textColor == that.textColor;
    }

    @Override
    public int hashCode() {
        int result = textColor.hashCode();
        result = 31 * result + intTextColor;
        result = 31 * result + backgroundColor.hashCode();
        result = 31 * result + intBackgroundColor;
        result = 31 * result + intensity.hashCode();
        return result;
    }

    public String fullString() {
       return ANSI.START+toString()+"m";
    }

    public String toString() {
        if(cache != null)
            return cache;
        StringBuilder builder = new StringBuilder();
        if(intTextColor > -1) {
            builder.append(38).append(';').append(5).append(';').append(intTextColor);
        }
        else {
            builder.append(intensity.getValue(Color.Type.FOREGROUND)).append(textColor.getValue());
        }
        builder.append(';');
        if(intBackgroundColor > -1) {
            builder.append(48).append(';').append(5).append(';').append(intBackgroundColor);
        }
        else {
            builder.append(intensity.getValue(Color.Type.BACKGROUND)).append(backgroundColor.getValue());
        }

        cache = builder.toString();
        length = cache.length();

        return cache;
    }

    public int getLength() {
        if(length < 0)
            toString();
        return length;
    }

    public void write(PrintStream out) {
        out.print(toString());
    }

    public String toString(TerminalColor prev) {
        if(this.equals(prev))
            return "";
        else {

            String txt = textString(prev);
            String bg = backgroundString(prev);
            if(txt.length() > 0 && bg.length() > 0)
                return textString(prev) +';'+ backgroundString(prev);
            else
                return textString(prev) + backgroundString(prev);
        }
    }

    private String backgroundString(TerminalColor prev) {
        StringBuilder builder = new StringBuilder();
        if(prev.intBackgroundColor == intBackgroundColor &&
                prev.backgroundColor == backgroundColor &&
                prev.intensity == intensity) {
        }
        else if(prev.intBackgroundColor > -1 || intBackgroundColor > -1) {
            if(prev.intBackgroundColor == intBackgroundColor)
                builder.append("");
            if(prev.intBackgroundColor != intBackgroundColor && intBackgroundColor > -1)
                builder.append(38).append(';').append(5).append(';').append(intBackgroundColor);
            else
                builder.append(intensity.getValue(Color.Type.BACKGROUND)).append(backgroundColor.getValue());
        }
        else {
            if(prev.backgroundColor != backgroundColor) {
                builder.append(intensity.getValue(Color.Type.BACKGROUND)).append(backgroundColor.getValue());
            }
        }
        return builder.toString();
    }

    private String textString(TerminalColor prev) {
        StringBuilder builder = new StringBuilder();
        if(prev.intTextColor == intTextColor &&
                prev.textColor == textColor &&
                prev.intensity == intensity) {
        }
        else if(prev.intTextColor > -1 || intTextColor > -1) {
            if(prev.intTextColor == intTextColor)
                builder.append("");
            if(prev.intTextColor != intTextColor && intTextColor > -1)
                builder.append(38).append(';').append(5).append(';').append(intTextColor);
            else
                builder.append(intensity.getValue(Color.Type.FOREGROUND)).append(textColor.getValue());
        }
        else {
            if(prev.textColor != textColor) {
                builder.append(intensity.getValue(Color.Type.FOREGROUND)).append(textColor.getValue());
            }
        }
        return builder.toString();
    }

}
