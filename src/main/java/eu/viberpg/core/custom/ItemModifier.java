package eu.viberpg.core.custom;

import eu.viberpg.core.util.TranslationUtil;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import eu.viberpg.core.display.DisplayHelper;
import eu.viberpg.core.player.AXEPlayer;
import eu.viberpg.core.storage.FileProvider;

import java.util.*;

@SuppressWarnings("all")
public class ItemModifier extends TranslationUtil {

    // @todo add item creation

    private final YamlConfiguration yml;
    private final FileProvider provider;

    public ItemModifier() {
        provider = new FileProvider("items.yml");
        this.yml = provider.getConfiguration();
        List<String> tags = new ArrayList<>();
        for(DisplayHelper.special special : DisplayHelper.special.values()) {
            tags.add("#" + special.name());
        }
        List<String> explicit = new ArrayList<>();
        for(DisplayHelper.explicit ex : DisplayHelper.explicit.values()) {
            explicit.add("#" + ex.name());
        }
        if(!yml.isSet("conf.STONE.tags")) {
            yml.set("INFORMATION", "set strings to \"none\" when there is nothing to show - at arrays please leave it empty like this: []");
            for(Material material : Material.values()) {
                yml.set("conf." + material.name() + ".tags", tags);
                yml.set("conf." + material.name() + ".explicit", explicit);
                yml.set("conf." + material.name() + ".rarity", material.getItemRarity().name() + "-THREE_STAR");
                provider.save();
            }
            yml.set("extras.NAME", "ID");
            yml.set("extra.ITEM_ID.tags", tags);
            yml.set("extra.ITEM_ID.explicit", explicit);
            yml.set("extra.ITEM_ID.rarity", "COMMON-ONE_STAR");
            yml.set("extra.ITEM_ID.lore_translation", "German");
            yml.set("extra.ITEM_ID.lore_translate_key", "KEY");
            yml.set("extra.ITEM_ID.name_translation", "German");
            yml.set("extra.ITEM_ID.name_translate_key", "KEY");
            provider.save();
        }
    }

    public ItemStack loadModifications(ItemStack stack, AXEPlayer... player) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(!meta.hasDisplayName()) {
            if(!Objects.requireNonNull(yml.getString("conf." + stack.getType().name() + ".rarity")).equalsIgnoreCase("none")) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS.val());
                lore.add("§f");
                String[] array  = Objects.requireNonNull(yml.getString("conf." + stack.getType().name() + ".rarity")).split("-");
                String rarity = array[0].toLowerCase();
                switch (rarity) {
                    case "common":
                        lore.add(DisplayHelper.common.valueOf(array[1]).val());
                        break;
                    case "epic":
                        lore.add(DisplayHelper.epic.valueOf(array[1]).val());
                        break;
                    case "heroic":
                        lore.add(DisplayHelper.heroic.valueOf(array[1]).val());
                        break;
                    case "legendary":
                        lore.add(DisplayHelper.legendary.valueOf(array[1]).val());
                        break;
                    case "mythical":
                        lore.add(DisplayHelper.mythical.valueOf(array[1]).val());
                        break;
                    case "rare":
                        lore.add(DisplayHelper.rare.valueOf(array[1]).val());
                        break;
                    case "ultrarare":
                        lore.add(DisplayHelper.ultrarare.valueOf(array[1]).val());
                        break;
                    case "uncommon":
                        lore.add(DisplayHelper.uncommon.valueOf(array[1]).val());
                        break;
                }
            }
            if(!yml.getStringList("conf." + stack.getType().name() + ".explicit").isEmpty()) {
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("conf." + stack.getType().name() + ".explicit");
                for(String tag : tags) {
                    if(tag.startsWith("#")) continue;
                    DisplayHelper.explicit t = DisplayHelper.explicit.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("conf." + stack.getType().name() + ".tags").isEmpty()) {
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("conf." + stack.getType().name() + ".tags");
                for(String tag : tags) {
                    if(tag.startsWith("#")) continue;
                    DisplayHelper.special t = DisplayHelper.special.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
        } else {



            //@todo item activation & translation command



            String iid = yml.isSet("extras." + meta.getDisplayName()) ? UUID.randomUUID().toString() : yml.getString("extras." + meta.getDisplayName());
            if(yml.isSet("extra." + iid + ".active")) {
                if(!yml.getBoolean("extra." + iid + ".active")) {
                    return stack;
                }
            }
            if(!yml.isSet("extra." + iid + ".rarity")) {
                List<String> tags = new ArrayList<>();
                for(DisplayHelper.special special : DisplayHelper.special.values()) {
                    tags.add("#" + special.name());
                }
                List<String> explicit = new ArrayList<>();
                for(DisplayHelper.explicit ex : DisplayHelper.explicit.values()) {
                    explicit.add("#" + ex.name());
                }
                yml.set("extras." + meta.getDisplayName(), iid);
                yml.set("extra." + iid + ".tags", tags);
                yml.set("extra." + iid + ".explicit", explicit);
                yml.set("extra." + iid + ".rarity", "COMMON-ONE_STAR");
                yml.set("extra." + iid + ".lore_translation", "");
                yml.set("extra." + iid + ".lore_translate_key", "");
                yml.set("extra." + iid + ".name_translation", "");
                yml.set("extra." + iid + ".name_translate_key", "");
                yml.set("extra." + iid + ".active", false);
                provider.save();
            }
            if(!Objects.requireNonNull(yml.getString("extra." + iid + ".lore_translate_key")).equalsIgnoreCase("none")) {
                lore.add("§f");
                String[] array = key(yml.getString("extra." + iid + ".lore_translate_key"), player[0]).split(";");
                lore.addAll(Arrays.asList(array));
            }
            if(Objects.requireNonNull(yml.getString("extra." + iid + ".name_translate_key")).equalsIgnoreCase("none")) {
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }
            if(!Objects.requireNonNull(yml.getString("extra." + iid + ".name_translate_key")).equalsIgnoreCase("none")) {
                meta.setDisplayName(yml.getString("extra." + iid + ".name_translate_key"));
            }
            if(!Objects.requireNonNull(yml.getString("extra." + iid + ".rarity")).equalsIgnoreCase("none")) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS.val());
                lore.add("§f");
                String[] array  = Objects.requireNonNull(yml.getString("extra." + iid + ".rarity")).split("-");
                String rarity = array[0].toLowerCase();
                switch (rarity) {
                    case "common":
                        lore.add(DisplayHelper.common.valueOf(array[1]).val());
                        break;
                    case "epic":
                        lore.add(DisplayHelper.epic.valueOf(array[1]).val());
                        break;
                    case "heroic":
                        lore.add(DisplayHelper.heroic.valueOf(array[1]).val());
                        break;
                    case "legendary":
                        lore.add(DisplayHelper.legendary.valueOf(array[1]).val());
                        break;
                    case "mythical":
                        lore.add(DisplayHelper.mythical.valueOf(array[1]).val());
                        break;
                    case "rare":
                        lore.add(DisplayHelper.rare.valueOf(array[1]).val());
                        break;
                    case "ultrarare":
                        lore.add(DisplayHelper.ultrarare.valueOf(array[1]).val());
                        break;
                    case "uncommon":
                        lore.add(DisplayHelper.uncommon.valueOf(array[1]).val());
                        break;
                }
            }
            if(!yml.getStringList("extra." + iid + ".explicit").isEmpty()) {
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("extra." + iid + ".explicit");
                for(String tag : tags) {
                    if(tag.startsWith("#")) continue;
                    DisplayHelper.explicit t = DisplayHelper.explicit.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("extra." + iid + ".tags").isEmpty()) {
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("extra." + iid + ".tags");
                for(String tag : tags) {
                    if(tag.startsWith("#")) continue;
                    DisplayHelper.special t = DisplayHelper.special.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

}
