package us.tastybento.bskyblock.api.commands;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import us.tastybento.bskyblock.BSkyBlock;
import us.tastybento.bskyblock.Settings;
import us.tastybento.bskyblock.api.events.command.CommandEvent;
import us.tastybento.bskyblock.api.user.User;
import us.tastybento.bskyblock.commands.IslandCommand;
import us.tastybento.bskyblock.managers.CommandsManager;
import us.tastybento.bskyblock.managers.IslandWorldManager;
import us.tastybento.bskyblock.managers.IslandsManager;
import us.tastybento.bskyblock.managers.PlayersManager;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Bukkit.class, BSkyBlock.class, CommandEvent.class})
public class DefaultHelpCommandTest {

    private User user;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        // Set up plugin
        BSkyBlock plugin = mock(BSkyBlock.class);
        Whitebox.setInternalState(BSkyBlock.class, "instance", plugin);
        
        // Command manager
        CommandsManager cm = mock(CommandsManager.class);
        when(plugin.getCommandsManager()).thenReturn(cm);
        
        // Settings
        Settings s = mock(Settings.class);
        when(s.getResetWait()).thenReturn(0L);
        when(s.getResetLimit()).thenReturn(3);
        when(plugin.getSettings()).thenReturn(s);
        
        // Player
        Player player = mock(Player.class);
        // Sometimes use: Mockito.withSettings().verboseLogging()
        user = mock(User.class);
        when(user.isOp()).thenReturn(false);
        UUID uuid = UUID.randomUUID();
        when(user.getUniqueId()).thenReturn(uuid);
        when(user.getPlayer()).thenReturn(player);
        User.setPlugin(plugin);
        // Set up user already
        User.getInstance(player);
        
        // Parent command has no aliases
        IslandCommand ic = mock(IslandCommand.class);
        when(ic.getSubCommandAliases()).thenReturn(new HashMap<>());

        // No island for player to begin with (set it later in the tests)
        IslandsManager im = mock(IslandsManager.class);
        when(im.hasIsland(Mockito.any(), Mockito.eq(uuid))).thenReturn(false);
        when(im.isOwner(Mockito.any(), Mockito.eq(uuid))).thenReturn(false);
        // Has team
        when(im.inTeam(Mockito.any(), Mockito.eq(uuid))).thenReturn(true);
        when(plugin.getIslands()).thenReturn(im);


        PlayersManager pm = mock(PlayersManager.class);
        when(plugin.getPlayers()).thenReturn(pm);

        // Server & Scheduler
        BukkitScheduler sch = mock(BukkitScheduler.class);
        PowerMockito.mockStatic(Bukkit.class);
        when(Bukkit.getScheduler()).thenReturn(sch);
        
        // IWM friendly name
        IslandWorldManager iwm = mock(IslandWorldManager.class);
        when(iwm.getFriendlyName(Mockito.any())).thenReturn("BSkyBlock");
        when(plugin.getIWM()).thenReturn(iwm);

    }
    
    class FakeParent extends CompositeCommand {

        public FakeParent() {
            super("island", "is");
        }

        @Override
        public void setup() {
        }

        @Override
        public boolean execute(User user, List<String> args) {
            return false;
        }
        
    }

    @Test
    public void testDefaultHelpCommand() throws Exception {
        CompositeCommand cc = mock(CompositeCommand.class);
        
        DefaultHelpCommand dhc = new DefaultHelpCommand(cc);
        assertNotNull(dhc);
        Mockito.verify(cc).getSubCommands();                
    }

    @Test
    public void testSetup() {
        CompositeCommand cc = mock(CompositeCommand.class);
        DefaultHelpCommand dhc = new DefaultHelpCommand(cc);
        assertNotNull(dhc);
        // Verify that parent's parameters and permission is used
        Mockito.verify(cc).getParameters();
        Mockito.verify(cc).getDescription();
        Mockito.verify(cc).getPermission();
    }

    @Test
    public void testExecuteUserListOfString() {
        CompositeCommand parent = mock(CompositeCommand.class);
        when(parent.getLabel()).thenReturn("island");
        when(parent.getUsage()).thenReturn("island");
        when(parent.getParameters()).thenReturn("parameters");
        when(parent.getDescription()).thenReturn("description");
        when(parent.getPermission()).thenReturn("permission");
        when(user.getTranslation("island")).thenReturn("island");
        when(user.getTranslation("parameters")).thenReturn("");
        when(user.getTranslation("description")).thenReturn("the main island command");
        DefaultHelpCommand dhc = new DefaultHelpCommand(parent);
        dhc.execute(user, new ArrayList<>());
        Mockito.verify(user).sendMessage("commands.help.header", "[label]", "BSkyBlock");
        Mockito.verify(user).getTranslation("island");
        Mockito.verify(user).getTranslation("parameters");
        Mockito.verify(user).getTranslation("description");
        Mockito.verify(user).sendMessage(
                "commands.help.syntax",
                "[usage]",
                "island",
                "[parameters]",
                "",
                "[description]",
                "the main island command"
            );
        Mockito.verify(user).sendMessage("commands.help.end");
    }
    
    @Test
    public void testExecuteSecondLevelHelp() {
        CompositeCommand parent = mock(CompositeCommand.class);
        when(parent.getLabel()).thenReturn("island");
        when(parent.getUsage()).thenReturn("island");
        when(parent.getParameters()).thenReturn("parameters");
        when(parent.getDescription()).thenReturn("description");
        when(parent.getPermission()).thenReturn("permission");
        when(user.getTranslation("island")).thenReturn("island");
        when(user.getTranslation("parameters")).thenReturn("");
        when(user.getTranslation("description")).thenReturn("the main island command");
        DefaultHelpCommand dhc = new DefaultHelpCommand(parent);
        List<String> args = new ArrayList<>();
        args.add("1");
        dhc.execute(user, args);
        // There are no header or footer shown
        Mockito.verify(user).getTranslation("island");
        Mockito.verify(user).getTranslation("parameters");
        Mockito.verify(user).getTranslation("description");
        Mockito.verify(user).sendMessage(
                "commands.help.syntax",
                "[usage]",
                "island",
                "[parameters]",
                "",
                "[description]",
                "the main island command"
            );
    }

    @Test
    public void testExecuteDirectHelpHelp() {
        CompositeCommand parent = mock(CompositeCommand.class);
        when(parent.getLabel()).thenReturn("island");
        when(parent.getUsage()).thenReturn("island");
        when(parent.getParameters()).thenReturn("parameters");
        when(parent.getDescription()).thenReturn("description");
        when(parent.getPermission()).thenReturn("permission");
        when(user.getTranslation("island")).thenReturn("island");
        when(user.getTranslation("parameters")).thenReturn("");
        when(user.getTranslation("description")).thenReturn("the main island command");
        when(user.getTranslation("commands.help.parameters")).thenReturn("help-parameters");
        when(user.getTranslation("commands.help.description")).thenReturn("the help command");
        DefaultHelpCommand dhc = new DefaultHelpCommand(parent);
        List<String> args = new ArrayList<>();
        // Test /island help team
        args.add("team");
        dhc.execute(user, args);
        // There are no header or footer shown
        Mockito.verify(user).getTranslation("island");
        Mockito.verify(user).getTranslation("commands.help.parameters");
        Mockito.verify(user).getTranslation("commands.help.description");
        Mockito.verify(user).sendMessage(
                "commands.help.syntax",
                "[usage]",
                "island",
                "[parameters]",
                "help-parameters",
                "[description]",
                "the help command"
            );
    }

}
