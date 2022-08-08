package world.axe.axecore.util;

import org.bukkit.configuration.file.YamlConfiguration;
import world.axe.axecore.storage.FileProvider;

public class LocationUtil {

    // @todo random location: caves & surface
    // @todo scheduler mob spawn

    private final FileProvider provider;
    private final YamlConfiguration yml;

    public LocationUtil() {
        provider = new FileProvider("mobs.yml");
        yml = provider.getConfiguration();
    }

}
