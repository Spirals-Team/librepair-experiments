/*
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
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

package net.runelite.client.plugins.grandexchange;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.client.ui.components.materialtabs.MaterialTab;
import net.runelite.client.ui.components.materialtabs.MaterialTabGroup;

@Slf4j
class GrandExchangePanel extends PluginPanel
{
	private static final int MAX_OFFERS = 8;

	@Getter
	private GrandExchangeSearchPanel searchPanel;

	private GrandExchangeOfferSlot[] offerSlotPanels = new GrandExchangeOfferSlot[MAX_OFFERS];

	// this panel will hold either the ge search panel or the ge offers panel
	private final JPanel display = new JPanel();

	private JPanel offerPanel = new JPanel();

	private PluginErrorPanel errorPanel = new PluginErrorPanel();

	private final MaterialTabGroup tabGroup = new MaterialTabGroup(display);
	private MaterialTab searchTab;

	@Inject
	GrandExchangePanel(Client client, ItemManager itemManager, ScheduledExecutorService executor)
	{
		//disable the default scroll bar to implement my own, ONLY on the results
		//and not on the whole page (tabs and search box stay in place when scrolling)
		getScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		// Offer Panel
		offerPanel.setLayout(new GridLayout(8, 1, 0, 7));
		offerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		offerPanel.setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		errorPanel.setBorder(new EmptyBorder(50, 20, 20, 20));
		errorPanel.setContent("No offers detected", "No grand exchange offers were found on your account.");

		// Search Panel
		searchPanel = new GrandExchangeSearchPanel(client, itemManager, executor);

		MaterialTab offersTab = new MaterialTab("Offers", tabGroup, offerPanel);
		searchTab = new MaterialTab("Search", tabGroup, searchPanel);

		tabGroup.addTab(offersTab);
		tabGroup.addTab(searchTab);

		// selects the default selected tab
		tabGroup.select(offersTab);

		setLayout(new BorderLayout());
		add(tabGroup, BorderLayout.NORTH);

		add(display, BorderLayout.CENTER);

		getScrollPane().addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent componentEvent)
			{
				super.componentResized(componentEvent);
				searchPanel.resizePanel(getScrollPane().getSize());
			}
		});

		setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		updateEmptyOffersPanel();
	}

	void updateOffer(ItemComposition item, BufferedImage itemImage, GrandExchangeOffer newOffer, int slot)
	{
		if (newOffer == null || newOffer.getState() == GrandExchangeOfferState.EMPTY)
		{

			if (offerSlotPanels[slot] != null)
			{
				offerPanel.remove(offerSlotPanels[slot]);
				offerSlotPanels[slot] = null;
				revalidate();
				repaint();
			}

			updateEmptyOffersPanel();
			return;
		}

		if (offerSlotPanels[slot] == null)
		{
			GrandExchangeOfferSlot newSlot = new GrandExchangeOfferSlot();
			offerSlotPanels[slot] = newSlot;
			offerPanel.add(newSlot);
		}
		offerSlotPanels[slot].updateOffer(item, itemImage, newOffer);

		revalidate();
		repaint();

		updateEmptyOffersPanel();
	}

	private void updateEmptyOffersPanel()
	{
		int nullCount = 0;
		for (GrandExchangeOfferSlot slot : offerSlotPanels)
		{
			if (slot == null)
			{
				nullCount++;
			}
		}

		if (nullCount == MAX_OFFERS)
		{
			offerPanel.add(errorPanel);
		}
		else
		{
			if (errorPanel.getParent() != null)
			{
				offerPanel.remove(errorPanel);
			}
		}

	}

	void showSearch()
	{
		if (searchPanel.isShowing())
		{
			return;
		}

		tabGroup.select(searchTab);
		revalidate();
	}
}