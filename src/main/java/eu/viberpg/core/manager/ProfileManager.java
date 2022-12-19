package eu.viberpg.core.manager;

import com.google.common.collect.Lists;
import eu.viberpg.core.player.Profile;
import eu.viberpg.core.storage.mysql.MySQLTask;
import eu.viberpg.core.storage.mysql.function.*;
import eu.viberpg.core.util.TransformUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
        player.getInventory().clear();
        Profile pro = getActiveProfile(player);
        player.getInventory().setArmorContents(pro.getArmor());
        player.getInventory().setExtraContents(pro.getExtra());
        player.getInventory().setItemInMainHand(pro.getHand());
        player.getInventory().setItemInOffHand(pro.getOffhand());
        player.getInventory().setStorageContents(pro.getInventoryItems());
        player.setExp((float) pro.getExp());
        player.setHealth(pro.getHealth());
        if(pro.getLocation().getX() != 0 && pro.getLocation().getY() != 0 && pro.getLocation().getZ() != 0) {
            player.teleport(pro.getLocation());
        }
    }

    public Profile createProfile(Player player) {
        if(getActiveProfile(player) != null) {
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
        }
        player.getInventory().clear();
        Profile profile = new Profile(null);
        profile.setOriginalUUID(player.getUniqueId().toString());
        profile.setLocation(player.getLocation());
        profile.setInventoryItems(player.getInventory().getStorageContents());
        profile.setExtra(player.getInventory().getExtraContents());
        profile.setHand(player.getInventory().getItemInMainHand());
        profile.setOffhand(player.getInventory().getItemInOffHand());
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
                    profile.getUUID(),
                    new TransformUtil().toJsonArray(Lists.newArrayList(profile.getUUID())));
            insert.execute();
        } else {
            MySQLPush push = new MySQLTask().update();
            List<String> uuids = Arrays.stream(new TransformUtil().fromJsonArray(response.getString("jsonArray"))).collect(Collectors.toList());
            uuids.add(profile.getUUID());
            push.prepare("playerProfileList", "jsonArray", new TransformUtil().toJsonArray(uuids));
            push.execute();
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
        Bukkit.getConsoleSender().sendMessage(response.getString("jsonArray"));
        return new TransformUtil().fromJsonArray(response.getString("jsonArray"));
    }

    public Profile[] getProfiles(Player player) {
        String[] uuids = getProfileUUIDs(player);
        Profile[] profiles = new Profile[uuids.length];
        for(int i = 0; i < uuids.length; i++) {
            profiles[i] = getProfile(uuids[i]);
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
