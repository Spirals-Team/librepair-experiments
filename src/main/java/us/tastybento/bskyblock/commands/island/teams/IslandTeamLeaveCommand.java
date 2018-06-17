package us.tastybento.bskyblock.commands.island.teams;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import us.tastybento.bskyblock.api.commands.CompositeCommand;
import us.tastybento.bskyblock.api.localization.TextVariables;
import us.tastybento.bskyblock.api.user.User;

public class IslandTeamLeaveCommand extends CompositeCommand {

    Set<UUID> leaveSet;

    public IslandTeamLeaveCommand(CompositeCommand islandTeamCommand) {
        super(islandTeamCommand, "leave");
    }

    @Override
    public void setup() {
        setPermission("island.team");
        setOnlyPlayer(true);
        setDescription("commands.island.team.leave.description");
        leaveSet = new HashSet<>();
    }

    @Override
    public boolean execute(User user, List<String> args) {
        if (!getIslands().inTeam(getWorld(), user.getUniqueId())) {
            user.sendMessage("general.errors.no-team");
            return false;
        }       
        if (getIslands().hasIsland(getWorld(), user.getUniqueId())) {
            user.sendMessage("commands.island.team.leave.cannot-leave");
            return false;
        }
        if (!getSettings().isLeaveConfirmation() || leaveSet.contains(user.getUniqueId())) {
            leaveSet.remove(user.getUniqueId());
            UUID leaderUUID = getIslands().getTeamLeader(getWorld(), user.getUniqueId());
            if (leaderUUID != null) {
                User.getInstance(leaderUUID).sendMessage("commands.island.team.leave.left-your-island", TextVariables.NAME, user.getName());
            }
            getIslands().removePlayer(getWorld(), user.getUniqueId());
            user.sendMessage("general.success");
            return true;
        } else {
            user.sendMessage("commands.island.team.leave.type-again");
            leaveSet.add(user.getUniqueId());
            new BukkitRunnable() {

                @Override
                public void run() {
                    if (leaveSet.contains(user.getUniqueId())) {
                        leaveSet.remove(user.getUniqueId());
                        user.sendMessage("general.errors.command-cancelled");
                    }
                }}.runTaskLater(getPlugin(), getSettings().getLeaveWait() * 20);
            return false;
        }
    }

}