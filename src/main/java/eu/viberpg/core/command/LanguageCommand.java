package eu.viberpg.core.command;

import eu.viberpg.core.player.AXEPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import eu.viberpg.core.player.Languages;

public class LanguageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Inventory inventory;
        AXEPlayer player = new AXEPlayer((Player) sender);
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
        ((Player)sender).openInventory(inventory);
        return false;
    }
}
