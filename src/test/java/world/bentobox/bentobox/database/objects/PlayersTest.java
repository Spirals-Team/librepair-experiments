package world.bentobox.bentobox.database.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.Settings;
import world.bentobox.bentobox.managers.IslandWorldManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, BentoBox.class})
public class PlayersTest {

    private IslandWorldManager s;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Set up plugin
        plugin = mock(BentoBox.class);
        Whitebox.setInternalState(BentoBox.class, "instance", plugin);
        s = mock(IslandWorldManager.class);

        when(s.getDeathsMax(Mockito.any())).thenReturn(3);
        when(plugin.getIWM()).thenReturn(s);

        Server server = mock(Server.class);
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getServer()).thenReturn(server);
        OfflinePlayer olp = mock(OfflinePlayer.class);
        when(olp.getName()).thenReturn("tasty");
        when(server.getOfflinePlayer(Mockito.any(UUID.class))).thenReturn(olp);

    }

    private BentoBox plugin;

    @Test
    public void testPlayersBSkyBlockUUID() {
        assertNotNull(new Players(plugin, UUID.randomUUID()));
    }

    @Test
    public void testSetHomeLocationLocation() {
        Players p = new Players(plugin, UUID.randomUUID());
        Location l = mock(Location.class);
        World w = mock(World.class);
        when(w.getName()).thenReturn("world");
        when(l.getWorld()).thenReturn(w);
        p.setHomeLocation(l, 5);
        assertEquals(l, p.getHomeLocation(w, 5));
        assertNotEquals(l, p.getHomeLocation(w, 0));
        p.clearHomeLocations(w);
        assertTrue(p.getHomeLocations(w).isEmpty());
    }

    @Test
    public void testDeaths() {
        Players p = new Players(plugin, UUID.randomUUID());
        World world = mock(World.class);
        when(world.getName()).thenReturn("world_name");
        assertTrue(p.getDeaths(world) == 0);
        p.addDeath(world);
        assertTrue(p.getDeaths(world) == 1);
        p.addDeath(world);
        assertTrue(p.getDeaths(world) == 2);
        p.addDeath(world);
        assertTrue(p.getDeaths(world) == 3);
        p.addDeath(world);
        assertTrue(p.getDeaths(world) == 3);
        p.addDeath(world);
        assertTrue(p.getDeaths(world) == 3);
        p.setDeaths(world, 10);
        assertTrue(p.getDeaths(world) == 3);
        p.setDeaths(world, 0);
        assertTrue(p.getDeaths(world) == 0);
    }

    @Test
    public void testInviteCoolDownTime() throws InterruptedException {
        Settings settings = mock(Settings.class);
        when(settings.getInviteWait()).thenReturn(1);
        when(plugin.getSettings()).thenReturn(settings);
        Players p = new Players(plugin, UUID.randomUUID());
        // Check a null location
        assertTrue(p.getInviteCoolDownTime(null) == 0);
        // Real location
        Location l = mock(Location.class);
        // Should be no cooldown
        assertTrue(p.getInviteCoolDownTime(l) == 0);
        // Start the timer
        p.startInviteCoolDownTimer(l);
        // More than 0 cooldown
        assertTrue(p.getInviteCoolDownTime(l) > 0);
    }
}
