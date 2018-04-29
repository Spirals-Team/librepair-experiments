/*
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.GrandExchangeOffer;
import net.runelite.api.GrandExchangeOfferState;
import static net.runelite.api.GrandExchangeOfferState.CANCELLED_BUY;
import static net.runelite.api.GrandExchangeOfferState.CANCELLED_SELL;
import static net.runelite.api.GrandExchangeOfferState.EMPTY;
import net.runelite.api.ItemComposition;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.ThinProgressBar;
import net.runelite.client.util.StackFormatter;

@Slf4j
public class GrandExchangeOfferSlot extends JPanel
{

	private static final String FACE_CARD = "FACE_CARD";
	private static final String DETAILS_CARD = "DETAILS_CARD";

	private JPanel container = new JPanel();
	private final CardLayout cardLayout = new CardLayout();

	private final JLabel itemIcon = new JLabel();
	private final JLabel itemName = new JLabel();
	private final JLabel offerInfo = new JLabel();
	private final JLabel switchFaceViewIcon = new JLabel();

	private final JLabel itemPrice = new JLabel();
	private final JLabel offerSpent = new JLabel();
	private final JLabel switchDetailsViewIcon = new JLabel();

	private final ThinProgressBar progressBar = new ThinProgressBar();

	private ImageIcon rightArrowIcon;
	private ImageIcon leftArrowIcon;

	private boolean showingFace = true;

	/**
	 * This (sub)panel is used for each GE slot displayed
	 * in the sidebar
	 */
	GrandExchangeOfferSlot()
	{
		buildPanel();
	}

	private void buildPanel()
	{

		setLayout(new BorderLayout());
		container.setLayout(cardLayout);

		try
		{
			BufferedImage rightImg;
			BufferedImage leftImg;
			synchronized (ImageIO.class)
			{
				rightImg = ImageIO.read(GrandExchangePlugin.class.getResourceAsStream("arrow_right.png"));
				leftImg = ImageIO.read(GrandExchangePlugin.class.getResourceAsStream("arrow_left.png"));
			}
			rightArrowIcon = new ImageIcon(rightImg);
			leftArrowIcon = new ImageIcon(leftImg);
		}
		catch (IOException e)
		{
			log.warn("Failed to read icon", e);
		}

		JPanel faceCard = new JPanel();
		faceCard.setBackground(ColorScheme.GRAND_EXCHANGE_BG_COLOR);
		faceCard.setLayout(new BorderLayout());
		faceCard.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				super.mousePressed(mouseEvent);
				switchPanel();
			}
		});

		itemIcon.setVerticalAlignment(JLabel.CENTER);
		itemIcon.setHorizontalAlignment(JLabel.CENTER);
		itemIcon.setPreferredSize(new Dimension(45, 45));

		itemName.setForeground(Color.WHITE);
		itemName.setVerticalAlignment(JLabel.BOTTOM);
		itemName.setFont(FontManager.getRunescapeSmallFont());

		offerInfo.setForeground(ColorScheme.GRAND_EXCHANGE_OFFER_DETAILS);
		offerInfo.setVerticalAlignment(JLabel.TOP);
		offerInfo.setFont(FontManager.getRunescapeSmallFont());

		switchFaceViewIcon.setIcon(rightArrowIcon);
		switchFaceViewIcon.setVerticalAlignment(JLabel.CENTER);
		switchFaceViewIcon.setHorizontalAlignment(JLabel.CENTER);
		switchFaceViewIcon.setPreferredSize(new Dimension(30, 45));


		JPanel offerFaceDetails = new JPanel();
		offerFaceDetails.setOpaque(false);
		offerFaceDetails.setLayout(new GridLayout(2, 1, 0, 2));

		offerFaceDetails.add(itemName);
		offerFaceDetails.add(offerInfo);

		faceCard.add(offerFaceDetails, BorderLayout.CENTER);
		faceCard.add(itemIcon, BorderLayout.WEST);
		faceCard.add(switchFaceViewIcon, BorderLayout.EAST);


		/* -------------------------------------------------------------------------------------- */

		JPanel detailsCard = new JPanel();
		detailsCard.setBackground(ColorScheme.GRAND_EXCHANGE_BG_COLOR);
		detailsCard.setLayout(new BorderLayout());
		detailsCard.setBorder(new EmptyBorder(0, 15, 0, 0));
		detailsCard.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				super.mousePressed(mouseEvent);
				switchPanel();
			}
		});

		itemPrice.setForeground(Color.WHITE);
		itemPrice.setVerticalAlignment(JLabel.BOTTOM);
		itemPrice.setFont(FontManager.getRunescapeSmallFont());

		offerSpent.setForeground(Color.WHITE);
		offerSpent.setVerticalAlignment(JLabel.TOP);
		offerSpent.setFont(FontManager.getRunescapeSmallFont());

		switchDetailsViewIcon.setIcon(leftArrowIcon);
		switchDetailsViewIcon.setVerticalAlignment(JLabel.CENTER);
		switchDetailsViewIcon.setHorizontalAlignment(JLabel.CENTER);
		switchDetailsViewIcon.setPreferredSize(new Dimension(30, 45));


		JPanel offerDetails = new JPanel();
		offerDetails.setOpaque(false);
		offerDetails.setLayout(new GridLayout(2, 1));

		offerDetails.add(itemPrice);
		offerDetails.add(offerSpent);

		detailsCard.add(offerDetails, BorderLayout.CENTER);
		detailsCard.add(switchDetailsViewIcon, BorderLayout.EAST);

		container.add(faceCard, FACE_CARD);
		container.add(detailsCard, DETAILS_CARD);

		cardLayout.show(container, FACE_CARD);

		add(container, BorderLayout.CENTER);
		add(progressBar, BorderLayout.SOUTH);

	}


	void updateOffer(ItemComposition offerItem, BufferedImage itemImage, @Nullable GrandExchangeOffer newOffer)
	{
		if (newOffer == null || newOffer.getState() == EMPTY)
		{
			return;
		}
		else
		{
			cardLayout.show(container, FACE_CARD);

			itemName.setText(offerItem.getName());
			itemIcon.setIcon(new ImageIcon(itemImage));

			boolean buying = newOffer.getState() == GrandExchangeOfferState.BOUGHT
				|| newOffer.getState() == GrandExchangeOfferState.BUYING
				|| newOffer.getState() == GrandExchangeOfferState.CANCELLED_BUY;

			String offerState = (buying ? "Bought " : "Sold ")
				+ StackFormatter.quantityToRSDecimalStack(newOffer.getQuantitySold()) + " / "
				+ StackFormatter.quantityToRSDecimalStack(newOffer.getTotalQuantity());

			offerInfo.setText(offerState);

			itemPrice.setText(htmlLabel("Price each: ", newOffer.getPrice() + ""));
			offerSpent.setText(htmlLabel("Spent: ", StackFormatter.formatNumber(newOffer.getSpent()) + " / "
				+ StackFormatter.formatNumber(newOffer.getPrice() * newOffer.getTotalQuantity())));

			progressBar.setForeground(getProgressColor(newOffer));
			progressBar.setMaximumValue(newOffer.getTotalQuantity());
			progressBar.setValue(newOffer.getQuantitySold());
			progressBar.update();

			container.setToolTipText(htmlTooltip(((int) progressBar.getPercentage()) + "%"));
		}

		revalidate();
		repaint();
	}

	private String htmlTooltip(String value)
	{
		return "<html><body style = 'color:#a5a5a5'>Progress: <span style = 'color:white'>" + value + "</span></body></html>";
	}

	private String htmlLabel(String key, String value)
	{
		return "<html><body style = 'color:white'>" + key + "<span style = 'color:#a5a5a5'>" + value + "</span></body></html>";
	}

	private void switchPanel()
	{
		this.showingFace = !this.showingFace;
		cardLayout.show(container, showingFace ? FACE_CARD : DETAILS_CARD);
	}

	private Color getProgressColor(GrandExchangeOffer offer)
	{
		if (offer.getState() == CANCELLED_BUY || offer.getState() == CANCELLED_SELL)
		{
			return ColorScheme.GRAND_EXCHANGE_CANCELLED_COLOR;
		}

		if (offer.getQuantitySold() == offer.getTotalQuantity())
		{
			return ColorScheme.GRAND_EXCHANGE_COMPLETE_COLOR;
		}

		return ColorScheme.GRAND_EXCHANGE_INPROGRESS_COLOR;
	}
}

