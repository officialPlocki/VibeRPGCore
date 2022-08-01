package world.axe.axecore.manager;

import net.kyori.adventure.text.Component;
import world.axe.axecore.AXECore;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.util.Translator;

import java.util.Objects;

public class ResourcepackManager extends Translator {

    private final AXEPlayer player;

    public ResourcepackManager(AXEPlayer player) {
        this.player = player;
        this.define("§cDas Ressourcenpacket wird benötigt, damit du auf unserem Netzwerk spielen kannst.", "tp.needed.prompt");
    }

    public void send() {
        if(player.isOnline()) {
            player.getBukkitPlayer().setResourcePack(Objects.requireNonNull(AXECore.getConfigurationProvider().getConfiguration().getString("tp.url")),
                    Objects.requireNonNull(AXECore.getConfigurationProvider().getConfiguration().getString("tp.sha-1")),
                    true,
                    Component.text(key("tp.needed.prompt", player)));
        }
    }

}
