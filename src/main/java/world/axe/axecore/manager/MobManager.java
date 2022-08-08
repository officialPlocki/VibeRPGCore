package world.axe.axecore.manager;

import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAPIHelper;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.UUID;

public class MobManager {

    public Entity spawnMob(EntityType type, Location location) {
        return location.getWorld().spawnEntity(location, type, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    public ActiveMob spawnCustomMob(MythicMob type, Location location, int level) {
        BukkitAPIHelper helper = new BukkitAPIHelper();
        try {
            Entity entity = helper.spawnMythicMob(type, location, level);
            return helper.getMythicMobInstance(entity);
        } catch (InvalidMobTypeException e) {
            throw new RuntimeException(e);
        }
    }

    public ActiveMob spawnCustomMob(String type, Location location) {
        BukkitAPIHelper helper = new BukkitAPIHelper();
        try {
            Entity entity = helper.spawnMythicMob(type, location);
            return helper.getMythicMobInstance(entity);
        } catch (InvalidMobTypeException e) {
            throw new RuntimeException(e);
        }
    }

    public MythicMob getMythicMobType(String type) {
        return new BukkitAPIHelper().getMythicMob(type);
    }

    public ActiveMob getMythicMob(Entity entity) {
        return new BukkitAPIHelper().getMythicMobInstance(entity);
    }

    public boolean isMythicMob(Entity entity) {
        return new BukkitAPIHelper().isMythicMob(entity);
    }

    public boolean isMythicMob(UUID uuid) {
        return new BukkitAPIHelper().isMythicMob(uuid);
    }

    public boolean isMythicMob(String uuid) {
        return new BukkitAPIHelper().isMythicMob(UUID.fromString(uuid));
    }

    public BukkitAPIHelper getMythicMobsAPI() {
        return new BukkitAPIHelper();
    }

}
