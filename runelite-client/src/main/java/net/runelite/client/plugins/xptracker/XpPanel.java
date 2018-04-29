/*
 * Copyright (c) 2017, Cameron <moberg@tuta.io>
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
import java.awt.GridLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.util.LinkBrowser;
import okhttp3.HttpUrl;

@Slf4j
class XpPanel extends PluginPanel
{
	private final Map<Skill, XpInfoBox> infoBoxes = new HashMap<>();

	private final JLabel overallExpGained = new JLabel(XpInfoBox.htmlLabel("Gained: ", 0));
	private final JLabel overallExpHour = new JLabel(XpInfoBox.htmlLabel("Per hour: ", 0));

	final JPanel overallPanel = new JPanel();

	/* This displays the "No exp gained" text */
	final PluginErrorPanel errorPanel = new PluginErrorPanel();

	XpPanel(XpTrackerPlugin xpTrackerPlugin, Client client, SkillIconManager iconManager)
	{
		super();

		setBorder(new EmptyBorder(10, 10, 10, 10));
		setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		final JPanel layoutPanel = new JPanel();
		layoutPanel.setOpaque(false);
		layoutPanel.setLayout(new BorderLayout());
		add(layoutPanel);

		overallPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		overallPanel.setBackground(ColorScheme.DARKER_FRAME_COLOR);
		overallPanel.setLayout(new BorderLayout());
		overallPanel.setVisible(false); // this will only become visible when the player gets exp

		// Create open xp tracker menu
		final JMenuItem openXpTracker = new JMenuItem("Open online tracker");
		openXpTracker.addActionListener(e -> LinkBrowser.browse(XpPanel.buildXpTrackerUrl(client.getLocalPlayer(), Skill.OVERALL)));

		// Create reset all menu
		final JMenuItem reset = new JMenuItem("Reset All");
		reset.addActionListener(e -> resetAllInfoBoxes());

		// Create popup menu
		final JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
		popupMenu.add(openXpTracker);
		popupMenu.add(reset);
		overallPanel.setComponentPopupMenu(popupMenu);

		final JLabel overallIcon = new JLabel(new ImageIcon(iconManager.getSkillImage(Skill.OVERALL)));

		final JPanel overallInfo = new JPanel();
		overallInfo.setOpaque(false);
		overallInfo.setLayout(new GridLayout(2, 1));
		overallInfo.setBorder(new EmptyBorder(0, 10, 0, 0));

		overallExpGained.setFont(FontManager.getRunescapeSmallFont());
		overallExpHour.setFont(FontManager.getRunescapeSmallFont());

		overallInfo.add(overallExpGained);
		overallInfo.add(overallExpHour);

		overallPanel.add(overallIcon, BorderLayout.WEST);
		overallPanel.add(overallInfo, BorderLayout.CENTER);


		final JPanel infoBoxPanel = new JPanel();
		infoBoxPanel.setOpaque(false);
		infoBoxPanel.setLayout(new BoxLayout(infoBoxPanel, BoxLayout.Y_AXIS));
		layoutPanel.add(infoBoxPanel, BorderLayout.CENTER);
		layoutPanel.add(overallPanel, BorderLayout.NORTH);

		try
		{
			for (Skill skill : Skill.values())
			{
				if (skill == Skill.OVERALL)
				{
					break;
				}
				infoBoxes.put(skill, new XpInfoBox(client, infoBoxPanel, xpTrackerPlugin.getSkillXpInfo(skill), iconManager));
			}
		}
		catch (IOException e)
		{
			log.warn(null, e);
		}

		errorPanel.setContent("Exp trackers", "You have not gained experience yet.");
		errorPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(errorPanel);

	}

	static String buildXpTrackerUrl(final Actor player, final Skill skill)
	{
		if (player == null)
		{
			return "";
		}

		return new HttpUrl.Builder()
			.scheme("https")
			.host("runelite.net")
			.addPathSegment("xp")
			.addPathSegment("show")
			.addPathSegment(skill.getName().toLowerCase())
			.addPathSegment(player.getName())
			.addPathSegment("1week")
			.addPathSegment("now")
			.build()
			.toString();
	}

	void resetAllInfoBoxes()
	{
		infoBoxes.forEach((skill, xpInfoBox) -> xpInfoBox.reset());
		updateTotal();
	}

	void updateAllInfoBoxes()
	{
		infoBoxes.forEach((skill, xpInfoBox) -> xpInfoBox.update());
		updateTotal();
	}

	void updateSkillExperience(Skill skill)
	{
		final XpInfoBox xpInfoBox = infoBoxes.get(skill);
		xpInfoBox.update();
		xpInfoBox.init();
		updateTotal();
	}

	private void updateTotal()
	{

		//this determines if any exp has been gained
		boolean active = false;

		final AtomicInteger totalXpGainedVal = new AtomicInteger();
		final AtomicInteger totalXpHrVal = new AtomicInteger();

		for (XpInfoBox xpInfoBox : infoBoxes.values())
		{
			if (xpInfoBox.getXpInfo().getXpGained() > 0)
			{
				active = true;
			}
			totalXpGainedVal.addAndGet(xpInfoBox.getXpInfo().getXpGained());
			totalXpHrVal.addAndGet(xpInfoBox.getXpInfo().getXpHr());
		}

		// if player has gained exp and hasn't switched displays yet, hide error panel and show overall info
		if (active && !overallPanel.isVisible())
		{
			overallPanel.setVisible(true);
			remove(errorPanel);
		}

		SwingUtilities.invokeLater(() ->
		{
			overallExpGained.setText(XpInfoBox.htmlLabel("Gained: ", totalXpGainedVal.get()));
			overallExpHour.setText(XpInfoBox.htmlLabel("Per hour: ", totalXpHrVal.get()));
		});

	}
}
