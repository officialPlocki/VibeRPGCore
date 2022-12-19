package eu.viberpg.core.command;

import com.google.common.collect.Lists;
import eu.viberpg.core.manager.ProfileManager;
import eu.viberpg.core.util.TranslationUtil;
import net.kyori.adventure.text.Component;
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
import eu.viberpg.core.player.Profile;

import java.util.*;
import java.util.stream.Collectors;

public class ProfileCommand extends TranslationUtil implements CommandExecutor {

    public ProfileCommand() {
        define("§8»§b§l Profil", "profile.inventory.name");
        define("§a§l Neues Profil erstellen", "profile.inventory.create");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(key("profile.inventory.name", new AXEPlayer((Player) sender))));
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.displayName(Component.text("§f"));
        filler.setItemMeta(fillerMeta);
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        AXEPlayer player = new AXEPlayer((Player) sender);
        List<String> profiles = Arrays.stream(new ProfileManager().getProfileUUIDs(player.getBukkitPlayer())).collect(Collectors.toList());
        int i = 0;
        for (String uuid : profiles) {
            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            Profile profile = new ProfileManager().getProfile(uuid);
            sender.sendMessage(uuid);
            if (Objects.equals(uuid, player.getActiveProfile().getUUID())) {
                meta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            meta.displayName(Component.text("§e" + uuid));
            meta.lore(Lists.newArrayList(Component.text(""),
                    Component.text("§aTeam: " + profile.getSkillTeam().name()),
                    Component.text("§cHP: " + profile.getHealth()),
                    Component.text("§eCD: " + new Date(profile.getCreated())),
                    Component.text("")));
            item.setItemMeta(meta);
            inventory.setItem(i++, item);
        }
        ItemStack create = new ItemStack(Material.GREEN_DYE);
        ItemMeta createMeta = create.getItemMeta();
        createMeta.displayName(Component.text(key("profile.inventory.create", player)));
        create.setItemMeta(createMeta);
        inventory.setItem(inventory.getSize() - 1, create);
        ((Player) sender).openInventory(inventory);
        return false;
    }

}
