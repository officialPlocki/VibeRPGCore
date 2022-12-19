package eu.viberpg.core.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eu.viberpg.core.util.json.JsonItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import eu.viberpg.core.custom.ProfileLocation;

import java.util.List;
import java.util.Objects;

public class TransformUtil {

    public @Nullable String toJson(@Nullable Object obj) {
        if(obj == null) return null;
        try {
            return new Gson().toJson(obj);
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(exception.getMessage());
        }
        return null;
    }

    public @Nullable <T> T fromJson(@Nullable String json, @Nullable Class<T> clazz)  {
        if(json == null) return null;
        if(clazz == null) return null;
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception exception) {
            exception.printStackTrace();
            Bukkit.getConsoleSender().sendMessage(exception.getMessage());
            Bukkit.getConsoleSender().sendMessage(json);
        }
        return null;
    }

    public @Nullable Location fromProfileLocation(@Nullable ProfileLocation location) {
        if(location == null) return null;
        return new Location(Bukkit.getWorld(location.getWorld()), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public @NotNull ProfileLocation toProfileLocation(@NotNull Location location) {
        return new ProfileLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), location.getWorld().getName());
    }

    public @Nullable JsonObject[] toProfileItems(@Nullable ItemStack[] armor) {
        if(armor == null) return null;
        ItemStack[] items = new ItemStack[armor.length];
        int i = 0;
        for(ItemStack stack : armor) {
            items[i] = stack;
            if(items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
            }
        }
        JsonObject[] strings = new JsonObject[items.length];
        for (int i2 = 0; i2 < items.length; i2++) {
            JsonObject json = itemToJson(items[i2]);
            if(json.isJsonNull()) {
                strings[i2] = itemToJson(new ItemStack(Material.AIR));
            } else {
                if(armor[i2] == null) {
                    strings[i2] = itemToJson(new ItemStack(Material.AIR));
                } else {
                    strings[i2] = json;
                }
            }
        }
        return strings;
    }

    public @Nullable ItemStack[] fromProfileItems(@NotNull JsonObject[] armor) {
        if(armor == null) return null;
        ItemStack[] strings = new ItemStack[armor.length];
        for (int i = 0; i < armor.length; i++) {
            if(armor[i] == null) {
                armor[i] = itemToJson(new ItemStack(Material.AIR));
                continue;
            }
            if(armor[i].isJsonNull()) {
                strings[i] = new ItemStack(Material.AIR);
            } else {
                ItemStack json = itemFromJson(armor[i]);
                strings[i] = Objects.requireNonNullElseGet(json, () -> new ItemStack(Material.AIR));
            }
        }
        return strings;
    }

    public @NotNull JsonObject itemToJson(@Nullable ItemStack item) {
        return JsonItemStack.toJson(item);
    }

    public @Nullable ItemStack itemFromJson(@Nullable JsonObject json) {
        if(json == null) return null;
        return JsonItemStack.fromJson(json);
    }

    public @Nullable String toJsonArray(@Nullable List<String> strings) {
        if(strings == null) return null;
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();
        for(String str : strings) {
            array.put(str);
        }
        object.put("array", array);
        return object.toString();
    }

    public @Nullable String[] fromJsonArray(@Nullable String json) {
        if(json == null) return null;
        JSONObject object = new JSONObject(json);
        JSONArray array = object.getJSONArray("array");
        String[] strings = new String[array.length()];
        for (int i = 0; i < array.length(); i++) {
            strings[i] = (String) array.get(i);
        }
        return strings;
    }

}
