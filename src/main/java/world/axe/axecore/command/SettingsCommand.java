package world.axe.axecore.command;

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
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Profile;
import world.axe.axecore.player.SoundSettings;
import world.axe.axecore.util.TranslationUtil;

public class SettingsCommand extends TranslationUtil implements CommandExecutor {

    public SettingsCommand() {
        define("§8»§b§l Einstellungen", "settings.inventory.title");
        define("Sprung Sound", "settings.item.jump");
        define("Atem Sound", "settings.item.breath");
        define("Todes Sound", "settings.item.death");
        define("Schadens Sound", "settings.item.damage");
        define("Benachrichtigungs Sound", "settings.item.notification");
        define("Ambiente Musik", "settings.item.ambientMusic");
        define("Sounds von anderen Spielern", "settings.item.otherPlayer");
        define("Interface Sounds", "settings.item.ui");
        define("Ankündigungs Sound", "settings.item.announce");
        define("Angriffs Sound", "settings.item.attack");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        AXEPlayer player = new AXEPlayer((Player) sender);
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(key("settings.inventory.title", player)));
        ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName("§f");
        filler.setItemMeta(fillerMeta);
        for(int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        Profile profile = player.getActiveProfile();
        SoundSettings settings = profile.getSoundSettings();
        
        ItemStack jumpSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta jumpMeta = jumpSound.getItemMeta();
        jumpMeta.displayName(Component.text("§e" + key("settings.item.jump", player)));
        if(settings.isJump()) {
            jumpMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            jumpMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        jumpSound.setItemMeta(jumpMeta);

        ItemStack breathSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta breathMeta = breathSound.getItemMeta();
        breathMeta.displayName(Component.text("§e" + key("settings.item.breath", player)));
        if(settings.isBreath()) {
            breathMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            breathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        breathSound.setItemMeta(breathMeta);

        ItemStack deathSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta deathMeta = deathSound.getItemMeta();
        deathMeta.displayName(Component.text("§e" + key("settings.item.death", player)));
        if(settings.isDeath()) {
            deathMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            deathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        deathSound.setItemMeta(deathMeta);

        ItemStack damageSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta damageMeta = damageSound.getItemMeta();
        damageMeta.displayName(Component.text("§e" + key("settings.item.damage", player)));
        if(settings.isDamage()) {
            damageMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            damageMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        damageSound.setItemMeta(damageMeta);

        ItemStack notificationSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta notificationMeta = notificationSound.getItemMeta();
        notificationMeta.displayName(Component.text("§e" + key("settings.item.notification", player)));
        if(settings.isNotifications()) {
            notificationMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            notificationMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        notificationSound.setItemMeta(notificationMeta);

        ItemStack ambientMusicSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta ambientMusicMeta = ambientMusicSound.getItemMeta();
        ambientMusicMeta.displayName(Component.text("§e" + key("settings.item.ambientMusic", player)));
        if(settings.isAmbientMusic()) {
            ambientMusicMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            ambientMusicMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        ambientMusicSound.setItemMeta(ambientMusicMeta);

        ItemStack otherPlayerSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta otherPlayerMeta = otherPlayerSound.getItemMeta();
        otherPlayerMeta.displayName(Component.text("§e" + key("settings.item.otherPlayer", player)));
        if(settings.isOtherUserSounds()) {
            otherPlayerMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            otherPlayerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        otherPlayerSound.setItemMeta(otherPlayerMeta);

        ItemStack uiSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta uiMeta = uiSound.getItemMeta();
        uiMeta.displayName(Component.text("§e" + key("settings.item.ui", player)));
        if(settings.isUi()) {
            uiMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            uiMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        uiSound.setItemMeta(uiMeta);

        ItemStack announceSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta announceMeta = announceSound.getItemMeta();
        announceMeta.displayName(Component.text("§e" + key("settings.item.announce", player)));
        if(settings.isAnnounce()) {
            announceMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            announceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        announceSound.setItemMeta(announceMeta);

        ItemStack attackSound = new ItemStack(Material.STONE_BUTTON);
        ItemMeta attackMeta = attackSound.getItemMeta();
        attackMeta.displayName(Component.text("§e" + key("settings.item.attack", player)));
        if(settings.isAttack()) {
            attackMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
            attackMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        attackSound.setItemMeta(attackMeta);


        inventory.setItem(0, jumpSound);
        inventory.setItem(1, breathSound);
        inventory.setItem(2, deathSound);
        inventory.setItem(3, damageSound);
        inventory.setItem(4, notificationSound);
        inventory.setItem(5, ambientMusicSound);
        inventory.setItem(6, otherPlayerSound);
        inventory.setItem(7, uiSound);
        inventory.setItem(8, announceSound);
        inventory.setItem(9, attackSound);

        ((Player) sender).openInventory(inventory);
        return false;
    }

}
