package world.axe.axecore.util;

import com.craftmend.openaudiomc.api.interfaces.AudioApi;
import com.craftmend.openaudiomc.api.interfaces.Client;
import com.craftmend.openaudiomc.generic.media.objects.MediaOptions;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import world.axe.axecore.player.VoicePacks;
import world.axe.axecore.storage.FileProvider;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AudioUtil {

    private static final HashMap<Player, Double> remaining = new HashMap<>();

    private final YamlConfiguration yml;
    private final FileProvider provider;

    public AudioUtil(Plugin plugin, boolean isBukkit) {
        provider = new FileProvider("sounds.yml");
        yml = provider.getConfiguration();
        if(!yml.isSet("setup")) {
            yml.set("setup", true);
            yml.set("soundList", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("sounds.SOUNDNAME.volume", 100);
            yml.set("sounds.SOUNDNAME.url", "https://storage.eaxe.world//.WAV");
            yml.set("sounds.SOUNDNAME.length", 1.0);
            for(Biome biome : Biome.values()) {
                yml.set("biomes." + biome.name() + ".music", Lists.newArrayList("soundName1", "soundName2"));
                yml.set("biomes." + biome.name() + ".pack", Lists.newArrayList("soundPackName1", "soundPackName2"));
            }
            yml.set("soundPack.PACKNAME.sounds", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("worldguard.REGIONNAME.music", Lists.newArrayList("soundName1", "soundName2"));
            yml.set("worldguard.REGIONNAME.pack", Lists.newArrayList("soundName1", "soundName2"));
            provider.save();
        }
        if(isBukkit) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> remaining.forEach((player, aDouble) -> {
                remaining.put(player, aDouble-1.0);
                if(remaining.get(player) <= 0.0) {
                    remaining.remove(player);
                }
            }), 20,20);
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getOnlinePlayers().forEach((player) -> {
                VoicePacks pack = VoicePacks.male_a;
                if(pack.name().contains("male")) {
                    playSound(player, "voice_male_c_breath_loop_01_single_01", 5, false);
                } else {
                    playSound(player, "voice_female_a_breath_quick_01", 5, false);
                }
            }), 20,355);
        }
    }

    public boolean isConnected(Player player) {
        return AudioApi.getInstance().isClientConnected(player.getUniqueId());
    }

    public void measureLength() {
        List<String> sounds = getSounds();
        for(String sound : sounds) {
            String url = getSoundURL(sound);
            String fileName = UUID.randomUUID().toString();
            File file = new File(fileName);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
                 FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                long frames = audioInputStream.getFrameLength();
                double s = (frames+0.0) / audioInputStream.getFormat().getFrameRate();
                yml.set("sounds." + sound + ".length", s);
                provider.save();
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }
            file.delete();
        }
    }

    public boolean isPlaying(Player player) {
        if(!isConnected(player)) return true;
        return remaining.containsKey(player);
    }

    public void importSounds(String file) {
        JSONParser parser = new JSONParser();
        try {
            //new FileProvider(file).getFile()
            Object obj = parser.parse(new FileReader(new FileProvider(file).getFile()));
            JSONObject jsonObject =  (JSONObject) obj;

            List<String> types = new ArrayList<>();
            JSONArray t = (JSONArray) jsonObject.get("types");
            for (Object o : t) {
                types.add((String) o);
            }

            JSONObject urlObj = (JSONObject) jsonObject.get("urls");
            JSONObject settingsObj = (JSONObject) jsonObject.get("settings");
            JSONObject placeholderObj = (JSONObject) jsonObject.get("placeholder");
            JSONObject soundsObj = (JSONObject) jsonObject.get("sounds");
            for(String type : types) {
                boolean isArray = (boolean) jsonObject.get(type + "ARRAY");
                String url = (String) urlObj.get(type + "_url");
                List<String> placeholders = new ArrayList<>();
                JSONArray a = (JSONArray) placeholderObj.get(type + "_soundname_replace");
                for (Object o : a) {
                    placeholders.add((String) o);
                }
                int volume = Integer.parseInt(String.valueOf((long) settingsObj.get(type + "_volume")));

                if(isArray) {
                    List<String> sounds = new ArrayList<>();
                    JSONArray array = (JSONArray) soundsObj.get(type);
                    for(int i = 0; i < array.size(); i++) {
                        sounds.add((String) array.get(i));
                    }
                    for(String sound : sounds) {
                        String soundFileName = sound;
                        for(String replacement : placeholders) {
                            sound = sound.replaceAll(replacement, "");
                        }
                        List<String> list = yml.getStringList("soundList");
                        list.add(sound.toLowerCase());
                        yml.set("soundList", list);
                        yml.set("sounds." + sound.toLowerCase() + ".volume", volume);
                        yml.set("sounds." + sound.toLowerCase() + ".url", url + soundFileName);
                        provider.save();
                    }
                } else {
                    JSONArray uTypes = (JSONArray) jsonObject.get(type + "UNDERTYPES");
                    List<String> ut = new ArrayList<>();
                    for(int i = 0; i < uTypes.size(); i++) {
                        ut.add((String) uTypes.get(i));
                    }
                    JSONObject over = (JSONObject) soundsObj.get(type);
                    for(String underType : ut) {
                        String uURL = url.replaceAll("%TYPE%", underType);
                        List<String> sounds = new ArrayList<>();
                        JSONArray array = (JSONArray) over.get(underType);
                        for(int i = 0; i < array.size(); i++) {
                            sounds.add((String) array.get(i));
                        }
                        for(String sound : sounds) {
                            String soundFileName = sound;
                            for(String replacement : placeholders) {
                                sound = sound.replaceAll(replacement, "");
                            }
                            List<String> list = yml.getStringList("soundList");
                            list.add(sound.toLowerCase());
                            yml.set("soundList", list);
                            yml.set("sounds." + sound.toLowerCase() + ".volume", volume);
                            yml.set("sounds." + sound.toLowerCase() + ".url", uURL + soundFileName);
                            provider.save();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        measureLength();
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
    public double getSoundLength(String soundName) {
        if(!yml.isSet("sounds." + soundName + ".length")) {
            return 50;
        } else {
            return yml.getInt("sounds." + soundName + ".length");
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

    public List<String> getSounds() {
        return yml.getStringList("soundList");
    }

    /**
     * It plays a sound to a player
     *
     * @param player The player you want to play the sound for.
     * @param soundName The name of the sound you want to play.
     * @param volume The volume of the sound.
     * @param music If the sound is a music sound, it will be played only for the player.
     * @param ui If the sound is a UI sound, it will only play for the player.
     */
    public void playSound(Player player, String soundName, int volume, boolean music, boolean ui) {
        if(!isConnected(player)) return;
        if(!music) {
            if(!ui) {
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(all.getLocation().distance(player.getLocation()) < 6) {
                        Client client = AudioApi.getInstance().getClient(all.getUniqueId());
                        String url = getSoundURL(soundName);
                        MediaOptions options = new MediaOptions();
                        options.setVolume(volume);
                        AudioApi.getInstance().getMediaApi().playSpatialSound(client, url, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), volume, true, 5);
                    }
                }
            }
        }
        if(music) {
            remaining.put(player, getSoundLength(soundName));
        }
        Client client = AudioApi.getInstance().getClient(player.getUniqueId());
        String url = getSoundURL(soundName);
        MediaOptions options = new MediaOptions();
        options.setVolume(volume);
        AudioApi.getInstance().getMediaApi().playMedia(client, url, options);
    }

    /**
     * It plays a sound to a player
     *
     * @param player The player you want to play the sound to.
     * @param soundName The name of the sound you want to play.
     * @param volume 0-100
     * @param music If the sound is music, it will be played for the player only. If it's not music, it will be played for
     * all players within 6 blocks of the player.
     */
    public void playSound(Player player, String soundName, int volume, boolean music) {
        if(!isConnected(player)) return;
        if(music) {
            remaining.put(player, getSoundLength(soundName));
        } else {
            for(Player all : Bukkit.getOnlinePlayers()) {
                if(all.getLocation().distance(player.getLocation()) < 6) {
                    Client client = AudioApi.getInstance().getClient(all.getUniqueId());
                    String url = getSoundURL(soundName);
                    MediaOptions options = new MediaOptions();
                    options.setVolume(volume);
                    AudioApi.getInstance().getMediaApi().playSpatialSound(client, url, player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ(), volume, true, 5);
                }
            }
        }
        Client client = AudioApi.getInstance().getClient(player.getUniqueId());
        String url = getSoundURL(soundName);
        MediaOptions options = new MediaOptions();
        options.setVolume(volume);
        AudioApi.getInstance().getMediaApi().playMedia(client, url, options);
    }

}
