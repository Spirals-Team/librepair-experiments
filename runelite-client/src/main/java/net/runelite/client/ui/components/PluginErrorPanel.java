package net.runelite.client.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

/**
 * A component to display an error/info message (to be used on a plugin panel)
 * Example uses are: no ge search results found, no ge offers found.
 */
public class PluginErrorPanel extends JPanel
{

	private JLabel noResultsTitle = new JShadowedLabel();
	private JLabel noResultsDescription = new JShadowedLabel();

	public PluginErrorPanel()
	{
		setOpaque(false);
		setBorder(new EmptyBorder(50, 0, 0, 0));
		setLayout(new BorderLayout());

		noResultsTitle.setForeground(Color.WHITE);
		noResultsTitle.setHorizontalAlignment(SwingConstants.CENTER);

		noResultsDescription.setFont(FontManager.getRunescapeSmallFont());
		noResultsDescription.setForeground(Color.GRAY);
		noResultsDescription.setHorizontalAlignment(SwingConstants.CENTER);

		add(noResultsTitle, BorderLayout.NORTH);
		add(noResultsDescription, BorderLayout.CENTER);

		setVisible(false);
	}


	/**
	 * Changes the content of the panel to the given parameters.
	 * The description has to be wrapped in html so that its text can be wrapped.
	 */
	public void setContent(String title, String description)
	{
		noResultsTitle.setText(title);
		noResultsDescription.setText("<html><body style = 'text-align:center'>" + description + "</body></html>");
		setVisible(true);
	}

}