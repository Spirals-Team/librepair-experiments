package us.tastybento.bskyblock.commands.admin.teams;

import java.util.List;
import java.util.UUID;

import us.tastybento.bskyblock.api.commands.CompositeCommand;
import us.tastybento.bskyblock.api.user.User;

public class AdminTeamMakeLeaderCommand extends CompositeCommand {

    public AdminTeamMakeLeaderCommand(CompositeCommand parent) {
        super(parent, "makeleader");
    }
    
    @Override
    public void setup() {
        setPermission("admin.team");
        setParameters("commands.admin.team.makeleader.parameters");
        setDescription("commands.admin.team.makeleader.description");
    }

    @Override
    public boolean execute(User user, List<String> args) {
        // If args are not right, show help
        if (args.size() != 1) {
            showHelp(this, user);
            return false;
        }
        // Get target
        UUID targetUUID = getPlayers().getUUID(args.get(0));
        if (targetUUID == null) {
            user.sendMessage("general.errors.unknown-player");
            return false;
        }
        if (!getIslands().hasIsland(getWorld(), targetUUID)) {
            user.sendMessage("general.errors.no-island");
            return false;
        }
        if (!getIslands().inTeam(getWorld(), targetUUID)) {
            user.sendMessage("general.errors.not-in-team");
            return false;
        }
        if (getIslands().getTeamLeader(getWorld(), targetUUID).equals(targetUUID)) {
            user.sendMessage("commands.admin.team.makeleader.already-leader");
            return false;
        }
        // Make new leader
        getIslands().makeLeader(getWorld(), user, targetUUID, getPermissionPrefix());
        user.sendMessage("general.success");
        return true;
    }
}