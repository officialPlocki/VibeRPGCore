package world.axe.axecore.util;

import com.google.gson.Gson;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import world.axe.axecore.util.json.JsonItemStack;

public class TransformUtil {

    public @Nullable String toJson(Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public @NotNull <T> T fromJson(String json, Class<T> clazz)  {
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public String toJson(ItemStack item) {
        return JsonItemStack.toJson(item);
    }

    public ItemStack fromJson(String json) {
        return JsonItemStack.fromJson(json);
    }

}
