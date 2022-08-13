package world.axe.axecore.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import com.mojang.authlib.GameProfile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.axe.axecore.custom.LabyModDisplay;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

public class NPCUtil {

    private void example() {
        Random random = new Random();
        Location location = null;
        // Create NPC uuid with least significant bits set to 0
        UUID uuid = UUID.randomUUID();

        // Add NPC to the tablist
        GameProfile gameProfile = new GameProfile(uuid, "NPC");
        addPlayerToTablist(gameProfile, gameProfile.getName());

        // Spawn the entity in the world (Maybe with a little delay of few ticks)
        spawnPlayerInWorld(1337, uuid, location);

        // Play hello emote (Another delay here) -> Emotes https://docs.labymod.net/pages/server/labymod/emote_api/
        new LabyModDisplay().sendEmote(null, uuid, 4);
    }

    private void spawnPlayerInWorld( int entityId, UUID uuid, Location location ) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket( PacketType.Play.Server.NAMED_ENTITY_SPAWN );

        // Entity id and uuid
        packet.getIntegers().write( 0, entityId );
        packet.getUUIDs().write( 0, uuid );

        // Location
        packet.getIntegers().write( 1, ( int ) Math.floor( location.getX() * 32D ) );
        packet.getIntegers().write( 2, ( int ) Math.floor( location.getY() * 32D ) );
        packet.getIntegers().write( 3, ( int ) Math.floor( location.getZ() * 32D ) );
        packet.getBytes().write( 0, ( byte ) ( location.getYaw() * 256F / 360F ) );
        packet.getBytes().write( 1, ( byte ) ( location.getPitch() * 256F / 360F ) );

        packet.getIntegers().write( 4, 0 /* Item in hand id */ );

        // Data watcher for second skin layer
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        watcher.setObject( 0, ( byte ) 0 );
        watcher.setObject( 10, ( byte ) 127 );
        packet.getDataWatcherModifier().write( 0, watcher );

        // Send packet to all players
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket( player, packet );
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void addPlayerToTablist( GameProfile gameProfile, String displayName ) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket( PacketType.Play.Server.PLAYER_INFO );

        // Write action type
        packet.getPlayerInfoAction().write( 0, EnumWrappers.PlayerInfoAction.ADD_PLAYER );

        // Write gameprofile
        WrappedGameProfile wrappedProfile = WrappedGameProfile.fromHandle( gameProfile );
        EnumWrappers.NativeGameMode nativeGameMode = EnumWrappers.NativeGameMode.fromBukkit( GameMode.SURVIVAL );
        packet.getPlayerInfoDataLists().write( 0, Collections.singletonList( new PlayerInfoData( wrappedProfile, 20, nativeGameMode, WrappedChatComponent.fromText( displayName ) ) ) );

        // Send packet to all players
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket( player, packet );
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
