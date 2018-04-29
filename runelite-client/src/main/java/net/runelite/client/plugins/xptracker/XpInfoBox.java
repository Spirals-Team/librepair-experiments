/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Psikoi <https://github.com/psikoi>
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
package net.runelite.client.plugins.xptracker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.ProgressBar;
import net.runelite.client.util.LinkBrowser;
import net.runelite.client.util.StackFormatter;

@Slf4j
class XpInfoBox extends JPanel
{
	private final Client client;
	private final JPanel panel;

	@Getter(AccessLevel.PACKAGE)
	private final SkillXPInfo xpInfo;


	/* The tracker's wrapping container */
	private final JPanel container = new JPanel();

	/* Contains the skill icon and the stats panel */
	private final JPanel headerPanel = new JPanel();

	/* Contains all the skill information (exp gained, per hour, etc) */
	private final JPanel statsPanel = new JPanel();

	private final ProgressBar progressBar = new ProgressBar();

	private final JLabel expGained = new JLabel();
	private final JLabel expHour = new JLabel();
	private final JLabel expLeft = new JLabel();
	private final JLabel actionsLeft = new JLabel();


	XpInfoBox(Client client, JPanel panel, SkillXPInfo xpInfo, SkillIconManager iconManager) throws IOException
	{
		this.client = client;
		this.panel = panel;
		this.xpInfo = xpInfo;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(10, 0, 0, 0));
		setOpaque(false);

		container.setLayout(new BorderLayout());
		container.setOpaque(true);
		container.setBackground(ColorScheme.DARKER_FRAME_COLOR);

		// Create open xp tracker menu
		final JMenuItem openXpTracker = new JMenuItem("Open online tracker");
		openXpTracker.addActionListener(e -> LinkBrowser.browse(XpPanel.buildXpTrackerUrl(client.getLocalPlayer(), xpInfo.getSkill())));

		// Create reset menu
		final JMenuItem reset = new JMenuItem("Reset");
		reset.addActionListener(e -> reset());

		// Create popup menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(openXpTracker);
		popupMenu.add(reset);
		container.setComponentPopupMenu(popupMenu);

		JLabel skillIcon = new JLabel(new ImageIcon(iconManager.getSkillImage(xpInfo.getSkill())));
		skillIcon.setHorizontalAlignment(SwingConstants.CENTER);
		skillIcon.setVerticalAlignment(SwingConstants.CENTER);
		skillIcon.setPreferredSize(new Dimension(35, 35));

		headerPanel.setOpaque(false);
		headerPanel.setLayout(new BorderLayout());

		statsPanel.setLayout(new BorderLayout());
		statsPanel.setBorder(new EmptyBorder(9, 5, 9, 10));
		statsPanel.setOpaque(false);

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(false);
		leftPanel.setLayout(new GridLayout(2, 1));

		expGained.setFont(FontManager.getRunescapeSmallFont());
		expHour.setFont(FontManager.getRunescapeSmallFont());

		leftPanel.add(expGained);
		leftPanel.add(expHour);

		JPanel rightPanel = new JPanel();
		rightPanel.setOpaque(false);
		rightPanel.setLayout(new GridLayout(2, 1));

		expLeft.setFont(FontManager.getRunescapeSmallFont());
		actionsLeft.setFont(FontManager.getRunescapeSmallFont());

		rightPanel.add(expLeft);
		rightPanel.add(actionsLeft);

		statsPanel.add(leftPanel, BorderLayout.WEST);
		statsPanel.add(rightPanel, BorderLayout.EAST);


		headerPanel.add(skillIcon, BorderLayout.WEST);
		headerPanel.add(statsPanel, BorderLayout.CENTER);


		JPanel progressWrapper = new JPanel();
		progressWrapper.setOpaque(false);
		progressWrapper.setLayout(new BorderLayout());
		progressWrapper.setBorder(new EmptyBorder(0, 7, 7, 7));


		progressBar.setMaximumValue(100);
		progressBar.setBackground(new Color(61, 56, 49));
		progressBar.setForeground(SkillColor.values()[xpInfo.getSkill().ordinal()].getColor());

		progressWrapper.add(progressBar, BorderLayout.NORTH);

		container.add(headerPanel, BorderLayout.NORTH);
		container.add(progressWrapper, BorderLayout.SOUTH);

		add(container, BorderLayout.NORTH);

	}

	void reset()
	{
		xpInfo.reset(client.getSkillExperience(xpInfo.getSkill()));
		panel.remove(this);
		panel.revalidate();
	}

	void init()
	{
		if (xpInfo.getStartXp() != -1)
		{
			return;
		}

		xpInfo.setStartXp(client.getSkillExperience(xpInfo.getSkill()));
	}

	void update()
	{
		if (xpInfo.getStartXp() == -1)
		{
			return;
		}

		boolean updated = xpInfo.update(client.getSkillExperience(xpInfo.getSkill()));

		SwingUtilities.invokeLater(() ->
		{
			if (updated)
			{
				if (getParent() != panel)
				{
					panel.add(this);
					panel.revalidate();
				}

				// Update information labels
				expGained.setText(htmlLabel("Gained: ", xpInfo.getXpGained()));
				expLeft.setText(htmlLabel("Left: ", xpInfo.getXpRemaining()));
				actionsLeft.setText(htmlLabel("Actions: ", xpInfo.getActionsRemaining()));

				// Update progress bar
				progressBar.setValue(xpInfo.getSkillProgress());
				progressBar.setCenterLabel(xpInfo.getSkillProgress() + "%");
				progressBar.setLeftLabel("Lvl. " + xpInfo.getLevel());
				progressBar.setRightLabel("Lvl. " + (xpInfo.getLevel() + 1));

				progressBar.setToolTipText("<html>"
					+ xpInfo.getActions() + " actions"
					+ "<br/>"
					+ xpInfo.getActionsHr() + " actions/hr"
					+ "<br/>"
					+ xpInfo.getTimeTillLevel() + " till next lvl"
					+ "</html>");

				progressBar.repaint();

			}

			// Update exp per hour seperately, everytime (not only when there's an update)
			expHour.setText(htmlLabel("Per hour: ", xpInfo.getXpHr()));
		});
	}

	public static String htmlLabel(String key, int value)
	{
		String valueStr = value + "";

		if (value > 9999999 || value < -9999999)
		{
			valueStr = "Lots!";
		}
		else
		{
			valueStr = StackFormatter.quantityToRSDecimalStack(value);
		}

		return "<html><body style = 'color:#a5a5a5'>" + key + "<span style = 'color:white'>" + valueStr + "</span></body></html>";
	}

}
