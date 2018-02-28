package net.runelite.client.plugins.cannon;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;

public class CannonUiOverlay  extends Overlay
{
    private final CannonConfig config;
    private final CannonPlugin plugin;
    private final PanelComponent panelComponent = new PanelComponent();
    private static final int COMPONENT_WIDTH = 40;

    @Inject
    CannonUiOverlay(CannonConfig config, CannonPlugin plugin)
    {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics, Point parent)
    {
        if (!plugin.cannonPlaced || plugin.cannonPosition == null)
        {
            return null;
        }

        if(!config.showAmountOnScreen())
        {
            return null;
        }

        panelComponent.setTitleColor(plugin.getStateColor());
        panelComponent.setTitle(String.valueOf(plugin.cballsLeft));
        panelComponent.setWidth(COMPONENT_WIDTH);
        return panelComponent.render(graphics, parent);
    }
}