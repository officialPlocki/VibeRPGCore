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
import world.axe.axecore.player.Profile;
import world.axe.axecore.util.TranslationUtil;

public class SettingsListener extends TranslationUtil implements Listener {

    @SuppressWarnings("all")
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null) {
            AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
            if(event.getView().getTitle().equalsIgnoreCase(key("settings.inventory.title", player))) {
                event.setCancelled(true);
                String name = event.getCurrentItem().getItemMeta().getDisplayName();
                Profile profile = player.getActiveProfile();
                if(name.equalsIgnoreCase("§e" + key("settings.item.jump", player))) {
                    profile.setJump(!profile.isJump());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.breath", player))) {
                    profile.setBreath(!profile.isBreath());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.death", player))) {
                    profile.setDeath(!profile.isDeath());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.damage", player))) {
                    profile.setDamage(!profile.isDamage());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.notification", player))) {
                    profile.setNotification(!profile.isNotifications());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.ambientMusic", player))) {
                    profile.setAmbientMusic(!profile.isAmbientMusic());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.otherPlayer", player))) {
                    profile.setOtherUserSounds(!profile.isOtherUserSounds());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.ui", player))) {
                    profile.setUi(!profile.isUi());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.announce", player))) {
                    profile.setAnnounce(!profile.isAnnounce());
                } else if(name.equalsIgnoreCase("§e" + key("settings.item.attack", player))) {
                    profile.setAttack(!profile.isAttack());
                }
                if(event.getCurrentItem().getType().equals(Material.PAPER)) {
                    event.getWhoClicked().closeInventory();
                    profile.save();
                    Inventory inventory = Bukkit.createInventory(null, 3 * 9, Component.text(key("settings.inventory.title", player)));
                    ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta fillerMeta = filler.getItemMeta();
                    fillerMeta.setDisplayName("§f");
                    filler.setItemMeta(fillerMeta);
                    for(int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, filler);
                    }

                    ItemStack jumpSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta jumpMeta = jumpSound.getItemMeta();
                    jumpMeta.displayName(Component.text("§e" + key("settings.item.jump", player)));
                    if(profile.isJump()) {
                        jumpMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        jumpMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    jumpSound.setItemMeta(jumpMeta);

                    ItemStack breathSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta breathMeta = breathSound.getItemMeta();
                    breathMeta.displayName(Component.text("§e" + key("settings.item.breath", player)));
                    if(profile.isBreath()) {
                        breathMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        breathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    breathSound.setItemMeta(breathMeta);

                    ItemStack deathSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta deathMeta = deathSound.getItemMeta();
                    deathMeta.displayName(Component.text("§e" + key("settings.item.death", player)));
                    if(profile.isDeath()) {
                        deathMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        deathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    deathSound.setItemMeta(deathMeta);

                    ItemStack damageSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta damageMeta = damageSound.getItemMeta();
                    damageMeta.displayName(Component.text("§e" + key("settings.item.damage", player)));
                    if(profile.isDamage()) {
                        damageMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        damageMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    damageSound.setItemMeta(damageMeta);

                    ItemStack notificationSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta notificationMeta = notificationSound.getItemMeta();
                    notificationMeta.displayName(Component.text("§e" + key("settings.item.notification", player)));
                    if(profile.isNotifications()) {
                        notificationMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        notificationMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    notificationSound.setItemMeta(notificationMeta);

                    ItemStack ambientMusicSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta ambientMusicMeta = ambientMusicSound.getItemMeta();
                    ambientMusicMeta.displayName(Component.text("§e" + key("settings.item.ambientMusic", player)));
                    if(profile.isAmbientMusic()) {
                        ambientMusicMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        ambientMusicMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    ambientMusicSound.setItemMeta(ambientMusicMeta);

                    ItemStack otherPlayerSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta otherPlayerMeta = otherPlayerSound.getItemMeta();
                    otherPlayerMeta.displayName(Component.text("§e" + key("settings.item.otherPlayer", player)));
                    if(profile.isOtherUserSounds()) {
                        otherPlayerMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        otherPlayerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    otherPlayerSound.setItemMeta(otherPlayerMeta);

                    ItemStack uiSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta uiMeta = uiSound.getItemMeta();
                    uiMeta.displayName(Component.text("§e" + key("settings.item.ui", player)));
                    if(profile.isUi()) {
                        uiMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        uiMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    uiSound.setItemMeta(uiMeta);

                    ItemStack announceSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta announceMeta = announceSound.getItemMeta();
                    announceMeta.displayName(Component.text("§e" + key("settings.item.announce", player)));
                    if(profile.isAnnounce()) {
                        announceMeta.addEnchant(Enchantment.KNOCKBACK, 0, true);
                        announceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    }
                    announceSound.setItemMeta(announceMeta);

                    ItemStack attackSound = new ItemStack(Material.STONE_BUTTON);
                    ItemMeta attackMeta = attackSound.getItemMeta();
                    attackMeta.displayName(Component.text("§e" + key("settings.item.attack", player)));
                    if(profile.isAttack()) {
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

                    event.getWhoClicked().openInventory(inventory);
                }
            }
        }
    }

}
