package us.tastybento.bskyblock.api.user;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.permissions.PermissionAttachmentInfo;
import us.tastybento.bskyblock.BSkyBlock;
import us.tastybento.bskyblock.Settings;
import us.tastybento.bskyblock.api.placeholders.PlaceholderHandler;
import us.tastybento.bskyblock.managers.LocalesManager;
import us.tastybento.bskyblock.managers.PlayersManager;

public class User {
    private static Map<UUID, User> users = new HashMap<>();

    private static BSkyBlock plugin = BSkyBlock.getInstance();

    private Player player;

    private final UUID playerUUID;

    private final CommandSender sender;

    public static void clearUsers() {
        MethodContext _bcornu_methode_context1 = new MethodContext(void.class, 35, 773, 893);
        try {
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 35, 773, 893);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 35, 773, 893);
            if (CallChecker.beforeDeref(User.users, Map.class, 36, 874, 878)) {
                CallChecker.isCalled(User.users, Map.class, 36, 874, 878).clear();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public static User getInstance(CommandSender sender) {
        MethodContext _bcornu_methode_context2 = new MethodContext(User.class, 44, 900, 1218);
        try {
            CallChecker.varInit(sender, "sender", 44, 900, 1218);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 44, 900, 1218);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 44, 900, 1218);
            if (sender instanceof Player) {
                return User.getInstance(((Player) (sender)));
            }
            return new User(sender);
        } catch (ForceReturn _bcornu_return_t) {
            return ((User) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    public static User getInstance(Player player) {
        MethodContext _bcornu_methode_context3 = new MethodContext(User.class, 56, 1224, 1613);
        try {
            CallChecker.varInit(player, "player", 56, 1224, 1613);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 56, 1224, 1613);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 56, 1224, 1613);
            if (player == null) {
                return null;
            }
            if (CallChecker.beforeDeref(User.users, Map.class, 60, 1471, 1475)) {
                if (CallChecker.isCalled(User.users, Map.class, 60, 1471, 1475).containsKey(player.getUniqueId())) {
                    if (CallChecker.beforeDeref(User.users, Map.class, 61, 1533, 1537)) {
                        return CallChecker.isCalled(User.users, Map.class, 61, 1533, 1537).get(player.getUniqueId());
                    }else
                        throw new AbnormalExecutionError();
                    
                }
            }else
                throw new AbnormalExecutionError();
            
            return new User(player);
        } catch (ForceReturn _bcornu_return_t) {
            return ((User) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    public static User getInstance(UUID uuid) {
        MethodContext _bcornu_methode_context4 = new MethodContext(User.class, 70, 1619, 2008);
        try {
            CallChecker.varInit(uuid, "uuid", 70, 1619, 2008);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 70, 1619, 2008);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 70, 1619, 2008);
            if (uuid == null) {
                return null;
            }
            if (CallChecker.beforeDeref(User.users, Map.class, 74, 1843, 1847)) {
                if (CallChecker.isCalled(User.users, Map.class, 74, 1843, 1847).containsKey(uuid)) {
                    if (CallChecker.beforeDeref(User.users, Map.class, 75, 1889, 1893)) {
                        return CallChecker.isCalled(User.users, Map.class, 75, 1889, 1893).get(uuid);
                    }else
                        throw new AbnormalExecutionError();
                    
                }
            }else
                throw new AbnormalExecutionError();
            
            return new User(uuid);
        } catch (ForceReturn _bcornu_return_t) {
            return ((User) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    public static void removePlayer(Player player) {
        MethodContext _bcornu_methode_context5 = new MethodContext(void.class, 84, 2014, 2208);
        try {
            CallChecker.varInit(player, "player", 84, 2014, 2208);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 84, 2014, 2208);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 84, 2014, 2208);
            if (CallChecker.beforeDeref(player, Player.class, 85, 2181, 2186)) {
                if (CallChecker.beforeDeref(User.users, Map.class, 85, 2168, 2172)) {
                    player = CallChecker.beforeCalled(player, Player.class, 85, 2181, 2186);
                    CallChecker.isCalled(User.users, Map.class, 85, 2168, 2172).remove(CallChecker.isCalled(player, Player.class, 85, 2181, 2186).getUniqueId());
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    private User(CommandSender sender) {
        ConstructorContext _bcornu_methode_context1 = new ConstructorContext(User.class, 96, 2443, 2564);
        try {
            player = null;
            CallChecker.varAssign(this.player, "this.player", 97, 2488, 2501);
            playerUUID = null;
            CallChecker.varAssign(this.playerUUID, "this.playerUUID", 98, 2511, 2528);
            this.sender = sender;
            CallChecker.varAssign(this.sender, "this.sender", 99, 2538, 2558);
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    private User(Player player) {
        ConstructorContext _bcornu_methode_context2 = new ConstructorContext(User.class, 102, 2571, 2750);
        try {
            this.player = player;
            CallChecker.varAssign(this.player, "this.player", 103, 2609, 2629);
            sender = player;
            CallChecker.varAssign(this.sender, "this.sender", 104, 2639, 2654);
            player = CallChecker.beforeCalled(player, Player.class, 105, 2677, 2682);
            playerUUID = CallChecker.isCalled(player, Player.class, 105, 2677, 2682).getUniqueId();
            CallChecker.varAssign(this.playerUUID, "this.playerUUID", 105, 2664, 2697);
            if (CallChecker.beforeDeref(player, Player.class, 106, 2717, 2722)) {
                if (CallChecker.beforeDeref(User.users, Map.class, 106, 2707, 2711)) {
                    player = CallChecker.beforeCalled(player, Player.class, 106, 2717, 2722);
                    CallChecker.isCalled(User.users, Map.class, 106, 2707, 2711).put(CallChecker.isCalled(player, Player.class, 106, 2717, 2722).getUniqueId(), this);
                }
            }
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    private User(UUID playerUUID) {
        ConstructorContext _bcornu_methode_context3 = new ConstructorContext(User.class, 109, 2757, 2903);
        try {
            player = Bukkit.getPlayer(playerUUID);
            CallChecker.varAssign(this.player, "this.player", 110, 2797, 2834);
            this.playerUUID = playerUUID;
            CallChecker.varAssign(this.playerUUID, "this.playerUUID", 111, 2844, 2872);
            sender = player;
            CallChecker.varAssign(this.sender, "this.sender", 112, 2882, 2897);
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    public static void setPlugin(BSkyBlock p) {
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 119, 2910, 3039);
        try {
            CallChecker.varInit(p, "p", 119, 2910, 3039);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 119, 2910, 3039);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 119, 2910, 3039);
            User.plugin = p;
            CallChecker.varAssign(User.plugin, "User.plugin", 120, 3023, 3033);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        MethodContext _bcornu_methode_context7 = new MethodContext(Set.class, 123, 3046, 3164);
        try {
            CallChecker.varInit(this, "this", 123, 3046, 3164);
            CallChecker.varInit(this.sender, "sender", 123, 3046, 3164);
            CallChecker.varInit(this.playerUUID, "playerUUID", 123, 3046, 3164);
            CallChecker.varInit(this.player, "player", 123, 3046, 3164);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 123, 3046, 3164);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 123, 3046, 3164);
            if (CallChecker.beforeDeref(sender, CommandSender.class, 124, 3126, 3131)) {
                return CallChecker.isCalled(sender, CommandSender.class, 124, 3126, 3131).getEffectivePermissions();
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Set<PermissionAttachmentInfo>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    public PlayerInventory getInventory() {
        MethodContext _bcornu_methode_context8 = new MethodContext(PlayerInventory.class, 127, 3171, 3277);
        try {
            CallChecker.varInit(this, "this", 127, 3171, 3277);
            CallChecker.varInit(this.sender, "sender", 127, 3171, 3277);
            CallChecker.varInit(this.playerUUID, "playerUUID", 127, 3171, 3277);
            CallChecker.varInit(this.player, "player", 127, 3171, 3277);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 127, 3171, 3277);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 127, 3171, 3277);
            if ((player) != null) {
                return player.getInventory();
            }else {
                return null;
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((PlayerInventory) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    public Location getLocation() {
        MethodContext _bcornu_methode_context9 = new MethodContext(Location.class, 131, 3284, 3381);
        try {
            CallChecker.varInit(this, "this", 131, 3284, 3381);
            CallChecker.varInit(this.sender, "sender", 131, 3284, 3381);
            CallChecker.varInit(this.playerUUID, "playerUUID", 131, 3284, 3381);
            CallChecker.varInit(this.player, "player", 131, 3284, 3381);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 131, 3284, 3381);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 131, 3284, 3381);
            if ((player) != null) {
                return player.getLocation();
            }else {
                return null;
            }
        } catch (ForceReturn _bcornu_return_t) {
            return ((Location) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }

    public String getName() {
        MethodContext _bcornu_methode_context10 = new MethodContext(String.class, 135, 3388, 3640);
        try {
            CallChecker.varInit(this, "this", 135, 3388, 3640);
            CallChecker.varInit(this.sender, "sender", 135, 3388, 3640);
            CallChecker.varInit(this.playerUUID, "playerUUID", 135, 3388, 3640);
            CallChecker.varInit(this.player, "player", 135, 3388, 3640);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 135, 3388, 3640);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 135, 3388, 3640);
            player = Bukkit.getPlayer(playerUUID);
            CallChecker.varAssign(this.player, "this.player", 136, 3422, 3459);
            if ((player) != null) {
                return player.getName();
            }
            if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 141, 3595, 3600)) {
                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 141, 3595, 3600);
                if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 141, 3595, 3600).getPlayers(), PlayersManager.class, 141, 3595, 3613)) {
                    User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 141, 3595, 3600);
                    return CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 141, 3595, 3600).getPlayers(), PlayersManager.class, 141, 3595, 3613).getName(playerUUID);
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context10.methodEnd();
        }
    }

    public Player getPlayer() {
        MethodContext _bcornu_methode_context11 = new MethodContext(Player.class, 147, 3647, 3744);
        try {
            CallChecker.varInit(this, "this", 147, 3647, 3744);
            CallChecker.varInit(this.sender, "sender", 147, 3647, 3744);
            CallChecker.varInit(this.playerUUID, "playerUUID", 147, 3647, 3744);
            CallChecker.varInit(this.player, "player", 147, 3647, 3744);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 147, 3647, 3744);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 147, 3647, 3744);
            return player;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Player) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context11.methodEnd();
        }
    }

    public boolean isPlayer() {
        MethodContext _bcornu_methode_context12 = new MethodContext(boolean.class, 154, 3751, 3904);
        try {
            CallChecker.varInit(this, "this", 154, 3751, 3904);
            CallChecker.varInit(this.sender, "sender", 154, 3751, 3904);
            CallChecker.varInit(this.playerUUID, "playerUUID", 154, 3751, 3904);
            CallChecker.varInit(this.player, "player", 154, 3751, 3904);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 154, 3751, 3904);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 154, 3751, 3904);
            return (player) != null;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context12.methodEnd();
        }
    }

    public CommandSender getSender() {
        MethodContext _bcornu_methode_context13 = new MethodContext(CommandSender.class, 158, 3911, 3973);
        try {
            CallChecker.varInit(this, "this", 158, 3911, 3973);
            CallChecker.varInit(this.sender, "sender", 158, 3911, 3973);
            CallChecker.varInit(this.playerUUID, "playerUUID", 158, 3911, 3973);
            CallChecker.varInit(this.player, "player", 158, 3911, 3973);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 158, 3911, 3973);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 158, 3911, 3973);
            return sender;
        } catch (ForceReturn _bcornu_return_t) {
            return ((CommandSender) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context13.methodEnd();
        }
    }

    public UUID getUniqueId() {
        MethodContext _bcornu_methode_context14 = new MethodContext(UUID.class, 162, 3980, 4039);
        try {
            CallChecker.varInit(this, "this", 162, 3980, 4039);
            CallChecker.varInit(this.sender, "sender", 162, 3980, 4039);
            CallChecker.varInit(this.playerUUID, "playerUUID", 162, 3980, 4039);
            CallChecker.varInit(this.player, "player", 162, 3980, 4039);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 162, 3980, 4039);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 162, 3980, 4039);
            return playerUUID;
        } catch (ForceReturn _bcornu_return_t) {
            return ((UUID) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context14.methodEnd();
        }
    }

    public boolean hasPermission(String permission) {
        MethodContext _bcornu_methode_context15 = new MethodContext(boolean.class, 170, 4046, 4294);
        try {
            CallChecker.varInit(this, "this", 170, 4046, 4294);
            CallChecker.varInit(permission, "permission", 170, 4046, 4294);
            CallChecker.varInit(this.sender, "sender", 170, 4046, 4294);
            CallChecker.varInit(this.playerUUID, "playerUUID", 170, 4046, 4294);
            CallChecker.varInit(this.player, "player", 170, 4046, 4294);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 170, 4046, 4294);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 170, 4046, 4294);
            permission = CallChecker.beforeCalled(permission, String.class, 171, 4232, 4241);
            return (CallChecker.isCalled(permission, String.class, 171, 4232, 4241).isEmpty()) || (CallChecker.isCalled(sender, CommandSender.class, 171, 4256, 4261).hasPermission(permission));
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context15.methodEnd();
        }
    }

    public boolean isOnline() {
        MethodContext _bcornu_methode_context16 = new MethodContext(boolean.class, 174, 4301, 4385);
        try {
            CallChecker.varInit(this, "this", 174, 4301, 4385);
            CallChecker.varInit(this.sender, "sender", 174, 4301, 4385);
            CallChecker.varInit(this.playerUUID, "playerUUID", 174, 4301, 4385);
            CallChecker.varInit(this.player, "player", 174, 4301, 4385);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 174, 4301, 4385);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 174, 4301, 4385);
            player = CallChecker.beforeCalled(player, Player.class, 175, 4362, 4367);
            return ((player) != null) && (CallChecker.isCalled(player, Player.class, 175, 4362, 4367).isOnline());
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context16.methodEnd();
        }
    }

    public boolean isOp() {
        MethodContext _bcornu_methode_context17 = new MethodContext(boolean.class, 182, 4392, 4817);
        try {
            CallChecker.varInit(this, "this", 182, 4392, 4817);
            CallChecker.varInit(this.sender, "sender", 182, 4392, 4817);
            CallChecker.varInit(this.playerUUID, "playerUUID", 182, 4392, 4817);
            CallChecker.varInit(this.player, "player", 182, 4392, 4817);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 182, 4392, 4817);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 182, 4392, 4817);
            if ((sender) != null) {
                return sender.isOp();
            }
            if ((playerUUID) != null) {
                OfflinePlayer offlinePlayer = CallChecker.varInit(Bukkit.getOfflinePlayer(playerUUID), "offlinePlayer", 187, 4614, 4679);
                if (offlinePlayer != null) {
                    return offlinePlayer.isOp();
                }
            }
            return false;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context17.methodEnd();
        }
    }

    public String getTranslation(String reference, String... variables) {
        MethodContext _bcornu_methode_context18 = new MethodContext(String.class, 201, 4824, 5811);
        try {
            CallChecker.varInit(this, "this", 201, 4824, 5811);
            CallChecker.varInit(variables, "variables", 201, 4824, 5811);
            CallChecker.varInit(reference, "reference", 201, 4824, 5811);
            CallChecker.varInit(this.sender, "sender", 201, 4824, 5811);
            CallChecker.varInit(this.playerUUID, "playerUUID", 201, 4824, 5811);
            CallChecker.varInit(this.player, "player", 201, 4824, 5811);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 201, 4824, 5811);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 201, 4824, 5811);
            String translation = CallChecker.init(String.class);
            if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 203, 5172, 5177)) {
                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 203, 5172, 5177);
                if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 203, 5172, 5177).getLocalesManager(), LocalesManager.class, 203, 5172, 5197)) {
                    User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 203, 5172, 5177);
                    translation = CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 203, 5172, 5177).getLocalesManager(), LocalesManager.class, 203, 5172, 5197).get(this, reference);
                    CallChecker.varAssign(translation, "translation", 203, 5172, 5177);
                }
            }
            if (translation == null) {
                return reference;
            }
            if (CallChecker.beforeDeref(variables, String[].class, 211, 5430, 5438)) {
                variables = CallChecker.beforeCalled(variables, String[].class, 211, 5430, 5438);
                if ((CallChecker.isCalled(variables, String[].class, 211, 5430, 5438).length) > 1) {
                    variables = CallChecker.beforeCalled(variables, String[].class, 212, 5486, 5494);
                    for (int i = 0; i < (CallChecker.isCalled(variables, String[].class, 212, 5486, 5494).length); i += 2) {
                        CallChecker.varAssign(i, "i", 212, 5504, 5509);
                        if (CallChecker.beforeDeref(variables, String[].class, 213, 5564, 5572)) {
                            if (CallChecker.beforeDeref(variables, String[].class, 213, 5578, 5586)) {
                                variables = CallChecker.beforeCalled(variables, String[].class, 213, 5564, 5572);
                                variables = CallChecker.beforeCalled(variables, String[].class, 213, 5578, 5586);
                                translation = translation.replace(CallChecker.isCalled(variables, String[].class, 213, 5564, 5572)[i], CallChecker.isCalled(variables, String[].class, 213, 5578, 5586)[(i + 1)]);
                                CallChecker.varAssign(translation, "translation", 213, 5530, 5593);
                            }
                        }
                    }
                }
            }else
                throw new AbnormalExecutionError();
            
            translation = PlaceholderHandler.replacePlaceholders(this, translation);
            CallChecker.varAssign(translation, "translation", 218, 5660, 5731);
            return ChatColor.translateAlternateColorCodes('&', translation);
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context18.methodEnd();
        }
    }

    public String getTranslationOrNothing(String reference, String... variables) {
        MethodContext _bcornu_methode_context19 = new MethodContext(String.class, 229, 5818, 6256);
        try {
            CallChecker.varInit(this, "this", 229, 5818, 6256);
            CallChecker.varInit(variables, "variables", 229, 5818, 6256);
            CallChecker.varInit(reference, "reference", 229, 5818, 6256);
            CallChecker.varInit(this.sender, "sender", 229, 5818, 6256);
            CallChecker.varInit(this.playerUUID, "playerUUID", 229, 5818, 6256);
            CallChecker.varInit(this.player, "player", 229, 5818, 6256);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 229, 5818, 6256);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 229, 5818, 6256);
            String translation = CallChecker.varInit(getTranslation(reference, variables), "translation", 230, 6128, 6185);
            if (CallChecker.beforeDeref(translation, String.class, 231, 6202, 6212)) {
                translation = CallChecker.beforeCalled(translation, String.class, 231, 6202, 6212);
                if (CallChecker.isCalled(translation, String.class, 231, 6202, 6212).equals(reference)) {
                    return "";
                }else {
                    return translation;
                }
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context19.methodEnd();
        }
    }

    public void sendMessage(String reference, String... variables) {
        MethodContext _bcornu_methode_context20 = new MethodContext(void.class, 239, 6263, 6879);
        try {
            CallChecker.varInit(this, "this", 239, 6263, 6879);
            CallChecker.varInit(variables, "variables", 239, 6263, 6879);
            CallChecker.varInit(reference, "reference", 239, 6263, 6879);
            CallChecker.varInit(this.sender, "sender", 239, 6263, 6879);
            CallChecker.varInit(this.playerUUID, "playerUUID", 239, 6263, 6879);
            CallChecker.varInit(this.player, "player", 239, 6263, 6879);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 239, 6263, 6879);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 239, 6263, 6879);
            String message = CallChecker.varInit(getTranslation(reference, variables), "message", 240, 6524, 6577);
            if (CallChecker.beforeDeref(ChatColor.stripColor(message), String.class, 241, 6592, 6620)) {
                if (CallChecker.beforeDeref(CallChecker.isCalled(ChatColor.stripColor(message), String.class, 241, 6592, 6620).trim(), String.class, 241, 6592, 6627)) {
                    if (!(CallChecker.isCalled(CallChecker.isCalled(ChatColor.stripColor(message), String.class, 241, 6592, 6620).trim(), String.class, 241, 6592, 6627).isEmpty())) {
                        if ((sender) != null) {
                            sender.sendMessage(message);
                        }else {
                        }
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context20.methodEnd();
        }
    }

    public void sendRawMessage(String message) {
        MethodContext _bcornu_methode_context21 = new MethodContext(void.class, 255, 6886, 7289);
        try {
            CallChecker.varInit(this, "this", 255, 6886, 7289);
            CallChecker.varInit(message, "message", 255, 6886, 7289);
            CallChecker.varInit(this.sender, "sender", 255, 6886, 7289);
            CallChecker.varInit(this.playerUUID, "playerUUID", 255, 6886, 7289);
            CallChecker.varInit(this.player, "player", 255, 6886, 7289);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 255, 6886, 7289);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 255, 6886, 7289);
            if ((sender) != null) {
                sender.sendMessage(message);
            }else {
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context21.methodEnd();
        }
    }

    public void notify(String reference, String... variables) {
        MethodContext _bcornu_methode_context22 = new MethodContext(void.class, 271, 7296, 7879);
        try {
            CallChecker.varInit(this, "this", 271, 7296, 7879);
            CallChecker.varInit(variables, "variables", 271, 7296, 7879);
            CallChecker.varInit(reference, "reference", 271, 7296, 7879);
            CallChecker.varInit(this.sender, "sender", 271, 7296, 7879);
            CallChecker.varInit(this.playerUUID, "playerUUID", 271, 7296, 7879);
            CallChecker.varInit(this.player, "player", 271, 7296, 7879);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 271, 7296, 7879);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 271, 7296, 7879);
            String message = CallChecker.varInit(getTranslation(reference, variables), "message", 272, 7673, 7726);
            if (CallChecker.beforeDeref(ChatColor.stripColor(message), String.class, 273, 7741, 7769)) {
                if (CallChecker.beforeDeref(CallChecker.isCalled(ChatColor.stripColor(message), String.class, 273, 7741, 7769).trim(), String.class, 273, 7741, 7776)) {
                    if ((!(CallChecker.isCalled(CallChecker.isCalled(ChatColor.stripColor(message), String.class, 273, 7741, 7769).trim(), String.class, 273, 7741, 7776).isEmpty())) && ((sender) != null)) {
                        if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 274, 7821, 7826)) {
                            User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 274, 7821, 7826);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 274, 7821, 7826).getNotifier(), Notifier.class, 274, 7821, 7840)) {
                                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 274, 7821, 7826);
                                CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 274, 7821, 7826).getNotifier(), Notifier.class, 274, 7821, 7840).notify(this, message);
                            }
                        }
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context22.methodEnd();
        }
    }

    public void setGameMode(GameMode mode) {
        MethodContext _bcornu_methode_context23 = new MethodContext(void.class, 282, 7886, 8033);
        try {
            CallChecker.varInit(this, "this", 282, 7886, 8033);
            CallChecker.varInit(mode, "mode", 282, 7886, 8033);
            CallChecker.varInit(this.sender, "sender", 282, 7886, 8033);
            CallChecker.varInit(this.playerUUID, "playerUUID", 282, 7886, 8033);
            CallChecker.varInit(this.player, "player", 282, 7886, 8033);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 282, 7886, 8033);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 282, 7886, 8033);
            if (CallChecker.beforeDeref(player, Player.class, 283, 8003, 8008)) {
                player = CallChecker.beforeCalled(player, Player.class, 283, 8003, 8008);
                CallChecker.isCalled(player, Player.class, 283, 8003, 8008).setGameMode(mode);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context23.methodEnd();
        }
    }

    public void teleport(Location location) {
        MethodContext _bcornu_methode_context24 = new MethodContext(void.class, 290, 8040, 8266);
        try {
            CallChecker.varInit(this, "this", 290, 8040, 8266);
            CallChecker.varInit(location, "location", 290, 8040, 8266);
            CallChecker.varInit(this.sender, "sender", 290, 8040, 8266);
            CallChecker.varInit(this.playerUUID, "playerUUID", 290, 8040, 8266);
            CallChecker.varInit(this.player, "player", 290, 8040, 8266);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 290, 8040, 8266);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 290, 8040, 8266);
            if (CallChecker.beforeDeref(player, Player.class, 291, 8235, 8240)) {
                player = CallChecker.beforeCalled(player, Player.class, 291, 8235, 8240);
                CallChecker.isCalled(player, Player.class, 291, 8235, 8240).teleport(location);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context24.methodEnd();
        }
    }

    public World getWorld() {
        MethodContext _bcornu_methode_context25 = new MethodContext(World.class, 298, 8273, 8427);
        try {
            CallChecker.varInit(this, "this", 298, 8273, 8427);
            CallChecker.varInit(this.sender, "sender", 298, 8273, 8427);
            CallChecker.varInit(this.playerUUID, "playerUUID", 298, 8273, 8427);
            CallChecker.varInit(this.player, "player", 298, 8273, 8427);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 298, 8273, 8427);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 298, 8273, 8427);
            if (CallChecker.beforeDeref(player, Player.class, 299, 8404, 8409)) {
                player = CallChecker.beforeCalled(player, Player.class, 299, 8404, 8409);
                return CallChecker.isCalled(player, Player.class, 299, 8404, 8409).getWorld();
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((World) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context25.methodEnd();
        }
    }

    public void closeInventory() {
        MethodContext _bcornu_methode_context26 = new MethodContext(void.class, 305, 8434, 8553);
        try {
            CallChecker.varInit(this, "this", 305, 8434, 8553);
            CallChecker.varInit(this.sender, "sender", 305, 8434, 8553);
            CallChecker.varInit(this.playerUUID, "playerUUID", 305, 8434, 8553);
            CallChecker.varInit(this.player, "player", 305, 8434, 8553);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 305, 8434, 8553);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 305, 8434, 8553);
            if (CallChecker.beforeDeref(player, Player.class, 306, 8524, 8529)) {
                player = CallChecker.beforeCalled(player, Player.class, 306, 8524, 8529);
                CallChecker.isCalled(player, Player.class, 306, 8524, 8529).closeInventory();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context26.methodEnd();
        }
    }

    public Locale getLocale() {
        MethodContext _bcornu_methode_context27 = new MethodContext(Locale.class, 313, 8560, 8932);
        try {
            CallChecker.varInit(this, "this", 313, 8560, 8932);
            CallChecker.varInit(this.sender, "sender", 313, 8560, 8932);
            CallChecker.varInit(this.playerUUID, "playerUUID", 313, 8560, 8932);
            CallChecker.varInit(this.player, "player", 313, 8560, 8932);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 313, 8560, 8932);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 313, 8560, 8932);
            if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 314, 8696, 8701)) {
                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701);
                if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701).getPlayers(), PlayersManager.class, 314, 8696, 8714)) {
                    User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701).getPlayers(), PlayersManager.class, 314, 8696, 8714).getLocale(playerUUID), String.class, 314, 8696, 8736)) {
                        User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701);
                        if (((sender) instanceof Player) && (!(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 314, 8696, 8701).getPlayers(), PlayersManager.class, 314, 8696, 8714).getLocale(playerUUID), String.class, 314, 8696, 8736).isEmpty()))) {
                            if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 315, 8792, 8797)) {
                                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 315, 8792, 8797);
                                if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 315, 8792, 8797).getPlayers(), PlayersManager.class, 315, 8792, 8810)) {
                                    User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 315, 8792, 8797);
                                    return Locale.forLanguageTag(CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 315, 8792, 8797).getPlayers(), PlayersManager.class, 315, 8792, 8810).getLocale(playerUUID));
                                }else
                                    throw new AbnormalExecutionError();
                                
                            }else
                                throw new AbnormalExecutionError();
                            
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
            if (CallChecker.beforeDeref(User.plugin, BSkyBlock.class, 317, 8883, 8888)) {
                User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 317, 8883, 8888);
                if (CallChecker.beforeDeref(CallChecker.isCalled(User.plugin, BSkyBlock.class, 317, 8883, 8888).getSettings(), Settings.class, 317, 8883, 8902)) {
                    User.plugin = CallChecker.beforeCalled(User.plugin, BSkyBlock.class, 317, 8883, 8888);
                    return Locale.forLanguageTag(CallChecker.isCalled(CallChecker.isCalled(User.plugin, BSkyBlock.class, 317, 8883, 8888).getSettings(), Settings.class, 317, 8883, 8902).getDefaultLanguage());
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Locale) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context27.methodEnd();
        }
    }

    @SuppressWarnings(value = "deprecation")
    public void updateInventory() {
        MethodContext _bcornu_methode_context28 = new MethodContext(void.class, 326, 8939, 9177);
        try {
            CallChecker.varInit(this, "this", 326, 8939, 9177);
            CallChecker.varInit(this.sender, "sender", 326, 8939, 9177);
            CallChecker.varInit(this.playerUUID, "playerUUID", 326, 8939, 9177);
            CallChecker.varInit(this.player, "player", 326, 8939, 9177);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 326, 8939, 9177);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 326, 8939, 9177);
            if (CallChecker.beforeDeref(player, Player.class, 327, 9146, 9151)) {
                player = CallChecker.beforeCalled(player, Player.class, 327, 9146, 9151);
                CallChecker.isCalled(player, Player.class, 327, 9146, 9151).updateInventory();
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context28.methodEnd();
        }
    }

    public boolean performCommand(String cmd) {
        MethodContext _bcornu_methode_context29 = new MethodContext(boolean.class, 336, 9184, 9417);
        try {
            CallChecker.varInit(this, "this", 336, 9184, 9417);
            CallChecker.varInit(cmd, "cmd", 336, 9184, 9417);
            CallChecker.varInit(this.sender, "sender", 336, 9184, 9417);
            CallChecker.varInit(this.playerUUID, "playerUUID", 336, 9184, 9417);
            CallChecker.varInit(this.player, "player", 336, 9184, 9417);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 336, 9184, 9417);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 336, 9184, 9417);
            if (CallChecker.beforeDeref(player, Player.class, 337, 9384, 9389)) {
                player = CallChecker.beforeCalled(player, Player.class, 337, 9384, 9389);
                return CallChecker.isCalled(player, Player.class, 337, 9384, 9389).performCommand(cmd);
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context29.methodEnd();
        }
    }

    @Override
    public int hashCode() {
        MethodContext _bcornu_methode_context30 = new MethodContext(int.class, 345, 9424, 9698);
        try {
            CallChecker.varInit(this, "this", 345, 9424, 9698);
            CallChecker.varInit(this.sender, "sender", 345, 9424, 9698);
            CallChecker.varInit(this.playerUUID, "playerUUID", 345, 9424, 9698);
            CallChecker.varInit(this.player, "player", 345, 9424, 9698);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 345, 9424, 9698);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 345, 9424, 9698);
            final int prime = CallChecker.varInit(((int) (31)), "prime", 346, 9539, 9559);
            int result = CallChecker.varInit(((int) (1)), "result", 347, 9569, 9583);
            if ((playerUUID) == null) {
                result = (prime * result) + 0;
                CallChecker.varAssign(result, "result", 348, 9593, 9669);
            }else {
                result = (prime * result) + (playerUUID.hashCode());
                CallChecker.varAssign(result, "result", 348, 9593, 9669);
            }
            return result;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Integer) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context30.methodEnd();
        }
    }

    @Override
    public boolean equals(Object obj) {
        MethodContext _bcornu_methode_context31 = new MethodContext(boolean.class, 355, 9704, 10311);
        try {
            CallChecker.varInit(this, "this", 355, 9704, 10311);
            CallChecker.varInit(obj, "obj", 355, 9704, 10311);
            CallChecker.varInit(this.sender, "sender", 355, 9704, 10311);
            CallChecker.varInit(this.playerUUID, "playerUUID", 355, 9704, 10311);
            CallChecker.varInit(this.player, "player", 355, 9704, 10311);
            CallChecker.varInit(User.plugin, "us.tastybento.bskyblock.api.user.User.plugin", 355, 9704, 10311);
            CallChecker.varInit(User.users, "us.tastybento.bskyblock.api.user.User.users", 355, 9704, 10311);
            if ((this) == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof User)) {
                return false;
            }
            User other = CallChecker.varInit(((User) (obj)), "other", 365, 10044, 10067);
            if ((playerUUID) == null) {
                if (CallChecker.beforeDeref(other, User.class, 367, 10119, 10123)) {
                    other = CallChecker.beforeCalled(other, User.class, 367, 10119, 10123);
                    if ((CallChecker.isCalled(other, User.class, 367, 10119, 10123).playerUUID) != null) {
                        return false;
                    }
                }else
                    throw new AbnormalExecutionError();
                
            }else
                if (CallChecker.beforeDeref(other, User.class, 370, 10229, 10233)) {
                    other = CallChecker.beforeCalled(other, User.class, 370, 10229, 10233);
                    if (!(playerUUID.equals(CallChecker.isCalled(other, User.class, 370, 10229, 10233).playerUUID))) {
                        return false;
                    }
                }else
                    throw new AbnormalExecutionError();
                
            
            return true;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context31.methodEnd();
        }
    }
}

