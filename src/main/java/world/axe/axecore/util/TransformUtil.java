package world.axe.axecore.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import world.axe.axecore.custom.ProfileLocation;
import world.axe.axecore.util.json.JsonItemStack;

import java.util.List;

public class TransformUtil {

    public @Nullable String toJson(Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public @Nullable <T> T fromJson(String json, Class<T> clazz)  {
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public @Nullable Location fromProfileLocation(@NotNull ProfileLocation location) {
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public @NotNull ProfileLocation toProfileLocation(@NotNull Location location) {
        return new ProfileLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
    }

    public String[] toProfileItems(ItemStack[] armor) {
        String[] strings = new String[armor.length];
        for (int i = 0; i < armor.length; i++) {
            strings[i] = itemToJson(armor[i]);
        }
        return strings;
    }

    public ItemStack[] fromProfileItems(String[] armor) {
        ItemStack[] strings = new ItemStack[armor.length];
        for (int i = 0; i < armor.length; i++) {
            strings[i] = itemFromJson(armor[i]);
        }
        return strings;
    }

    public @NotNull String itemToJson(@NotNull ItemStack item) {
        return JsonItemStack.toJson(item);
    }

    public @Nullable ItemStack itemFromJson(@NotNull String json) {
        return JsonItemStack.fromJson(json);
    }

    public JsonArray toJsonArray(List<String> strings) {
        JsonArray array = new JsonArray();
        for (String string : strings) {
            array.add(string);
        }
        return array;
    }

    public String[] fromJsonArray(String json) {
        List<Object> objects = new JSONArray(json).toList();
        String[] array = new String[objects.size()];
        for (int i = 0; i < objects.size(); i++) {
            array[i] = (String) objects.get(i);
        }
        return array;
    }

}
