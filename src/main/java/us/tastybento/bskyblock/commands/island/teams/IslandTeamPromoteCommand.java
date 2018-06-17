package us.tastybento.bskyblock.commands.island.teams;

import java.util.List;

import us.tastybento.bskyblock.api.commands.CompositeCommand;
import us.tastybento.bskyblock.api.localization.TextVariables;
import us.tastybento.bskyblock.api.user.User;

public class IslandTeamPromoteCommand extends CompositeCommand {

    public IslandTeamPromoteCommand(CompositeCommand islandTeamCommand, String string) {
        super(islandTeamCommand, string);
    }

    @Override
    public void setup() {
        setPermission("island.team");
        setOnlyPlayer(true);
        if (this.getLabel().equals("promote")) {
            setParameters("commands.island.team.promote.parameters");
            setDescription("commands.island.team.promote.description");
        } else {
            setParameters("commands.island.team.demote.parameters");
            setDescription("commands.island.team.demote.description"); 
        }
    }

    @Override
    public boolean execute(User user, List<String> args) {
        if (!getIslands().inTeam(getWorld(), user.getUniqueId())) {
            user.sendMessage("general.errors.no-team");
            return true;
        }
        if (!getTeamLeader(getWorld(), user).equals(user.getUniqueId())) {
            user.sendMessage("general.errors.not-leader");
            return true;
        }
        // If args are not right, show help
        if (args.size() != 1) {
            showHelp(this, user);
            return false;
        }
        // Get target
        User target = getPlayers().getUser(args.get(0));
        if (target == null) {
            user.sendMessage("general.errors.unknown-player");
            return true;
        }
        if (!inTeam(getWorld(), target) || !getTeamLeader(getWorld(), user).equals(getTeamLeader(getWorld(), target))) {
            user.sendMessage("general.errors.not-in-team");
            return true;
        }

        return change(user, target);
    }

    private boolean change(User user, User target) {
        int currentRank = getIslands().getIsland(getWorld(), user.getUniqueId()).getRank(target);
        if (this.getLabel().equals("promote")) {
            int nextRank = getPlugin().getRanksManager().getRankUpValue(currentRank);
            if (nextRank > currentRank) {
                getIslands().getIsland(getWorld(), user.getUniqueId()).setRank(target, nextRank);
                String rankName = user.getTranslation(getPlugin().getRanksManager().getRank(nextRank));
                user.sendMessage("commands.island.team.promote.success", TextVariables.NAME, target.getName(), TextVariables.RANK, rankName);
                return true;
            } else {
                user.sendMessage("commands.island.team.promote.failure");
                return false;
            }
        } else {
            // Demote
            int prevRank = getPlugin().getRanksManager().getRankDownValue(currentRank);
            if (prevRank < currentRank) {
                getIslands().getIsland(getWorld(), user.getUniqueId()).setRank(target, prevRank);
                String rankName = user.getTranslation(getPlugin().getRanksManager().getRank(prevRank));
                user.sendMessage("commands.island.team.demote.success", TextVariables.NAME, target.getName(), TextVariables.RANK, rankName);
                return true;
            } else {
                user.sendMessage("commands.island.team.demote.failure");
                return false;
            }
        }
    }

}