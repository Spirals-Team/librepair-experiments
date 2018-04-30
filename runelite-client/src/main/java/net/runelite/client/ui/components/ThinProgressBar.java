package net.runelite.client.ui.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import lombok.Setter;

/**
 * A progress bar to be displayed underneath the GE offer item panels
 */
public class ThinProgressBar extends JPanel
{
	@Setter
	private int maximumValue;

	@Setter
	private int value;

	private JPanel topBar;

	public ThinProgressBar()
	{
		setLayout(new BorderLayout());
		setBackground(Color.GREEN.darker());

		topBar = new JPanel();
		topBar.setPreferredSize(new Dimension(100, 4));
		topBar.setBackground(Color.GREEN);

		add(topBar, BorderLayout.WEST);
	}

	/**
	 * Updates the UI based on the percentage progress
	 */
	public void update()
	{
		double percentage = getPercentage();
		int topWidth = (int) (getSize().width * (percentage / 100));

		topBar.setPreferredSize(new Dimension(topWidth, 4));
		topBar.repaint();

		revalidate();
		repaint();
	}

	public double getPercentage()
	{
		if (value == 0)
		{
			return 0;
		}

		return (value * 100) / maximumValue;
	}

	@Override
	public void setForeground(Color color)
	{
		if (topBar == null)
		{
			return;
		}

		topBar.setBackground(color);
		setBackground(color.darker());
	}
}