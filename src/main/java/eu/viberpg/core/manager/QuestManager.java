package eu.viberpg.core.manager;

import com.google.gson.JsonObject;
import eu.viberpg.core.quests.Quest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import eu.viberpg.core.advancements.Advancement;
import eu.viberpg.core.player.AXEPlayer;
import eu.viberpg.core.player.Profile;
import eu.viberpg.core.storage.mysql.function.MySQLInsert;
import eu.viberpg.core.storage.mysql.function.MySQLRequest;
import eu.viberpg.core.storage.mysql.function.MySQLResponse;
import eu.viberpg.core.storage.mysql.function.MySQLTable;
import eu.viberpg.core.util.TransformUtil;

import java.util.List;

public class QuestManager {

    private final MySQLTable.fin quests;
    private final MySQLTable.fin sideQuests;

    public QuestManager() {
        MySQLTable quests = new MySQLTable();
        quests.prepare("quests",
                "questID",
                "questJson");
        this.quests = quests.build();
        MySQLTable sideQuests = new MySQLTable();
        sideQuests.prepare("sideQuests",
                "questID",
                "questJson");
        this.sideQuests = sideQuests.build();
    }

    public @Nullable Quest getQuest(String questID) {
        MySQLRequest request = new MySQLRequest();
        if(questID.startsWith("s")) {
            request.prepare("questJson", "sideQuests");
        } else if(questID.startsWith("q")) {
            request.prepare("questJson", "quests");
        } else {
            return null;
        }
        request.addRequirement("questID", questID);
        MySQLResponse response = request.execute();
        if(response.isEmpty()) {
            return null;
        } else {
            return new TransformUtil().fromJson(response.getString("questJson"), Quest.class);
        }
    }

    public void setActiveQuest(Player player, Quest quest) {
        AXEPlayer ae = new AXEPlayer(player);
        Profile profile = ae.getActiveProfile();
        profile.setActiveQuest(quest);
        profile.save();
    }

    public int getQuestCount() {
        MySQLRequest request = new MySQLRequest();
        request.prepare("*", "quests");
        MySQLResponse response = request.execute();
        return !response.isEmpty() ? 0 : response.raw().size();
    }

    public int getSideQuestCount() {
        MySQLRequest request = new MySQLRequest();
        request.prepare("*", "sideQuests");
        MySQLResponse response = request.execute();
        return !response.isEmpty() ? 0 : response.raw().size();
    }

    public Quest createSideQuest(String objective, boolean triggerMobID, boolean triggerItem, int mobID, ItemStack item, int itemAmount, List<Quest> questRequirement, List<Advancement> advancementRequirement) {
        String obj = "s"+(getSideQuestCount() + 1);
        JsonObject jsonObject = new TransformUtil().itemToJson(item);

        Quest[] q = new Quest[questRequirement.size()];
        for(int i = 0; i < questRequirement.size(); i++) {
            q[i] = questRequirement.get(i);
        }
        Advancement[] a = new Advancement[advancementRequirement.size()];
        for(int i = 0; i < advancementRequirement.size(); i++) {
            a[i] = advancementRequirement.get(i);
        }
        Quest quest = new Quest(
                objective,
                obj,
                triggerMobID,
                triggerItem,
                mobID,
                jsonObject,
                itemAmount,
                0.0,
                true,
                q,
                a,
                new Quest[0]);
        String json = new TransformUtil().toJson(quest);
        MySQLInsert insert = new MySQLInsert();
        insert.prepare(sideQuests,
                obj,
                json);
        insert.execute();
        return quest;
    }

    public Quest createQuest(String objective, boolean triggerMobID, boolean triggerItem, int mobID, ItemStack item, int itemAmount, List<Quest> questRequirement, List<Advancement> advancementRequirement, List<Quest> sideQuests) {
        String obj = "q"+(getQuestCount() + 1);
        JsonObject jsonObject = new TransformUtil().itemToJson(item);

        Quest[] q = new Quest[questRequirement.size()];
        for(int i = 0; i < questRequirement.size(); i++) {
            q[i] = questRequirement.get(i);
        }
        Quest[] q1 = new Quest[sideQuests.size()];
        for(int i = 0; i < sideQuests.size(); i++) {
            q1[i] = sideQuests.get(i);
        }
        Advancement[] a = new Advancement[advancementRequirement.size()];
        for(int i = 0; i < advancementRequirement.size(); i++) {
            a[i] = advancementRequirement.get(i);
        }
        Quest quest = new Quest(
                objective,
                obj,
                triggerMobID,
                triggerItem,
                mobID,
                jsonObject,
                itemAmount,
                0.0,
                false,
                q,
                a,
                q1);
        String json = new TransformUtil().toJson(quest);
        MySQLInsert insert = new MySQLInsert();
        insert.prepare(quests,
                obj,
                json);
        insert.execute();
        return quest;
    }

}
