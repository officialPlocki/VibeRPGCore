package world.axe.axecore.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerResourcePackStatusEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.axe.axecore.AXECore;
import world.axe.axecore.custom.LabyModDisplay;
import world.axe.axecore.manager.ProfileManager;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Languages;
import world.axe.axecore.player.Profile;
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
        /*if(!event.getPlayer().getClientBrandName().toLowerCase().contains("labymod")) {
            event.getPlayer().sendMessage("§c§lWe've noticed that you doesn't play with LabyMod.\n§c§lPlease rejoin to ignore this.\n§bGet LabyMod here:§7 https://labymod.net");
            // @todo kick, mysql entry and continue
        }*/
        try {
            if(!event.getPlayer().hasResourcePack()) {
                new AXEPlayer(event.getPlayer()).sendResourcePack();
            }
            AXECore.getRanks().sendTablist(event.getPlayer());
            new LabyModDisplay().displayRank(event.getPlayer());
            new LabyModDisplay().sendTablistImage(event.getPlayer());
            if(new AXEPlayer(event.getPlayer()).getActiveProfile() == null) {
                Profile profile = new AXEPlayer(event.getPlayer()).createProfile();
                new ProfileManager().forceProfile(event.getPlayer(), profile);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onAccept(PlayerResourcePackStatusEvent event) {
        if(event.getStatus().equals(PlayerResourcePackStatusEvent.Status.ACCEPTED)) {
            if(!new AXEPlayer(event.getPlayer()).languageIsSet()) {
                Inventory inventory;
                AXEPlayer player = new AXEPlayer(event.getPlayer());
                if(player.getLanguage().equals(Languages.DE)) {
                    inventory = Bukkit.createInventory(null, 6*9, Component.text("§f①①①①①①‡"));
                } else if(player.getLanguage().equals(Languages.FR)) {
                    inventory = Bukkit.createInventory(null, 6*9, Component.text("§f①①①①①①➽"));
                } else {
                    inventory = Bukkit.createInventory(null, 6*9, Component.text("§f①①①①①①⊂"));
                }
                ItemStack de = new ItemStack(Material.MAP);
                ItemMeta deMeta = de.getItemMeta();
                deMeta.setCustomModelData(1010);
                deMeta.displayName(Component.text("§eDeutsch"));
                de.setItemMeta(deMeta);
                inventory.setItem(18, de);
                inventory.setItem(19, de);
                inventory.setItem(20, de);
                inventory.setItem(27, de);
                inventory.setItem(28, de);
                inventory.setItem(29, de);
                ItemStack en = new ItemStack(Material.MAP);
                ItemMeta enMeta = en.getItemMeta();
                enMeta.setCustomModelData(1010);
                enMeta.displayName(Component.text("§cEnglish"));
                en.setItemMeta(enMeta);
                inventory.setItem(21, en);
                inventory.setItem(22, en);
                inventory.setItem(23, en);
                inventory.setItem(30, en);
                inventory.setItem(31, en);
                inventory.setItem(32, en);
                ItemStack fr = new ItemStack(Material.MAP);
                ItemMeta frMeta = fr.getItemMeta();
                frMeta.setCustomModelData(1010);
                frMeta.displayName(Component.text("§bFrançais"));
                fr.setItemMeta(frMeta);
                inventory.setItem(24, fr);
                inventory.setItem(25, fr);
                inventory.setItem(26, fr);
                inventory.setItem(33, fr);
                inventory.setItem(34, fr);
                inventory.setItem(35, fr);
                event.getPlayer().openInventory(inventory);
            }
        }
    }

}
