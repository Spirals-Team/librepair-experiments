package net.runelite.client.ui.components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.ColorScheme;

/**
 * A custom list renderer to avoid substance's weird coloring.
 * Substance was making selected items' foreground color black, this
 * was very hard to see in the dark gray background, this makes the selected
 * item white and adds some padding to the elements for more readable list.
 */
public final class ComboBoxListRenderer extends JLabel implements ListCellRenderer
{

	@Override
	public Component getListCellRendererComponent(JList list, Object o, int index, boolean isSelected, boolean cellHasFocus)
	{
		if (isSelected)
		{
			setBackground(ColorScheme.SIDE_PANEL_BG_COLOR);
			setForeground(Color.WHITE);
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		setBorder(new EmptyBorder(5, 5, 5, 0));

		String text = (String) o.toString();
		setText(text);

		return this;
	}
}