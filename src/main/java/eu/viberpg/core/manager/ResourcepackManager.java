package eu.viberpg.core.manager;

import eu.viberpg.core.player.AXEPlayer;
import eu.viberpg.core.util.TranslationUtil;
import net.kyori.adventure.text.Component;
import eu.viberpg.core.Core;

import java.util.Objects;

public class ResourcepackManager extends TranslationUtil {

    private final AXEPlayer player;

    public ResourcepackManager(AXEPlayer player) {
        this.player = player;
        this.define("§cDas Ressourcenpacket wird benötigt, damit du auf unserem Netzwerk spielen kannst.", "tp.needed.prompt");
    }

    public void send() {
        if(player.isOnline()) {
            if(!player.getBukkitPlayer().hasResourcePack()) {
                player.getBukkitPlayer().setResourcePack(Objects.requireNonNull(Core.getConfigurationProvider().getConfiguration().getString("tp.url")),
                        Objects.requireNonNull(Core.getConfigurationProvider().getConfiguration().getString("tp.sha-1")),
                        true,
                        Component.text(key("tp.needed.prompt", player)));
            }
        }
    }

}
