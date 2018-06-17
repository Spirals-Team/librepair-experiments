package us.tastybento.bskyblock.commands.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;

import us.tastybento.bskyblock.api.commands.CompositeCommand;
import us.tastybento.bskyblock.api.user.User;
import us.tastybento.bskyblock.island.builders.Clipboard;
import us.tastybento.bskyblock.util.Util;

public class AdminSchemCommand extends CompositeCommand {
    private Map<UUID, Clipboard> clipboards;

    public AdminSchemCommand(CompositeCommand parent) {
        super(parent, "schem");
    }

    public void setup() {
        setPermission("admin.schem");
        setParameters("commands.admin.schem.parameters");
        setDescription("commands.admin.schem.description");
        setOnlyPlayer(true);
        clipboards = new HashMap<>();
    }

    @SuppressWarnings("deprecation")
    public boolean execute(User user, List<String> args) {
        if (args.isEmpty()) {
            showHelp(this, user);
            return false;
        }
        Clipboard cb = clipboards.getOrDefault(user.getUniqueId(), new Clipboard(getPlugin()));

        if (args.get(0).equalsIgnoreCase("paste")) {
            if (cb.isFull()) {
                cb.paste(user.getLocation());
                user.sendMessage("general.success");
                return true;
            } else {
                user.sendMessage("commands.admin.schem.copy-first");
                return false;
            }
        }

        if (args.get(0).equalsIgnoreCase("load")) {
            if (args.size() == 2) {
                if (cb.load(user, args.get(1))) {
                    clipboards.put(user.getUniqueId(), cb);
                    return true;
                }
            } else {
                showHelp(this, user);
                return false;
            }
            return false;
        }

        if (args.get(0).equalsIgnoreCase("origin")) {
            if (cb.getPos1() == null || cb.getPos2() == null) {
                user.sendMessage("commands.admin.schem.need-pos1-pos2");
                return false;
            }
            // Get the block player is looking at
            Block b = user.getPlayer().getLineOfSight(null, 20).stream().filter(x -> !x.getType().equals(Material.AIR)).findFirst().orElse(null);
            if (b != null) {
                cb.setOrigin(b.getLocation());
                user.getPlayer().sendBlockChange(b.getLocation(), Material.STAINED_GLASS,(byte)14);
                Bukkit.getScheduler().runTaskLater(getPlugin(), 
                        () -> user.getPlayer().sendBlockChange(b.getLocation(), b.getType(), b.getData()), 20L);

                user.sendMessage("general.success");
                return true;
            } else {
                user.sendMessage("commands.admin.schem.look-at-a-block");
                return false;
            }
        }

        if (args.get(0).equalsIgnoreCase("copy")) {
            boolean copyAir = (args.size() == 2 && args.get(1).equalsIgnoreCase("air"));
            return cb.copy(user, copyAir);
        }

        if (args.get(0).equalsIgnoreCase("save")) {
            if (cb.isFull()) {
                if (args.size() == 2) {
                    return cb.save(user, args.get(1));
                } else {
                    showHelp(this, user);
                    return false;
                }
            } else {
                user.sendMessage("commands.admin.schem.copy-first");
                return false;
            }
        }

        if (args.get(0).equalsIgnoreCase("pos1")) {
            if (user.getLocation().equals(cb.getPos2())) {
                user.sendMessage("commands.admin.schem.set-different-pos");
                return false;
            }
            cb.setPos1(user.getLocation());
            user.sendMessage("commands.admin.schem.set-pos1", "[vector]", Util.xyz(user.getLocation().toVector()));
            clipboards.put(user.getUniqueId(), cb);
            return true;
        }

        if (args.get(0).equalsIgnoreCase("pos2")) {
            if (user.getLocation().equals(cb.getPos1())) {
                user.sendMessage("commands.admin.schem.set-different-pos");
                return false;
            }
            cb.setPos2(user.getLocation());
            user.sendMessage("commands.admin.schem.set-pos2", "[vector]", Util.xyz(user.getLocation().toVector()));
            clipboards.put(user.getUniqueId(), cb);
            return true;
        }

        return false;
    }

}
