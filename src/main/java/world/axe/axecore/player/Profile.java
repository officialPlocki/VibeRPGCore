package world.axe.axecore.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import world.axe.axecore.custom.ProfileLocation;
import world.axe.axecore.custom.SkillTeam;
import world.axe.axecore.storage.mysql.MySQLTask;
import world.axe.axecore.storage.mysql.function.MySQLInsert;
import world.axe.axecore.storage.mysql.function.MySQLPush;
import world.axe.axecore.storage.mysql.function.MySQLTable;
import world.axe.axecore.util.TransformUtil;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.UUID;

public class Profile {
    @JsonProperty("uuid")
    private String uuid = UUID.randomUUID().toString();
    @JsonProperty("originalUUID")
    private String originalUUID;
    @JsonProperty("skillTeam")
    private SkillTeam skillTeam = SkillTeam.values()[new Random().nextInt(SkillTeam.values().length - 1)];
    @JsonProperty("hunger")
    private double hunger = 20;
    @JsonProperty("health")
    private double health = 20;
    @JsonProperty("location")
    private ProfileLocation location = new ProfileLocation(0,0,0,0,0, "world");
    @JsonProperty("voicepack")
    private VoicePacks voicePack = VoicePacks.male_a;
    @JsonProperty("voicepack_set")
    private boolean voicepack_set = false;
    @JsonProperty("extra")
    private JsonObject[] extra;
    @JsonProperty("hand")
    private JsonObject hand;
    @JsonProperty("offhand")
    private JsonObject offhand;
    @JsonProperty("inventory")
    private JsonObject[] inventoryItems;
    @JsonProperty("armor")
    private JsonObject[] armor;
    @JsonProperty("exp")
    private double exp = 0;
    @JsonProperty("ruby")
    private double ruby = 0;
    @JsonProperty("created")
    private long created = System.currentTimeMillis();
    @JsonProperty("banned")
    private boolean banned = false;
    @JsonProperty("jump")
    private boolean jump = true;
    @JsonProperty("breath")
    private boolean breath = true;
    @JsonProperty("death")
    private boolean death = true;
    @JsonProperty("attack")
    private boolean attack = true;
    @JsonProperty("damage")
    private boolean damage = true;
    @JsonProperty("notifications")
    private boolean notifications = true;
    @JsonProperty("ambientMusic")
    private boolean ambientMusic = true;
    @JsonProperty("otherUserSounds")
    private boolean otherUserSounds = true;
    @JsonProperty("ui")
    private boolean ui = true;
    @JsonProperty("announceMusic")
    private boolean announce = true;

    @JsonCreator
    public Profile(@Nullable Profile profile) {
        MySQLTable raw = new MySQLTask().buildTable();
        raw.prepare("playerProfiles",
                "uuid",
                "json");
        if(profile == null) {
            MySQLInsert insert = new MySQLTask().insert();
            insert.prepare(raw.build(),
                    uuid,
                    "{}");
            insert.execute();
        } else {
            uuid = profile.getUUID();
            originalUUID = profile.getOriginalUUID();
            hunger = profile.getHunger();
            health = profile.getHealth();
            if(profile.getLocation() != null) {
                location = new TransformUtil().toProfileLocation(profile.getLocation());
            }
            inventoryItems = new TransformUtil().toProfileItems(profile.getInventoryItems());
            armor = new TransformUtil().toProfileItems(profile.getArmor());
            exp = profile.getExp();
            ruby = profile.getRuby();
            created = profile.getCreated();
            banned = profile.isBanned();
            skillTeam = profile.getSkillTeam();
            voicePack = profile.getVoicePack();
            offhand = new TransformUtil().itemToJson(profile.getOffhand());
            hand = new TransformUtil().itemToJson(profile.getHand());
            extra = new TransformUtil().toProfileItems(profile.getExtra());
            voicepack_set = profile.isVoicepack_set();
            jump = profile.isJump();
            breath = profile.isBreath();
            death = profile.isDeath();
            damage = profile.isDamage();
            attack = profile.isAttack();
            notifications = profile.isNotifications();
            ambientMusic = profile.isAmbientMusic();
            otherUserSounds = profile.isOtherUserSounds();
            ui = profile.isUi();
            announce = profile.isAnnounce();
        }
    }

    public boolean isAnnounce() {
        return announce;
    }

    public void setAnnounce(boolean announce) {
        this.announce = announce;
    }

    public boolean isDamage() {
        return damage;
    }

    public boolean isAttack() {
        return attack;
    }

    public boolean isBreath() {
        return breath;
    }

    public boolean isDeath() {
        return death;
    }

    public boolean isJump() {
        return jump;
    }

    public boolean isUi() {
        return ui;
    }

    public boolean isAmbientMusic() {
        return ambientMusic;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public boolean isOtherUserSounds() {
        return otherUserSounds;
    }

    public void setDamage(boolean damage) {
        this.damage = damage;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public void setBreath(boolean breath) {
        this.breath = breath;
    }

    public void setDeath(boolean death) {
        this.death = death;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void setNotification(boolean notification) {
        this.notifications = notification;
    }

    public void setAmbientMusic(boolean ambientMusic) {
        this.ambientMusic = ambientMusic;
    }

    @SuppressWarnings("unused")
    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public void setOtherUserSounds(boolean otherUserSounds) {
        this.otherUserSounds = otherUserSounds;
    }

    public void setUi(boolean ui) {
        this.ui = ui;
    }

    public boolean isVoicepack_set() {
        return voicepack_set;
    }

    public ItemStack[] getExtra() {
        return new TransformUtil().fromProfileItems(extra);
    }

    public void setExtra(ItemStack[] extra) {
        this.extra = new TransformUtil().toProfileItems(extra);
    }

    public void setOffhand(ItemStack offhand) {
        this.offhand = new TransformUtil().itemToJson(offhand);
    }

    public ItemStack getOffhand() {
        return new TransformUtil().itemFromJson(offhand);
    }

    public void setHand(ItemStack hand) {
        this.hand = new TransformUtil().itemToJson(hand);
    }

    public ItemStack getHand() {
        return new TransformUtil().itemFromJson(hand);
    }


    public void setVoicePack(VoicePacks voicePack) {
        this.voicePack = voicePack;
        this.voicepack_set = true;
    }

    public VoicePacks getVoicePack() {
        return voicePack;
    }

    @SuppressWarnings("unused")
    public void setSkillTeam(SkillTeam skillTeam) {
        this.skillTeam = skillTeam;
    }

    public SkillTeam getSkillTeam() {
        return skillTeam;
    }

    public void setOriginalUUID(String originalUUID) {
        this.originalUUID = originalUUID;
    }

    public String getOriginalUUID() {
        return originalUUID;
    }

    public double getHunger() {
        return hunger;
    }

    public double getHealth() {
        return health;
    }

    public double getExp() {
        return exp;
    }

    public double getRuby() {
        return ruby;
    }

    public ItemStack[] getArmor() {
        return new TransformUtil().fromProfileItems(armor);
    }

    public ItemStack[] getInventoryItems() {
        return new TransformUtil().fromProfileItems(inventoryItems);
    }

    public Location getLocation() {
        return new TransformUtil().fromProfileLocation(location);
    }

    public long getCreated() {
        return created;
    }

    public String getUUID() {
        return uuid;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = new TransformUtil().toProfileItems(armor);
    }

    @SuppressWarnings("unused")
    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @SuppressWarnings("unused")
    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public void setInventoryItems(ItemStack[] inventoryItems) {
        this.inventoryItems = new TransformUtil().toProfileItems(inventoryItems);
    }

    public void setLocation(Location location) {
        this.location = new TransformUtil().toProfileLocation(location);
    }

    @SuppressWarnings("unused")
    public void setRuby(double ruby) {
        this.ruby = ruby;
    }

    @SuppressWarnings("unused")
    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void save() {
        MySQLPush push = new MySQLTask().update();
        push.prepare("playerProfiles", "json", new TransformUtil().toJson(this));
        push.addRequirement("uuid", uuid);
        push.execute();
    }

    @Override
    public String toString() {
        return new TransformUtil().toJson(this);
    }
}
