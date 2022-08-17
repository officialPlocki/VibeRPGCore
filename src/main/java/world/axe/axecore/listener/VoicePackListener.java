package world.axe.axecore.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Profile;
import world.axe.axecore.player.VoicePacks;
import world.axe.axecore.util.TranslationUtil;

public class VoicePackListener extends TranslationUtil implements Listener {

    public VoicePackListener() {
        define("Das Sprachpaket wurde gewechselt.", "voicepack.message.changed");
    }

    @SuppressWarnings("all")
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null) {
            AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
            if(event.getView().getTitle().equalsIgnoreCase(key("voicepack.inventory.name", player))) {
                event.setCancelled(true);
                if(event.getCurrentItem().getItemMeta().getDisplayName().contains(key("voicepack.female", player)) || event.getCurrentItem().getItemMeta().getDisplayName().contains(key("voicepack.male", player))) {
                    String pack = "";
                    if(event.getCurrentItem().getItemMeta().getDisplayName().contains(key("voicepack.female", player))) {
                        pack = "female_" + event.getCurrentItem().getItemMeta().getDisplayName().charAt(event.getCurrentItem().getItemMeta().getDisplayName().length() - 1);
                    } else {
                        pack = "male_" + event.getCurrentItem().getItemMeta().getDisplayName().charAt(event.getCurrentItem().getItemMeta().getDisplayName().length() - 1);
                    }
                    Profile profile = player.getActiveProfile();
                    profile.setVoicePack(VoicePacks.valueOf(pack.toLowerCase()));
                    profile.save();
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage(key("voicepack.message.changed", player));
                    Inventory inventory = Bukkit.createInventory(null, 3*9, key("voicepack.inventory.name", new AXEPlayer((Player) event.getWhoClicked())));
                    ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta fillerMeta = filler.getItemMeta();
                    fillerMeta.setDisplayName("§f");
                    filler.setItemMeta(fillerMeta);
                    for(int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, filler);
                    }
                    int female = 0;
                    int male = 0;
                    ItemStack[] items = new ItemStack[7];
                    String[] strings = {"a", "b", "c", "d"};
                    for(int i = 0; i < 7; i++) {
                        ItemStack item = new ItemStack(Material.PAPER);
                        ItemMeta meta = item.getItemMeta();
                        String b = "";
                        if(female < 3) {
                            pack = "female_";
                            b = strings[female++];
                            meta.setDisplayName(key("voicepack.female", new AXEPlayer((Player) event.getWhoClicked())) + " " + b.toUpperCase());
                        } else if(male < 5) {
                            pack = "male_";
                            b = strings[male++];
                            meta.setDisplayName(key("voicepack.male", new AXEPlayer((Player) event.getWhoClicked())) + " " + b.toUpperCase());
                        }
                        pack = pack + b;
                        if(new AXEPlayer((Player) event.getWhoClicked()).getActiveProfile().getVoicePack().equals(VoicePacks.valueOf(pack))) {
                            meta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        }
                        item.setItemMeta(meta);
                        items[i] = item;
                    }
                    //female
                    inventory.setItem(2, items[0]);
                    inventory.setItem(10, items[1]);
                    inventory.setItem(20, items[2]);
                    //male
                    inventory.setItem(13, items[3]);
                    inventory.setItem(6, items[4]);
                    inventory.setItem(16, items[5]);
                    inventory.setItem(24, items[6]);
                    event.getWhoClicked().openInventory(inventory);
                }
            }
        }
    }

}
