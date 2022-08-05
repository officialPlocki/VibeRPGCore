package world.axe.axecore.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import world.axe.axecore.display.DisplayHelper;
import world.axe.axecore.util.FileProvider;

import java.util.ArrayList;
import java.util.List;

public class SetupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("Generating...");
        long time = System.currentTimeMillis();
        new Thread(() -> {
            FileProvider provider = new FileProvider("items.yml");
            YamlConfiguration yml = provider.getConfiguration();
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
                    provider.save();
                }
                yml.set("extra.ITEM_ID.tags", tags);
                yml.set("extra.ITEM_ID.explicit", explicit);
                yml.set("extra.ITEM_ID.lore_translate_key", "KEY");
                yml.set("extra.ITEM_ID.quality", quality);
                yml.set("extra.ITEM_ID.rarity", "RARITY-ONE_STAR");
                yml.set("extra.ITEM_ID.name_translate_key", "KEY");
                provider.save();
            }
            sender.sendMessage("Generated in " + ((System.currentTimeMillis() - time) / 100) + "s!");
        }).start();
        return false;
    }
}
