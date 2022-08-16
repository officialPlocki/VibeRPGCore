package world.axe.axecore.player;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import world.axe.axecore.util.TransformUtil;

@SuppressWarnings("unused")
public class SoundSettings {

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
    public SoundSettings(SoundSettings settings) {
        if(settings != null) {
            this.ambientMusic = settings.isAmbientMusic();
            this.damage = settings.isDamage();
            this.ui = settings.isUi();
            this.jump = settings.isJump();
            this.attack = settings.isAttack();
            this.breath = settings.isBreath();
            this.death = settings.isDeath();
            this.notifications = settings.isNotifications();
            this.otherUserSounds = settings.isOtherUserSounds();
            this.announce = settings.isAnnounce();
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

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public void setOtherUserSounds(boolean otherUserSounds) {
        this.otherUserSounds = otherUserSounds;
    }

    public void setUi(boolean ui) {
        this.ui = ui;
    }

    @Override
    public String toString() {
        return new TransformUtil().toJson(this);
    }
}
