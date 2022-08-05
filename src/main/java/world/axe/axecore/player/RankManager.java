package world.axe.axecore.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.google.common.base.Predicates;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import world.axe.axecore.display.DisplayHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

public class RankManager {

    private final Player player;

    public RankManager(Player player) {
        this.player = player;
    }

    public void sendTablist() {
        PacketContainer container = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);
        container.getChatComponents().write(0, WrappedChatComponent.fromText("\n§8» §a§lE§b§lAXE §c§lRPG §8«\n\n"));
        container.getChatComponents().write(1, WrappedChatComponent.fromText("\n\n§7Discord §8» §bdiscord.eaxe.world\n§7a §b§lElectronic AXE Entertainment project\n§8Make your own world.\n"));
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
        WrappedChatComponent name = WrappedChatComponent.fromText(player.getPlayerListName());
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(player);
        WrappedSignedProperty signed = new PlayerInfoData(profile, 0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), name).getProfile().getProperties().get("textures").iterator().next();

        PacketContainer add = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        PacketContainer remove = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);

        PacketContainer destroy = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_DESTROY);
        PacketContainer spawn = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);

        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Collections.singletonList(new PlayerInfoData(WrappedGameProfile.fromPlayer(player), 0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(player.getDisplayName()))));

        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);

        PlayerInfoData data = new PlayerInfoData(profile.withName(getPrefix() + " §7" + player.getName() + " " + getSuffix()), 0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()), WrappedChatComponent.fromText(""));
        data.getProfile().getProperties().clear();
        data.getProfile().getProperties().get("textures").add(signed);

        add.getPlayerInfoDataLists().write(0, Collections.singletonList(data));

        destroy.getIntegerArrays().write(0, new int[]{player.getEntityId()});

        spawn.getIntegers().write(0, player.getEntityId());
        spawn.getUUIDs().write(0, player.getUniqueId());
        spawn.getDoubles().write(0, player.getLocation().getX());
        spawn.getDoubles().write(1, player.getLocation().getY());
        spawn.getDoubles().write(2, player.getLocation().getZ());
        spawn.getBytes().write(0, (byte)((int)(player.getLocation().getYaw() * 256.0F / 360.0F)));
        spawn.getBytes().write(1, (byte)((int)(player.getLocation().getPitch() * 256.0F / 360.0F)));
        spawn.getDataWatcherModifier().write(0, watcher);

        ProtocolLibrary.getProtocolManager().broadcastServerPacket(remove);
        ProtocolLibrary.getProtocolManager().broadcastServerPacket(add);

        Bukkit.getOnlinePlayers().stream().filter(Predicates.not(player::equals)).forEach(object -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(object, destroy);
                ProtocolLibrary.getProtocolManager().sendServerPacket(object, spawn);
            } catch (InvocationTargetException exception) {
                exception.printStackTrace();
            }
        });
    }

    public String getSuffix() {
        String suffix = "§f";
        if(player.hasPermission("suffix.event_guide")) {
            suffix = suffix + DisplayHelper.ranks.EVENT_GUIDE.val();
        } else if(player.hasPermission("suffix.rstaff")) {
            suffix = suffix + DisplayHelper.ranks.RSTAFF_TAG.val();
        } else if(player.hasPermission("suffix.staff")) {
            suffix = suffix + DisplayHelper.ranks.STAFF_TAG.val();
        }
        if(player.hasPermission("suffix.use.ancient")) {
            suffix = suffix + DisplayHelper.ranks.ANCIENT_TAG.val();
        } else if(player.hasPermission("suffix.use.caesar")) {
            suffix = suffix + DisplayHelper.ranks.CAESAR_TAG.val();
        } else if(player.hasPermission("suffix.use.champion")) {
            suffix = suffix + DisplayHelper.ranks.CHAMPION_TAG.val();
        } else if(player.hasPermission("suffix.use.emperor")) {
            suffix = suffix + DisplayHelper.ranks.EMPEROR_TAG.val();
        } else if(player.hasPermission("suffix.use.extreme")) {
            suffix = suffix + DisplayHelper.ranks.EXTREME_TAG.val();
        } else if(player.hasPermission("suffix.use.god")) {
            suffix = suffix + DisplayHelper.ranks.GOD_TAG.val();
        } else if(player.hasPermission("suffix.use.hero")) {
            suffix = suffix + DisplayHelper.ranks.IMMORTAL_TAG.val();
        } else if(player.hasPermission("suffix.use.knight")) {
            suffix = suffix + DisplayHelper.ranks.KNIGHT_TAG.val();
        } else if(player.hasPermission("suffix.use.kratos")) {
            suffix = suffix + DisplayHelper.ranks.KRATOS_TAG.val();
        } else if(player.hasPermission("suffix.use.legend")) {
            suffix = suffix + DisplayHelper.ranks.LEGEND_TAG.val();
        } else if(player.hasPermission("suffix.use.legion")) {
            suffix = suffix + DisplayHelper.ranks.LEGION_TAG.val();
        } else if(player.hasPermission("suffix.use.mage")) {
            suffix = suffix + DisplayHelper.ranks.MAGE_TAG.val();
        } else if(player.hasPermission("suffix.use.sage")) {
            suffix = suffix + DisplayHelper.ranks.SAGE_TAG.val();
        } else if(player.hasPermission("suffix.use.prince")) {
            suffix = suffix + DisplayHelper.ranks.PRINCE_TAG.val();
        }
        return suffix;
    }

    public String getPrefix() {
        if(player.hasPermission("rank.headadmin")) {
            return "§f" + DisplayHelper.ranks.HEADADMIN_TAG.val();
        } else if(player.hasPermission("rank.admin")) {
            return "§f" + DisplayHelper.ranks.ADMIN_TAG.val();
        } else if(player.hasPermission("rank.manager")) {
            return "§f" + DisplayHelper.ranks.MANAGER_TAG.val();
        } else if(player.hasPermission("rank.engineer")) {
            return "§f" + DisplayHelper.ranks.ENGINEER.val();
        } else if(player.hasPermission("rank.srmod")) {
            return "§f" + DisplayHelper.ranks.SRMOD_TAG.val();
        } else if(player.hasPermission("rank.mod")) {
            return "§f" + DisplayHelper.ranks.MOD_TAG.val();
        } else if(player.hasPermission("rank.builder")) {
            return "§f" + DisplayHelper.ranks.BUILDER_TAG.val();
        } else if(player.hasPermission("rank.helper")) {
            return "§f" + DisplayHelper.ranks.HELPER_TAG.val();
        } else if(player.hasPermission("rank.twitch")) {
            return "§f" + DisplayHelper.ranks.TWITCH_TAG.val();
        } else if(player.hasPermission("rank.youtuber")) {
            return "§f" + DisplayHelper.ranks.YOUTUBER_TAG.val();
        } else if(player.hasPermission("rank.baron")) {
            return "§f" + DisplayHelper.ranks.BARON_TAG.val();
        } else if(player.hasPermission("rank.mvpplus")) {
            return "§f" + DisplayHelper.ranks.MVPPLUS_TAG.val();
        } else if(player.hasPermission("rank.mvp")) {
            return "§f" + DisplayHelper.ranks.MVP_TAG.val();
        } else if(player.hasPermission("rank.vipplus")) {
            return "§f" + DisplayHelper.ranks.VIPPLUS_TAG.val();
        } else if(player.hasPermission("rank.vip")) {
            return "§f" + DisplayHelper.ranks.VIP_TAG.val();
        } else {
            return "§f" + DisplayHelper.ranks.BAMBOO_TAG.val();
        }
    }

}
