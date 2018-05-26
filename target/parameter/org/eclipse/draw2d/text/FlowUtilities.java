package org.eclipse.draw2d.text;


public class FlowUtilities {
    interface LookAhead {
        int getWidth();
    }

    public static org.eclipse.draw2d.text.FlowUtilities INSTANCE = new org.eclipse.draw2d.text.FlowUtilities();

    private static final com.ibm.icu.text.BreakIterator INTERNAL_LINE_BREAK = com.ibm.icu.text.BreakIterator.getLineInstance();

    private static org.eclipse.swt.graphics.TextLayout layout;

    static final com.ibm.icu.text.BreakIterator LINE_BREAK = com.ibm.icu.text.BreakIterator.getLineInstance();

    static boolean canBreakAfter(char c) {
        boolean result = (java.lang.Character.isWhitespace(c)) || (c == '-');
        if ((!result) && ((c < 'a') || (c > 'z'))) {
            org.eclipse.draw2d.text.FlowUtilities.LINE_BREAK.setText((c + "a"));
            result = org.eclipse.draw2d.text.FlowUtilities.LINE_BREAK.isBoundary(1);
        }
        return result;
    }

    private static int findFirstDelimeter(java.lang.String string) {
        int macNL = string.indexOf('\r');
        int unixNL = string.indexOf('\n');
        if (macNL == (-1))
            macNL = java.lang.Integer.MAX_VALUE;

        if (unixNL == (-1))
            unixNL = java.lang.Integer.MAX_VALUE;

        return java.lang.Math.min(macNL, unixNL);
    }

    protected float getAverageCharWidth(org.eclipse.draw2d.text.TextFragmentBox fragment, org.eclipse.swt.graphics.Font font) {
        if (((fragment.getWidth()) > 0) && ((fragment.length) != 0))
            return (fragment.getWidth()) / ((float) (fragment.length));

        return org.eclipse.draw2d.FigureUtilities.getFontMetrics(font).getAverageCharWidth();
    }

    static int getBorderAscent(org.eclipse.draw2d.text.InlineFlow owner) {
        if ((owner.getBorder()) instanceof org.eclipse.draw2d.text.FlowBorder) {
            org.eclipse.draw2d.text.FlowBorder border = ((org.eclipse.draw2d.text.FlowBorder) (owner.getBorder()));
            return border.getInsets(owner).top;
        }
        return 0;
    }

    static int getBorderAscentWithMargin(org.eclipse.draw2d.text.InlineFlow owner) {
        if ((owner.getBorder()) instanceof org.eclipse.draw2d.text.FlowBorder) {
            org.eclipse.draw2d.text.FlowBorder border = ((org.eclipse.draw2d.text.FlowBorder) (owner.getBorder()));
            return (border.getTopMargin()) + (border.getInsets(owner).top);
        }
        return 0;
    }

    static int getBorderDescent(org.eclipse.draw2d.text.InlineFlow owner) {
        if ((owner.getBorder()) instanceof org.eclipse.draw2d.text.FlowBorder) {
            org.eclipse.draw2d.text.FlowBorder border = ((org.eclipse.draw2d.text.FlowBorder) (owner.getBorder()));
            return border.getInsets(owner).bottom;
        }
        return 0;
    }

    static int getBorderDescentWithMargin(org.eclipse.draw2d.text.InlineFlow owner) {
        if ((owner.getBorder()) instanceof org.eclipse.draw2d.text.FlowBorder) {
            org.eclipse.draw2d.text.FlowBorder border = ((org.eclipse.draw2d.text.FlowBorder) (owner.getBorder()));
            return (border.getBottomMargin()) + (border.getInsets(owner).bottom);
        }
        return 0;
    }

    static org.eclipse.swt.graphics.TextLayout getTextLayout() {
        if ((org.eclipse.draw2d.text.FlowUtilities.layout) == null)
            org.eclipse.draw2d.text.FlowUtilities.layout = new org.eclipse.swt.graphics.TextLayout(org.eclipse.swt.widgets.Display.getDefault());

        org.eclipse.draw2d.text.FlowUtilities.layout.setOrientation(SWT.LEFT_TO_RIGHT);
        return org.eclipse.draw2d.text.FlowUtilities.layout;
    }

    private static void initBidi(org.eclipse.draw2d.text.TextFragmentBox frag, java.lang.String string, org.eclipse.swt.graphics.Font font) {
        if (frag.requiresBidi()) {
            org.eclipse.swt.graphics.TextLayout textLayout = org.eclipse.draw2d.text.FlowUtilities.getTextLayout();
            textLayout.setFont(font);
            textLayout.setText(string);
        }
    }

    private int measureString(org.eclipse.draw2d.text.TextFragmentBox frag, java.lang.String string, int guess, org.eclipse.swt.graphics.Font font) {
        if (frag.requiresBidi()) {
            return getTextLayoutBounds(string, font, 0, (guess - 1)).width;
        }else
            return getTextUtilities().getStringExtents(string.substring(0, guess), font).width;

    }

    protected final void setupFragment(org.eclipse.draw2d.text.TextFragmentBox fragment, org.eclipse.swt.graphics.Font font, java.lang.String string) {
        if (((fragment.getWidth()) == (-1)) || (fragment.isTruncated())) {
            int width;
            if (((string.length()) == 0) || ((fragment.length) == 0))
                width = 0;
            else
                if (fragment.requiresBidi()) {
                    width = getTextLayoutBounds(string, font, 0, ((fragment.length) - 1)).width;
                }else
                    width = getTextUtilities().getStringExtents(string.substring(0, fragment.length), font).width;


            if (fragment.isTruncated())
                width += getEllipsisWidth(font);

            fragment.setWidth(width);
        }
    }

    protected final int wrapFragmentInContext(org.eclipse.draw2d.text.TextFragmentBox frag, java.lang.String string, org.eclipse.draw2d.text.FlowContext context, org.eclipse.draw2d.text.FlowUtilities.LookAhead lookahead, org.eclipse.swt.graphics.Font font, int wrapping) {
        frag.setTruncated(false);
        int strLen = string.length();
        if (strLen == 0) {
            frag.setWidth((-1));
            frag.length = 0;
            setupFragment(frag, font, string);
            context.addToCurrentLine(frag);
            return 0;
        }
        org.eclipse.draw2d.text.FlowUtilities.INTERNAL_LINE_BREAK.setText(string);
        org.eclipse.draw2d.text.FlowUtilities.initBidi(frag, string, font);
        float avgCharWidth = getAverageCharWidth(frag, font);
        frag.setWidth((-1));
        int absoluteMin = 0;
        int max;
        int min = 1;
        if (wrapping == (ParagraphTextLayout.WORD_WRAP_HARD)) {
            absoluteMin = org.eclipse.draw2d.text.FlowUtilities.INTERNAL_LINE_BREAK.next();
            while ((absoluteMin > 0) && (java.lang.Character.isWhitespace(string.charAt((absoluteMin - 1)))))
                absoluteMin--;

            min = java.lang.Math.max(absoluteMin, 1);
        }
        int firstDelimiter = org.eclipse.draw2d.text.FlowUtilities.findFirstDelimeter(string);
        if (firstDelimiter == 0)
            min = max = 0;
        else
            max = (java.lang.Math.min(strLen, firstDelimiter)) + 1;

        int availableWidth = context.getRemainingLineWidth();
        int guess = 0;
        int guessSize = 0;
        while (true) {
            if ((max - min) <= 1) {
                if ((((min == absoluteMin) && (context.isCurrentLineOccupied())) && (!(context.getContinueOnSameLine()))) && (availableWidth < ((measureString(frag, string, min, font)) + ((min == strLen) && (lookahead != null) ? lookahead.getWidth() : 0)))) {
                    context.endLine();
                    availableWidth = context.getRemainingLineWidth();
                    max = (java.lang.Math.min(strLen, firstDelimiter)) + 1;
                    if ((max - min) <= 1)
                        break;

                }else
                    break;

            }
            guess += 0.5F + ((availableWidth - guessSize) / avgCharWidth);
            if (guess >= max)
                guess = max - 1;

            if (guess <= min)
                guess = min + 1;

            guessSize = measureString(frag, string, guess, font);
            if ((((guess == strLen) && (lookahead != null)) && (!(org.eclipse.draw2d.text.FlowUtilities.canBreakAfter(string.charAt((strLen - 1)))))) && ((guessSize + (lookahead.getWidth())) > availableWidth)) {
                max = guess;
                continue;
            }
            if (guessSize <= availableWidth) {
                min = guess;
                frag.setWidth(guessSize);
                if (guessSize == availableWidth)
                    max = guess + 1;

            }else
                max = guess;

        } 
        int result = min;
        boolean continueOnLine = false;
        if (min == strLen) {
            if ((string.charAt((strLen - 1))) == ' ') {
                if ((frag.getWidth()) == (-1)) {
                    frag.length = result;
                    frag.setWidth(measureString(frag, string, result, font));
                }
                if ((lookahead.getWidth()) > (availableWidth - (frag.getWidth()))) {
                    frag.length = result - 1;
                    frag.setWidth((-1));
                }else
                    frag.length = result;

            }else {
                continueOnLine = !(org.eclipse.draw2d.text.FlowUtilities.canBreakAfter(string.charAt((strLen - 1))));
                frag.length = result;
            }
        }else
            if (min == firstDelimiter) {
                frag.length = result;
                if ((string.charAt(min)) == '\r') {
                    result++;
                    if (((++min) < strLen) && ((string.charAt(min)) == '\n'))
                        result++;

                }else
                    if ((string.charAt(min)) == '\n')
                        result++;


            }else
                if ((((string.charAt(min)) == ' ') || (org.eclipse.draw2d.text.FlowUtilities.canBreakAfter(string.charAt((min - 1))))) || (org.eclipse.draw2d.text.FlowUtilities.INTERNAL_LINE_BREAK.isBoundary(min))) {
                    frag.length = min;
                    if ((string.charAt(min)) == ' ')
                        result++;
                    else
                        if ((string.charAt((min - 1))) == ' ') {
                            (frag.length)--;
                            frag.setWidth((-1));
                        }

                }else out : {
                    result = org.eclipse.draw2d.text.FlowUtilities.INTERNAL_LINE_BREAK.preceding(min);
                    if (result == 0) {
                        switch (wrapping) {
                            case ParagraphTextLayout.WORD_WRAP_TRUNCATE :
                                int truncatedWidth = availableWidth - (getEllipsisWidth(font));
                                if (truncatedWidth > 0) {
                                    while (min > 0) {
                                        guessSize = measureString(frag, string, min, font);
                                        if (guessSize <= truncatedWidth)
                                            break;

                                        min--;
                                    } 
                                    frag.length = min;
                                }else
                                    frag.length = 0;

                                frag.setTruncated(true);
                                result = org.eclipse.draw2d.text.FlowUtilities.INTERNAL_LINE_BREAK.following((max - 1));
                                break out;
                            default :
                                result = min;
                                break;
                        }
                    }
                    frag.length = result;
                    if ((string.charAt((result - 1))) == ' ')
                        (frag.length)--;

                    frag.setWidth((-1));
                }


        setupFragment(frag, font, string);
        context.addToCurrentLine(frag);
        context.setContinueOnSameLine(continueOnLine);
        return result;
    }

    protected org.eclipse.swt.graphics.Rectangle getTextLayoutBounds(java.lang.String s, org.eclipse.swt.graphics.Font f, int start, int end) {
        org.eclipse.swt.graphics.TextLayout textLayout = org.eclipse.draw2d.text.FlowUtilities.getTextLayout();
        textLayout.setFont(f);
        textLayout.setText(s);
        return textLayout.getBounds(start, end);
    }

    protected org.eclipse.draw2d.TextUtilities getTextUtilities() {
        return org.eclipse.draw2d.TextUtilities.INSTANCE;
    }

    private int getEllipsisWidth(org.eclipse.swt.graphics.Font font) {
        return getTextUtilities().getStringExtents(TextFlow.ELLIPSIS, font).width;
    }
}

