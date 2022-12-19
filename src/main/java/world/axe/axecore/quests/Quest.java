package world.axe.axecore.quests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.ItemStack;
import world.axe.axecore.advancements.Advancement;
import world.axe.axecore.util.TransformUtil;

@AllArgsConstructor
public class Quest {

    @JsonProperty("objective")
    private String objective;
    @JsonProperty("questID")
    private String questID;
    @JsonProperty("triggerFinishedThroughMobID")
    private boolean triggerFinishedThroughMobID;
    @JsonProperty("triggerFinishedThroughItem")
    private boolean triggerFinishedThroughItem;
    @JsonProperty("triggerMobID_let_minus1_if_none")
    private int triggerMobID_let_minus1_if_none;
    @JsonProperty("triggerItem")
    private JsonObject triggerItem;
    @JsonProperty("triggerItemAmount")
    private int triggerItemAmount;
    @JsonProperty("questPercentage")
    private double questPercentage;
    @JsonProperty("sideQuest")
    private boolean sideQuest;
    @JsonProperty("questRequirement")
    private Quest[] questRequirement;
    @JsonProperty("advancementRequirement")
    private Advancement[] advancementRequirement;
    @JsonProperty("availableSideQuests")
    private Quest[] availableSideQuests;

    public Quest[] getAvailableSideQuests() {
        return availableSideQuests;
    }

    public String getQuestID() {
        return questID;
    }

    public void setAvailableSideQuests(Quest[] availableSideQuests) {
        this.availableSideQuests = availableSideQuests;
    }

    public void setQuestPercentage(double questPercentage) {
        this.questPercentage = questPercentage;
    }

    public boolean isSideQuest() {
        return sideQuest;
    }

    public Quest[] getQuestRequirement() {
        return questRequirement;
    }

    public Advancement[] getAdvancementRequirement() {
        return advancementRequirement;
    }

    public double getQuestPercentage() {
        return questPercentage;
    }

    public boolean isTriggerFinishedThroughItem() {
        return triggerFinishedThroughItem;
    }

    public int getTriggerItemAmount() {
        return triggerItemAmount;
    }

    public String getGetQuestID() {
        return questID;
    }

    public boolean isTriggerFinishedThroughMobID() {
        return triggerFinishedThroughMobID;
    }

    public ItemStack getTriggerItem() {
        return new TransformUtil().itemFromJson(triggerItem);
    }

    public String getObjective() {
        return objective;
    }

    public int getTriggerMobID_let_minus1_if_none() {
        return triggerMobID_let_minus1_if_none;
    }

}
