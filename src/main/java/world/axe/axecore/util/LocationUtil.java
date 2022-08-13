package world.axe.axecore.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import world.axe.axecore.storage.FileProvider;

import java.util.Random;

public class LocationUtil {

    // @todo random location: caves & surface
    // @todo scheduler mob spawn

    private final FileProvider provider;
    private final YamlConfiguration yml;

    public LocationUtil() {
        provider = new FileProvider("mobs.yml");
        yml = provider.getConfiguration();
    }

    public Location getRandomLocation(Location origin, int radius, boolean _3D) {
        Random random = new Random();
        Location output = origin.clone();
        output.add(random.nextInt(radius) - random.nextInt(radius),
                random.nextInt(radius) - random.nextInt(radius),
                random.nextInt(radius) - random.nextInt(radius));
        output.setY(256);
        while (true) {
            if(!output.getBlock().getType().isSolid()) {
                output.setY(output.getY() - 1);
            } else {
                if(output.getBlock().getType().name().contains("LEAVE")) {
                    output.setY(output.getY() - 1);
                } else {
                    if(!output.getBlock().getBiome().name().contains("CAVE")) {
                        output.setY(output.getY() + 2);
                        break;
                    } else {
                        boolean b = false;
                        Location copy = output.clone();
                        for(int i = 0; i < 6; i++) {
                            copy.setY(copy.getY() + 1);
                            b = copy.getBlock().getType().isAir();
                        }
                        if(b) {
                            return output;
                        }
                    }
                }
            }
        }
        return output;
    }

}
