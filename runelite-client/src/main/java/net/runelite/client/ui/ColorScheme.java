package net.runelite.client.ui;

import java.awt.Color;

/**
 * This class serves to hold commonly used colors that reach the whole client UI.
 */
public class ColorScheme
{
	/* The background color of the scrollbar's track*/
	public static final Color SCROLL_TRACK_COLOR = new Color(25, 25, 25);

	/* The darker frame color */
	public static final Color DARKER_FRAME_COLOR = new Color(30, 30, 30);

	/* The background color for the side panel plugins (Ex: grand exchange, hiscores, etc) */
	public static final Color SIDE_PANEL_BG_COLOR = new Color(40, 40, 40);

	/* The background color for some of the elements in the grand exchange plugin (searchbar, result items, etc) */
	public static final Color GRAND_EXCHANGE_BG_COLOR = new Color(77, 77, 77);

	/* The color for the price indicator in the ge search results */
	public static final Color GRAND_EXCHANGE_PRICE = new Color(110, 225, 110);

	/* The color for the high alch indicator in the ge search results */
	public static final Color GRAND_EXCHANGE_ALCH = new Color(240, 207, 123);

	/* The color for the high alch indicator in the ge search results */
	public static final Color GRAND_EXCHANGE_OFFER_DETAILS = new Color(165, 165, 165);

	/* The color for the green progress bar in the ge offers */
	public static final Color GRAND_EXCHANGE_COMPLETE_COLOR = new Color(55, 240, 70);

	/* The color for the red progress bar in the ge offers */
	public static final Color GRAND_EXCHANGE_CANCELLED_COLOR = new Color(230, 30, 30);

	/* The color for the orange progress bar in the ge offers */
	public static final Color GRAND_EXCHANGE_INPROGRESS_COLOR = new Color(230, 150, 30);

	/* The background color for some of the elements in the hiscores plugin (searchbar, stats, etc) */
	public static final Color HISCORES_BG_COLOR = DARKER_FRAME_COLOR;

	/* The hover background color for some of the elements in the hiscores plugin (searchbar, stats, etc) */
	public static final Color HISCORES_BG_HOVER_COLOR = new Color(35, 35, 35);

	/* The orange color used for the branding's accents */
	public static final Color BRAND_ORANGE = new Color(220, 138, 0);
}