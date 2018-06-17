package us.tastybento.bskyblock.listeners.flags;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import us.tastybento.bskyblock.BSkyBlock;
import us.tastybento.bskyblock.api.user.User;
import us.tastybento.bskyblock.lists.Flags;
import us.tastybento.bskyblock.managers.IslandsManager;

/**
 * Listener for the lock flag
 * Also handles ban protection
 * 
 * @author tastybento
 *
 */
public class LockAndBanListener implements Listener {

    private IslandsManager im;
    private enum CheckResult {
        BANNED,
        LOCKED,
        OPEN
    }

    /**
     * Enforces island bans and locks
     */
    public LockAndBanListener() {
        this.im = BSkyBlock.getInstance().getIslands();
    }

    // Teleport check
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        e.setCancelled(!checkAndNotify(e.getPlayer(), e.getTo()).equals(CheckResult.OPEN));
    }

    // Movement check
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent e) {
        // Ignore only vertical movement
        if (e.getFrom().getBlockX() - e.getTo().getBlockX() == 0 && e.getFrom().getBlockZ() - e.getTo().getBlockZ() == 0) {
            return;
        }
        if (!checkAndNotify(e.getPlayer(), e.getTo()).equals(CheckResult.OPEN)) {
            e.setCancelled(true);
            e.getFrom().getWorld().playSound(e.getFrom(), Sound.BLOCK_ANVIL_HIT, 1F, 1F);
            e.getPlayer().setVelocity(new Vector(0,0,0));
            e.getPlayer().setGliding(false);
        }
        // Check from - just in case the player is inside the island
        if (!check(e.getPlayer(), e.getFrom()).equals(CheckResult.OPEN)) {
            // Has to be done 1 tick later otherwise it doesn't happen for some reason...
            Bukkit.getScheduler().runTask(BSkyBlock.getInstance(), () -> eject(e.getPlayer()));
        }
    }

    // Vehicle move check
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent e) {
        // Ignore only vertical movement
        if (e.getFrom().getBlockX() - e.getTo().getBlockX() == 0 && e.getFrom().getBlockZ() - e.getTo().getBlockZ() == 0) {
            return;
        }
        // For each Player in the vehicle
        e.getVehicle().getPassengers().stream().filter(en -> en instanceof Player).map(en -> (Player)en).forEach(p -> {
            if (!checkAndNotify(p, e.getTo()).equals(CheckResult.OPEN)) {
                p.leaveVehicle();
                p.teleport(e.getFrom());                
                e.getFrom().getWorld().playSound(e.getFrom(), Sound.BLOCK_ANVIL_HIT, 1F, 1F);
                eject(p);
            }
        });
    }  

    // Login check
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerLogin(PlayerJoinEvent e) {
        if (!checkAndNotify(e.getPlayer(), e.getPlayer().getLocation()).equals(CheckResult.OPEN)) {
            eject(e.getPlayer());
        }
    }

    /**
     * Check if a player is banned or the island is locked
     * @param player - player
     * @param loc - location to check
     * @return CheckResult LOCKED, BANNED or OPEN. If an island is locked, that will take priority over banned
     */
    private CheckResult check(Player player, Location loc) {

        // See if the island is locked to non-members or player is banned
        return im.getProtectedIslandAt(loc)
                .map(is -> {
                    if (is.isBanned(player.getUniqueId())) {
                        return CheckResult.BANNED;
                    }
                    if (!is.isAllowed(User.getInstance(player), Flags.LOCK)) {
                        return CheckResult.LOCKED;
                    }
                    return CheckResult.OPEN;
                }).orElse(CheckResult.OPEN);
    }

    /**
     * Checks if a player is banned from this location and notifies them if so
     * @param player - player
     * @param loc - location to check
     * @return true if banned
     */
    private CheckResult checkAndNotify(Player player, Location loc) {
        CheckResult r = check(player,loc);
        switch (r) {
        case BANNED:
            User.getInstance(player).notify("commands.island.ban.you-are-banned");
            break;
        case LOCKED:
            User.getInstance(player).notify("protection.locked");
            break;
        default:
            break;
        }
        return r;
    }

    /**
     * Sends player home
     * @param player - player
     */
    private void eject(Player player) {
        player.setGameMode(GameMode.SPECTATOR);
        // Teleport player to their home
        if (im.hasIsland(player.getWorld(), player.getUniqueId())) {
            im.homeTeleport(player.getWorld(), player);
        } // else, TODO: teleport somewhere else?   
    }

}
