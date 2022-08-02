package world.axe.axecore.custom;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import world.axe.axecore.display.DisplayHelper;
import world.axe.axecore.player.AXEPlayer;
import world.axe.axecore.util.FileProvider;
import world.axe.axecore.util.Translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemModifier extends Translator {

    private final FileProvider provider;
    private final YamlConfiguration yml;

    public ItemModifier() {
        this.provider = new FileProvider("items.yml");
        this.yml = provider.getConfiguration();
        List<String> tags = new ArrayList<>();
        for(DisplayHelper.special special : DisplayHelper.special.values()) {
            tags.add(special.name());
        }
        List<String> explicit = new ArrayList<>();
        for(DisplayHelper.explicit ex : DisplayHelper.explicit.values()) {
            explicit.add(ex.name());
        }
        List<String> quality = new ArrayList<>();
        for(DisplayHelper.quality quality1 : DisplayHelper.quality.values()) {
            quality.add(quality1.name());
        }
        if(!yml.isSet("conf.STONE.tags")) {
            yml.set("INFORMATION", "set strings to \"none\" when there is nothing to show - at arrays please leave it empty like this: []");
            for(Material material : Material.values()) {
                yml.set("conf." + material.name() + ".tags", tags);
                yml.set("conf." + material.name() + ".explicit", explicit);
                yml.set("conf." + material.name() + ".quality", quality);
                yml.set("conf." + material.name() + ".rarity", "RARITY-ONE_STAR");
            }
            yml.set("extra.ITEM_ID.tags", tags);
            yml.set("extra.ITEM_ID.explicit", explicit);
            yml.set("extra.ITEM_ID.lore_translate_key", "KEY");
            yml.set("extra.ITEM_ID.quality", quality);
            yml.set("extra.ITEM_ID.rarity", "RARITY-ONE_STAR");
            yml.set("extra.ITEM_ID.name_translate_key", "KEY");
            provider.save();
        }
    }

    public ItemStack loadModifications(ItemStack stack, AXEPlayer... player) {
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(!meta.hasDisplayName()) {
            if(!yml.getStringList("conf." + stack.getType().name() + ".explicit").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("conf." + stack.getType().name() + ".explicit");
                for(String tag : tags) {
                    DisplayHelper.explicit t = DisplayHelper.explicit.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("conf." + stack.getType().name() + ".quality").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("conf." + stack.getType().name() + ".quality");
                for(String tag : tags) {
                    DisplayHelper.quality t = DisplayHelper.quality.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("conf." + stack.getType().name() + ".rarity").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
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
            if(!yml.getStringList("conf." + stack.getType().name() + ".tags").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("conf." + stack.getType().name() + ".tags");
                for(String tag : tags) {
                    DisplayHelper.special t = DisplayHelper.special.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
                lore.add("§f");
            } else {
                lore.add("§f");
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
        } else {
            if(!yml.isSet("extra." + meta.getDisplayName() + ".name_translate_key")) {
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return stack;
            }
            if(!Objects.requireNonNull(yml.getString("extra." + meta.getDisplayName() + ".name_translate_key")).equalsIgnoreCase("none")) {
                meta.setDisplayName(yml.getString("extra." + meta.getDisplayName() + ".name_translate_key"));
            }
            if(!Objects.requireNonNull(yml.getString("extra." + meta.getDisplayName() + ".lore_translate_key")).equalsIgnoreCase("none")) {
                lore.add("§f");
                String[] array = key(yml.getString("extra." + meta.getDisplayName() + ".lore_translate_key"), player[0]).split(";");
                lore.addAll(Arrays.asList(array));
            }
            if(!yml.getStringList("extra." + meta.getDisplayName() + ".explicit").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("extra." + meta.getDisplayName() + ".explicit");
                for(String tag : tags) {
                    DisplayHelper.explicit t = DisplayHelper.explicit.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("extra." + meta.getDisplayName() + ".quality").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("extra." + meta.getDisplayName() + ".quality");
                for(String tag : tags) {
                    DisplayHelper.quality t = DisplayHelper.quality.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
            }
            if(!yml.getStringList("extra." + meta.getDisplayName() + ".rarity").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                String[] array  = Objects.requireNonNull(yml.getString("extra." + meta.getDisplayName() + ".rarity")).split("-");
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
            if(!yml.getStringList("extra." + meta.getDisplayName() + ".tags").isEmpty()) {
                lore.add("§f");
                lore.add("§8§l»------------------------«");
                lore.add("§f" + DisplayHelper.explicit.TAGS);
                lore.add("§f");
                StringBuilder str = new StringBuilder("§f");
                List<String> tags = yml.getStringList("extra." + meta.getDisplayName() + ".tags");
                for(String tag : tags) {
                    DisplayHelper.special t = DisplayHelper.special.valueOf(tag);
                    str.append(t.val());
                }
                lore.add(str.toString());
                lore.add("§f");
            }
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

}
