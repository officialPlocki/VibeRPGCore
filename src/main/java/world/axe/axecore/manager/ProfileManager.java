package world.axe.axecore.manager;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.axe.axecore.custom.ProfileLocation;
import world.axe.axecore.player.Profile;
import world.axe.axecore.storage.mysql.MySQLTask;
import world.axe.axecore.storage.mysql.function.MySQLRequest;
import world.axe.axecore.storage.mysql.function.MySQLResponse;
import world.axe.axecore.storage.mysql.function.MySQLTable;
import world.axe.axecore.util.TransformUtil;

public class ProfileManager {

    // @todo player profile list
    public ProfileManager() {
        MySQLTable raw = new MySQLTask().buildTable();
        raw.prepare("playerProfiles",
                "uuid",
                "json");
        raw.build();
    }

    public Profile createProfile(Player player) {
        Profile profile = new Profile(null);
        profile.setOriginalUUID(player.getUniqueId().toString());
        Location l = player.getLocation();
        profile.setLocation(new ProfileLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(), l.getWorld().getName()));
        profile.save();
        return profile;
    }

    public void transferProfile(String profileUUID, Player target) {
        Profile profile = getProfile(profileUUID);
        profile.setOriginalUUID(target.getUniqueId().toString());
        profile.save();
    }

    public Profile getProfile(String uuid) {
        MySQLRequest request = new MySQLTask().ask();
        request.prepare("json", "playerProfiles");
        request.addRequirement("uuid", uuid);
        MySQLResponse response = request.execute();
        if(response.isEmpty()) {
            return new Profile(null);
        } else {
            return new Profile(new TransformUtil().fromJson(response.getString("json"), Profile.class));
        }
    }

}
