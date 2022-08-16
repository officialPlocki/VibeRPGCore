package world.axe.axecore.listener;

import com.google.common.collect.Lists;
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
import world.axe.axecore.manager.ProfileManager;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.player.Profile;
import world.axe.axecore.util.TranslationUtil;

import java.util.Date;
import java.util.Objects;

public class ProfileListener extends TranslationUtil implements Listener {

    public ProfileListener() {
        define("Du hast bereits dein Profil Limit erreicht.", "profile.message.too_many_profiles");
        define("Das Profil wurde erstellt.", "profile.message.profile_created");
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getCurrentItem() != null) {
            AXEPlayer player = new AXEPlayer((Player) event.getWhoClicked());
            if(event.getView().getTitle().equalsIgnoreCase(key("profile.inventory.name", player))) {
                event.setCancelled(true);
                if(event.getCurrentItem().getType().equals(Material.PAPER)) {
                    String uuid = event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§e", "");
                    event.getWhoClicked().closeInventory();
                    player.setProfile(new ProfileManager().getProfile(uuid));
                    Inventory inventory = Bukkit.createInventory(null, 3*9, key("profile.inventory.name", player));
                    ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta fillerMeta = filler.getItemMeta();
                    fillerMeta.setDisplayName("§f");
                    filler.setItemMeta(fillerMeta);
                    for(int i = 0; i < inventory.getSize(); i++) {
                        inventory.setItem(i, filler);
                    }
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
                    event.getWhoClicked().openInventory(inventory);
                } else {
                    if(event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(key("profile.inventory.create", player))) {
                        if(player.getProfiles().length >= 5) {
                            event.getWhoClicked().sendMessage(key("profile.message.too_many_profiles", player));
                        } else {
                            player.createProfile();
                            event.getWhoClicked().sendMessage(key("profile.message.profile_created", player));
                            event.getWhoClicked().closeInventory();
                            Inventory inventory = Bukkit.createInventory(null, 3*9, key("profile.inventory.name", player));
                            ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                            ItemMeta fillerMeta = filler.getItemMeta();
                            fillerMeta.setDisplayName("§f");
                            filler.setItemMeta(fillerMeta);
                            for(int i = 0; i < inventory.getSize(); i++) {
                                inventory.setItem(i, filler);
                            }
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
                            event.getWhoClicked().openInventory(inventory);
                        }
                    }
                }
            }
        }
    }

}
