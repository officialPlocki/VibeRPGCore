package world.axe.axecore.custom;

import com.destroystokyo.paper.Title;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.labymod.serverapi.api.LabyAPI;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import world.axe.axecore.player.RankManager;

import java.util.*;

public class LabyModDisplay {

    public void updateGemsDisplay(Player player, int gems) {
        updateBalanceDisplay(player, EnumBalanceType.GEMS, true, gems);
    }

    public void updateRubyDisplay(Player player, int ruby) {
        updateBalanceDisplay(player, EnumBalanceType.RUBY, true, ruby);
    }

    public boolean hasLabyMod(Player player) {
        return LabyAPI.getService().getLabyPlayerService().getPlayer(player.getUniqueId()).isPresent();
    }

    public void displayRank(Player player) {
        String rank = new RankManager().getPrefix(player);
        for(Player all : Bukkit.getOnlinePlayers()) {
            if(hasLabyMod(all)) {
                setSubtitle(all, player.getUniqueId(), rank);
            }
        }
    }

    public void sendTablistImage(Player player) {
        if(hasLabyMod(player)) {
            sendServerBanner(player, "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/1200px-Google_2015_logo.svg.png");
        }
    }

    public void sendEmote(Player receiver, UUID npc, int emote) {
        forceEmote(receiver, npc, emote);
    }

    public void disableVoiceChat( Player player ) {
        JsonObject object = new JsonObject();

        // Disable the voice chat for this player
        object.addProperty( "allowed", false );

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "voicechat", object );
    }

    private void forceEmote( Player receiver, UUID npcUUID, int emoteId ) {
        // List of all forced emotes
        JsonArray array = new JsonArray();

        // Emote and target NPC
        JsonObject forcedEmote = new JsonObject();
        forcedEmote.addProperty( "uuid", npcUUID.toString() );
        forcedEmote.addProperty( "emote_id", emoteId );
        array.add(forcedEmote);

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( receiver.getUniqueId(), "emote_api", array );
    }

    public void playCinematic(Player player, List<Location> points, HashMap<Title, Long> titles, long duration, Location after) {
        if(!hasLabyMod(player)) return;
        JsonObject cinematic = new JsonObject();

        // Add points
        JsonArray ps = new JsonArray();
        for ( Location location : points ) {
            // Add points
            JsonObject point = new JsonObject();
            point.addProperty( "x", location.getX() );
            point.addProperty( "y", location.getY() );
            point.addProperty( "z", location.getZ() );
            point.addProperty( "yaw", location.getYaw() );
            point.addProperty( "pitch", location.getPitch() );
            point.addProperty( "tilt", new Random().nextInt(-25, 25));
            ps.add( point );
        }
        cinematic.add( "points", ps );

        // Clear the Minecraft Chat
        cinematic.addProperty("clear_chat", true );

        // Cinematic duration in ms
        cinematic.addProperty( "duration", duration );

        // Always teleport the player to the start point
        player.teleport( points.get(0) );
        player.setGameMode(GameMode.SPECTATOR);

        // The player needs to fly for the cinematic
        player.setAllowFlight( true );

        // Play cinematic
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "cinematic", cinematic );
        int i = 0;
        for(long time : titles.values()) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AXECore")),() -> {
                titles.forEach((title, aLong) -> {
                    if(aLong.equals(time)) {
                        player.sendTitle(title);
                    }
                });
            }, 20, time * 20); // SECONDS
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("AXECore")), () -> {
            player.teleport(after);
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(false);
        }, (duration / 100) + 1); // 1000MS = 1S
    }

    public void cancelCinematic( Player player ) {
        if(!hasLabyMod(player)) return;
        // Cancel currently playing cinematic
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "cinematic", new JsonObject() ); // Empty object
    }

    /**
     * Recommend or force player to change the voice chat settings
     */
    public void sendVoiceSettings( Player player, boolean keepSettings, boolean required ) {
        JsonObject voicechatObject = new JsonObject();

        // "Keep settings" means that the settings are only reset when the player leaves the entire server network.
        // If this setting is set to "false", everything will be reset when the player switches the lobby for example.
        voicechatObject.addProperty( "keep_settings_on_server_switch", keepSettings );

        JsonObject requestSettingsObject = new JsonObject();

        // Required means the player MUST accept the settings to access the server. It's no longer a suggestion.
        requestSettingsObject.addProperty("required", required );

        JsonObject settingsObject = new JsonObject();

        // Only set the settings you want to change, remove everything else!
        settingsObject.addProperty("microphoneVolume", 20); // Own microphone volume. (0 - 10, Default 10)
        settingsObject.addProperty("surroundRange", 15); // Range of the players you can hear (5 - 18, Default: 10)
        settingsObject.addProperty("surroundVolume", 10); // Volume of other players (0 - 10, Default: 10)

        requestSettingsObject.add("settings", settingsObject );
        voicechatObject.add("request_settings", requestSettingsObject);

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "voicechat", voicechatObject );
    }

    public void sendCineScope( Player player, int coveragePercent, long duration ) {
        JsonObject object = new JsonObject();

        // Cinescope height (0% - 50%)
        object.addProperty( "coverage", coveragePercent );

        // Duration
        object.addProperty( "duration", duration );

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "cinescopes", object );
    }

    public void sendWatermark(Player player) {
        if(hasLabyMod(player)) {
            JsonObject object = new JsonObject();

            // Visibility
            object.addProperty( "visible", false );

            // Send to LabyMod using the API
            LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "watermark", object );
        }
    }

    private void sendServerBanner(Player player, String imageUrl) {
        JsonObject object = new JsonObject();
        object.addProperty("url", imageUrl); // Url of the image
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage(player.getUniqueId(), "server_banner", object);
    }

    private void setSubtitle(Player receiver, UUID subtitlePlayer, String value ) {
        // List of all subtitles
        JsonArray array = new JsonArray();

        // Add subtitle
        JsonObject subtitle = new JsonObject();
        subtitle.addProperty( "uuid", subtitlePlayer.toString() );

        // Optional: Size of the subtitle
        subtitle.addProperty( "size", 0.8d ); // Range is 0.8 - 1.6 (1.6 is Minecraft default)

        // no value = remove the subtitle
        if(value != null)
            subtitle.addProperty( "value", value );

        // If you want to use the new text format in 1.16+
        // subtitle.add("raw_json_text", textObject );

        // You can set multiple subtitles in one packet
        array.add(subtitle);

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( receiver.getUniqueId(), "account_subtitle", array );
    }

    private void updateBalanceDisplay( Player player, EnumBalanceType type, boolean visible, int balance ) {
        JsonObject economyObject = new JsonObject();
        JsonObject cashObject = new JsonObject();

        // Visibility
        cashObject.addProperty( "visible", visible );

        // Amount
        cashObject.addProperty( "balance", balance );

    cashObject.addProperty( "icon", type.getValue() );

    JsonObject decimalObject = new JsonObject();
    decimalObject.addProperty("format", "##.##"); // Decimal format
    decimalObject.addProperty("divisor", 100); // The value that divides the balance
    cashObject.add( "decimal", decimalObject );

        // The display type can be "cash" or "bank".
        economyObject.add(type.getKey(), cashObject);

        // Send to LabyMod using the API
        LabyAPI.getService().getPayloadCommunicator().sendLabyModMessage( player.getUniqueId(), "economy", economyObject );
    }

    private enum EnumBalanceType {
        GEMS("gems", "https://storage.eaxe.world/icons/gems.png"),
        RUBY("ruby", "https://storage.eaxe.world/icons/ruby.png");

        private final String key;
        private final String value;

        EnumBalanceType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getKey() {
            return this.key;
        }
    }
}
