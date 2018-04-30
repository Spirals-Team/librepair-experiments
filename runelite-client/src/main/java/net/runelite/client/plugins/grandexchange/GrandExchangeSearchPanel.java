/*
 * Copyright (c) 2018, Seth <https://github.com/sethtroll>
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

import com.google.common.base.Strings;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.client.game.AsyncBufferedImage;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.components.CustomScrollBarUI;
import net.runelite.client.ui.components.IconTextField;
import net.runelite.client.ui.components.PluginErrorPanel;
import net.runelite.http.api.item.Item;
import net.runelite.http.api.item.ItemClient;
import net.runelite.http.api.item.ItemPrice;
import net.runelite.http.api.item.SearchResult;

/**
 * This panel holds the search section of the Grand Exchange Plugin.
 * It should display a search bar and either item results or a label (Searching..)
 */
@Slf4j
class GrandExchangeSearchPanel extends JPanel
{
	private static final List<GrandExchangeItems> ITEMS_LIST = new ArrayList<>();

	private final Client client;
	private final ItemManager itemManager;
	private final ScheduledExecutorService executor;

	private ItemClient itemClient;

	private ImageIcon searchIcon;
	private ImageIcon loadingIcon;
	private ImageIcon errorIcon;

	private IconTextField searchBox = new IconTextField();
	private JPanel container = new JPanel();
	private JPanel searchItemsPanel = new JPanel();

	private JScrollPane resultsWrapper;
	/* the parent container's size, this gets updated on every client resize */
	private Dimension parentSize;

	private PluginErrorPanel errorPanel = new PluginErrorPanel();
	private static final int RESULT_ITEM_HEIGHT = 58 + 5; // 58 panel height, 5 margin

	GrandExchangeSearchPanel(Client client, ItemManager itemManager, ScheduledExecutorService executor)
	{
		this.client = client;
		this.itemManager = itemManager;
		this.executor = executor;
		init();
	}

	void init()
	{
		setLayout(new BorderLayout());
		container.setLayout(new BorderLayout(5, 5));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));
		container.setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		// Search Box
		try
		{
			BufferedImage icon;
			BufferedImage error;
			synchronized (ImageIO.class)
			{
				icon = ImageIO.read(GrandExchangePlugin.class.getResourceAsStream("search.png"));
				error = ImageIO.read(GrandExchangePlugin.class.getResourceAsStream("error.png"));
				loadingIcon = new ImageIcon(this.getClass().getResource("loading_spinner.gif"));
			}
			searchIcon = new ImageIcon(icon);
			errorIcon = new ImageIcon(error);
		}
		catch (IOException e)
		{
			log.warn("Failed to read icon", e);
		}

		searchBox.setPreferredSize(new Dimension(100, 30));
		searchBox.setBackground(ColorScheme.GRAND_EXCHANGE_BG_COLOR);
		searchBox.setHoverBackgroundColor(ColorScheme.GRAND_EXCHANGE_BG_COLOR.brighter());
		searchBox.setIcon(searchIcon);
		searchBox.addActionListener(e -> executor.execute(() -> priceLookup(false)));

		container.add(searchBox, BorderLayout.NORTH);

		// Items Panel
		searchItemsPanel.setLayout(new GridLayout(0, 1, 0, 5));
		searchItemsPanel.setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);

		resultsWrapper = new JScrollPane(searchItemsPanel);
		resultsWrapper.setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);
		resultsWrapper.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		resultsWrapper.getVerticalScrollBar().setUnitIncrement(16);
		resultsWrapper.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
		resultsWrapper.getVerticalScrollBar().setBorder(new EmptyBorder(0, 5, 0, 0));
		resultsWrapper.setVisible(false);

		container.add(resultsWrapper, BorderLayout.CENTER);

		container.add(errorPanel, BorderLayout.SOUTH);

		errorPanel.setContent("Grand Exchange Search",
			"Here you can search for an item by its name to find price information.");

		add(container, BorderLayout.NORTH);
	}

	void priceLookup(String item)
	{
		searchBox.setText(item);
		executor.execute(() -> priceLookup(true));
	}

	public void resizePanel(Dimension parentSize)
	{
		this.parentSize = parentSize;

		resultsWrapper.setPreferredSize(calculatePrefSize());
		resultsWrapper.revalidate();
		resultsWrapper.repaint();
	}

	/**
	 * This method calculates the prefered size for the scroll pane.
	 * I unfortunately cannot make the scroll panel resize automatically so I'll have to
	 * recalculate everytime the search changes or the client resizes.
	 * <p>
	 * If all items fit on screen, the size is set to their heights (58 height + 5 margin) combined,
	 * if not, the size will be set to the full panel height - 100 pixels (enough room for the search bar and tabs).
	 */
	private Dimension calculatePrefSize()
	{
		int availableHeight = parentSize.height - 100;

		if (ITEMS_LIST.isEmpty() || ITEMS_LIST.size() > (availableHeight / RESULT_ITEM_HEIGHT))
		{
			return new Dimension(100, availableHeight);
		}

		return new Dimension(100, ITEMS_LIST.size() * RESULT_ITEM_HEIGHT);
	}

	private void priceLookup(boolean exactMatch)
	{
		String lookup = searchBox.getText();

		if (Strings.isNullOrEmpty(lookup))
		{
			searchItemsPanel.removeAll();
			return;
		}

		// Input is not empty, add searching label
		searchItemsPanel.removeAll();
		searchBox.setBackground(ColorScheme.GRAND_EXCHANGE_BG_COLOR);
		searchBox.lockInput();
		searchBox.setIcon(loadingIcon);

		SearchResult result;

		try
		{
			result = itemManager.searchForItem(lookup);
		}
		catch (ExecutionException ex)
		{
			log.warn("Unable to search for item {}", lookup, ex);
			searchBox.setIcon(errorIcon);
			errorPanel.setContent("Error fetching results", "An error occured why trying to fetch item data, please try again later.");
			resultsWrapper.setVisible(false);
			return;
		}

		ITEMS_LIST.clear();

		if (result != null && !result.getItems().isEmpty())
		{
			errorPanel.setVisible(false);
			resultsWrapper.setVisible(true);

			itemClient = new ItemClient();

			for (Item item : result.getItems())
			{
				int itemId = item.getId();

				ItemComposition itemComp = client.getItemDefinition(itemId);
				if (itemComp == null)
				{
					continue;
				}

				ItemPrice itemPrice = null;
				try
				{
					itemPrice = itemManager.getItemPrice(itemId);
				}
				catch (IOException ex)
				{
					log.warn("Unable to fetch item price for {}", itemId, ex);
				}

				AsyncBufferedImage itemImage = itemManager.getImage(itemId);

				ITEMS_LIST.add(new GrandExchangeItems(itemImage, item.getName(), itemId, itemPrice != null ? itemPrice.getPrice() : 0, itemComp.getPrice() * 0.6));

				// If using hotkey to lookup item, stop after finding match.
				if (exactMatch && item.getName().equalsIgnoreCase(lookup))
				{
					break;
				}
			}
		}
		else
		{
			searchBox.setIcon(errorIcon);
			errorPanel.setContent("No results found.", "No items were found with that name, please try again.");
			resultsWrapper.setVisible(false);
		}

		SwingUtilities.invokeLater(() ->
		{

			// unfortunately, as I have not found a automatic way to make the scroll pane resize
			// to properly wrap the content, it has to recalculate its size every search
			resultsWrapper.setPreferredSize(calculatePrefSize());

			for (GrandExchangeItems item : ITEMS_LIST)
			{
				GrandExchangeItemPanel panel = new GrandExchangeItemPanel(item.getIcon(), item.getName(),
					item.getItemId(), item.getGePrice(), item.getHaPrice());

				searchItemsPanel.add(panel);
			}

			// remove focus from the search bar
			searchItemsPanel.requestFocusInWindow();
			searchBox.unlockInput();

			// Remove searching label after search is complete
			if (!ITEMS_LIST.isEmpty())
			{
				searchBox.setIcon(searchIcon);
			}

		});
	}

}