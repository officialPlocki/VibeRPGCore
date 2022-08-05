package world.axe.axecore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.RankManager;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        //NO ASCII!!!!!!!!!
        RankManager rank = new RankManager(event.getPlayer());
        event.setFormat(rank.getPrefix() + " " + "§7" + event.getPlayer().getName() + " " + rank.getSuffix());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        new AXEPlayer(event.getPlayer()).sendResourcePack();
        new RankManager(event.getPlayer()).sendTablist();
    }

}
