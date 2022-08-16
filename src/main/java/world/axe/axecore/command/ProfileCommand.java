package world.axe.axecore.command;

import com.google.common.collect.Lists;
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
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Profile;
import world.axe.axecore.util.TranslationUtil;

import java.util.Date;
import java.util.Objects;

public class ProfileCommand extends TranslationUtil implements CommandExecutor {

    public ProfileCommand() {
        define("§8»§b§l Profile", "profile.inventory.name");
        define("§a§l Neues Profil erstellen", "profile.inventory.create");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Inventory inventory = Bukkit.createInventory(null, 3*9, key("profile.inventory.name", new AXEPlayer((Player) sender)));
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName("§f");
        filler.setItemMeta(fillerMeta);
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        AXEPlayer player = new AXEPlayer((Player) sender);
        Profile[] profiles = player.getProfiles();
        for(int i = 0; i < profiles.length; i++) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            Profile profile = profiles[i];
            if(Objects.equals(profile.getUUID(), player.getActiveProfile().getUUID())) {
                meta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            meta.setDisplayName("§e" + profile.getUUID());
            meta.setLore(Lists.newArrayList("",
                    "§aTeam: " + profile.getSkillTeam().name(),
                    "§cHP: " + profile.getHealth(),
                    "§eCD: " + new Date(profile.getCreated()),
                    ""));
            item.setItemMeta(meta);
            inventory.setItem(i, item);
        }
        ItemStack create = new ItemStack(Material.GREEN_DYE);
        ItemMeta createMeta = create.getItemMeta();
        createMeta.setDisplayName(key("profile.inventory.create", player));
        create.setItemMeta(createMeta);
        inventory.setItem(inventory.getSize() - 1, create);
        ((Player) sender).openInventory(inventory);
        return false;
    }

}
