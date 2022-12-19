package eu.viberpg.core.command;

import eu.viberpg.core.util.TranslationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import eu.viberpg.core.player.AXEPlayer;
import eu.viberpg.core.player.VoicePacks;

public class VoicePackCommand extends TranslationUtil implements CommandExecutor {

    public VoicePackCommand() {
        define("§8»§e§l Sprachpaket", "voicepack.inventory.name");
        define("§d Weiblich", "voicepack.female");
        define("§b Männlich", "voicepack.male");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, key("voicepack.inventory.name", new AXEPlayer((Player) sender)));
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
                meta.setDisplayName(key("voicepack.female", new AXEPlayer((Player) sender)) + " " + b.toUpperCase());
            } else if(male < 5) {
                pack = "male_";
                b = strings[male++];
                meta.setDisplayName(key("voicepack.male", new AXEPlayer((Player) sender)) + " " + b.toUpperCase());
            }
            pack = pack + b;
            if(new AXEPlayer((Player) sender).getActiveProfile().getVoicePack().equals(VoicePacks.valueOf(pack))) {
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
        ((Player)sender).openInventory(inventory);
        return false;
    }

}
