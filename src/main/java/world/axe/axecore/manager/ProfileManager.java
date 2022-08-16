package world.axe.axecore.manager;

import com.google.common.collect.Lists;
import org.bukkit.entity.Player;
import world.axe.axecore.player.Profile;
import world.axe.axecore.storage.mysql.MySQLTask;
import world.axe.axecore.storage.mysql.function.*;
import world.axe.axecore.util.TransformUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class ProfileManager {

    // @todo profile sounds (UI)

    public ProfileManager() {
        MySQLTable raw = new MySQLTask().buildTable();
        raw.prepare("playerProfiles",
                "uuid",
                "json");
        raw.build();
        MySQLTable raw2 = new MySQLTask().buildTable();
        raw2.prepare("playerProfileList",
                "uuid",
                "activeProfile",
                "jsonArray");
        raw2.build();
    }

    public Profile getActiveProfile(Player player) {
        MySQLRequest request = new MySQLRequest();
        request.prepare("activeProfile", "playerProfileList");
        request.addRequirement("uuid", player.getUniqueId().toString());
        MySQLResponse response = request.execute();
        if(!response.isEmpty()) {
            return getProfile(response.getString("activeProfile"));
        } else {
            return null;
        }
    }

    public void forceProfile(Player player, Profile profile) {
        MySQLPush push = new MySQLPush();
        push.prepare("playerProfileList",
                "activeProfile",
                profile.getUUID());
        push.addRequirement("uuid", player.getUniqueId().toString());
        push.execute();
    }
    public void changeProfile(Player player, Profile profile) {
        Profile active = getActiveProfile(player);
        active.setArmor(player.getInventory().getArmorContents());
        active.setInventoryItems(player.getInventory().getStorageContents());
        active.setExtra(player.getInventory().getExtraContents());
        active.setHand(player.getInventory().getItemInMainHand());
        active.setOffhand(player.getInventory().getItemInOffHand());
        active.setExp(player.getExp());
        active.setHealth(player.getHealth());
        active.setLocation(player.getLocation());
        active.save();
        MySQLPush push = new MySQLPush();
        push.prepare("playerProfileList",
                "activeProfile",
                profile.getUUID());
        push.addRequirement("uuid", player.getUniqueId().toString());
        push.execute();
        Profile pro = getActiveProfile(player);
        player.getInventory().setArmorContents(pro.getArmor());
        player.getInventory().setExtraContents(pro.getExtra());
        player.getInventory().setItemInMainHand(pro.getHand());
        player.getInventory().setItemInOffHand(pro.getOffhand());
        player.getInventory().setStorageContents(pro.getInventoryItems());
        player.setExp((float) pro.getExp());
        player.setHealth(pro.getHealth());
        player.teleport(pro.getLocation());
    }

    public Profile createProfile(Player player) {
        Profile profile = new Profile(null);
        profile.setOriginalUUID(player.getUniqueId().toString());
        profile.setLocation(player.getLocation());
        profile.save();
        MySQLTable raw2 = new MySQLTask().buildTable();
        raw2.prepare("playerProfileList",
                "uuid",
                "activeProfile",
                "jsonArray");
        MySQLTable.fin table = raw2.build();
        MySQLRequest request = new MySQLTask().ask();
        request.prepare("jsonArray","playerProfileList");
        request.addRequirement("uuid", player.getUniqueId().toString());
        MySQLResponse response = request.execute();
        if(response.isEmpty()) {
            MySQLInsert insert = new MySQLTask().insert();
            insert.prepare(table,
                    player.getUniqueId().toString(),
                    new TransformUtil().toJsonArray(Lists.newArrayList(profile.getUUID())));
        } else {
            MySQLPush push = new MySQLTask().update();
            List<String> uuids = Arrays.stream(new TransformUtil().fromJsonArray(response.getString("jsonArray"))).collect(Collectors.toList());
            uuids.add(profile.getUUID());
            push.prepare("playerProfileList", "jsonArray", new TransformUtil().toJsonArray(uuids));
        }
        return profile;
    }

    public void transferProfile(String profileUUID, Player target) {
        Profile profile = getProfile(profileUUID);
        profile.setOriginalUUID(target.getUniqueId().toString());
        profile.save();
    }

    public String[] getProfileUUIDs(Player player) {
        MySQLRequest request = new MySQLTask().ask();
        request.prepare("jsonArray","playerProfileList");
        request.addRequirement("uuid", player.getUniqueId().toString());
        MySQLResponse response = request.execute();
        if(response.isEmpty()) return null;
        return new TransformUtil().fromJsonArray(response.getString("jsonArray"));
    }

    public Profile[] getProfiles(Player player) {
        MySQLRequest request = new MySQLTask().ask();
        request.prepare("jsonArray","playerProfileList");
        request.addRequirement("uuid", player.getUniqueId().toString());
        MySQLResponse response = request.execute();
        if(response.isEmpty()) return null;
        List<String> uuids = Arrays.stream(new TransformUtil().fromJsonArray(response.getString("jsonArray"))).collect(Collectors.toList());
        Profile[] profiles = new Profile[uuids.size()];
        for(int i = 0; i < uuids.size(); i++) {
            profiles[i] = getProfile(uuids.get(i));
        }
        return profiles;
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
