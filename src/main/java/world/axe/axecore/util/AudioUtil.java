package world.axe.axecore.util;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.api.interfaces.Client;
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions;
import com.google.common.collect.Lists;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import world.axe.axecore.storage.FileProvider;

import java.util.ArrayList;
import java.util.List;

public class AudioUtil {

    private final YamlConfiguration yml;

    public AudioUtil() {
        FileProvider provider = new FileProvider("sounds.yml");
        yml = provider.getConfiguration();
        if(!yml.isSet("setup")) {
            yml.set("setup", true);
            yml.set("sounds", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("sounds.SOUNDNAME.volume", 100);
            yml.set("sounds.SOUNDNAME.url", "https://storage.eaxe.world//.WAV");
            for(Biome biome : Biome.values()) {
                yml.set("biomes." + biome.name() + ".music", Lists.newArrayList("soundName1", "soundName2"));
                yml.set("biomes." + biome.name() + ".pack", Lists.newArrayList("soundPackName1", "soundPackName2"));
            }
            yml.set("soundPack.PACKNAME.sounds", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("worldguard.REGIONNAME.music", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("worldguard.REGIONNAME.pack", Lists.newArrayList("soundName1", "soundName2"));
            provider.save();
        }
    }

    public String getSoundURL(String soundName) {
        if(!yml.isSet("sounds." + soundName + ".volume")) {
            return "ERROR";
        } else {
            return yml.getString("sounds." + soundName + ".url");
        }
    }

    public int getSoundVolume(String soundName) {
        if(!yml.isSet("sounds." + soundName + ".volume")) {
            return 50;
        } else {
            return yml.getInt("sounds." + soundName + ".volume");
        }
    }

    public List<String> getSoundPack(String name) {
        return yml.getStringList("soundPack." + name + ".sounds");
    }

    public List<String> getRegionMusic(String region) {
        List<String> list = new ArrayList<>();
        if(!yml.getStringList("worldguard." + region + ".pack").isEmpty()) {
            for(String pack : yml.getStringList("worldguard." + region + ".pack")) {
                list.addAll(getSoundPack(pack));
            }
        }
        if(!yml.getStringList("worldguard." + region + ".music").isEmpty()) {
            list.addAll(yml.getStringList("worldguard." + region + ".music"));
        }
        return list;
    }

    public List<String> getBiomeMusic(Biome biome) {
        List<String> list = new ArrayList<>();
        if(!yml.getStringList("biomes." + biome.name() + ".pack").isEmpty()) {
            for(String pack : yml.getStringList("biomes." + biome.name() + ".pack")) {
                list.addAll(getSoundPack(pack));
            }
        }
        if(!yml.getStringList("biomes." + biome.name() + ".music").isEmpty()) {
            list.addAll(yml.getStringList("biomes." + biome.name() + ".music"));
        }
        return list;
    }

    public void playSound(Player player, String soundName, int volume) {
        Client client = AudioApi.getInstance().getClient(player.getUniqueId());
        String url = "";
        MediaOptions options = new MediaOptions();
        options.setVolume(volume);
        AudioApi.getInstance().getMediaApi().playMedia(client, url, options);
    }

}
