package world.axe.axecore.listener;

import net.kyori.adventure.text.Component;
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
import world.axe.axecore.player.Languages;
import world.axe.axecore.player.VoicePacks;
import world.axe.axecore.util.TranslationUtil;

public class LanguageListener extends TranslationUtil implements Listener {

    public LanguageListener() {
        define("Sprache gewechselt", "language.message.changed");
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(event.getCurrentItem() == null)) {
            if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eDeutsch")) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                new AXEPlayer((Player) event.getWhoClicked()).setLanguage(Languages.DE);
                event.getWhoClicked().sendMessage(key("language.message.changed", new AXEPlayer((Player) event.getWhoClicked())));

                if(!new AXEPlayer((Player) event.getWhoClicked()).getActiveProfile().isVoicepack_set()) {
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
                        String pack = "";
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
                } else {
                    Inventory inventory;
                    AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
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
                    event.getWhoClicked().openInventory(inventory);
                }
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cEnglish")) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                new AXEPlayer((Player) event.getWhoClicked()).setLanguage(Languages.EN);
                event.getWhoClicked().sendMessage(key("language.message.changed", new AXEPlayer((Player) event.getWhoClicked())));

                if(!new AXEPlayer((Player) event.getWhoClicked()).getActiveProfile().isVoicepack_set()) {
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
                        String pack = "";
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
                } else {
                    Inventory inventory;
                    AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
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
                    event.getWhoClicked().openInventory(inventory);
                }
            } else if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bFrançais")) {
                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                new AXEPlayer((Player) event.getWhoClicked()).setLanguage(Languages.FR);
                event.getWhoClicked().sendMessage(key("language.message.changed", new AXEPlayer((Player) event.getWhoClicked())));

                if(!new AXEPlayer((Player) event.getWhoClicked()).getActiveProfile().isVoicepack_set()) {
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
                        String pack = "";
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
                } else {
                    Inventory inventory;
                    AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
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
                    event.getWhoClicked().openInventory(inventory);
                }
            }
        }
    }

}
