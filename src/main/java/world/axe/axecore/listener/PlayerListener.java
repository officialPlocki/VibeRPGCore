package world.axe.axecore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import world.axe.axecore.player.AXEPlayer;

public class PlayerListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        //NO ASCII!!!!!!!!!
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new AXEPlayer(event.getPlayer()).sendResourcePack();
    }

}
