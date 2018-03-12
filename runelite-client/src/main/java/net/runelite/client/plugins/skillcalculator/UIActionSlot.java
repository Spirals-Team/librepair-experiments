/*
 * Copyright (c) 2018, Kruithne <kruithne@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.client.plugins.skillcalculator;

import net.runelite.client.ui.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

class UIActionSlot extends JPanel
{
	SkillDataEntry action;
	private JLabel uiLabelActions;
	private static final Dimension ICON_SIZE = new Dimension(32, 32);

	UIActionSlot(SkillDataEntry action)
	{
		this.action = action;

		BorderLayout layout = new BorderLayout();
		layout.setHgap(8);
		setLayout(layout);

		setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

		JLabel uiIcon = new JLabel();

		BufferedImage icon = SkillCalculator.cacheItemIcon.getIcon(action.icon);
		if (icon != null)
			uiIcon.setIcon(new ImageIcon(icon));

		uiIcon.setMinimumSize(ICON_SIZE);
		uiIcon.setMaximumSize(ICON_SIZE);
		uiIcon.setPreferredSize(ICON_SIZE);
		uiIcon.setHorizontalAlignment(JLabel.CENTER);
		add(uiIcon, BorderLayout.LINE_START);

		JPanel uiInfo = new JPanel(new GridLayout(2, 1));
		uiInfo.setOpaque(false);

		JLabel uiLabelName = new JLabel(action.name);
		uiInfo.add(uiLabelName);

		uiLabelActions = new JLabel("Unknown");
		uiLabelActions.setFont(FontManager.getRunescapeSmallFont());
		uiInfo.add(uiLabelActions);

		add(uiInfo, BorderLayout.CENTER);
	}

	void setSlotColor(Color color)
	{
		setBackground(color);
	}

	void setText(String text)
	{
		uiLabelActions.setText(text);
	}
}