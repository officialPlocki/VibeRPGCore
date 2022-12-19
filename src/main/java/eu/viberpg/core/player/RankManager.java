package eu.viberpg.core.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import eu.viberpg.core.display.DisplayHelper;

public class RankManager {

    private final Scoreboard scoreboard;

    public RankManager() {
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    public void sendTablist(Player player) {
        player.setScoreboard(scoreboard);
        Team team;
        if(scoreboard.getTeam(player.getName()) == null) {
            team = scoreboard.registerNewTeam(player.getName());
        } else {
            team = scoreboard.getTeam(player.getName());
        }
        assert team != null;
        team.setSuffix(getSuffix(player));
        team.setColor(ChatColor.GRAY);
        team.addPlayer(player);
    }

    public String getSuffix(Player player) {
        String suffix = "§f";
        if(player.hasPermission("suffix.event_guide")) {
            suffix = suffix + DisplayHelper.ranks.EVENT_GUIDE.val();
        } else if(player.hasPermission("suffix.rstaff")) {
            suffix = suffix + DisplayHelper.ranks.RSTAFF_TAG.val();
        } else if(player.hasPermission("suffix.staff")) {
            suffix = suffix + DisplayHelper.ranks.STAFF_TAG.val();
        }
        if(player.hasPermission("suffix.use.ancient")) {
            suffix = suffix + DisplayHelper.ranks.ANCIENT_TAG.val();
        } else if(player.hasPermission("suffix.use.caesar")) {
            suffix = suffix + DisplayHelper.ranks.CAESAR_TAG.val();
        } else if(player.hasPermission("suffix.use.champion")) {
            suffix = suffix + DisplayHelper.ranks.CHAMPION_TAG.val();
        } else if(player.hasPermission("suffix.use.emperor")) {
            suffix = suffix + DisplayHelper.ranks.EMPEROR_TAG.val();
        } else if(player.hasPermission("suffix.use.extreme")) {
            suffix = suffix + DisplayHelper.ranks.EXTREME_TAG.val();
        } else if(player.hasPermission("suffix.use.god")) {
            suffix = suffix + DisplayHelper.ranks.GOD_TAG.val();
        } else if(player.hasPermission("suffix.use.hero")) {
            suffix = suffix + DisplayHelper.ranks.IMMORTAL_TAG.val();
        } else if(player.hasPermission("suffix.use.knight")) {
            suffix = suffix + DisplayHelper.ranks.KNIGHT_TAG.val();
        } else if(player.hasPermission("suffix.use.kratos")) {
            suffix = suffix + DisplayHelper.ranks.KRATOS_TAG.val();
        } else if(player.hasPermission("suffix.use.legend")) {
            suffix = suffix + DisplayHelper.ranks.LEGEND_TAG.val();
        } else if(player.hasPermission("suffix.use.legion")) {
            suffix = suffix + DisplayHelper.ranks.LEGION_TAG.val();
        } else if(player.hasPermission("suffix.use.mage")) {
            suffix = suffix + DisplayHelper.ranks.MAGE_TAG.val();
        } else if(player.hasPermission("suffix.use.sage")) {
            suffix = suffix + DisplayHelper.ranks.SAGE_TAG.val();
        } else if(player.hasPermission("suffix.use.prince")) {
            suffix = suffix + DisplayHelper.ranks.PRINCE_TAG.val();
        }
        return suffix;
    }

    public String getPrefix(Player player) {
        if(player.hasPermission("rank.headadmin")) {
            return "§f" + DisplayHelper.ranks.HEADADMIN_TAG.val();
        } else if(player.hasPermission("rank.admin")) {
            return "§f" + DisplayHelper.ranks.ADMIN_TAG.val();
        } else if(player.hasPermission("rank.manager")) {
            return "§f" + DisplayHelper.ranks.MANAGER_TAG.val();
        } else if(player.hasPermission("rank.engineer")) {
            return "§f" + DisplayHelper.ranks.ENGINEER.val();
        } else if(player.hasPermission("rank.srmod")) {
            return "§f" + DisplayHelper.ranks.SRMOD_TAG.val();
        } else if(player.hasPermission("rank.mod")) {
            return "§f" + DisplayHelper.ranks.MOD_TAG.val();
        } else if(player.hasPermission("rank.builder")) {
            return "§f" + DisplayHelper.ranks.BUILDER_TAG.val();
        } else if(player.hasPermission("rank.helper")) {
            return "§f" + DisplayHelper.ranks.HELPER_TAG.val();
        } else if(player.hasPermission("rank.twitch")) {
            return "§f" + DisplayHelper.ranks.TWITCH_TAG.val();
        } else if(player.hasPermission("rank.youtuber")) {
            return "§f" + DisplayHelper.ranks.YOUTUBER_TAG.val();
        } else if(player.hasPermission("rank.baron")) {
            return "§f" + DisplayHelper.ranks.BARON_TAG.val();
        } else if(player.hasPermission("rank.mvpplus")) {
            return "§f" + DisplayHelper.ranks.MVPPLUS_TAG.val();
        } else if(player.hasPermission("rank.mvp")) {
            return "§f" + DisplayHelper.ranks.MVP_TAG.val();
        } else if(player.hasPermission("rank.vipplus")) {
            return "§f" + DisplayHelper.ranks.VIPPLUS_TAG.val();
        } else if(player.hasPermission("rank.vip")) {
            return "§f" + DisplayHelper.ranks.VIP_TAG.val();
        } else {
            return "§f" + DisplayHelper.ranks.BAMBOO_TAG.val();
        }
    }

}
