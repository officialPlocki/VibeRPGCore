package world.axe.axecore.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import world.axe.axecore.custom.ProfileLocation;
import world.axe.axecore.custom.SkillTeam;
import world.axe.axecore.storage.mysql.MySQLTask;
import world.axe.axecore.storage.mysql.function.MySQLInsert;
import world.axe.axecore.storage.mysql.function.MySQLPush;
import world.axe.axecore.storage.mysql.function.MySQLTable;
import world.axe.axecore.util.TransformUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class Profile {
    @JsonProperty("uuid")
    private String uuid = UUID.randomUUID().toString();
    @JsonProperty("originalUUID")
    private String originalUUID;
    @JsonProperty("skillTeam")
    private SkillTeam skillTeam;
    @JsonProperty("hunger")
    private double hunger = 20;
    @JsonProperty("health")
    private double health = 20;
    @JsonProperty("location")
    private ProfileLocation location;

    @JsonProperty("voicepack")
    private VoicePacks voicePack = VoicePacks.male_a;
    @JsonProperty("inventory")
    private String[] inventoryItems;
    @JsonProperty("armor")
    private String[] armor;
    @JsonProperty("exp")
    private double exp = 0;
    @JsonProperty("ruby")
    private double ruby = 0;
    @JsonProperty("created")
    private long created = System.currentTimeMillis();
    @JsonProperty("banned")
    private boolean banned = false;

    @JsonCreator
    public Profile(@Nullable Profile profile) {
        MySQLTable raw = new MySQLTask().buildTable();
        raw.prepare("playerProfiles",
                "uuid",
                "json");
        MySQLTable.fin table = raw.build();
        if(profile == null) {
            MySQLInsert insert = new MySQLTask().insert();
            insert.prepare(table,
                    uuid,
                    new TransformUtil().toJson(this));
            insert.execute();
        } else {
            uuid = profile.getUUID();
            originalUUID = profile.getOriginalUUID();
            hunger = profile.getHunger();
            health = profile.getHealth();
            location = profile.getLocation();
            inventoryItems = profile.getInventoryItems();
            armor = profile.getArmor();
            exp = profile.getExp();
            ruby = profile.getRuby();
            created = profile.getCreated();
            banned = profile.isBanned();
            skillTeam = profile.getSkillTeam();
            voicePack = profile.getVoicePack();
        }
    }

    public void setVoicePack(VoicePacks voicePack) {
        this.voicePack = voicePack;
    }

    public VoicePacks getVoicePack() {
        return voicePack;
    }

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

    public String[] getArmor() {
        return armor;
    }

    public String[] getInventoryItems() {
        return inventoryItems;
    }

    public ProfileLocation getLocation() {
        return location;
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

    public void setArmor(String[] armor) {
        this.armor = armor;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setHunger(double hunger) {
        this.hunger = hunger;
    }

    public void setInventoryItems(String[] inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void setLocation(ProfileLocation location) {
        this.location = location;
    }

    public void setRuby(double ruby) {
        this.ruby = ruby;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void save() {
        MySQLPush push = new MySQLTask().update();
        push.prepare("playerProfiles", "json", new TransformUtil().toJson(this));
        push.addRequirement("uuid", uuid);
        push.execute();
    }

}
