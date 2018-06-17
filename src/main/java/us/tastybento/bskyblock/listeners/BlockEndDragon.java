package us.tastybento.bskyblock.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import us.tastybento.bskyblock.BSkyBlock;

public class BlockEndDragon implements Listener {
    private BSkyBlock plugin;

    public BlockEndDragon(BSkyBlock plugin) {
        this.plugin = plugin;
    }

    /**
     * This handles end dragon spawning prevention
     * 
     * @param e - event
     * @return true if dragon can spawn, false if not
     */
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public boolean onDragonSpawn(CreatureSpawnEvent e) {
        if (!e.getEntityType().equals(EntityType.ENDER_DRAGON) || plugin.getIWM().isDragonSpawn(e.getEntity().getWorld())) {
            return true;
        }
        e.getEntity().setHealth(0);
        e.getEntity().remove();
        e.setCancelled(true);
        return false;
    }


}
