package us.tastybento.bskyblock.database.objects;

import com.google.gson.annotations.Expose;
import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.ConstructorContext;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import us.tastybento.bskyblock.BSkyBlock;
import us.tastybento.bskyblock.Settings;
import us.tastybento.bskyblock.util.Util;

public class Players implements DataObject {
    @Expose
    private Map<Location, Integer> homeLocations = new HashMap<>();

    @Expose
    private String uniqueId;

    @Expose
    private String playerName;

    @Expose
    private int resetsLeft;

    @Expose
    private String locale = "";

    @Expose
    private int deaths;

    @Expose
    private Map<Location, Long> kickedList = new HashMap<>();

    public Players() {
        ConstructorContext _bcornu_methode_context4 = new ConstructorContext(Players.class, 44, 935, 1014);
        try {
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    public Players(BSkyBlock plugin, UUID uniqueId) {
        ConstructorContext _bcornu_methode_context5 = new ConstructorContext(Players.class, 52, 1021, 1555);
        try {
            if (CallChecker.beforeDeref(uniqueId, UUID.class, 53, 1264, 1271)) {
                uniqueId = CallChecker.beforeCalled(uniqueId, UUID.class, 53, 1264, 1271);
                this.uniqueId = CallChecker.isCalled(uniqueId, UUID.class, 53, 1264, 1271).toString();
                CallChecker.varAssign(this.uniqueId, "this.uniqueId", 53, 1248, 1283);
            }
            homeLocations = new HashMap<>();
            CallChecker.varAssign(this.homeLocations, "this.homeLocations", 54, 1293, 1324);
            if (CallChecker.beforeDeref(plugin, BSkyBlock.class, 55, 1347, 1352)) {
                plugin = CallChecker.beforeCalled(plugin, BSkyBlock.class, 55, 1347, 1352);
                if (CallChecker.beforeDeref(CallChecker.isCalled(plugin, BSkyBlock.class, 55, 1347, 1352).getSettings(), Settings.class, 55, 1347, 1366)) {
                    plugin = CallChecker.beforeCalled(plugin, BSkyBlock.class, 55, 1347, 1352);
                    resetsLeft = CallChecker.isCalled(CallChecker.isCalled(plugin, BSkyBlock.class, 55, 1347, 1352).getSettings(), Settings.class, 55, 1347, 1366).getResetLimit();
                    CallChecker.varAssign(this.resetsLeft, "this.resetsLeft", 55, 1334, 1383);
                }
            }
            locale = "";
            CallChecker.varAssign(this.locale, "this.locale", 56, 1393, 1404);
            kickedList = new HashMap<>();
            CallChecker.varAssign(this.kickedList, "this.kickedList", 57, 1414, 1442);
            if (CallChecker.beforeDeref(Bukkit.getOfflinePlayer(uniqueId), OfflinePlayer.class, 59, 1506, 1538)) {
                this.playerName = CallChecker.isCalled(Bukkit.getOfflinePlayer(uniqueId), OfflinePlayer.class, 59, 1506, 1538).getName();
                CallChecker.varAssign(this.playerName, "this.playerName", 59, 1488, 1549);
            }
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    public Location getHomeLocation(World world) {
        MethodContext _bcornu_methode_context32 = new MethodContext(Location.class, 67, 1562, 1807);
        try {
            CallChecker.varInit(this, "this", 67, 1562, 1807);
            CallChecker.varInit(world, "world", 67, 1562, 1807);
            CallChecker.varInit(this.kickedList, "kickedList", 67, 1562, 1807);
            CallChecker.varInit(this.deaths, "deaths", 67, 1562, 1807);
            CallChecker.varInit(this.locale, "locale", 67, 1562, 1807);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 67, 1562, 1807);
            CallChecker.varInit(this.playerName, "playerName", 67, 1562, 1807);
            CallChecker.varInit(this.uniqueId, "uniqueId", 67, 1562, 1807);
            CallChecker.varInit(this.homeLocations, "homeLocations", 67, 1562, 1807);
            return getHomeLocation(world, 1);
        } catch (ForceReturn _bcornu_return_t) {
            return ((Location) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context32.methodEnd();
        }
    }

    public Location getHomeLocation(World world, int number) {
        MethodContext _bcornu_methode_context33 = new MethodContext(Location.class, 77, 1814, 2364);
        try {
            CallChecker.varInit(this, "this", 77, 1814, 2364);
            CallChecker.varInit(number, "number", 77, 1814, 2364);
            CallChecker.varInit(world, "world", 77, 1814, 2364);
            CallChecker.varInit(this.kickedList, "kickedList", 77, 1814, 2364);
            CallChecker.varInit(this.deaths, "deaths", 77, 1814, 2364);
            CallChecker.varInit(this.locale, "locale", 77, 1814, 2364);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 77, 1814, 2364);
            CallChecker.varInit(this.playerName, "playerName", 77, 1814, 2364);
            CallChecker.varInit(this.uniqueId, "uniqueId", 77, 1814, 2364);
            CallChecker.varInit(this.homeLocations, "homeLocations", 77, 1814, 2364);
            if (CallChecker.beforeDeref(homeLocations, Map.class, 78, 2122, 2134)) {
                homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                if (CallChecker.beforeDeref(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145)) {
                    homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145).stream(), Stream.class, 78, 2122, 2154)) {
                        homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145).stream(), Stream.class, 78, 2122, 2154).filter(( en) -> (Util.sameWorld(en.getKey().getWorld(), world)) && ((en.getValue()) == number)), Stream.class, 78, 2122, 2258)) {
                            homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                            if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145).stream(), Stream.class, 78, 2122, 2154).filter(( en) -> (Util.sameWorld(en.getKey().getWorld(), world)) && ((en.getValue()) == number)), Stream.class, 78, 2122, 2258).map(( en) -> en.getKey()), Stream.class, 78, 2122, 2298)) {
                                homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                                if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145).stream(), Stream.class, 78, 2122, 2154).filter(( en) -> (Util.sameWorld(en.getKey().getWorld(), world)) && ((en.getValue()) == number)), Stream.class, 78, 2122, 2258).map(( en) -> en.getKey()), Stream.class, 78, 2122, 2298).findFirst(), Optional.class, 78, 2122, 2327)) {
                                    homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 78, 2122, 2134);
                                    return CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 78, 2122, 2134).entrySet(), Set.class, 78, 2122, 2145).stream(), Stream.class, 78, 2122, 2154).filter(( en) -> (Util.sameWorld(en.getKey().getWorld(), world)) && ((en.getValue()) == number)), Stream.class, 78, 2122, 2258).map(( en) -> en.getKey()), Stream.class, 78, 2122, 2298).findFirst(), Optional.class, 78, 2122, 2327).orElse(null);
                                }else
                                    throw new AbnormalExecutionError();
                                
                            }else
                                throw new AbnormalExecutionError();
                            
                        }else
                            throw new AbnormalExecutionError();
                        
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Location) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context33.methodEnd();
        }
    }

    public Map<Location, Integer> getHomeLocations(World world) {
        MethodContext _bcornu_methode_context34 = new MethodContext(Map.class, 89, 2371, 2702);
        try {
            CallChecker.varInit(this, "this", 89, 2371, 2702);
            CallChecker.varInit(world, "world", 89, 2371, 2702);
            CallChecker.varInit(this.kickedList, "kickedList", 89, 2371, 2702);
            CallChecker.varInit(this.deaths, "deaths", 89, 2371, 2702);
            CallChecker.varInit(this.locale, "locale", 89, 2371, 2702);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 89, 2371, 2702);
            CallChecker.varInit(this.playerName, "playerName", 89, 2371, 2702);
            CallChecker.varInit(this.uniqueId, "uniqueId", 89, 2371, 2702);
            CallChecker.varInit(this.homeLocations, "homeLocations", 89, 2371, 2702);
            if (CallChecker.beforeDeref(homeLocations, Map.class, 90, 2523, 2535)) {
                homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 90, 2523, 2535);
                if (CallChecker.beforeDeref(CallChecker.isCalled(homeLocations, Map.class, 90, 2523, 2535).entrySet(), Set.class, 90, 2523, 2546)) {
                    homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 90, 2523, 2535);
                    if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 90, 2523, 2535).entrySet(), Set.class, 90, 2523, 2546).stream(), Stream.class, 90, 2523, 2555)) {
                        homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 90, 2523, 2535);
                        if (CallChecker.beforeDeref(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 90, 2523, 2535).entrySet(), Set.class, 90, 2523, 2546).stream(), Stream.class, 90, 2523, 2555).filter(( e) -> Util.sameWorld(e.getKey().getWorld(), world)), Stream.class, 90, 2523, 2612)) {
                            homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 90, 2523, 2535);
                            return CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 90, 2523, 2535).entrySet(), Set.class, 90, 2523, 2546).stream(), Stream.class, 90, 2523, 2555).filter(( e) -> Util.sameWorld(e.getKey().getWorld(), world)), Stream.class, 90, 2523, 2612).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                        }else
                            throw new AbnormalExecutionError();
                        
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Map<Location, Integer>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context34.methodEnd();
        }
    }

    public Map<Location, Long> getKickedList() {
        MethodContext _bcornu_methode_context35 = new MethodContext(Map.class, 97, 2713, 2835);
        try {
            CallChecker.varInit(this, "this", 97, 2713, 2835);
            CallChecker.varInit(this.kickedList, "kickedList", 97, 2713, 2835);
            CallChecker.varInit(this.deaths, "deaths", 97, 2713, 2835);
            CallChecker.varInit(this.locale, "locale", 97, 2713, 2835);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 97, 2713, 2835);
            CallChecker.varInit(this.playerName, "playerName", 97, 2713, 2835);
            CallChecker.varInit(this.uniqueId, "uniqueId", 97, 2713, 2835);
            CallChecker.varInit(this.homeLocations, "homeLocations", 97, 2713, 2835);
            return kickedList;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Map<Location, Long>) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context35.methodEnd();
        }
    }

    public void setKickedList(Map<Location, Long> kickedList) {
        MethodContext _bcornu_methode_context36 = new MethodContext(void.class, 104, 2842, 3007);
        try {
            CallChecker.varInit(this, "this", 104, 2842, 3007);
            CallChecker.varInit(kickedList, "kickedList", 104, 2842, 3007);
            CallChecker.varInit(this.kickedList, "kickedList", 104, 2842, 3007);
            CallChecker.varInit(this.deaths, "deaths", 104, 2842, 3007);
            CallChecker.varInit(this.locale, "locale", 104, 2842, 3007);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 104, 2842, 3007);
            CallChecker.varInit(this.playerName, "playerName", 104, 2842, 3007);
            CallChecker.varInit(this.uniqueId, "uniqueId", 104, 2842, 3007);
            CallChecker.varInit(this.homeLocations, "homeLocations", 104, 2842, 3007);
            this.kickedList = kickedList;
            CallChecker.varAssign(this.kickedList, "this.kickedList", 105, 2973, 3001);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context36.methodEnd();
        }
    }

    public void setHomeLocations(Map<Location, Integer> homeLocations) {
        MethodContext _bcornu_methode_context37 = new MethodContext(void.class, 111, 3014, 3200);
        try {
            CallChecker.varInit(this, "this", 111, 3014, 3200);
            CallChecker.varInit(homeLocations, "homeLocations", 111, 3014, 3200);
            CallChecker.varInit(this.kickedList, "kickedList", 111, 3014, 3200);
            CallChecker.varInit(this.deaths, "deaths", 111, 3014, 3200);
            CallChecker.varInit(this.locale, "locale", 111, 3014, 3200);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 111, 3014, 3200);
            CallChecker.varInit(this.playerName, "playerName", 111, 3014, 3200);
            CallChecker.varInit(this.uniqueId, "uniqueId", 111, 3014, 3200);
            CallChecker.varInit(this.homeLocations, "homeLocations", 111, 3014, 3200);
            this.homeLocations = homeLocations;
            CallChecker.varAssign(this.homeLocations, "this.homeLocations", 112, 3160, 3194);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context37.methodEnd();
        }
    }

    public void setPlayerName(String playerName) {
        MethodContext _bcornu_methode_context38 = new MethodContext(void.class, 118, 3207, 3359);
        try {
            CallChecker.varInit(this, "this", 118, 3207, 3359);
            CallChecker.varInit(playerName, "playerName", 118, 3207, 3359);
            CallChecker.varInit(this.kickedList, "kickedList", 118, 3207, 3359);
            CallChecker.varInit(this.deaths, "deaths", 118, 3207, 3359);
            CallChecker.varInit(this.locale, "locale", 118, 3207, 3359);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 118, 3207, 3359);
            CallChecker.varInit(this.playerName, "playerName", 118, 3207, 3359);
            CallChecker.varInit(this.uniqueId, "uniqueId", 118, 3207, 3359);
            CallChecker.varInit(this.homeLocations, "homeLocations", 118, 3207, 3359);
            this.playerName = playerName;
            CallChecker.varAssign(this.playerName, "this.playerName", 119, 3325, 3353);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context38.methodEnd();
        }
    }

    public Player getPlayer() {
        MethodContext _bcornu_methode_context39 = new MethodContext(Player.class, 122, 3366, 3458);
        try {
            CallChecker.varInit(this, "this", 122, 3366, 3458);
            CallChecker.varInit(this.kickedList, "kickedList", 122, 3366, 3458);
            CallChecker.varInit(this.deaths, "deaths", 122, 3366, 3458);
            CallChecker.varInit(this.locale, "locale", 122, 3366, 3458);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 122, 3366, 3458);
            CallChecker.varInit(this.playerName, "playerName", 122, 3366, 3458);
            CallChecker.varInit(this.uniqueId, "uniqueId", 122, 3366, 3458);
            CallChecker.varInit(this.homeLocations, "homeLocations", 122, 3366, 3458);
            return Bukkit.getPlayer(UUID.fromString(uniqueId));
        } catch (ForceReturn _bcornu_return_t) {
            return ((Player) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context39.methodEnd();
        }
    }

    public UUID getPlayerUUID() {
        MethodContext _bcornu_methode_context40 = new MethodContext(UUID.class, 126, 3465, 3541);
        try {
            CallChecker.varInit(this, "this", 126, 3465, 3541);
            CallChecker.varInit(this.kickedList, "kickedList", 126, 3465, 3541);
            CallChecker.varInit(this.deaths, "deaths", 126, 3465, 3541);
            CallChecker.varInit(this.locale, "locale", 126, 3465, 3541);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 126, 3465, 3541);
            CallChecker.varInit(this.playerName, "playerName", 126, 3465, 3541);
            CallChecker.varInit(this.uniqueId, "uniqueId", 126, 3465, 3541);
            CallChecker.varInit(this.homeLocations, "homeLocations", 126, 3465, 3541);
            return UUID.fromString(uniqueId);
        } catch (ForceReturn _bcornu_return_t) {
            return ((UUID) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context40.methodEnd();
        }
    }

    public String getPlayerName() {
        MethodContext _bcornu_methode_context41 = new MethodContext(String.class, 130, 3548, 3611);
        try {
            CallChecker.varInit(this, "this", 130, 3548, 3611);
            CallChecker.varInit(this.kickedList, "kickedList", 130, 3548, 3611);
            CallChecker.varInit(this.deaths, "deaths", 130, 3548, 3611);
            CallChecker.varInit(this.locale, "locale", 130, 3548, 3611);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 130, 3548, 3611);
            CallChecker.varInit(this.playerName, "playerName", 130, 3548, 3611);
            CallChecker.varInit(this.uniqueId, "uniqueId", 130, 3548, 3611);
            CallChecker.varInit(this.homeLocations, "homeLocations", 130, 3548, 3611);
            return playerName;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context41.methodEnd();
        }
    }

    public int getResetsLeft() {
        MethodContext _bcornu_methode_context42 = new MethodContext(int.class, 137, 3618, 3724);
        try {
            CallChecker.varInit(this, "this", 137, 3618, 3724);
            CallChecker.varInit(this.kickedList, "kickedList", 137, 3618, 3724);
            CallChecker.varInit(this.deaths, "deaths", 137, 3618, 3724);
            CallChecker.varInit(this.locale, "locale", 137, 3618, 3724);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 137, 3618, 3724);
            CallChecker.varInit(this.playerName, "playerName", 137, 3618, 3724);
            CallChecker.varInit(this.uniqueId, "uniqueId", 137, 3618, 3724);
            CallChecker.varInit(this.homeLocations, "homeLocations", 137, 3618, 3724);
            return resetsLeft;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Integer) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context42.methodEnd();
        }
    }

    public void setResetsLeft(int resetsLeft) {
        MethodContext _bcornu_methode_context43 = new MethodContext(void.class, 145, 3731, 3898);
        try {
            CallChecker.varInit(this, "this", 145, 3731, 3898);
            CallChecker.varInit(resetsLeft, "resetsLeft", 145, 3731, 3898);
            CallChecker.varInit(this.kickedList, "kickedList", 145, 3731, 3898);
            CallChecker.varInit(this.deaths, "deaths", 145, 3731, 3898);
            CallChecker.varInit(this.locale, "locale", 145, 3731, 3898);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 145, 3731, 3898);
            CallChecker.varInit(this.playerName, "playerName", 145, 3731, 3898);
            CallChecker.varInit(this.uniqueId, "uniqueId", 145, 3731, 3898);
            CallChecker.varInit(this.homeLocations, "homeLocations", 145, 3731, 3898);
            this.resetsLeft = resetsLeft;
            CallChecker.varAssign(this.resetsLeft, "this.resetsLeft", 146, 3864, 3892);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context43.methodEnd();
        }
    }

    public void setHomeLocation(final Location l) {
        MethodContext _bcornu_methode_context44 = new MethodContext(void.class, 155, 3905, 4128);
        try {
            CallChecker.varInit(this, "this", 155, 3905, 4128);
            CallChecker.varInit(l, "l", 155, 3905, 4128);
            CallChecker.varInit(this.kickedList, "kickedList", 155, 3905, 4128);
            CallChecker.varInit(this.deaths, "deaths", 155, 3905, 4128);
            CallChecker.varInit(this.locale, "locale", 155, 3905, 4128);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 155, 3905, 4128);
            CallChecker.varInit(this.playerName, "playerName", 155, 3905, 4128);
            CallChecker.varInit(this.uniqueId, "uniqueId", 155, 3905, 4128);
            CallChecker.varInit(this.homeLocations, "homeLocations", 155, 3905, 4128);
            setHomeLocation(l, 1);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context44.methodEnd();
        }
    }

    public void setHomeLocation(Location location, int number) {
        MethodContext _bcornu_methode_context45 = new MethodContext(void.class, 164, 4135, 4410);
        try {
            CallChecker.varInit(this, "this", 164, 4135, 4410);
            CallChecker.varInit(number, "number", 164, 4135, 4410);
            CallChecker.varInit(location, "location", 164, 4135, 4410);
            CallChecker.varInit(this.kickedList, "kickedList", 164, 4135, 4410);
            CallChecker.varInit(this.deaths, "deaths", 164, 4135, 4410);
            CallChecker.varInit(this.locale, "locale", 164, 4135, 4410);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 164, 4135, 4410);
            CallChecker.varInit(this.playerName, "playerName", 164, 4135, 4410);
            CallChecker.varInit(this.uniqueId, "uniqueId", 164, 4135, 4410);
            CallChecker.varInit(this.homeLocations, "homeLocations", 164, 4135, 4410);
            if (CallChecker.beforeDeref(homeLocations, Map.class, 165, 4369, 4381)) {
                homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 165, 4369, 4381);
                CallChecker.isCalled(homeLocations, Map.class, 165, 4369, 4381).put(location, number);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context45.methodEnd();
        }
    }

    public void setPlayerUUID(UUID uuid) {
        MethodContext _bcornu_methode_context46 = new MethodContext(void.class, 172, 4417, 4581);
        try {
            CallChecker.varInit(this, "this", 172, 4417, 4581);
            CallChecker.varInit(uuid, "uuid", 172, 4417, 4581);
            CallChecker.varInit(this.kickedList, "kickedList", 172, 4417, 4581);
            CallChecker.varInit(this.deaths, "deaths", 172, 4417, 4581);
            CallChecker.varInit(this.locale, "locale", 172, 4417, 4581);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 172, 4417, 4581);
            CallChecker.varInit(this.playerName, "playerName", 172, 4417, 4581);
            CallChecker.varInit(this.uniqueId, "uniqueId", 172, 4417, 4581);
            CallChecker.varInit(this.homeLocations, "homeLocations", 172, 4417, 4581);
            if (CallChecker.beforeDeref(uuid, UUID.class, 173, 4560, 4563)) {
                uuid = CallChecker.beforeCalled(uuid, UUID.class, 173, 4560, 4563);
                uniqueId = CallChecker.isCalled(uuid, UUID.class, 173, 4560, 4563).toString();
                CallChecker.varAssign(this.uniqueId, "this.uniqueId", 173, 4549, 4575);
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context46.methodEnd();
        }
    }

    public void clearHomeLocations(World world) {
        MethodContext _bcornu_methode_context47 = new MethodContext(void.class, 180, 4588, 4800);
        try {
            CallChecker.varInit(this, "this", 180, 4588, 4800);
            CallChecker.varInit(world, "world", 180, 4588, 4800);
            CallChecker.varInit(this.kickedList, "kickedList", 180, 4588, 4800);
            CallChecker.varInit(this.deaths, "deaths", 180, 4588, 4800);
            CallChecker.varInit(this.locale, "locale", 180, 4588, 4800);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 180, 4588, 4800);
            CallChecker.varInit(this.playerName, "playerName", 180, 4588, 4800);
            CallChecker.varInit(this.uniqueId, "uniqueId", 180, 4588, 4800);
            CallChecker.varInit(this.homeLocations, "homeLocations", 180, 4588, 4800);
            if (CallChecker.beforeDeref(homeLocations, Map.class, 181, 4721, 4733)) {
                homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 181, 4721, 4733);
                if (CallChecker.beforeDeref(CallChecker.isCalled(homeLocations, Map.class, 181, 4721, 4733).keySet(), Set.class, 181, 4721, 4742)) {
                    homeLocations = CallChecker.beforeCalled(homeLocations, Map.class, 181, 4721, 4733);
                    CallChecker.isCalled(CallChecker.isCalled(homeLocations, Map.class, 181, 4721, 4733).keySet(), Set.class, 181, 4721, 4742).removeIf(( l) -> Util.sameWorld(l.getWorld(), world));
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context47.methodEnd();
        }
    }

    public String getLocale() {
        MethodContext _bcornu_methode_context48 = new MethodContext(String.class, 187, 4807, 4904);
        try {
            CallChecker.varInit(this, "this", 187, 4807, 4904);
            CallChecker.varInit(this.kickedList, "kickedList", 187, 4807, 4904);
            CallChecker.varInit(this.deaths, "deaths", 187, 4807, 4904);
            CallChecker.varInit(this.locale, "locale", 187, 4807, 4904);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 187, 4807, 4904);
            CallChecker.varInit(this.playerName, "playerName", 187, 4807, 4904);
            CallChecker.varInit(this.uniqueId, "uniqueId", 187, 4807, 4904);
            CallChecker.varInit(this.homeLocations, "homeLocations", 187, 4807, 4904);
            return locale;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context48.methodEnd();
        }
    }

    public void setLocale(String locale) {
        MethodContext _bcornu_methode_context49 = new MethodContext(void.class, 194, 4911, 5039);
        try {
            CallChecker.varInit(this, "this", 194, 4911, 5039);
            CallChecker.varInit(locale, "locale", 194, 4911, 5039);
            CallChecker.varInit(this.kickedList, "kickedList", 194, 4911, 5039);
            CallChecker.varInit(this.deaths, "deaths", 194, 4911, 5039);
            CallChecker.varInit(this.locale, "locale", 194, 4911, 5039);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 194, 4911, 5039);
            CallChecker.varInit(this.playerName, "playerName", 194, 4911, 5039);
            CallChecker.varInit(this.uniqueId, "uniqueId", 194, 4911, 5039);
            CallChecker.varInit(this.homeLocations, "homeLocations", 194, 4911, 5039);
            this.locale = locale;
            CallChecker.varAssign(this.locale, "this.locale", 195, 5013, 5033);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context49.methodEnd();
        }
    }

    public int getDeaths() {
        MethodContext _bcornu_methode_context50 = new MethodContext(int.class, 201, 5046, 5140);
        try {
            CallChecker.varInit(this, "this", 201, 5046, 5140);
            CallChecker.varInit(this.kickedList, "kickedList", 201, 5046, 5140);
            CallChecker.varInit(this.deaths, "deaths", 201, 5046, 5140);
            CallChecker.varInit(this.locale, "locale", 201, 5046, 5140);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 201, 5046, 5140);
            CallChecker.varInit(this.playerName, "playerName", 201, 5046, 5140);
            CallChecker.varInit(this.uniqueId, "uniqueId", 201, 5046, 5140);
            CallChecker.varInit(this.homeLocations, "homeLocations", 201, 5046, 5140);
            return deaths;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Integer) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context50.methodEnd();
        }
    }

    public void setDeaths(int deaths) {
        MethodContext _bcornu_methode_context51 = new MethodContext(void.class, 208, 5147, 5367);
        try {
            CallChecker.varInit(this, "this", 208, 5147, 5367);
            CallChecker.varInit(deaths, "deaths", 208, 5147, 5367);
            CallChecker.varInit(this.kickedList, "kickedList", 208, 5147, 5367);
            CallChecker.varInit(this.deaths, "deaths", 208, 5147, 5367);
            CallChecker.varInit(this.locale, "locale", 208, 5147, 5367);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 208, 5147, 5367);
            CallChecker.varInit(this.playerName, "playerName", 208, 5147, 5367);
            CallChecker.varInit(this.uniqueId, "uniqueId", 208, 5147, 5367);
            CallChecker.varInit(this.homeLocations, "homeLocations", 208, 5147, 5367);
            if (CallChecker.beforeDeref(getPlugin(), BSkyBlock.class, 209, 5269, 5279)) {
                if (CallChecker.beforeDeref(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 209, 5269, 5279).getSettings(), Settings.class, 209, 5269, 5293)) {
                    if (deaths > (CallChecker.isCalled(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 209, 5269, 5279).getSettings(), Settings.class, 209, 5269, 5293).getDeathsMax())) {
                        if (CallChecker.beforeDeref(getPlugin(), BSkyBlock.class, 209, 5312, 5322)) {
                            if (CallChecker.beforeDeref(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 209, 5312, 5322).getSettings(), Settings.class, 209, 5312, 5336)) {
                                this.deaths = CallChecker.isCalled(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 209, 5312, 5322).getSettings(), Settings.class, 209, 5312, 5336).getDeathsMax();
                                CallChecker.varAssign(this.deaths, "this.deaths", 209, 5246, 5361);
                            }
                        }
                    }else {
                        this.deaths = deaths;
                        CallChecker.varAssign(this.deaths, "this.deaths", 209, 5246, 5361);
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context51.methodEnd();
        }
    }

    public void addDeath() {
        MethodContext _bcornu_methode_context52 = new MethodContext(void.class, 215, 5374, 5533);
        try {
            CallChecker.varInit(this, "this", 215, 5374, 5533);
            CallChecker.varInit(this.kickedList, "kickedList", 215, 5374, 5533);
            CallChecker.varInit(this.deaths, "deaths", 215, 5374, 5533);
            CallChecker.varInit(this.locale, "locale", 215, 5374, 5533);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 215, 5374, 5533);
            CallChecker.varInit(this.playerName, "playerName", 215, 5374, 5533);
            CallChecker.varInit(this.uniqueId, "uniqueId", 215, 5374, 5533);
            CallChecker.varInit(this.homeLocations, "homeLocations", 215, 5374, 5533);
            if (CallChecker.beforeDeref(getPlugin(), BSkyBlock.class, 216, 5453, 5463)) {
                if (CallChecker.beforeDeref(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 216, 5453, 5463).getSettings(), Settings.class, 216, 5453, 5477)) {
                    if ((deaths) < (CallChecker.isCalled(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 216, 5453, 5463).getSettings(), Settings.class, 216, 5453, 5477).getDeathsMax())) {
                        (deaths)++;
                    }
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context52.methodEnd();
        }
    }

    public long getInviteCoolDownTime(Location location) {
        MethodContext _bcornu_methode_context53 = new MethodContext(long.class, 228, 5540, 6757);
        try {
            CallChecker.varInit(this, "this", 228, 5540, 6757);
            CallChecker.varInit(location, "location", 228, 5540, 6757);
            CallChecker.varInit(this.kickedList, "kickedList", 228, 5540, 6757);
            CallChecker.varInit(this.deaths, "deaths", 228, 5540, 6757);
            CallChecker.varInit(this.locale, "locale", 228, 5540, 6757);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 228, 5540, 6757);
            CallChecker.varInit(this.playerName, "playerName", 228, 5540, 6757);
            CallChecker.varInit(this.uniqueId, "uniqueId", 228, 5540, 6757);
            CallChecker.varInit(this.homeLocations, "homeLocations", 228, 5540, 6757);
            if (CallChecker.beforeDeref(kickedList, Map.class, 230, 5862, 5871)) {
                kickedList = CallChecker.beforeCalled(kickedList, Map.class, 230, 5862, 5871);
                if ((location != null) && (CallChecker.isCalled(kickedList, Map.class, 230, 5862, 5871).containsKey(location))) {
                    Date kickedDate = CallChecker.init(Date.class);
                    if (CallChecker.beforeDeref(kickedList, Map.class, 233, 6015, 6024)) {
                        kickedList = CallChecker.beforeCalled(kickedList, Map.class, 233, 6015, 6024);
                        kickedDate = new Date(CallChecker.isCalled(kickedList, Map.class, 233, 6015, 6024).get(location));
                        CallChecker.varAssign(kickedDate, "kickedDate", 233, 6015, 6024);
                    }
                    Calendar coolDownTime = CallChecker.varInit(Calendar.getInstance(), "coolDownTime", 234, 6054, 6100);
                    if (CallChecker.beforeDeref(coolDownTime, Calendar.class, 235, 6114, 6125)) {
                        coolDownTime = CallChecker.beforeCalled(coolDownTime, Calendar.class, 235, 6114, 6125);
                        CallChecker.isCalled(coolDownTime, Calendar.class, 235, 6114, 6125).setTime(kickedDate);
                    }
                    if (CallChecker.beforeDeref(getPlugin(), BSkyBlock.class, 236, 6194, 6204)) {
                        if (CallChecker.beforeDeref(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 236, 6194, 6204).getSettings(), Settings.class, 236, 6194, 6218)) {
                            if (CallChecker.beforeDeref(coolDownTime, Calendar.class, 236, 6160, 6171)) {
                                coolDownTime = CallChecker.beforeCalled(coolDownTime, Calendar.class, 236, 6160, 6171);
                                CallChecker.isCalled(coolDownTime, Calendar.class, 236, 6160, 6171).add(Calendar.MINUTE, CallChecker.isCalled(CallChecker.isCalled(getPlugin(), BSkyBlock.class, 236, 6194, 6204).getSettings(), Settings.class, 236, 6194, 6218).getInviteWait());
                            }
                        }
                    }
                    Calendar timeNow = CallChecker.varInit(Calendar.getInstance(), "timeNow", 238, 6296, 6337);
                    if (CallChecker.beforeDeref(coolDownTime, Calendar.class, 239, 6355, 6366)) {
                        coolDownTime = CallChecker.beforeCalled(coolDownTime, Calendar.class, 239, 6355, 6366);
                        if (CallChecker.isCalled(coolDownTime, Calendar.class, 239, 6355, 6366).before(timeNow)) {
                            if (CallChecker.beforeDeref(kickedList, Map.class, 241, 6443, 6452)) {
                                kickedList = CallChecker.beforeCalled(kickedList, Map.class, 241, 6443, 6452);
                                CallChecker.isCalled(kickedList, Map.class, 241, 6443, 6452).remove(location);
                            }
                            return 0;
                        }else {
                            coolDownTime = CallChecker.beforeCalled(coolDownTime, Calendar.class, 246, 6634, 6645);
                            timeNow = CallChecker.beforeCalled(timeNow, Calendar.class, 246, 6667, 6673);
                            return ((long) (Math.ceil((((CallChecker.isCalled(coolDownTime, Calendar.class, 246, 6634, 6645).getTimeInMillis()) - (CallChecker.isCalled(timeNow, Calendar.class, 246, 6667, 6673).getTimeInMillis())) / (1000 * 60.0)))));
                        }
                    }else
                        throw new AbnormalExecutionError();
                    
                }
            }else
                throw new AbnormalExecutionError();
            
            return 0;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Long) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context53.methodEnd();
        }
    }

    public void startInviteCoolDownTimer(Location location) {
        MethodContext _bcornu_methode_context54 = new MethodContext(void.class, 256, 6764, 7086);
        try {
            CallChecker.varInit(this, "this", 256, 6764, 7086);
            CallChecker.varInit(location, "location", 256, 6764, 7086);
            CallChecker.varInit(this.kickedList, "kickedList", 256, 6764, 7086);
            CallChecker.varInit(this.deaths, "deaths", 256, 6764, 7086);
            CallChecker.varInit(this.locale, "locale", 256, 6764, 7086);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 256, 6764, 7086);
            CallChecker.varInit(this.playerName, "playerName", 256, 6764, 7086);
            CallChecker.varInit(this.uniqueId, "uniqueId", 256, 6764, 7086);
            CallChecker.varInit(this.homeLocations, "homeLocations", 256, 6764, 7086);
            if (location != null) {
                if (CallChecker.beforeDeref(kickedList, Map.class, 258, 7018, 7027)) {
                    kickedList = CallChecker.beforeCalled(kickedList, Map.class, 258, 7018, 7027);
                    CallChecker.isCalled(kickedList, Map.class, 258, 7018, 7027).put(location, System.currentTimeMillis());
                }
            }
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context54.methodEnd();
        }
    }

    @Override
    public String getUniqueId() {
        MethodContext _bcornu_methode_context55 = new MethodContext(String.class, 263, 7093, 7166);
        try {
            CallChecker.varInit(this, "this", 263, 7093, 7166);
            CallChecker.varInit(this.kickedList, "kickedList", 263, 7093, 7166);
            CallChecker.varInit(this.deaths, "deaths", 263, 7093, 7166);
            CallChecker.varInit(this.locale, "locale", 263, 7093, 7166);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 263, 7093, 7166);
            CallChecker.varInit(this.playerName, "playerName", 263, 7093, 7166);
            CallChecker.varInit(this.uniqueId, "uniqueId", 263, 7093, 7166);
            CallChecker.varInit(this.homeLocations, "homeLocations", 263, 7093, 7166);
            return uniqueId;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context55.methodEnd();
        }
    }

    @Override
    public void setUniqueId(String uniqueId) {
        MethodContext _bcornu_methode_context56 = new MethodContext(void.class, 268, 7173, 7268);
        try {
            CallChecker.varInit(this, "this", 268, 7173, 7268);
            CallChecker.varInit(uniqueId, "uniqueId", 268, 7173, 7268);
            CallChecker.varInit(this.kickedList, "kickedList", 268, 7173, 7268);
            CallChecker.varInit(this.deaths, "deaths", 268, 7173, 7268);
            CallChecker.varInit(this.locale, "locale", 268, 7173, 7268);
            CallChecker.varInit(this.resetsLeft, "resetsLeft", 268, 7173, 7268);
            CallChecker.varInit(this.playerName, "playerName", 268, 7173, 7268);
            CallChecker.varInit(this.uniqueId, "uniqueId", 268, 7173, 7268);
            CallChecker.varInit(this.homeLocations, "homeLocations", 268, 7173, 7268);
            this.uniqueId = uniqueId;
            CallChecker.varAssign(this.uniqueId, "this.uniqueId", 269, 7238, 7262);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context56.methodEnd();
        }
    }
}

