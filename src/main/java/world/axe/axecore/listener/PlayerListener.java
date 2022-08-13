package world.axe.axecore.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import world.axe.axecore.AXECore;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.RankManager;

import java.util.Objects;

public class PlayerListener implements Listener {

    // @todo chat message translation & colored chat
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        //NO ASCII!!!!!!!!!
        RankManager rank = AXECore.getRanks();
        event.setFormat(rank.getPrefix(event.getPlayer()) + " " + "§7" + event.getPlayer().getName() + " " + rank.getSuffix(event.getPlayer()) + " §8» §7" + event.getMessage());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        if(!Objects.requireNonNull(event.getPlayer().getClientBrandName()).toLowerCase().contains("labymod") || !event.getPlayer().getClientBrandName().toLowerCase().contains("lunarclient")) {
            event.getPlayer().kick(Component.text("§cElectronic AXE does currently only support LabyMod because of many deep implementations.\n\n§bGet LabyMod here: §7https://labymod.net"), PlayerKickEvent.Cause.PLUGIN);
        }
        new AXEPlayer(event.getPlayer()).sendResourcePack();
        AXECore.getRanks().sendTablist(event.getPlayer());
    }

}
